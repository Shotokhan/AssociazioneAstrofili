package astrofili.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import astrofili.entity.Articolo;
import astrofili.entity.Astronomo;
import astrofili.entity.OggettoCeleste;
import astrofili.enumeration.TipoOggetto;

public class ArticoloDAO {
	// TODO - gestire i diversi tipi di errore con delle eccezioni piuttosto che con dei return null
	
	public static Articolo create(Articolo articolo) throws SQLException {
		
		Connection conn = DBManager.getConnection();
		
		String query = "INSERT INTO ARTICOLO(TITOLO, CORPO, OGGETTOARTICOLO, DATAINSERIMENTO, NUMEROLIKE, "
				+ "NUMERODISLIKE, OGGETTOCELESTE, AUTORE) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		Astronomo autore = AstronomoDAO.read(articolo.getAutore().getIdAstronomo());
		if(autore != null) {
			stmt.setInt(8, articolo.getAutore().getIdAstronomo());
		} else {
			System.err.println("Author specified in the article not found in DB; abort transaction");
			return null;
		}
		
		OggettoCeleste oggetto = OggettoCelesteDAO.read(articolo.getOggettoCeleste().getIdOggetto());
		boolean newObject = false;
		if(oggetto != null) {
			stmt.setInt(7, articolo.getOggettoCeleste().getIdOggetto());
		} else try {
			oggetto = OggettoCelesteDAO.create(articolo.getOggettoCeleste());
			newObject = true;
			articolo.setOggettoCeleste(oggetto);
			stmt.setInt(7, articolo.getOggettoCeleste().getIdOggetto());
		} catch(SQLException e) {
			System.err.println("Creation of objects referenced by the article failed; abort transaction");
			return null;
		}
		
		stmt.setString(1, articolo.getTitolo());
		stmt.setString(2, articolo.getCorpo());
		stmt.setString(3, articolo.getOggettoArticolo());
		stmt.setDate(4, (Date) articolo.getDataInserimento());
		stmt.setInt(5, articolo.getNumeroLike());
		stmt.setInt(6, articolo.getNumeroDislike());
		
		try {
			stmt.executeUpdate();
			ResultSet result = stmt.getGeneratedKeys();
			if(result.next()) {
				articolo.setIdArticolo(result.getInt("IDARTICOLO"));
			}
		} catch(SQLException e) {
			System.err.println("Creation of article failed; abort transaction");
			if(newObject) {
				System.err.println("Rollback of creation of objects due to failure of creation of article");
				// TODO - rollback
			}
			return null;
		}
		return articolo;
	}
	
	public static Articolo read(int idArticolo) throws SQLException {
		Articolo articolo = new Articolo();
		Connection conn = DBManager.getConnection();
		
		String query = "SELECT * FROM ARTICOLO WHERE IDARTICOLO = ?;";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, idArticolo);
		
		ResultSet result = stmt.executeQuery();
		if(result.next()) {
			articolo.setIdArticolo(result.getInt("IDARTICOLO"));
			articolo.setTitolo(result.getString("TITOLO"));
			articolo.setCorpo(result.getString("CORPO"));
			articolo.setOggettoArticolo(result.getString("OGGETTOARTICOLO"));
			articolo.setDataInserimento(result.getDate("DATAINSERIMENTO"));
			articolo.setNumeroLike(result.getInt("NUMEROLIKE"));
			articolo.setNumeroDislike(result.getInt("NUMERODISLIKE"));
			articolo.setOggettoCeleste(OggettoCelesteDAO.read(result.getInt("OGGETTOCELESTE")));
			articolo.setAutore(AstronomoDAO.read(result.getInt("AUTORE")));
			articolo.setCommenti(CommentoDAO.read(0, articolo.getIdArticolo()));
		} else {
			return null;
		}
		
		conn.close();
		DBManager.closeConnection();
		
		return articolo;
	}
	
	public static ArrayList<Articolo> read(String titolo, TipoOggetto tipo) throws SQLException {
		
		ArrayList<Articolo> output = new ArrayList<Articolo>();
		Connection conn = DBManager.getConnection();
		
		String query;
		// c'è bisogno del join per risolvere il riferimento all'ogg. celeste
		String baseQuery = "SELECT * FROM ARTICOLO T1 JOIN OGGETTOCELESTE T2 "
							+ "ON T1.OGGETTOCELESTE = T2.IDOGGETTO";
		
		PreparedStatement stmt;
		
		// TODO: codice meno ridondante per fare queste operazioni
		
		if(titolo == null) {
			if(tipo == null) {
				query = baseQuery + ";";
				stmt = conn.prepareStatement(query);
			} else {
				query = baseQuery
						+ " WHERE T2.TIPOOGGETTOCELESTE = ?;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, tipo.toString());
			}
		} else {
			if(tipo == null) {
				query = baseQuery
						+ " WHERE UPPER(T1.TITOLO) LIKE ?;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, "%" + titolo.toUpperCase() + "%");
			} else {
				query = baseQuery
						+ " WHERE T2.TIPOOGGETTOCELESTE = ? "
						+ "AND UPPER(T1.TITOLO) LIKE ?;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, tipo.toString());
				stmt.setString(2, "%" + titolo.toUpperCase() + "%");
			}
		}
		
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			Articolo art = new Articolo(result.getString("TITOLO"), 
							OggettoCelesteDAO.read(result.getInt("IDOGGETTO")));
			art.setIdArticolo(result.getInt("IDARTICOLO"));
			art.setCorpo(result.getString("CORPO"));
			art.setOggettoArticolo(result.getString("OGGETTOARTICOLO"));
			art.setDataInserimento(result.getDate("DATAINSERIMENTO"));
			art.setNumeroLike(result.getInt("NUMEROLIKE"));
			art.setNumeroDislike(result.getInt("NUMERODISLIKE"));
			art.setAutore(AstronomoDAO.read(result.getInt("AUTORE")));
			art.setCommenti(CommentoDAO.read(0, result.getInt("IDARTICOLO")));
			output.add(art);
		}
		
		return output;
	}
	
	public static Articolo update(Articolo articolo) throws SQLException {
		Connection conn = DBManager.getConnection();
		
		// per evitare violazioni di vincoli d'integrità referenziale
		// non si può cambiare né l'autore né l'oggetto celeste
		String query = "UPDATE ARTICOLO SET "
				+ "TITOLO = ?, "
				+ "OGGETTOARTICOLO = ?, "
				+ "CORPO = ?, "
				+ "DATAINSERIMENTO = ?, "
				+ "NUMEROLIKE = ?, "
				+ "NUMERODISLIKE = ? "
				+ "WHERE IDARTICOLO = ?;";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, articolo.getTitolo());
		stmt.setString(2, articolo.getOggettoArticolo());
		stmt.setString(3, articolo.getCorpo());
		stmt.setDate(4, (Date) articolo.getDataInserimento());
		stmt.setInt(5, articolo.getNumeroLike());
		stmt.setInt(6, articolo.getNumeroDislike());
		stmt.setInt(7, articolo.getIdArticolo());
		
		try {
			stmt.executeUpdate();
		} catch(SQLException e) {
			articolo = ArticoloDAO.read(articolo.getIdArticolo());
		}
		return articolo;
	}
	
	public static Boolean delete(Articolo articolo) throws SQLException {
		
		Connection conn = DBManager.getConnection();
		String query = "DELETE FROM ARTICOLO WHERE IDARTICOLO = ?;";
		
		// in questo caso non c'è bisogno di operazione preliminare ed eventuale rollback: si usa il trigger del DB
		/*
		try {
			CommentoDAO.delete(0, articolo.getIdArticolo());
		} catch(SQLException e) {
			System.err.println("Delete of comments referencing this article failed; aborting transaction");
			return Boolean.FALSE;
		}
		*/
		
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, articolo.getIdArticolo());
		
		try {	
			int deleted = stmt.executeUpdate();
			if(!(deleted > 0)) {
				return Boolean.FALSE;
			} else {
				return Boolean.TRUE;
			}
			
		} catch(SQLException e) {
			System.err.println("Delete of article failed; abort transaction");
			return Boolean.FALSE;
		}
	}
}

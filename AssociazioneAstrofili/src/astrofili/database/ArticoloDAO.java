package astrofili.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import astrofili.entity.Articolo;
import astrofili.enumeration.TipoOggetto;


public class ArticoloDAO {
	// TODO - gestire i diversi tipi di errore con delle eccezioni piuttosto che con dei return null
	
	private Connection conn;
	private OggettoCelesteDAO interfacciaOggettiCelesti = new OggettoCelesteDAO();
	private AstronomoDAO interfacciaAstronomi = new AstronomoDAO();
	private CommentoDAO interfacciaCommenti = new CommentoDAO();
	
	public Articolo create(Articolo articolo) throws SQLException, DAOException {
		conn = DBManager.getConnection();
		
		String query = "INSERT INTO ARTICOLO(TITOLO, CORPO, OGGETTOARTICOLO, DATAINSERIMENTO, NUMEROLIKE, "
				+ "NUMERODISLIKE, OGGETTOCELESTE, AUTORE) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, articolo.getTitolo());
		stmt.setString(2, articolo.getCorpo());
		stmt.setString(3, articolo.getOggettoArticolo());
		stmt.setDate(4, (Date) articolo.getDataInserimento());
		stmt.setInt(5, articolo.getNumeroLike());
		stmt.setInt(6, articolo.getNumeroDislike());
		stmt.setInt(7, articolo.getOggettoCeleste().getIdOggetto());
		try {
			stmt.setInt(8, articolo.getAutore().getIdAstronomo());
		} catch(NullPointerException e) {
			// shouldn't arrive here
			stmt.setNull(8, java.sql.Types.INTEGER);
		}
		
		try {
			stmt.executeUpdate();
			ResultSet result = stmt.getGeneratedKeys();
			if(result.next()) {
				articolo.setIdArticolo(result.getInt("IDARTICOLO"));
			}
		} catch(SQLException e) {
			throw new DAOException("Creation of article failed; abort transaction");
		}
		return articolo;
	}
	
	public Articolo read(int idArticolo) throws SQLException {
		Articolo articolo = new Articolo();
		conn = DBManager.getConnection();
		
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
			try {
				articolo.setOggettoCeleste(interfacciaOggettiCelesti.read(result.getInt("OGGETTOCELESTE")));
			} catch (DAOException e) {
				articolo.setOggettoCeleste(null);
			}
			try {
				articolo.setAutore(interfacciaAstronomi.read(result.getInt("AUTORE")));
			} catch (DAOException e) {
				articolo.setAutore(null);
			}
			try {
				articolo.setCommenti(interfacciaCommenti.read(0, articolo.getIdArticolo()));
			} catch (DAOException e) {
				articolo.setCommenti(null);
			}
		} else {
			return null;
		}
		
		return articolo;
	}
	
	public List<Articolo> read(String titolo, TipoOggetto tipo) throws SQLException {
		
		ArrayList<Articolo> output = new ArrayList<Articolo>();
		conn = DBManager.getConnection();
		
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
			Articolo art;
			try {
				art = new Articolo(result.getString("TITOLO"), 
								interfacciaOggettiCelesti.read(result.getInt("IDOGGETTO")));
			} catch (DAOException e) {
				art = new Articolo(result.getString("TITOLO"), null);
			}
			art.setIdArticolo(result.getInt("IDARTICOLO"));
			art.setCorpo(result.getString("CORPO"));
			art.setOggettoArticolo(result.getString("OGGETTOARTICOLO"));
			art.setDataInserimento(result.getDate("DATAINSERIMENTO"));
			art.setNumeroLike(result.getInt("NUMEROLIKE"));
			art.setNumeroDislike(result.getInt("NUMERODISLIKE"));
			try {
				art.setAutore(interfacciaAstronomi.read(result.getInt("AUTORE")));
			} catch (DAOException e) {
				art.setAutore(null);
			}
			try {
				art.setCommenti(interfacciaCommenti.read(0, result.getInt("IDARTICOLO")));
			} catch (DAOException e) {
				// nothing to do
			}
			output.add(art);
		}
		
		return output;
	}
	
	public Articolo update(Articolo articolo) throws SQLException, DAOException {
		conn = DBManager.getConnection();
		
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
			articolo = this.read(articolo.getIdArticolo());
			throw new DAOException("Article not updated");
		}
		return articolo;
	}
	
	public Boolean delete(Articolo articolo) throws SQLException, DAOException {
		
		conn = DBManager.getConnection();
		String query = "DELETE FROM ARTICOLO WHERE IDARTICOLO = ?;";
		
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
			throw new DAOException("Delete of article failed; abort transaction");
		}
	}
}

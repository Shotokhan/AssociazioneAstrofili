package astrofili.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import astrofili.entity.Articolo;
import astrofili.entity.OggettoCeleste;
import astrofili.enumeration.TipoOggetto;

public class ArticoloDAO {
	
	public static Articolo create(Articolo articolo) throws SQLException {
		// TODO - requires OggettoCelesteDAO
		return null;
	}
	
	public static ArrayList<Articolo> read(String titolo, TipoOggetto tipo) throws SQLException {
		
		ArrayList<Articolo> output = new ArrayList<Articolo>();
		Connection conn = DBManager.getConnection();
		
		String query;
		// c'è bisogno del join per risolvere il riferimento all'ogg. celeste
		// ci vorrebbe inoltre un'altra query per la lista dei commenti
		// ed un'altra ancora per l'astronomo
		// TODO - requires CommentoDAO and AstronomoDAO
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
			// TODO - copia di tutti i campi
			Articolo art = new Articolo(result.getString("TITOLO"), 
						new OggettoCeleste(result.getString("NOME"), 
						TipoOggetto.valueOf(result.getString("TIPOOGGETTOCELESTE"))));
			output.add(art);
		}
		
		return output;
	}
	
	public static Articolo update(Articolo articolo) throws SQLException {
		// TODO
		return null;
	}
	
	public static Boolean delete(Articolo articolo) throws SQLException {
		// TODO
		return false;
	}
}

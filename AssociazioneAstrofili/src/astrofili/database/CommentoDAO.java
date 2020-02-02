package astrofili.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import astrofili.entity.Commento;

public class CommentoDAO {
	
	private Connection conn;
	
	public Commento create(Commento commento) throws SQLException {
		// TODO
		return null;
	}
	
	public Commento read(int idCommento) throws SQLException, DAOException {
		// TODO
		return null;
	}
	
	public List<Commento> read(int idAstronomo, int idArticolo) throws SQLException, DAOException {
		// TODO - simile a read(titolo, tipo) di ArticoloDAO, però uso gli id
		// visto che int non può essere null, per convenzione lo 0 indica l'assenza del filtro di ricerca
		return null;
	}
	
	public Commento update(Commento commento) throws SQLException {
		// TODO
		return null;
	}
	
	public Boolean delete(int idAstronomo, int idArticolo) throws SQLException {
		// TODO - come il read
		return null;
	}
	
	public static Boolean delete(Commento commento) throws SQLException {
		// TODO
		return null;
	}
}

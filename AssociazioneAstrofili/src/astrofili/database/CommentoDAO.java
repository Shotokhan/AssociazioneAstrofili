package astrofili.database;

import java.sql.SQLException;
import java.util.ArrayList;


import astrofili.entity.Commento;

public class CommentoDAO {
	
	public static Commento create(Commento commento) throws SQLException {
		// TODO
		return null;
	}
	
	public static Commento read(int idCommento) throws SQLException {
		// TODO
		return null;
	}
	
	public static ArrayList<Commento> read(int idAstronomo, int idArticolo) throws SQLException {
		// TODO - simile a read(titolo, tipo) di ArticoloDAO, però uso gli id
		// visto che int non può essere null, per convenzione lo 0 indica l'assenza del filtro di ricerca
		return null;
	}
	
	public static Commento update(Commento commento) throws SQLException {
		// TODO
		return null;
	}
	
	public static Boolean delete(int idAstronomo, int idArticolo) throws SQLException {
		// TODO - come il read
		return null;
	}
	
	public static Boolean delete(Commento commento) throws SQLException {
		// TODO
		return null;
	}
}

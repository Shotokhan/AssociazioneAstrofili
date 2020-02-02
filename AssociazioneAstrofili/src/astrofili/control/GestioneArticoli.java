package astrofili.control;

import java.sql.SQLException;
import java.util.List;

import astrofili.database.ArticoloDAO;
import astrofili.entity.Articolo;
import astrofili.enumeration.TipoOggetto;

public class GestioneArticoli {
	
	private ArticoloDAO interfacciaArticoli = new ArticoloDAO();
	
	public List<Articolo> ricercaArticolo(String titolo, TipoOggetto tipoOggetto) throws SQLException {
		return interfacciaArticoli.read(titolo, tipoOggetto);
	}
	
	public static void votaArticolo() {
		// TODO
	}
	
	public static void commentaArticolo() {
		// TODO
	}
	
	public static void aggiungiArticolo() {
		// TODO
	}
	
	public static void modificaArticolo() {
		// TODO
	}
	
	public static void rimuoviArticolo() {
		// TODO
	}
}

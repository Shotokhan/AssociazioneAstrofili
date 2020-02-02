package astrofili.control;

import java.sql.SQLException;
import java.util.List;

import astrofili.database.OggettoCelesteDAO;
import astrofili.entity.OggettoCeleste;

public class GestioneOggettiCelesti {

	private OggettoCelesteDAO interfacciaOggettiCelesti;
	
	public List<OggettoCeleste> ricercaOggettiCelesti(OggettoCeleste oggetto) throws SQLException{
		return (List<OggettoCeleste>) interfacciaOggettiCelesti.read(oggetto);
	}
	
	public OggettoCeleste selezionaOggettoCeleste(int idOggetto) throws SQLException {
		return interfacciaOggettiCelesti.read(idOggetto);
	}
	
	public OggettoCeleste aggiungiOggettoCeleste(OggettoCeleste oggetto) throws SQLException {
		return interfacciaOggettiCelesti.create(oggetto);
	}
}

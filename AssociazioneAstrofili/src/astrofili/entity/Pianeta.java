package astrofili.entity;

import java.util.ArrayList;
import java.util.List;

public class Pianeta extends OggettoCeleste{

	private SistemaPlanetario sistemaPlanetarioDiAppartenenza = null;
	private ArrayList<SatelliteNaturale> listaSatelliti = null;
	
	public Pianeta(String nome) {
		super(nome);
	}

	public Pianeta(String nome, int idOggetto, Galassia galassiaDiAppartenenza, SistemaPlanetario
			sistemaPlanetarioDiAppartenenza, SistemaStellare centroStellare, SistemaStellare 
			sistemaStellareDiAppartenenza) 
	{
		super(nome, idOggetto, galassiaDiAppartenenza, sistemaPlanetarioDiAppartenenza, centroStellare, sistemaStellareDiAppartenenza);
		this.setSistemaPlanetarioDiAppartenenza(sistemaPlanetarioDiAppartenenza);
	}
	
	public Pianeta(OggettoCeleste other) {
		super(other);
	}

	public SistemaPlanetario getSistemaPlanetarioDiAppartenenza() {
		return sistemaPlanetarioDiAppartenenza;
	}

	public void setSistemaPlanetarioDiAppartenenza(SistemaPlanetario sistemaPlanetarioDiAppartenenza) {
		this.sistemaPlanetarioDiAppartenenza = sistemaPlanetarioDiAppartenenza;
	}

	public List<SatelliteNaturale> getListaSatelliti() {
		return listaSatelliti;
	}

	public void setListaSatelliti(List<SatelliteNaturale> listaSatelliti) {
		this.listaSatelliti = (ArrayList<SatelliteNaturale>) listaSatelliti;
	}

}

package astrofili.entity;

import java.util.ArrayList;
import java.util.List;

public class Galassia extends OggettoCeleste {

	private ArrayList<Sistema> listaSistemi;
	
	public Galassia(String nome) {
		super(nome);
		// TODO Stub di costruttore generato automaticamente
	}

	public Galassia(String nome, int idOggetto, Galassia galassiaDiAppartenenza, SistemaPlanetario
			sistemaPlanetarioDiAppartenenza, SistemaStellare centroStellare, SistemaStellare 
			sistemaStellareDiAppartenenza) 
	{
		super(nome, idOggetto, galassiaDiAppartenenza, sistemaPlanetarioDiAppartenenza, centroStellare, sistemaStellareDiAppartenenza);
	}
	
	public Galassia(OggettoCeleste other) {
		super(other);
		// TODO Stub di costruttore generato automaticamente
	}

	public List<Sistema> getListaSistemi() {
		return listaSistemi;
	}

	public void setListaSistemi(List<Sistema> listaSistemi) {
		this.listaSistemi = (ArrayList<Sistema>) listaSistemi;
	}

}

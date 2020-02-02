package astrofili.entity;

import java.util.ArrayList;
import java.util.List;

public class SistemaPlanetario extends Sistema {

	private SistemaStellare centroStellare = null;
	private ArrayList<Pianeta> listaPianeti = new ArrayList<Pianeta>();
	
	public SistemaPlanetario(String nome) {
		super(nome);
	}

	public SistemaPlanetario(String nome, int idOggetto, Galassia galassiaDiAppartenenza, SistemaPlanetario
			sistemaPlanetarioDiAppartenenza, SistemaStellare centroStellare, SistemaStellare 
			sistemaStellareDiAppartenenza) 
	{
		super(nome, idOggetto, galassiaDiAppartenenza, sistemaPlanetarioDiAppartenenza, centroStellare, sistemaStellareDiAppartenenza);
		this.setCentroStellare(centroStellare);
	}
	
	public SistemaPlanetario(Sistema other) {
		super(other);
	}

	public List<Pianeta> getListaPianeti() {
		return listaPianeti;
	}

	public void setListaPianeti(List<Pianeta> list) {
		this.listaPianeti = (ArrayList<Pianeta>) list;
	}

	public SistemaStellare getCentroStellare() {
		return centroStellare;
	}

	public void setCentroStellare(SistemaStellare centroStellare) {
		this.centroStellare = centroStellare;
	}
	
}

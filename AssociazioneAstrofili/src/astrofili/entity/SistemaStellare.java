package astrofili.entity;

import java.util.ArrayList;
import java.util.List;

public class SistemaStellare extends Sistema {

	private ArrayList<Stella> listaStelle = new ArrayList<Stella>();
	
	public SistemaStellare(String nome) {
		super(nome);
	}

	public SistemaStellare(String nome, int idOggetto, Galassia galassiaDiAppartenenza, SistemaPlanetario
			sistemaPlanetarioDiAppartenenza, SistemaStellare centroStellare, SistemaStellare 
			sistemaStellareDiAppartenenza) 
	{
		super(nome, idOggetto, galassiaDiAppartenenza, sistemaPlanetarioDiAppartenenza, centroStellare, sistemaStellareDiAppartenenza);
	}
	
	public SistemaStellare(Sistema other) {
		super(other);
	}

	public List<Stella> getListaStelle() {
		return listaStelle;
	}

	public void setListaStelle(List<Stella> listaStelle) {
		this.listaStelle = (ArrayList<Stella>) listaStelle;
	}

}

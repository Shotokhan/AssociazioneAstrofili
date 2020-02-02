package astrofili.entity;

public class Stella extends OggettoCeleste {

	private SistemaStellare sistemaStellareDiAppartenenza = null;
	
	public Stella(String nome) {
		super(nome);
	}

	public Stella(String nome, int idOggetto, Galassia galassiaDiAppartenenza, SistemaPlanetario
			sistemaPlanetarioDiAppartenenza, SistemaStellare centroStellare, SistemaStellare 
			sistemaStellareDiAppartenenza) 
	{
		super(nome, idOggetto, galassiaDiAppartenenza, sistemaPlanetarioDiAppartenenza, centroStellare, sistemaStellareDiAppartenenza);
		this.setSistemaStellareDiAppartenenza(sistemaStellareDiAppartenenza);
	}
	
	public Stella(OggettoCeleste other) {
		super(other);
	}

	public SistemaStellare getSistemaStellareDiAppartenenza() {
		return sistemaStellareDiAppartenenza;
	}

	public void setSistemaStellareDiAppartenenza(SistemaStellare sistemaStellareDiAppartenenza) {
		this.sistemaStellareDiAppartenenza = sistemaStellareDiAppartenenza;
	}

}

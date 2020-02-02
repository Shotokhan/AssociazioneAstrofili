package astrofili.entity;

public class Sistema extends OggettoCeleste{

	private Galassia galassiaDiAppartenenza = null;
	
	public Sistema(String nome) {
		super(nome);
	}

	public Sistema(String nome, int idOggetto, Galassia galassiaDiAppartenenza, SistemaPlanetario
			sistemaPlanetarioDiAppartenenza, SistemaStellare centroStellare, SistemaStellare 
			sistemaStellareDiAppartenenza) 
	{
		super(nome, idOggetto, galassiaDiAppartenenza, sistemaPlanetarioDiAppartenenza, centroStellare, sistemaStellareDiAppartenenza);
		this.setGalassiaDiAppartenenza(galassiaDiAppartenenza);
	}
	
	public Sistema(OggettoCeleste other) {
		super(other);
	}

	public Galassia getGalassiaDiAppartenenza() {
		return galassiaDiAppartenenza;
	}

	public void setGalassiaDiAppartenenza(Galassia galassiaDiAppartenenza) {
		this.galassiaDiAppartenenza = galassiaDiAppartenenza;
	}

}

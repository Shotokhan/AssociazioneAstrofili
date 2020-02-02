package astrofili.entity;

public class OggettoCeleste {

	private String nome;
	private int idOggetto = 0;
	
	public OggettoCeleste(String nome) {
		this.setNome(nome);
	}
	
	public OggettoCeleste(String nome, int idOggetto, Galassia galassiaDiAppartenenza, SistemaPlanetario
			sistemaPlanetarioDiAppartenenza, SistemaStellare centroStellare, SistemaStellare 
			sistemaStellareDiAppartenenza) 
	{
		this.setIdOggetto(idOggetto);
		this.setNome(nome);
	}
	
	public OggettoCeleste(OggettoCeleste other) {
		this.setNome(other.getNome());
		this.setIdOggetto(other.getIdOggetto());
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdOggetto() {
		return idOggetto;
	}

	public void setIdOggetto(int idOggetto) {
		this.idOggetto = idOggetto;
	}
}

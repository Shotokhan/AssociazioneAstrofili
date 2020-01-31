package astrofili.entity;

public class SatelliteNaturale {
	
	private String nome;
	private OggettoCeleste pianeta;
	private int idSatellite;
	
	public SatelliteNaturale(String nome, OggettoCeleste pianeta) {
		this.nome = nome;
		this.pianeta = pianeta;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public OggettoCeleste getPianeta() {
		return pianeta;
	}
	
	public void setPianeta(OggettoCeleste pianeta) {
		this.pianeta = pianeta;
	}
	
	public int getIdSatellite() {
		return idSatellite;
	}
	
	public void setIdSatellite(int idSatellite) {
		this.idSatellite = idSatellite;
	}
	
}

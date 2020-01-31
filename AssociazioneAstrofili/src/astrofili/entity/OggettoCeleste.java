package astrofili.entity;

import astrofili.enumeration.TipoOggetto;

public class OggettoCeleste {

	private String nome;
	private TipoOggetto tipoOggettoCeleste;
	private OggettoCeleste galassiaDiAppartenenza = null;
	private OggettoCeleste sistemaPlanetarioDiAppartenenza = null;
	private OggettoCeleste centroStellare = null;
	private int idOggetto = 0;
	
	public OggettoCeleste(String nome, TipoOggetto tipoOggettoCeleste) {
		this.setNome(nome);
		this.setTipoOggettoCeleste(tipoOggettoCeleste);
	}
	
	public OggettoCeleste(OggettoCeleste other) {
		this.setNome(other.getNome());
		this.setTipoOggettoCeleste(other.getTipoOggettoCeleste());
		this.setGalassiaDiAppartenenza(other.getGalassiaDiAppartenenza());
		this.setSistemaPlanetarioDiAppartenenza(other.getSistemaPlanetarioDiAppartenenza());
		this.setCentroStellare(other.getCentroStellare());
		this.setIdOggetto(other.getIdOggetto());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoOggetto getTipoOggettoCeleste() {
		return tipoOggettoCeleste;
	}

	public void setTipoOggettoCeleste(TipoOggetto tipoOggettoCeleste) {
		this.tipoOggettoCeleste = tipoOggettoCeleste;
	}

	public OggettoCeleste getGalassiaDiAppartenenza() {
		return galassiaDiAppartenenza;
	}

	public void setGalassiaDiAppartenenza(OggettoCeleste galassiaDiAppartenenza) {
		this.galassiaDiAppartenenza = galassiaDiAppartenenza;
	}

	public OggettoCeleste getSistemaPlanetarioDiAppartenenza() {
		return sistemaPlanetarioDiAppartenenza;
	}

	public void setSistemaPlanetarioDiAppartenenza(OggettoCeleste sistemaPlanetarioDiAppartenenza) {
		this.sistemaPlanetarioDiAppartenenza = sistemaPlanetarioDiAppartenenza;
	}

	public OggettoCeleste getCentroStellare() {
		return centroStellare;
	}

	public void setCentroStellare(OggettoCeleste centroStellare) {
		this.centroStellare = centroStellare;
	}

	public int getIdOggetto() {
		return idOggetto;
	}

	public void setIdOggetto(int idOggetto) {
		this.idOggetto = idOggetto;
	}
}

package astrofili.entity;

import java.util.Date;

public class Astronomo {
	
	private String nome;
	private String cognome;
	private Date dataNascita;
	private String email;
	private String descrizionePersonale;
	private String titolo;
	private String professione;
	private int idAstronomo;
	
	public Astronomo(String nome, String cognome, Date dataNascita, String email, String descrizionePersonale,
			String titolo, String professione) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.email = email;
		this.descrizionePersonale = descrizionePersonale;
		this.titolo = titolo;
		this.professione = professione;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public Date getDataNascita() {
		return dataNascita;
	}
	
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDescrizionePersonale() {
		return descrizionePersonale;
	}
	
	public void setDescrizionePersonale(String descrizionePersonale) {
		this.descrizionePersonale = descrizionePersonale;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public String getProfessione() {
		return professione;
	}
	
	public void setProfessione(String professione) {
		this.professione = professione;
	}
	
	public int getIdAstronomo() {
		return idAstronomo;
	}
	
	public void setIdAstronomo(int idAstronomo) {
		this.idAstronomo = idAstronomo;
	}
	
}

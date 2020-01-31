package astrofili.entity;

public class Commento {
	
	private String titolo;
	private String corpo;
	private Astronomo autore;
	private int idCommento;
	
	public Commento(String titolo, String corpo, Astronomo autore) {
		this.titolo = titolo;
		this.corpo = corpo;
		this.autore = autore;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public String getCorpo() {
		return corpo;
	}
	
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	
	public Astronomo getAutore() {
		return autore;
	}
	
	public void setAutore(Astronomo autore) {
		this.autore = autore;
	}

	public int getIdCommento() {
		return idCommento;
	}

	public void setIdCommento(int idCommento) {
		this.idCommento = idCommento;
	}
	
}

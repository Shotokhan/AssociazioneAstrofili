package astrofili.entity;

import java.util.ArrayList;
import java.util.Date;

public class Articolo {
	
	private String titolo;
	private String oggettoArticolo = null;
	private String corpo = null;
	private Date dataInserimento = null;
	private int numeroLike = 0;
	private int numeroDislike = 0;
	private OggettoCeleste oggettoCeleste;
	private ArrayList<Commento> commenti = new ArrayList<Commento>();
	private Astronomo autore = null;
	private int idArticolo = 0;
	
	public Articolo(String titolo, OggettoCeleste oggettoCeleste) {
		this.setTitolo(titolo);
		this.setOggettoCeleste(oggettoCeleste);
	}

	public Articolo(Articolo other) {
		this.setTitolo(other.getTitolo());
		this.setOggettoArticolo(other.getOggettoArticolo());
		this.setCorpo(other.getCorpo());
		this.setDataInserimento(other.getDataInserimento());
		this.setNumeroLike(other.getNumeroLike());
		this.setNumeroDislike(other.getNumeroDislike());
		this.setOggettoCeleste(other.getOggettoCeleste());
		this.setCommenti(other.getCommenti());
		this.setAutore(other.getAutore());
		this.setIdArticolo(other.getIdArticolo());
	}
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getOggettoArticolo() {
		return oggettoArticolo;
	}

	public void setOggettoArticolo(String oggettoArticolo) {
		this.oggettoArticolo = oggettoArticolo;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	
	public int like() {
		this.numeroLike += 1;
		return numeroLike;
	}
	
	public int dislike() {
		this.numeroDislike += 1;
		return numeroDislike;
	}

	public OggettoCeleste getOggettoCeleste() {
		return oggettoCeleste;
	}

	public void setOggettoCeleste(OggettoCeleste oggettoCeleste) {
		this.oggettoCeleste = oggettoCeleste;
	}
	
	public Commento addComment(Commento comment) {
		this.commenti.add(comment);
		return comment;
	}
	
	public Boolean removeComment(Commento comment) {
		return this.commenti.remove(comment);
	}

	public Astronomo getAutore() {
		return autore;
	}

	public void setAutore(Astronomo autore) {
		this.autore = autore;
	}

	public int getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(int idArticolo) {
		this.idArticolo = idArticolo;
	}

	public int getNumeroLike() {
		return numeroLike;
	}

	public void setNumeroLike(int numeroLike) {
		this.numeroLike = numeroLike;
	}

	public int getNumeroDislike() {
		return numeroDislike;
	}

	public void setNumeroDislike(int numeroDislike) {
		this.numeroDislike = numeroDislike;
	}

	public ArrayList<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(ArrayList<Commento> commenti) {
		this.commenti = commenti;
	}
}

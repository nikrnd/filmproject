package com.nicolasdelton.films.film;

/**
 * Classe per la creazione e gestione di ogeetti di tipo "Film"
 * @author nicolasdelton
 */
public class Film {
	private int codice;
	private String titolo;
	private String trama;

	/**
	 * Costruttore per la creazione e inizializzatone di un oggetto di tipo "Film"
	 * @param codice Codice del film
	 * @param titolo Titolo del film
	 * @param trama Trama del film
	 */
	public Film(int codice, String titolo, String trama) {
		this.codice = codice;
		this.titolo = titolo;
		this.trama = trama;
	}

	/**
	 * Costruttore di copia
	 * @param film Film da copiare
	 */
	public Film(Film film) {
		codice = film.codice;
		titolo = film.titolo;
		trama = film.trama;
	}

	/**
	 * Per calcolare il costo del film in base ai giorni
	 * @param giorni Giorni di noleggio
	 * @return Prezzo del noleggio
	 */
	public int calcoloCosto(int giorni) {
		return giorni * 2;
	}

	/**
	 * Per confrontare due film
	 * @param film Film da confrontare
	 * @return <ul><li>[true] sono uguali</li><li>[false] sono diverse</li></ul>
	 */
	public boolean equals(Film film) {
		return codice == film.codice;
	}

	/**
	 * Restituisce il codice del film
	 * @return Codice del film
	 */
	public int getCodice() {
		return codice;
	}

	/**
	 * Inserisce il codice del film
	 * @param codice Codice del film
	 */
	public void setCodice(int codice) {
		this.codice = codice;
	}

	/**
	 * Restituisce il titolo del film
	 * @return Titolo del film
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * Inserisce il titolo del film
	 * @param titolo Titolo del film
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * Restituisce la trama del film
	 * @return Trama del film
	 */
	public String getTrama() {
		return trama;
	}

	/**
	 * Inserisce la trama del film
	 * @param trama Trama del film
	 */
	public void setTrama(String trama) {
		this.trama = trama;
	}
}

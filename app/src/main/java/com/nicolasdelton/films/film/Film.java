package com.nicolasdelton.films.film;

public class Film {
	private int codice;
	private String titolo;
	private String trama;
	
	public Film(int codice, String titolo, String trama) {
		this.codice = codice;
		this.titolo = titolo;
		this.trama = trama;
	}
	
	public Film(Film film) {
		codice = film.codice;
		titolo = film.titolo;
		trama = film.trama;
	}
	
	public int calcoloCosto(int giorni) {
		return giorni * 2;
	}
	
	public boolean equals(Film film) {
		return codice == film.codice;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTrama() {
		return trama;
	}

	public void setTrama(String trama) {
		this.trama = trama;
	}
	
	
}

package com.nicolasdelton.films.film;

public class Noleggio {
	private Film film;
	private int idCliente;
	private int giorni;
	private int giorniRitardo;
	
	public Noleggio(Film film, int idCliente, int giorni, int giorniRitardo) {
		this.film = film;
		this.idCliente = idCliente;
		this.giorni = giorni;
		this.giorniRitardo = giorniRitardo;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getGiorni() {
		return giorni;
	}

	public void setGiorni(int giorni) {
		this.giorni = giorni;
	}

	public int getGiorniRitardo() {
		return giorniRitardo;
	}

	public void setGiorniRitardo(int giorniRitardo) {
		this.giorniRitardo = giorniRitardo;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}
	
}

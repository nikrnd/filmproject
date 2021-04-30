package com.nicolasdelton.films.film;

/**
 * Classe per la creazione e gestione di oggetti di tipo "Noleggio"
 * @author nicolasdelton
 * @see Film
 */
public class Noleggio {
	private Film film;
	private int idCliente;
	private int giorni;
	private int giorniRitardo;

	/**
	 * Costruttore per la creazione ed inizialiazzazione di un oggetto di tipo "Noleggio"
	 * @param film Oggetto di tipo "Film" da noleggiare
	 * @param idCliente Id del cliente che noleggia
	 * @param giorni Giorni di noleggio
	 * @param giorniRitardo Giorni di ritardo nel noleggio
	 */
	public Noleggio(Film film, int idCliente, int giorni, int giorniRitardo) {
		this.film = film;
		this.idCliente = idCliente;
		this.giorni = giorni;
		this.giorniRitardo = giorniRitardo;
	}

	/**
	 * Restituisce l'id del cliente
	 * @return Id del cliente
	 */
	public int getIdCliente() {
		return idCliente;
	}

	/**
	 * Inserisce l'id del cliente
	 * @param idCliente Id del cliente
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Restituisce i giorni di noleggio
	 * @return Giorni di noleggio
	 */
	public int getGiorni() {
		return giorni;
	}

	/**
	 * Inserisce i giorni di noleggio
	 * @param giorni Giorni di noleggio
	 */
	public void setGiorni(int giorni) {
		this.giorni = giorni;
	}

	/**
	 * Restituisce i giorni di ritardo
	 * @return Giorni di ritardo
	 */
	public int getGiorniRitardo() {
		return giorniRitardo;
	}

	/**
	 * Inserisce i giorni di ritardo
	 * @param giorniRitardo Giorni di ritardo
	 */
	public void setGiorniRitardo(int giorniRitardo) {
		this.giorniRitardo = giorniRitardo;
	}

	/**
	 * Restituisce il film noleggiato
	 * @return Film noleggiato
	 */
	public Film getFilm() {
		return film;
	}

	/**
	 * Inserisce il film noelggiato
	 * @param film Film noleggiato
	 */
	public void setFilm(Film film) {
		this.film = film;
	}

}

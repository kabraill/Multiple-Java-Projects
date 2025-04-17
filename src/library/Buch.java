/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

/**
 *
 * @author kabra
 */
public class Buch {
	public String titel;
	public String autor;
	public String isbn;
	public int jahr;
	public String genre;
	public boolean verfuegbar;

	public Buch(String titel, String autor, String isbn, int jahr, String genre, boolean verfuegbar) {
		this.titel = titel;
		this.autor = autor;
		this.isbn = isbn;
		this.jahr = jahr;
		this.genre = genre;
		this.verfuegbar = verfuegbar;
	}


	public String getTitel() {
		return titel;
	}

	public String getAutor() {
		return autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public int getJahr() {
		return jahr;
	}

	public String getGenre() {
		return genre;
	}

	public boolean isVerfuegbar() {
		return verfuegbar;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setVerfuegbar(boolean verfuegbar) {
		this.verfuegbar = verfuegbar;
	}

	@Override
	public String toString() {
		return "Buch{" +
				"titel='" + titel + '\'' +
				", autor='" + autor + '\'' +
				", isbn='" + isbn + '\'' +
				", jahr=" + jahr +
				", genre='" + genre + '\'' +
				", verfuegbar=" + verfuegbar +
				'}';
	}


}

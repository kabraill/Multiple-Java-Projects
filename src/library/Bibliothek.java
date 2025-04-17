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


import java.util.ArrayList;
import java.util.List;


public class Bibliothek {
	public List<Buch> buecherListe;

	public Bibliothek(List<Buch> buecherListe) {
		this.buecherListe = buecherListe;
	}

	public List<Buch> filterNachJahr(int jahr) {
		List<Buch> gefilterteBuecher = new ArrayList<>();
		for (Buch buch : buecherListe) {
			if (buch.getJahr() == jahr) {
				gefilterteBuecher.add(buch);
			}
		}
		return gefilterteBuecher;
	}

	public List<Buch> filterNachGenre(String genre) {
		List<Buch> gefilterteBuecher = new ArrayList<>();
		for (Buch buch : buecherListe) {
			if (buch.getGenre().equalsIgnoreCase(genre)) {
				gefilterteBuecher.add(buch);
			}
		}
		return gefilterteBuecher;
	}

	public List<Buch> filterVerfuegbareBuecher() {
		List<Buch> gefilterteBuecher = new ArrayList<>();
		for (Buch buch : buecherListe) {
			if (buch.isVerfuegbar()) {
				gefilterteBuecher.add(buch);
			}
		}
		return gefilterteBuecher;
	}

	public List<Buch> sucheBuecher(String genre, int jahr, boolean verfuegbar) {
		List<Buch> gefilterteBuecher = new ArrayList<>();
		for (Buch buch : buecherListe) {
			if (buch.getGenre().equalsIgnoreCase(genre) &&
					buch.getJahr() == jahr &&
					buch.isVerfuegbar()) {
				gefilterteBuecher.add(buch);
			}
		}
		return gefilterteBuecher;
	}
}
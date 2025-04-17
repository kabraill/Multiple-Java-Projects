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
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class BibliotheksMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static Bibliothek bibliothek;

    public static void main(String[] args) {
        List<Buch> buchListe = new ArrayList<>();
        bibliothek = new Bibliothek(buchListe);

        boolean weiter = true;
        while (weiter) {
            System.out.println("*************** Bibliotheks-Menu ***************");
            System.out.println("1. Buch hinzufügen");
            System.out.println("2. Bücher suchen");
            System.out.println("3. Bücher filtern");
            System.out.println("4. Buch aktualisieren");
            System.out.println("5. Alle Bücher anzeigen");
            System.out.println("6. Beenden");
            System.out.println("*************************************************");
            System.out.print("Wählen Sie eine Option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Leere den Scanner-Puffer

            switch (option) {
                case 1:
                    addBook();
                    break;
                case 2:
                    searchBooks();
                    break;
                case 3:
                    filterBooks();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    displayAllBooks();
                    break;
                case 6:
                    weiter = false;
                    break;
                default:
                    System.out.println("Ungültige Option. Bitte wählen Sie erneut.");
            }
        }
        scanner.close();
    }

    private static void addBook() {
        System.out.println("Buch hinzufügen:");
        System.out.print("Titel: ");
        String titel = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Jahr: ");
        int jahr = scanner.nextInt();
        scanner.nextLine(); // Leere den Scanner-Puffer
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Verfügbar (true/false): ");
        boolean verfuegbar = scanner.nextBoolean();
        scanner.nextLine(); // Leere den Scanner-Puffer

        Buch neuesBuch = new Buch(titel, autor, isbn, jahr, genre, verfuegbar);
        bibliothek.buecherListe.add(neuesBuch);
        System.out.println("Buch wurde hinzugefügt.");
    }

    private static void searchBooks() {
        System.out.print("Nach welchem Genre möchten Sie suchen? (Leer lassen, um alle Bücher anzuzeigen): ");
        String genre = scanner.nextLine();
        System.out.print("Nach welchem Jahr möchten Sie suchen? (0, um nicht zu filtern): ");
        int jahr = scanner.nextInt();
        scanner.nextLine(); // Leere den Scanner-Puffer
        System.out.print("Nur verfügbare Bücher anzeigen? (true/false): ");
        boolean verfuegbar = scanner.nextBoolean();
        scanner.nextLine(); // Leere den Scanner-Puffer

        List<Buch> gefilterteBuecher = bibliothek.sucheBuecher(genre, jahr, verfuegbar);
        if (gefilterteBuecher.isEmpty()) {
            System.out.println("Keine Bücher gefunden.");
        } else {
            System.out.println("Gefundene Bücher:");
            for (Buch buch : gefilterteBuecher) {
                System.out.println(buch);
            }
        }
    }

    private static void filterBooks() {
        System.out.print("Nach welchem Jahr möchten Sie filtern? (0, um nicht zu filtern): ");
        int jahr = scanner.nextInt();
        scanner.nextLine(); // Leere den Scanner-Puffer

        List<Buch> gefilterteBuecher = bibliothek.filterNachJahr(jahr);
        if (gefilterteBuecher.isEmpty()) {
            System.out.println("Keine Bücher gefunden.");
        } else {
            System.out.println("Gefilterte Bücher:");
            for (Buch buch : gefilterteBuecher) {
                System.out.println(buch);
            }
        }
    }

    private static void updateBook() {
        System.out.println("Buch aktualisieren:");
        System.out.print("Geben Sie die ISBN des zu aktualisierenden Buchs ein: ");
        String isbn = scanner.nextLine();
        boolean found = false;
        for (Buch buch : bibliothek.buecherListe) {
            if (buch.getIsbn().equals(isbn)) {
                System.out.print("Neuer Titel: ");
                buch.setTitel(scanner.nextLine());
                System.out.print("Neuer Autor: ");
                buch.setAutor(scanner.nextLine());
                System.out.print("Neue ISBN: ");
                buch.setIsbn(scanner.nextLine());
                System.out.print("Neues Jahr: ");
                buch.setJahr(scanner.nextInt());
                scanner.nextLine(); // Leere den Scanner-Puffer
                System.out.print("Neues Genre: ");
                buch.setGenre(scanner.nextLine());
                System.out.print("Verfügbarkeit aktualisieren? (true/false): ");
                buch.setVerfuegbar(scanner.nextBoolean());
                scanner.nextLine(); // Leere den Scanner-Puffer
                System.out.println("Buch wurde aktualisiert.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Buch mit der angegebenen ISBN nicht gefunden.");
        }
    }

    private static void displayAllBooks() {
        if (bibliothek.buecherListe.isEmpty()) {
            System.out.println("Die Bibliothek enthält keine Bücher.");
        } else {
            System.out.println("Alle Bücher:");
            for (Buch buch : bibliothek.buecherListe) {
                System.out.println(buch);
            }
        }
    }
}


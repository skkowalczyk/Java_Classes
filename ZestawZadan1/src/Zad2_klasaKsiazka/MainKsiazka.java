package Zad2_klasaKsiazka;

class Ksiazka {
    String tytul;
    String autor;

    public Ksiazka() {
        this.tytul = "Nieznany";
        this.autor = "Anonim";
    }

    public Ksiazka(String tytul, String autor) {
        this.tytul = tytul;
        this.autor = autor;
    }

    public Ksiazka(Ksiazka inny) {
        this.tytul = inny.tytul;
        this.autor = inny.autor;
    }

    void wyswietl() {
        System.out.println("Tytuł: " + tytul + ", Autor: " + autor);
    }
}

public class MainKsiazka {
    public static void main(String[] args) {
        Ksiazka k1 = new Ksiazka();
        Ksiazka k2 = new Ksiazka("Krzyżacy", "Henryk Sienkiewicz");
        Ksiazka k3= new Ksiazka(k2);

        k1.wyswietl();
        k2.wyswietl();
        k3.wyswietl();
    }
}

package Zad1_Przyklady;

// Przykład klasy z różnymi konstruktorami
class Samochod {
    String marka;
    int rok;

    // Konstruktor domyślny
    public Samochod() {
        this.marka = "Nieznana";
        this.rok = 2000;
    }

    // Konstruktor z parametrami
    public Samochod(String marka, int rok) {
        this.marka = marka;
        this.rok = rok;
    }

    // Konstruktor kopiujący
    public Samochod(Samochod inny) {
        this.marka = inny.marka;
        this.rok = inny.rok;
    }

    void wyswietl() {
        System.out.println("Samochód: " + marka + ", Rok: " + rok);
    }
}

public class Main {
    public static void main(String[] args) {
        Samochod s1 = new Samochod(); // Konstruktor domyślny
        Samochod s2 = new Samochod("Toyota", 2022); // Konstruktor z parametrami
        Samochod s3 = new Samochod(s2); // Konstruktor kopiujący

        s1.wyswietl();
        s2.wyswietl();
        s3.wyswietl();
    }
}


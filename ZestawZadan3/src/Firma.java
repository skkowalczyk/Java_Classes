import java.util.ArrayList;
import java.util.List;

public class Firma {

    private String nazwa;
    private List<Dzial> dzialy;

    public Firma(String nazwa) {
        this.nazwa = nazwa;
        this.dzialy = new ArrayList<>();
    }

    public void dodajDzial(Dzial dzial) {
        dzialy.add(dzial);
    }

    public String getNazwa() {
        return nazwa;
    }

    public List<Dzial> getDzialy() {
        return dzialy;
    }
}

class Dzial {

    private String nazwa;
    private List<Pracownik> pracownicy;

    public Dzial(String nazwa) {
        this.nazwa = nazwa;
        this.pracownicy = new ArrayList<>();
    }

    public void dodajPracownika(Pracownik pracownik) {
        pracownicy.add(pracownik);
    }

    public String getNazwa() {
        return nazwa;
    }

    public List<Pracownik> getPracownicy() {
        return pracownicy;
    }
}

class Pracownik {

    public String imie;
    public String nazwisko;
    public String stanowisko;

    public Pracownik() {}

    public Pracownik(String imie, String nazwisko, String stanowisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.stanowisko = stanowisko;
    }
}



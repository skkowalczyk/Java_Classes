import java.time.LocalDate;

public class Produkt {
    private String nazwa;
    private Double cena;
    private LocalDate dataProdukcji;

    public Produkt(String nazwa, Double cena, LocalDate dataProdukcji) {
        this.nazwa = nazwa;
        this.cena = cena;
        this.dataProdukcji = dataProdukcji;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public LocalDate getDataProdukcji() {
        return dataProdukcji;
    }

    public void setDataProdukcji(LocalDate dataProdukcji) {
        this.dataProdukcji = dataProdukcji;
    }

}
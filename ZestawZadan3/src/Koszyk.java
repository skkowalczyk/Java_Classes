import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Koszyk {

    private List<Produkt> produkty;

    public Koszyk() {
        this.produkty = new ArrayList<>();
    }

    public void dodajProdukt(Produkt produkt) {
        produkty.add(produkt);
    }

    public List<Produkt> getProdukty() {
        return produkty;
    }

    public void zapiszDoXML(String nazwaPliku) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File(nazwaPliku), this);
    }

    public void zapiszDoJSON(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(fileName), this);
    }

    public static Koszyk odczytajZXML(String nazwaPliku) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(new File(nazwaPliku), Koszyk.class);
    }

    public static Koszyk odczytajZJSON(String nazwaPliku) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(nazwaPliku), Koszyk.class);
    }

    public void wyswietlProdukty() {
        for (Produkt produkt: produkty) {
            System.out.println("Nazwa: " + produkt.getNazwa() + ", Cena: " + produkt.getCena() + ", Data Produkcji: " + produkt.getDataProdukcji());
        }
    }

}

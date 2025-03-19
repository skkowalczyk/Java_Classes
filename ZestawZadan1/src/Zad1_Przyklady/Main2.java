package Zad1_Przyklady;

class Singleton {
    private static Singleton instance;

    // Prywatny konstruktor – nie można tworzyć instancji spoza klasy
    private Singleton() {
        System.out.println("Singleton został utworzony");
    }

    // Metoda dostępu do jedynej instancji klasy
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("To jest metoda singletona");
    }
}

public class Main2 {
    public static void main(String[] args) {
        // Próba utworzenia instancji klasy Singleton – tylko przez metodę getInstance()
        Singleton obj1 = Singleton.getInstance();
        obj1.showMessage();

        // Druga próba uzyskania instancji – zwróci tę samą referencję
        Singleton obj2 = Singleton.getInstance();
        System.out.println(obj1 == obj2); // true (oba obiekty to ta sama instancja)
    }
}


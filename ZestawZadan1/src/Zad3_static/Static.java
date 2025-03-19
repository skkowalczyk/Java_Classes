package Zad3_static;

class Przyklad {
    // zmienna statyczna - wspólna dla wszystkich obiektów klasy
    static int staticVar = 0;

    // zmienna instancji - każdy obiekt ma swoją kopię
    int instanceVar;

    // statyczny blok kodu - wykonuje się raz przy załadowaniu klasy
    static {
        System.out.println("Statyczny blok - wykonuje się raz, przy pierwszym użyciu klasy");
        staticVar = 100; // inicjalizacja zmiennej statycznej
    }

    // konstruktor klasy
    public Przyklad(int value) {
        instanceVar = value;
        staticVar++; // każde utworzenie obiektu zwiększa wartość zmiennej statycznej
    }

    // metoda instancji - dla konkretnego obiektu
    void instanceMethod() {
        System.out.println("Metoda instancji - dostęp do instanceVar: " + instanceVar);
    }

    // metoda statyczna - nie wymaga tworzenia obiektu
    static void staticMethod() {
        System.out.println("Metoda statyczna - dostęp do staticVar: " + staticVar);
        // System.out.println(instanceVar); // nie można odwołać się do zmiennej niestatycznej
    }
}

public class Static {
    public static void main(String[] args) {
        // wywołanie metody statycznej - bez tworzenia obiektu
        Przyklad.staticMethod();

        // tworzenie obiektów
        Przyklad obj1 = new Przyklad(10);
        Przyklad obj2 = new Przyklad(20);

        // wywołanie metody instancji - dla konkretnego obiektu
        obj1.instanceMethod();
        obj2.instanceMethod();

        // wywołanie metody statycznej - dostępne z poziomu klasy
        Przyklad.staticMethod();
    }
}


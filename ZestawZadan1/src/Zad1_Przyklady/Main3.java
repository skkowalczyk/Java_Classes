package Zad1_Przyklady;

class Person {
    String name;
    int age;

    // Konstruktor domyślny wywołuje konstruktor z jednym argumentem
    public Person() {
        this("Nieznany");
        System.out.println("Wywołano konstruktor domyślny");
    }

    // Konstruktor z jednym argumentem wywołuje konstruktor z dwoma argumentami
    public Person(String name) {
        this(name, 18);
        System.out.println("Wywołano konstruktor z jednym argumentem: " + name);
    }

    // Konstruktor docelowy
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Wywołano konstruktor z dwoma argumentami: " + name + ", wiek: " + age);
    }

    // Metoda wyświetlająca dane
    public void display() {
        System.out.println("Imię: " + name + ", Wiek: " + age);
    }
}

public class Main3 {
    public static void main(String[] args) {
        Person person1 = new Person();  // Wywoła łańcuch konstruktorów
        person1.display();
    }
}


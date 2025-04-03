package Zadanie4;

public abstract class Shape {
    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double calculateArea();

    public abstract double calculatePerimeter();

    @Override
    public String toString() {
        return name + " - Pole: " + calculateArea() + ", Obwód: " + calculatePerimeter();
    }
}

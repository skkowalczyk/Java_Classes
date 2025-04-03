package Zadanie4;

public class ShapeFactory {
    public static Shape createShape(String type, double... params) {
        switch (type.toLowerCase()) {
            case "circle":
                if (params.length == 1) {
                    return new Circle(params[0]);
                }
                break;
            case "rectangle":
                if (params.length == 2) {
                    return new Rectangle(params[0], params[1]);
                }
                break;
            case "triangle":
                if (params.length == 3) {
                    return new Triangle(params[0], params[1], params[2]);
                }
                break;
        }
        throw new IllegalArgumentException("Nieprawidłowy typ figury lub parametry");
    }
}

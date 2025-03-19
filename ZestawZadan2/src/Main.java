import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String currentPath = new java.io.File(".").getCanonicalPath();
        List<Shape> shapes = ShapeFileReader.readShapesFromFile(currentPath + "/src/plik.txt");

        for (Shape shape : shapes) {
            System.out.println("Figura: " + shape.getName());
            System.out.println("Obwód: " + shape.calculatePerimeter());
            System.out.println("Pole: " + shape.calculateArea());
            System.out.println();
        }
    }
}

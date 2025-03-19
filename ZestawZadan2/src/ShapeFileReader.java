import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShapeFileReader {
    public ShapeFileReader() {
    }

    public static List<Shape> readShapesFromFile(String fileName) {
        List<Shape> shapes = new ArrayList<>();

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String shapeType = parts[0];
                double[] parameters = new double[parts.length - 1];

                for(int i = 1; i < parts.length; ++i) {
                    parameters[i - 1] = Double.parseDouble(parts[i]);
                }

                Shape shape = ShapeFactory.createShape(shapeType, parameters);
                shapes.add(shape);
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Błąd w danych: " + e.getMessage());
        }

        return shapes;
    }
}


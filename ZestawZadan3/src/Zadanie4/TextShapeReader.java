package Zadanie4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextShapeReader implements ShapeReader {
    @Override
    public List<Shape> readShapes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        List<Shape> shapes = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            try {
                if ("circle".equalsIgnoreCase(parts[0]) && parts.length == 2) {
                    double radius = Double.parseDouble(parts[1]);
                    shapes.add(ShapeFactory.createShape("circle", radius));
                } else if ("rectangle".equalsIgnoreCase(parts[0]) && parts.length == 3) {
                    double width = Double.parseDouble(parts[1]);
                    double height = Double.parseDouble(parts[2]);
                    shapes.add(ShapeFactory.createShape("rectangle", width, height));
                } else if ("triangle".equalsIgnoreCase(parts[0]) && parts.length == 4) {
                    double a = Double.parseDouble(parts[1]);
                    double b = Double.parseDouble(parts[2]);
                    double c = Double.parseDouble(parts[3]);
                    shapes.add(ShapeFactory.createShape("triangle", a, b, c));
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Błąd w linii: " + line + " - " + e.getMessage());
            }
        }

        if (shapes.isEmpty()) {
            throw new IOException("Nie znaleziono poprawnych definicji figur w pliku tekstowym");
        }

        return shapes;
    }
}

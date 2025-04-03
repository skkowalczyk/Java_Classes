package Zadanie4;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Tworzenie figur za pomocą fabryki:");
        Shape circle = ShapeFactory.createShape("circle", 5.0);
        Shape rectangle = ShapeFactory.createShape("rectangle", 4.0, 6.0);
        Shape triangle = ShapeFactory.createShape("triangle", 3.0, 4.0, 5.0);

        System.out.println(circle);
        System.out.println(rectangle);
        System.out.println(triangle);

        if (circle instanceof Resizable) {
            System.out.println("\nZmiana rozmiaru koła (współczynnik 2):");
            Resizable resizableCircle = (Resizable) circle;
            resizableCircle.resize(2.0);
            System.out.println(circle);
        }

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString() + "/ZestawZadan3/";

        ShapeReader textReader = new TextShapeReader();
        ShapeReader xmlReader = new XmlShapeReader();

        ShapeReaderService readerService = new ShapeReaderService(textReader, xmlReader);

        List<Shape> allShapes = new ArrayList<>();

        String xmlFilename = s + "plik.xml";
        String txtFilename = s + "plik.txt";
        File xmlFile = new File(xmlFilename);

        try {

            if (xmlFile.exists()) {
                System.out.println("\nOdczyt figur z pliku XML:");
                List<Shape> shapesFromXml = readerService.readShapesFromFile(xmlFilename);
                for (Shape shape : shapesFromXml) {
                    System.out.println(shape);
                }
                allShapes.addAll(shapesFromXml);
            } else {
                System.out.println("\nPlik XML nie istnieje. Próba odczytu z pliku tekstowego:");
                List<Shape> shapesFromTxt = readerService.readShapesFromFile(txtFilename);
                for (Shape shape : shapesFromTxt) {
                    System.out.println(shape);
                }
                allShapes.addAll(shapesFromTxt);
            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku " + xmlFilename + ": " + e.getMessage());
            System.out.println("Próba odczytu z pliku tekstowego:");
            try {
                List<Shape> shapesFromTxt = readerService.readShapesFromFile(txtFilename);
                for (Shape shape : shapesFromTxt) {
                    System.out.println(shape);
                }
                allShapes.addAll(shapesFromTxt);
            } catch (IOException e2) {
                System.err.println("Błąd odczytu pliku tekstowego: " + e2.getMessage());
            }
        }

        if (!allShapes.isEmpty()) {
            System.out.println("\nUruchamianie serwera HTTP z " + allShapes.size() + " figurami...");
            ShapeServer server = new ShapeServer(allShapes);
            server.start();
        } else {
            System.err.println("\nNie udało się wczytać żadnych figur, serwer HTTP nie zostanie uruchomiony.");
        }
    }
}

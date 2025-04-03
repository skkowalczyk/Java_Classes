package Zadanie4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ShapeReaderService {
    private final ShapeReader textReader;
    private final ShapeReader xmlReader;

    public ShapeReaderService() {
        this.textReader = new TextShapeReader();
        this.xmlReader = new XmlShapeReader();
    }

    public ShapeReaderService(ShapeReader textReader, ShapeReader xmlReader) {
        this.textReader = textReader;
        this.xmlReader = xmlReader;
    }

    public List<Shape> readShapesFromFile(String filePath) throws IOException {
        String extension = getFileExtension(filePath);

        if (extension.equalsIgnoreCase("xml")) {
            try {
                return xmlReader.readShapes(filePath);
            } catch (IOException e) {
                System.out.println("Nie udało się odczytać pliku jako XML, próbuję jako tekst...");

                try {
                    return textReader.readShapes(filePath);
                } catch (IOException e2) {
                    throw new IOException("Nie udało się odczytać pliku w żadnym formacie", e);
                }
            }
        } else {
            try {
                return textReader.readShapes(filePath);
            } catch (IOException e) {
                System.out.println("Nie udało się odczytać pliku jako tekst, próbuję jako XML...");

                String xmlFilePath = filePath.substring(0, filePath.lastIndexOf('.')) + ".xml";
                Path xmlPath = Paths.get(xmlFilePath);

                if (Files.exists(xmlPath)) {
                    return xmlReader.readShapes(xmlFilePath);
                }

                throw new IOException("Nie udało się odczytać pliku w żadnym formacie", e);
            }
        }
    }

    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0) {
            return filePath.substring(dotIndex + 1);
        }
        return "";
    }
}

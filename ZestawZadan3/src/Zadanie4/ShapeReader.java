package Zadanie4;

import java.io.IOException;
import java.util.List;

public interface ShapeReader {
    List<Shape> readShapes(String filePath) throws IOException;
}

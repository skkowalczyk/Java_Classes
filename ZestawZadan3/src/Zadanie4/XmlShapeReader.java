package Zadanie4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlShapeReader implements ShapeReader {
    @Override
    public List<Shape> readShapes(String filePath) throws IOException {
        List<Shape> shapes = new ArrayList<>();
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList circleList = doc.getElementsByTagName("circle");
            for (int i = 0; i < circleList.getLength(); i++) {
                Node node = circleList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    double radius = Double.parseDouble(element.getAttribute("radius"));
                    shapes.add(ShapeFactory.createShape("circle", radius));
                }
            }

            NodeList rectangleList = doc.getElementsByTagName("rectangle");
            for (int i = 0; i < rectangleList.getLength(); i++) {
                Node node = rectangleList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    double width = Double.parseDouble(element.getAttribute("width"));
                    double height = Double.parseDouble(element.getAttribute("height"));
                    shapes.add(ShapeFactory.createShape("rectangle", width, height));
                }
            }

            NodeList triangleList = doc.getElementsByTagName("triangle");
            for (int i = 0; i < triangleList.getLength(); i++) {
                Node node = triangleList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    double a = Double.parseDouble(element.getAttribute("a"));
                    double b = Double.parseDouble(element.getAttribute("b"));
                    double c = Double.parseDouble(element.getAttribute("c"));
                    shapes.add(ShapeFactory.createShape("triangle", a, b, c));
                }
            }

            if (shapes.isEmpty()) {
                throw new IOException("Nie znaleziono poprawnych definicji figur w pliku XML");
            }

        } catch (Exception e) {
            throw new IOException("Błąd podczas odczytu pliku XML: " + e.getMessage(), e);
        }
        return shapes;
    }
}

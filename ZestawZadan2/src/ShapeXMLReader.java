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

public class ShapeXMLReader {

    public List<Shape> XMLShapeReader(String filePath) throws IOException {
        List<Shape> shapes = new ArrayList<>();

        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList circleList = document.getElementsByTagName("circle");
            for (int i = 0; i < circleList.getLength(); i++) {
                Node node = circleList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    double radius = Double.parseDouble(element.getAttribute("radius"));
                    shapes.add(new Circle(radius));
                }
            }

            NodeList rectangleList = document.getElementsByTagName("rectangle");
            for (int i = 0; i < rectangleList.getLength(); i++) {
                Node node = rectangleList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    double width = Double.parseDouble(element.getAttribute("width"));
                    double height = Double.parseDouble(element.getAttribute("height"));
                    shapes.add(new Rectangle(width, height));
                }
            }

            NodeList triangleList = document.getElementsByTagName("triangle");
            for (int i = 0; i < triangleList.getLength(); i++) {
                Node node = triangleList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    double sideA = Double.parseDouble(element.getAttribute("sideA"));
                    double sideB = Double.parseDouble(element.getAttribute("sideB"));
                    double sideC = Double.parseDouble(element.getAttribute("sideC"));
                    shapes.add(new Triangle(sideA, sideB, sideC));
                }
            }

            if (shapes.isEmpty()) {
                throw new IOException("Pusty plik XML");
            }

        } catch (Exception e) {
            throw new IOException("Błąd: " + e.getMessage(), e);
        }

        return shapes;
    }

    public static void main(String[] args) {
        ShapeXMLReader reader = new ShapeXMLReader();
        try {
            List<Shape> shapes = reader.XMLShapeReader("./plik.xml");
            for (Shape shape : shapes) {
                System.out.println(shape);
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
        }
    }
}
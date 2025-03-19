public class ShapeFactory {

    public static Shape createShape(String shapeType, double... parameters) {
        switch (shapeType.toLowerCase()) {
            case "circle":
                if (parameters.length == 1) {
                    return new Circle(parameters[0]);
                }
                break;
            case "rectangle":
                if (parameters.length == 2) {
                    return new Rectangle(parameters[0], parameters[1]);
                }
                break;
            case "triangle":
                if (parameters.length == 3) {
                    return new Triangle(parameters[0], parameters[1], parameters[2]);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
        throw new IllegalArgumentException("Invalid number of parameters for shape type: " + shapeType);
    }
}

public class Circle  extends Shape {

    private double radius;

    public Circle (double radius){
        super("Circle");
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle [radius=" + radius + "]";
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public void resize(double factor) {
        this.radius *= factor;
    }

}


class Shape {
    
    public double area();
}


class Rectangle extends Shape {
    private double length;
    private double width;

    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    
  
    public double area() {
        return length * width;
    }
}


class Circle extends Shape {
    private double radius;

  
    public Circle(double radius) {
        this.radius = radius;
    }

   
   
    public double area() {
        return Math.PI * Math.pow(radius, 2);
    }
}


public class Main {
    public static void main(String[] args) { 
        Rectangle rectangle = new Rectangle(5, 7);
        Circle circle = new Circle(3);
        System.out.println("Rectangle area: " + rectangle.area());
        System.out.println("Circle area: " + circle.area());
    }
}

package org.example.figures;

public class Circle extends Figure implements Printing {

    private final double r;

    public Circle(double r) throws IllegalArgumentException {
        if (r <= 0)
            throw new IllegalArgumentException("Promień koła nie może być mniejszy niż lub równy 0!");

        this.r = r;
    }

    @Override
    public double calculateArea() {
        return Math.PI * r * r;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * r;
    }

    @Override
    public void print() {
        System.out.println("Koło o promieniu: " + r);
        System.out.println("Obwód koła: " + calculatePerimeter() + ", pole powierzchni koła: " + calculateArea());
    }

}

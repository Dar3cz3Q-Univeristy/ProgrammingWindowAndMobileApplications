package org.example.figures;

public class Square extends Figure implements Printing {

    private final double a;

    public Square(double a) throws IllegalArgumentException {
        if (a <= 0)
            throw new IllegalArgumentException("Długość boku kwadratu nie może być mniejsza niż lub równa 0!");

        this.a = a;
    }

    @Override
    public double calculateArea() {
        return a * a;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * a;
    }

    @Override
    public void print() {
        System.out.println("Kwadrat o boku: " + a);
        System.out.println("Obwód kwadratu: " + calculatePerimeter() + ", pole powierzchni kwadratu: " + calculateArea());
    }
}

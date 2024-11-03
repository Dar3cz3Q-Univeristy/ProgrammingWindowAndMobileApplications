package org.example.figures;

import java.util.Arrays;

import static java.lang.Math.sqrt;

public class Triangle extends Figure implements Printing {

    private final double a;
    private final double b;
    private final double c;

    public Triangle(double a, double b, double c) throws IllegalArgumentException {

        double[] arr = {a, b, c};

        Arrays.sort(arr);

        if (arr[0] + arr[1] <= arr[2])
            throw new IllegalArgumentException("Z podanych wartości nie można utworzyć trójkąta!");

        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateArea() {
        double p = calculatePerimeter() / 2;
        return sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public double calculatePerimeter() {
        return (a + b + c);
    }

    @Override
    public void print() {
        System.out.println("Trojkat o bokach a: " + a + ", b: " + b + ", c: " + c);
        System.out.println("Obwód trójkąta: " + calculateArea() + ", pole powierzchni trójkąta: " + calculatePerimeter());
    }
}

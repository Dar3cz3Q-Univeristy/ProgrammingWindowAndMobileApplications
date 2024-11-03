package org.example.figures;

public class ThreeDim implements Printing {

    private final Figure base;
    private final double h;

    public ThreeDim(Figure base, double h) {
        this.base = base;
        this.h = h;
    }

    public double calculateArea() {
        double baseArea = base.calculateArea();
        double basePerimeter = base.calculatePerimeter();
        return 2 * baseArea + basePerimeter * h;
    }

    public double calculateVolume() {
        return base.calculateArea() * h;
    }

    @Override
    public void print() {
        System.out.println("Obiekt 3D o podstawie: " + base.getClass().getSimpleName());
        System.out.println("Pole powierzchni całkowitej: " + calculateArea() + ", objętość: " + calculateVolume());
    }
}

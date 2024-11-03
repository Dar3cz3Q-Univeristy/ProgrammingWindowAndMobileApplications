package org.example.command;

import org.example.figures.Figure;
import org.example.figures.Triangle;

import java.util.Scanner;

public class TriangleCommand implements Command {

    private Triangle figure;
    private final Scanner scanner;

    public TriangleCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Tworzenie trójkąta...");

        boolean isInputValid = false;
        while(!isInputValid) {
            try {
                System.out.print("Podaj długość boku a: ");
                double a = scanner.nextDouble();
                System.out.print("Podaj długość boku b: ");
                double b = scanner.nextDouble();
                System.out.print("Podaj długość boku c: ");
                double c = scanner.nextDouble();

                this.figure = new Triangle(a, b, c);
                isInputValid = true;

                System.out.println();
                this.figure.print();
                System.out.println();
            } catch(IllegalArgumentException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    @Override
    public Figure getShape() {
        return this.figure;
    }
}

package org.example.command;

import org.example.figures.Circle;
import org.example.figures.Figure;

import java.util.Scanner;

public class CircleCommand implements Command {

    private Circle circle;
    private final Scanner scanner;

    public CircleCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Tworzenie koła...");

        boolean isInputValid = false;
        while(!isInputValid) {
            try {
                System.out.print("Podaj promień koła r: ");
                double r = scanner.nextDouble();

                this.circle = new Circle(r);
                isInputValid = true;

                System.out.println();
                this.circle.print();
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }
    @Override
    public Figure getShape() {
        return this.circle;
    }
}

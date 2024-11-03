package org.example.command;

import org.example.figures.Figure;
import org.example.figures.Square;

import java.util.Scanner;

public class SquareCommand implements Command {

    private Square figure;
    private final Scanner scanner;

    public SquareCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Tworzenie kwadratu...");

        boolean isInputValid = false;
        while(!isInputValid) {
            try {
                System.out.print("Podaj długość boku a: ");
                double a = scanner.nextDouble();

                this.figure = new Square(a);
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

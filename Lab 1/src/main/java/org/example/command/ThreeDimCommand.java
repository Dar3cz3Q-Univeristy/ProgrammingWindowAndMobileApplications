package org.example.command;

import org.example.figures.Figure;
import org.example.figures.ThreeDim;

import java.util.Scanner;

public class ThreeDimCommand implements Command {

    private final Scanner scanner;

    public ThreeDimCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    private void displaySubOptions() {
        System.out.println("Dostępne podstawy:");
        System.out.println("1. Trójkąt");
        System.out.println("2. Kwadrat");
        System.out.println("3. Koło");
    }

    @Override
    public void execute() {
        displaySubOptions();

        System.out.print("Wybierz podstawę: ");

        Command command = null;
        Figure figure = null;
        ThreeDim threeDim = null;

        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                command = new TriangleCommand(scanner);
                break;
            case 2:
                command = new SquareCommand(scanner);
                break;
            case 3:
                command = new CircleCommand(scanner);
                break;
            default:
                System.out.println("Wybrano niepoprawną opcję!");
                break;
        }

        if (command != null) {
            command.execute();
            figure = command.getShape();
        }

        if (figure != null) {
            System.out.print("Podaj wysokość bryły h: ");
            double h = scanner.nextDouble();

            threeDim = new ThreeDim(figure, h);

            System.out.println();
            threeDim.print();
            System.out.println();
        }
    }

    @Override
    public Figure getShape() {
        return null;
    }
}

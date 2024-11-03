package org.example.menu;

import org.example.command.*;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);

    private void displayOptions() {
        System.out.println("Dostępne figury:");
        System.out.println("1. Trójkąt");
        System.out.println("2. Kwadrat");
        System.out.println("3. Koło");
        System.out.println("4. Bryła 3D");
        System.out.println("0. Wyłączenie programu");
    }

    public void run() {
        int choice = -1;

        while(choice != 0) {
            displayOptions();

            System.out.print("Wybierz figurę: ");
            choice = scanner.nextInt();

            Command command = null;

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
                case 4:
                    command = new ThreeDimCommand(scanner);
                    break;
                case 0:
                    System.out.println("Wyłączanie programu");
                    break;
                default:
                    System.out.println("Wybrano niepoprawną opcję!");
                    break;
            }

            if (command != null) {
                command.execute();
            }
        }
    }
}

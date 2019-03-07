package pw.scho.minesweeper;

import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    private Game game = Game.newDefault();

    private void run() {
        printGameState();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            Position position = readPosition(scanner);

            readAction(scanner).accept(position);

            printGameState();

            if (game.isGameOver()) {
                System.out.println("* * * GAME OVER * * *");
                break;
            }

            if (game.isCorrect()) {
                System.out.println("* * * YOU WON * * *");
                break;
            }
        }
    }

    private Consumer<Position> readAction(Scanner scanner) {
        System.out.println("Reveal or mark as bomb (R/B):");
        while (scanner.hasNextLine()) {
            String actionString = scanner.nextLine();

            if (actionString.equals("R")) {
                return game::reveal;
            }
            if (actionString.equals("B")) {
                return game::reveal;
            }
            System.out.println("Must be R or B, please type in again (R/B):");
        }
        throw new IllegalStateException();
    }

    private Position readPosition(Scanner scanner) {
        System.out.println("Select position:");
        while (scanner.hasNextLine()) {
            try {
                Position position = Position.of(scanner.nextLine());

                if (position.isValid()) {
                    return position;
                }
                System.out.println("Position is invalid, please type in again:");
            } catch (IllegalArgumentException e) {
                System.out.println("Position is invalid, please type in again:");
            }
        }
        throw new IllegalStateException();
    }

    private void printGameState() {
        game.print(System.out::println);
        System.out.println();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

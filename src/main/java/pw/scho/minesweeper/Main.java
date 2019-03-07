package pw.scho.minesweeper;

import java.util.Scanner;

public class Main {

    private Game game = Game.newDefault();

    private void run() {
        game.print(System.out::println);
        System.out.println("Select position:");

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String position = sc.nextLine();

            char column = position.charAt(0);
            int row = Character.getNumericValue(position.charAt(1));

            System.out.println("Reveal or mark as bomb (R/B):");

            if (sc.hasNextLine()) {
                String revealOrMarkAsBomb = sc.nextLine();

                if (revealOrMarkAsBomb.equals("R")) {
                    game.reveal(column, row);
                }
                if (revealOrMarkAsBomb.equals("B")) {
                    game.markAsBomb(column, row);
                }
                throw new IllegalArgumentException("Must be R or B");
            }

            game.print(System.out::println);
            System.out.println("Select position:");
        }
    }

    public static void main(String[] args) {
        new Main().run();

    }
}

package pw.scho.minesweeper;

import java.util.Scanner;

public class Main {

    private Game game = Game.newDefault();

    private void run() {
        game.print(System.out::println);
        System.out.println("Select position:");

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            Position position = Position.of(sc.nextLine());

            System.out.println("Reveal or mark as bomb (R/B):");

            while (sc.hasNextLine()) {
                String revealOrMarkAsBomb = sc.nextLine();

                if (revealOrMarkAsBomb.equals("R")) {
                    game.reveal(position);
                    break;
                }
                if (revealOrMarkAsBomb.equals("B")) {
                    game.markAsBomb(position);
                    break;
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

import java.util.Scanner;
public class Ceelo {

    private Scanner scan;
    private Player p1;
    private Player p2;
    private Player p3;
    private Banker banker;
    private int roundNum;
    public Ceelo() {
        scan = new Scanner(System.in);
        banker = new Banker();
        roundNum = 1;
    }

    public void runGame() {
        gameLogic();
    }

    private void gameLogic() {
        welcome();
        while ((p1.getChips() != 0 && p2.getChips() != 0 && p3.getChips() != 0) || banker.getChips() != 0) {
            System.out.println("Round " + roundNum + ":");
            printChip();
            makeWager(p1.getOutOfGame(), p1);
            makeWager(p2.getOutOfGame(), p2);
            makeWager(p3.getOutOfGame(), p3);
            String bankerRoll = banker.rolldice();
            if (!bankerRoll.equals("score")) {
                switchChips(bankerRoll);
            } else {
                p1.rollDice();
                p2.rollDice();
                p3.rollDice();
            }
            roundNum++;
        }
    }

    // welcomes the players into the game and explains the rules and also initializes all three players
    private void welcome() {
        ConsoleUtility.clearScreen();
        System.out.println("Welcome to Cee - lo!");
        System.out.println();
        System.out.println("Rules: ");
        System.out.println("There are 3 players and a banker (controlled by computer)");
        System.out.println("The goal of the game is to try to bankrupt the banker before he takes all your chips.");
        System.out.println("If the players successfully bankrupt the banker, the player with the most chips wins!");

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println("error");
        }
        ConsoleUtility.clearScreen();

        // asks the players for their names and creates the three player objects
        System.out.print("Player 1, enter your name: ");
        p1 = new Player(scan.nextLine());

        System.out.print("Player 2, enter your name: ");
        p2 = new Player(scan.nextLine());

        System.out.print("Player 3, enter your name: ");
        p3 = new Player(scan.nextLine());

        ConsoleUtility.clearScreen();
    }

    // prints out how many chips each player and banker has each round
    private void printChip(){
        System.out.println(p1.getName() + " has " + p1.getChips() + " chips");
        System.out.println(p2.getName() + " has " + p2.getChips() + " chips");
        System.out.println(p3.getName() + " has " + p3.getChips() + " chips");
        System.out.println("The banker has " + banker.getChips() + " chips");
        System.out.println();
    }


    // asks the player how much they would like to wager
    private void makeWager(Boolean playerout, Player player) {
        if (!playerout){
            System.out.println(player.getName() + "'s Turn:");
            System.out.print("How much would you like to wager (0 - " + player.getChips() + "): " );
            player.setCurrentWager(scan.nextInt());
            while (player.getCurrentWager() < 0 || player.getCurrentWager() > player.getChips()) {
                System.out.println("Invalid wager. ");
                System.out.print("How much would you like to wager (0 - " + player.getChips() + "): " );
                player.setCurrentWager(scan.nextInt());
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("error");
            }

            ConsoleUtility.clearScreen();
        }
    }

    // adjusts the chips for both the banker and the players depending on who won
    private void switchChips(String status) {
        int p1Chips = p1.getCurrentWager();
        int p2Chips = p2.getCurrentWager();
        int p3Chips = p3.getCurrentWager();

        if (status.equals("win")) {
            p1.changeChips(p1Chips * -1);
            p2.changeChips(p1Chips * -1);
            p3.changeChips(p1Chips * -1);
            banker.changeChips(p1Chips + p2Chips + p3Chips);
        } else {
            p1.changeChips(p1Chips);
            p2.changeChips(p1Chips);
            p3.changeChips(p1Chips);
            banker.changeChips((p1Chips + p2Chips + p3Chips) * -1);
        }

    }


}

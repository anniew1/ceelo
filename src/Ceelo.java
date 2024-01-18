import java.io.Console;
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
        Boolean allThreePlayersOut = p1.getOutOfGame() && p2.getOutOfGame() && p3.getOutOfGame();
        while (!allThreePlayersOut && banker.getChips() > 0) {
            System.out.println("Round " + roundNum + ":");
            printChip();

            makeWager(p1.getOutOfGame(), p1);
            makeWager(p2.getOutOfGame(), p2);
            makeWager(p3.getOutOfGame(), p3);
            ConsoleUtility.clearScreen();

            String bankerRoll = banker.rolldice();
            System.out.println("---------------------------------------");
            ConsoleUtility.sleep(3000);

            if (!bankerRoll.equals("score")) {
                switchChips(bankerRoll);
            } else {
                takeTurn(p1);
                takeTurn(p2);
                takeTurn(p3);

                checkOutOfGame(p1);
                checkOutOfGame(p2);
                checkOutOfGame(p3);
                System.out.println("---------------------------------------");
                ConsoleUtility.sleep(1000);
            }
            roundNum++;
            allThreePlayersOut = p1.getOutOfGame() && p2.getOutOfGame() && p3.getOutOfGame();
        }
        printWinner();
    }

    // welcomes the players into the game and explains the rules and also initializes all three players
    private void welcome() {
        ConsoleUtility.clearScreen();
        System.out.println(ConsoleUtility.CYAN + "Welcome to Cee - lo!" + ConsoleUtility.RESET);
        System.out.println();
        System.out.println("Rules: ");
        System.out.println("There are 3 players and a banker (controlled by computer)");
        System.out.println("The goal of the game is to try to bankrupt the banker before he takes all your chips.");
        System.out.println("If the players successfully bankrupt the banker, the player with the most chips wins!");

        ConsoleUtility.sleep(10000);
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
    private void makeWager(Boolean playerOut, Player player) {
        if (!playerOut){
            System.out.println(ConsoleUtility.PURPLE + player.getName() + "'s Turn:" + ConsoleUtility.RESET);
            System.out.print("How much would you like to wager (0 - " + player.getChips() + "): " );
            player.setCurrentWager(scan.nextInt());
            while (player.getCurrentWager() < 0 || player.getCurrentWager() > player.getChips()) {
                System.out.println("Invalid wager. ");
                System.out.print("How much would you like to wager (0 - " + player.getChips() + "): " );
                player.setCurrentWager(scan.nextInt());
            }
            ConsoleUtility.sleep(1000);
        }
    }

    // adjusts the chips for both the banker and the players depending on who won (win = banker win)
    private void switchChips(String status) {
        int p1Chips = p1.getCurrentWager();
        int p2Chips = p2.getCurrentWager();
        int p3Chips = p3.getCurrentWager();
        int totalChips = p1Chips + p2Chips + p3Chips;

        if (status.equals("win")) {
            p1.changeChips(p1Chips * -1);
            p2.changeChips(p2Chips * -1);
            p3.changeChips(p3Chips * -1);
            banker.changeChips(totalChips);
            System.out.println("The banker has won " + totalChips + " chip(s) and the players has " + ConsoleUtility.RED + "lost: " + ConsoleUtility.RESET);
            printChipDifferences(p1Chips, p2Chips, p3Chips, totalChips);
            System.out.println("---------------------------------------");
        } else {
            p1.changeChips(p1Chips);
            p2.changeChips(p2Chips);
            p3.changeChips(p3Chips);
            banker.changeChips((p1Chips + p2Chips + p3Chips) * -1);
            System.out.println("The banker has lost " + totalChips + " chip(s) and the players has " + ConsoleUtility.YELLOW + "won: " + ConsoleUtility.RESET);
            printChipDifferences(p1Chips, p2Chips, p3Chips, totalChips);
            System.out.println("---------------------------------------");
        }

    }

    // adjusts the chips for both the banker and the players depending on who won (win = player win)
    private void switchChips(String status, Player player) {
        int chips = player.getCurrentWager();

        if (status.equals("win")) {
            player.changeChips(chips);
            banker.changeChips(chips * -1);
        } else {
            player.changeChips(chips * -1);
            banker.changeChips(chips);
        }
    }

    // determines whether player lost or won
    private String playerWin(String result, Player player) {
        String victory;
        if (result.equals("win")) {
            victory = "win";
        } else if (result.equals("lose")) {
            victory = "lose";
        } else {
            victory = higherScore(player.getRoundScore(), banker.getRoundScore());
        }
        return victory;
    }

    // checks whether the player's score is higher than the bankers & in the case there is a tie player wins
    private String higherScore(int playerScore, int bankerScore) {
        if (playerScore >= bankerScore) {
            return "win";
        } else {
            return "lose";
        }
    }

    // rolls the dice and does the according action depending on whether the player is still in or out of tge game
    private void takeTurn(Player player) {
        if (!player.getOutOfGame()) {
            String playerRoll = player.rollDice(player);
            System.out.println("---------------------------------------");
            ConsoleUtility.sleep(3000);

            switchChips(playerWin(playerRoll, player), player);
        }
    }

    // checks whether player lost and prints out a statement
    private void checkOutOfGame(Player player) {
        ConsoleUtility.sleep(1000);
        if (player.getOutOfGame()) {
            System.out.println(player.getName() + ":" + ConsoleUtility.RED + " out of the game" + ConsoleUtility.RESET);
        } else {
            System.out.println(player.getName() + ": in game");
        }
    }

    // prints out the winner of the whole game
    private void printWinner() {
        if (banker.getChips() > 0) {
            System.out.println(ConsoleUtility.RED + "The banker wins and all three players lose" + ConsoleUtility.RESET);
        } else {
            int greatest = p1.getChips();
            Player player = p1;
            if (p2.getChips() > greatest) {
                greatest = p2.getChips();
                player = p2;
            }
            if (p3.getChips() > greatest) {
                greatest = p3.getChips();
                player = p3;
            }
            System.out.println(ConsoleUtility.YELLOW + "The players have successfully bankrupted the banker and "+player.getName() + " wins with " + greatest + " chips!" + ConsoleUtility.RESET);
        }
    }

    // prints out the number of chips lost and won by all three players and banker
    private void printChipDifferences(int p1Chips, int p2Chips, int p3Chips, int bankerChips) {
        System.out.println(p1.getName() + ": " + p1Chips + " chips");
        System.out.println(p2.getName() + ": " + p2Chips + " chips");
        System.out.println(p3.getName() + ": " + p3Chips + " chips");
    }
}







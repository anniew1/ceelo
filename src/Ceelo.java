import java.util.Collection;
import java.util.Scanner;
public class Ceelo {

    private Scanner scan;
    private Player p1;
    private Player p2;
    private Player p3;
    private Banker banker;
    private int roundNum;
    private int highestChips;
    private Player highestPlayer;

    public Ceelo() {
        scan = new Scanner(System.in);
        banker = new Banker();
        roundNum = 1;
        highestChips = 0;
    }

    public void runGame() {
        gameLogic();
    }

    private void gameLogic() {
        welcome();
        String playAgain = "y";
        while (playAgain.equals("y")) {
            Boolean allThreePlayersOut = p1.getOutOfGame() && p2.getOutOfGame() && p3.getOutOfGame();
            while (!allThreePlayersOut && banker.getChips() > 0) {
                System.out.println("Round " + roundNum + ":");
                printChip();

                makeWager(p1.getOutOfGame(), p1);
                makeWager(p2.getOutOfGame(), p2);
                makeWager(p3.getOutOfGame(), p3);
                ConsoleUtility.clearScreen();

                String bankerRoll = banker.rollDice();
                System.out.println("---------------------------------------");
                ConsoleUtility.sleep(3000);

                if (!bankerRoll.equals("score")) {
                    switchChips(bankerRoll);
                } else {
                    takeTurn(p1);
                    takeTurn(p2);
                    takeTurn(p3);
                }
                allThreePlayersOut = p1.getOutOfGame() && p2.getOutOfGame() && p3.getOutOfGame();

                if (!allThreePlayersOut) {
                    System.out.println(ConsoleUtility.GREEN + "Chip Differences: " + ConsoleUtility.RESET);
                    printChipDifferences(p1.getRoundWin(), p1);
                    printChipDifferences(p2.getRoundWin(), p2);
                    printChipDifferences(p3.getRoundWin(), p3);
                    System.out.println("Banker: " + banker.getDifference() + " chips");
                    System.out.println("---------------------------------------");
                    banker.resetDifference();
                }

                System.out.println(ConsoleUtility.GREEN + "Game Status: " + ConsoleUtility.RESET);
                checkOutOfGame(p1);
                checkOutOfGame(p2);
                checkOutOfGame(p3);
                System.out.println("---------------------------------------");
                ConsoleUtility.sleep(1000);
                roundNum++;
            }
            printWinner();
            System.out.print("Would you like to play again (y/n) : ");
            playAgain = scan.nextLine();

            p1.resetGame();
            p2.resetGame();
            p3.resetGame();
            banker.resetGame();
            roundNum = 1;
        }
        System.out.println(ConsoleUtility.YELLOW + highestPlayer.getName() + " had the highest chip amount with " + highestChips + " chips!" + ConsoleUtility.RESET);
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
            scan.nextLine();
            while (player.getCurrentWager() < 0 || player.getCurrentWager() > player.getChips()) {
                System.out.println("Invalid wager. ");
                System.out.print("How much would you like to wager (0 - " + player.getChips() + "): " );
                player.setCurrentWager(scan.nextInt());
                scan.nextLine();
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
            banker.changeDifference(totalChips);

        } else {
            p1.changeChips(p1Chips);
            p2.changeChips(p2Chips);
            p3.changeChips(p3Chips);
            banker.changeChips(totalChips * -1);
            banker.changeDifference(totalChips * -1);
        }
    }

    // adjusts the chips for both the banker and the players depending on who won (win = player win)
    private void switchChips(Boolean status, Player player) {
        int chips = player.getCurrentWager();

        if (status) {
            player.changeChips(chips);
            banker.changeChips(chips * -1);
            banker.changeDifference(chips * -1);
        } else {
            player.changeChips(chips * -1);
            banker.changeChips(chips);
            banker.changeDifference(chips);
        }
    }

    // determines whether player lost or won
    private void playerWin(String result, Player player) {
        if (result.equals("win")) {
            player.setRoundWin(true);
        } else if (result.equals("lose")) {
            player.setRoundWin(false);
        } else {
            player.setRoundWin(higherScore(player.getRoundScore(), banker.getRoundScore()));
        }
    }

    // checks whether the player's score is higher than the bankers & in the case there is a tie player wins
    private Boolean higherScore(int playerScore, int bankerScore) {
        if (playerScore >= bankerScore) {
            return true;
        } else {
            return false;
        }
    }

    // rolls the dice and does the according action depending on whether the player is still in or out of tge game
    private void takeTurn(Player player) {
        if (!player.getOutOfGame()) {
            String playerRoll = player.rollDice(player);
            System.out.println("---------------------------------------");
            ConsoleUtility.sleep(3000);

            playerWin(playerRoll, player);
            switchChips(player.getRoundWin(), player);
        }
    }

    // checks whether player lost and prints out a statement
    private void checkOutOfGame(Player player) {
        ConsoleUtility.sleep(1000);
        if (player.getOutOfGame()) {
            System.out.println(player.getName() + ":" + ConsoleUtility.RED + " out of the game" + ConsoleUtility.RESET);
            player.setCurrentWager(0);
        } else {
            System.out.println(player.getName() + ": in game");
        }
    }

    // prints out the winner of the whole game
    private void printWinner() {
        Boolean tie = false;
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

            if (greatest > highestChips) {
                highestChips = greatest;
                highestPlayer = player;
            }

            System.out.println(ConsoleUtility.YELLOW + "The players have successfully bankrupted the banker and " + player.getName() + " wins with " + greatest + " chips!" + ConsoleUtility.RESET);
        }
    }

    // prints out the number of chips lost and won by all three players and banker
    private void printChipDifferences(int p1Chips, int p2Chips, int p3Chips, int bankerChips) {
        System.out.println(p1.getName() + ": " + p1Chips + " chips");
        System.out.println(p2.getName() + ": " + p2Chips + " chips");
        System.out.println(p3.getName() + ": " + p3Chips + " chips");
    }

    // prints out the number of chips lost and won by all three players and banker
    private void printChipDifferences(Boolean victory, Player player) {
        ConsoleUtility.sleep(1000);
        if (victory || player.getCurrentWager() == 0) {
            System.out.println(player.getName() + ": " + player.getCurrentWager() + " chips");
        } else {
            System.out.println(player.getName() + ": " + ConsoleUtility.RED + player.getCurrentWager() * -1 + " chips" + ConsoleUtility.RESET);
        }
    }


}







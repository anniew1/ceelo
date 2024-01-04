import java.util.Scanner;
public class Ceelo {

    private Scanner scan;
    private Player p1;
    private Player p2;
    private Player p3;
    public Ceelo() {
        scan = new Scanner(System.in);
    }

    public void runGame() {
        gameLogic();
    }

    private void gameLogic() {
        welcome();

        // asks the players for their names and creates the three player objects
        System.out.print("Player 1, enter your name: ");
        p1 = new Player(scan.nextLine());

        System.out.print("Player 2, enter your name: ");
        p2 = new Player(scan.nextLine());

        System.out.print("Player 3, enter your name: ");
        p3 = new Player(scan.nextLine());
    }

    // welcomes the players into the game and explains the rules
    private void welcome() {
        System.out.println("Welcome to Cee - lo!");
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

    }

    private void printStatus(){
        System.out.println(p1.getName() + " has " + p1.getChips() + " chips");
        System.out.println(p2.getName() + " has " + p2.getChips() + " chips");
        System.out.println(p3.getName() + " has " + p3.getChips() + " chips");
    }

}

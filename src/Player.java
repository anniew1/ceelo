public class Player {


    private int chips;
    private String name;


    private int currentWager;
    private int roundScore;
    private Boolean roundWin;


    private Boolean outOfGame;
    private Dice dice;


    public Player(String name) {
        chips = 100;
        this.name = name;
        currentWager = 0;
        outOfGame = false;
        dice = new Dice();
        roundWin = false;
    }

    // returns a Boolean value indicating whether the player has won the round or not
    public Boolean getRoundWin() {
        return roundWin;
    }

    // returns the number of chips player has
    public int getChips() {
        return chips;
    }


    // returns the name of the player
    public String getName() {
        return name;
    }


    // returns a boolean value indicating whether the player is out of the game
    public Boolean getOutOfGame() {
        return outOfGame;
    }


    // returns the amount that the player wagered in that round
    public int getCurrentWager() {
        return currentWager;
    }


    // returns the score of the player for that round
    public int getRoundScore() {
        return roundScore;
    }

    // sets the amount that the player wagered for the round
    public void setCurrentWager(int wager) {
        currentWager = wager;
    }

    public void setRoundWin(Boolean win) {
        roundWin = win;
    }


    // changes the amount of chips player has after round and checks to see if the player ran out of chips
    public void changeChips(int difference) {
        chips += difference;
        if (chips == 0) {
            outOfGame = true;
        }
    }


    //rolls 3 dice and returns a string depending on whether the player won
    public String rollDice(Player player) {
        Boolean roundFinished = false;
        String win = "score";


        while (!roundFinished) {
            int dice1 = (int)(Math.random() * 6) + 1;
            int dice2 = (int)(Math.random() * 6) + 1;
            int dice3 = (int)(Math.random() * 6) + 1;

            if ((dice1 == dice2 && dice2 == dice3) || dice.check456(dice1, dice2, dice3)) {
                System.out.println(player.getName() + " has rolled a " + dice1 + ", " + dice2 + ", " + dice3 + "\n" + player.getName() + " automatically" + ConsoleUtility.YELLOW +  " wins" + ConsoleUtility.RESET + " this round!");
                win = "win";
                roundFinished = true;
            } else if (dice.check123(dice1, dice2, dice3)) {
                System.out.println(player.getName() + " has rolled a " + dice1 + ", " + dice2 + ", " + dice3 + "\n" + player.getName() + " automatically" + ConsoleUtility.RED +  " loses" + ConsoleUtility.RESET + " this round!");
                win = "lose";
                roundFinished = true;
            } else if (dice.checkDouble(dice1, dice2, dice3) != 0) {
                roundScore = dice.checkDouble(dice1, dice2, dice3);
                System.out.println(player.getName() + " has rolled a " + dice1 + ", " + dice2 + ", " + dice3 + "\n" + player.getName() + " got a double!");
                System.out.println("Their round score is " + ConsoleUtility.BLUE + roundScore + ConsoleUtility.RESET);
                roundFinished = true;
            }
        }
        return win;
    }

    // resets the game
    public void resetGame() {
        chips = 100;
        currentWager = 0;
        outOfGame = false;
        roundWin = false;
    }




}

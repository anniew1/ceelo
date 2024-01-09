public class Player {

    private int chips;
    private String name;

    private int currentWager;

    private Boolean outOfGame;

    public Player(String name) {
        chips = 100;
        this.name = name;
        currentWager = 0;
        outOfGame = false;
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

    public void setCurrentWager(int wager) {
        currentWager = wager;
    }

    // changes the amount of chips player has after round
    public void changeChips(int difference) {
        chips += difference;
    }

    //rolls 3 dice and returns a string depending on whether the player won
    public void rolldice() {
        Boolean roundFinished = false;
        String win = "";
        int roundScore;

        while (!roundFinished) {
            int dice1 = (int)(Math.random() * 6) + 1;
            int dice2 = (int)(Math.random() * 6) + 1;
            int dice3 = (int)(Math.random() * 6) + 1;

            if ((dice1 == dice2 && dice2 == dice3) || check456(dice1, dice2, dice3)) {
                System.out.println("The player has rolled a" + dice1 + ", " + dice2 + ", " + dice3 + "\nThe player automatically wins this round!");
                win = "win";
            } else if (check123(dice1, dice2, dice3)) {
                System.out.println("The player has rolled a" + dice1 + ", " + dice2 + ", " + dice3 + "\nThe player automatically loses this round!");
                win = "lose";
            } else if (dice1 == dice2) {
                roundScore = dice3;
            } else if (dice2 == dice3) {
                roundScore = dice1;
            } else if (dice1 == dice3) {
                roundScore = dice2;
            }
        }
    }

    // checks whether the three dice that the player rolled are the numbers 4, 5, 6
    private Boolean check456(int dice1, int dice2, int dice3) {
        String allThree = "";
        for (int i = 4; i < 7; i++) {
            if (dice1 == i || dice2 == i ||dice3 == i) {
                allThree += "a";
            }
        }
        if (allThree.equals("aaa")) {
            return true;
        }
        return false;
    }

    // checks whether the three dice that the player rolled are the numbers 1, 2, 3
    private Boolean check123(int dice1, int dice2, int dice3) {
        String allThree = "";
        for (int i = 1; i < 4; i++) {
            if (dice1 == i || dice2 == i ||dice3 == i) {
                allThree += "a";
            }
        }
        if (allThree.equals("aaa")) {
            return true;
        }
        return false;
    }

}

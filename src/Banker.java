public class Banker {
    private int chips;
    private int roundScore;

    public Banker() {
        chips = 1000;
    }

    // returns the number of chips banker has
    public int getChips() {
        return chips;
    }

    // returns the score that the banker has rolled
    public int getRoundScore() {
        return roundScore;
    }

    // changes the amount of chips banker has after round
    public void changeChips(int difference) {
        chips += difference;
    }

    //rolls 3 dice and returns a string depending on whether the banker won
    public String rolldice() {
        Boolean roundFinished = false;

        while (!roundFinished) {
            int dice1 = (int)(Math.random() * 6) + 1;
            int dice2 = (int)(Math.random() * 6) + 1;
            int dice3 = (int)(Math.random() * 6) + 1;

            if ((dice1 == dice2 && dice2 == dice3) || check456(dice1, dice2, dice3)) {
                System.out.println("The banker has rolled a" + dice1 + ", " + dice2 + ", " + dice3 + "\nThe banker automatically wins this round!");
                return "win";
            } else if (check123(dice1, dice2, dice3)) {
                System.out.println("The banker has rolled a" + dice1 + ", " + dice2 + ", " + dice3 + "\nThe banker automatically loses this round!");
                return "lose";
            } else if (dice1 == dice2) {
                roundScore = dice3;
                return "score";
            } else if (dice2 == dice3) {
                roundScore = dice1;
                return "score";
            } else if (dice1 == dice3) {
                roundScore = dice2;
                return "score";
            }
        }
        return "nothing";
    }

    // checks whether the three dice that the banker rolled are the numbers 4, 5, 6
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

    // checks whether the three dice that the banker rolled are the numbers 1, 2, 3
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

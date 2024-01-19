public class Banker {
    private int chips;
    private int roundScore;
    private Dice dice;
    private int difference;


    public Banker() {
        chips = 1000;
        difference = 0;
        dice = new Dice();
    }


    // returns the number of chips banker has
    public int getChips() {
        return chips;
    }


    // returns the score that the banker has rolled
    public int getRoundScore() {
        return roundScore;
    }

    // returns the chip difference from the round
    public int getDifference() {
        return difference;
    }

    // changes the amount of chips banker has after round
    public void changeChips(int difference) {
        chips += difference;
    }

    // changes the amount that the banker loses or gains in the round
    public void changeDifference(int change) {
        difference += change;
    }

    // reset round gains/losses
    public void resetDifference() {
        difference = 0;
    }

    //rolls 3 dice and returns a string depending on whether the banker won
    public String rollDice() {
        Boolean roundFinished = false;


        while (!roundFinished) {
            int dice1 = (int)(Math.random() * 6) + 1;
            int dice2 = (int)(Math.random() * 6) + 1;
            int dice3 = (int)(Math.random() * 6) + 1;


            if ((dice1 == dice2 && dice2 == dice3) || dice.check456(dice1, dice2, dice3)) {
                System.out.println("The banker has rolled a " + dice1 + ", " + dice2 + ", " + dice3 + "\nThe banker automatically" + ConsoleUtility.YELLOW +  " wins" + ConsoleUtility.RESET + " this round!");
                return "win";
            } else if (dice.check123(dice1, dice2, dice3)) {
                System.out.println("The banker has rolled a " + dice1 + ", " + dice2 + ", " + dice3 + "\nThe banker automatically" + ConsoleUtility.RED +  " loses" + ConsoleUtility.RESET + " this round!");
                return "lose";
            } else if (dice.checkDouble(dice1, dice2, dice3) != 0) {
                roundScore = dice.checkDouble(dice1, dice2, dice3);
                System.out.println("The banker has rolled a " + dice1 + ", " + dice2 + ", " + dice3 + "\nThat is a double!");
                System.out.println("The banker's score for this round is " + ConsoleUtility.BLUE + roundScore + ConsoleUtility.RESET);
                return "score";
            }
        }
        return "nothing";
    }

    // resets the game for a new round
    public void resetGame() {
        chips = 1000;
        difference = 0;
    }


}





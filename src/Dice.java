public class Dice {

    public Dice() {}

    public int rollDice() {
        return (int)(Math.random() * 6) + 1;
    }

    // checks whether the three dice rolled are the numbers 4, 5, 6
    public Boolean check456(int dice1, int dice2, int dice3) {
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

    // checks whether the three dice rolled are the numbers 1, 2, 3
    public Boolean check123(int dice1, int dice2, int dice3) {
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

    // checks whether the three dice rolled is a double and if it is returns a score
    public int checkDouble(int dice1, int dice2, int dice3) {
        if (dice1 == dice2) {
            return dice3;
        } else if (dice2 == dice3) {
            return dice1;
        } else if (dice3 == dice1) {
            return dice2;
        } else {
            return 0;
        }
    }

    // checks whether the player won the round or not
    public Boolean winRound(int playerScore, int bankerScore) {
        if (playerScore >= bankerScore) {
            return true;
        } else {
            return false;
        }
    }
}

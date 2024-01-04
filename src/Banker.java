public class Banker {
    private int chips;

    public Banker() {
        chips = 1000;
    }

    // returns the number of chips banker has
    public int getChips() {
        return chips;
    }

    // changes the amount of chips banker has after round
    public void changeChips(int difference) {
        chips += difference;
    }
}

public class Player {

    private int chips;
    private String name;

    public Player(String name) {
        chips = 100;
        this.name = name;
    }

    // returns the number of chips player has
    public int getChips() {
        return chips;
    }

    // returns the name of the player
    public String getName() {
        return name;
    }

    // changes the amount of chips player has after round
    public void changeChips(int difference) {
        chips += difference;
    }
}

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

}

public class Dice {

    public Dice() {}

    public int rollDice() {
        return (int)(Math.random() * 6) + 1;
    }
}

package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;

public class Player extends DynamicObject {
    private static final int stepSize = 1;
    private int coins;
    private boolean won;
    private boolean gameOver;

    public Player(int xPosition, int yPosition) {
        super(xPosition, yPosition, Configuration.PLAYER_SPRITE);
    }

    public void move(Direction direction) {
        super.move(direction, stepSize);
    }

    @Override
    public String toString() {
        return "Player{[" + this.xPosition + ":" + this.yPosition + "]}";
    }

    public int getCoins() {
        return this.coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void won() {
        this.won = true;
    }

    public boolean isWon() {
        return this.won;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void gameOver() {
        this.gameOver = true;
    }
}

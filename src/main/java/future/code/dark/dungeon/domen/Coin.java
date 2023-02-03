package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;

public class Coin extends GameObject {
    private boolean collected;

    public Coin(int xPosition, int yPosition) {
        super(xPosition, yPosition, Configuration.COIN_SPRITE);
    }

    public boolean isCollected() {
        return this.collected;
    }

    public void setCollected(boolean earned) {
        this.collected = earned;
    }
}

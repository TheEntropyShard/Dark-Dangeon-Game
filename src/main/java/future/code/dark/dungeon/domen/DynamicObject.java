package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

public abstract class DynamicObject extends GameObject {
    public DynamicObject(int xPosition, int yPosition, String imagePath) {
        super(xPosition, yPosition, imagePath);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        private static final Direction[] VALUES = Direction.values();

        public static Direction getRandomDirection() {
            return Direction.VALUES[(int) (Math.random() * Direction.VALUES.length)];
        }
    }

    public void move(Direction direction, int distance) {
        int tmpXPosition = this.getXPosition();
        int tmpYPosition = this.getYPosition();

        switch (direction) {
            case UP -> tmpYPosition -= distance;
            case DOWN -> tmpYPosition += distance;
            case LEFT -> tmpXPosition -= distance;
            case RIGHT -> tmpXPosition += distance;
        }

        if(GameMaster.getInstance().getMap().getMap()[tmpYPosition][tmpXPosition] == Configuration.EXIT_CHARACTER) {
            if(this instanceof Player p) {
                if(p.getCoins() != Configuration.MAX_COINS) {
                    return;
                } else {
                    p.won();
                }
            }
            if(this instanceof Enemy) {
                return;
            }
        }

        if (isAllowedSurface(tmpXPosition, tmpYPosition)) {
            this.xPosition = tmpXPosition;
            this.yPosition = tmpYPosition;
        }
    }

    private Boolean isAllowedSurface(int x, int y) {
        return GameMaster.getInstance().getMap().getMap()[y][x] != Configuration.WALL_CHARACTER;
    }
}

package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.util.FileUtils;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;

import static future.code.dark.dungeon.config.Configuration.SPRITE_SIZE;

public abstract class GameObject {
    private final Image image;
    protected int xPosition;
    protected int yPosition;

    public GameObject(int xPosition, int yPosition, String imagePath) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        this.image = FileUtils.loadImage(imagePath);
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public void render(Graphics graphics) {
        if(this instanceof Coin c) {
            if(c.isCollected()) {
                return;
            }
        }
        graphics.drawImage(this.image, this.xPosition * SPRITE_SIZE, this.yPosition  * SPRITE_SIZE, null);
    }
}

package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.GameFrame;
import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.util.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static future.code.dark.dungeon.config.Configuration.SPRITE_SIZE;

public abstract class AnimatedObject extends GameObject {
    private int frameCounter;
    private final BufferedImage[] frames;

    public AnimatedObject(int xPosition, int yPosition, String imagePath) {
        super(xPosition, yPosition, imagePath);
        this.frames = new BufferedImage[Configuration.MAX_FRAMES];
        for(int i = 0; i < Configuration.MAX_FRAMES; i++) {
            this.frames[i] = FileUtils.loadImage(imagePath + "tile00" + i + ".png");
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(this.frames[this.frameCounter], this.xPosition * SPRITE_SIZE, this.yPosition  * SPRITE_SIZE, null);

        if(GameFrame.ticks % 10 == 0) {
            this.frameCounter++;
            if(frameCounter >= Configuration.MAX_FRAMES) {
                this.frameCounter = 0;
            }
        }
    }
}

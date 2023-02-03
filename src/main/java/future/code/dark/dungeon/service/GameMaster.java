package future.code.dark.dungeon.service;

import future.code.dark.dungeon.GameFrame;
import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.domen.*;
import future.code.dark.dungeon.util.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GameMaster {
    private static GameMaster instance;
    private static Image wonImage = FileUtils.loadImage("/assets/victory.jpg");
    private static Image gameOverImage = FileUtils.loadImage("/assets/game_over_screen.jpeg");

    private final Map map;
    private final List<GameObject> gameObjects;

    public static synchronized GameMaster getInstance() {
        if(instance == null) {
            instance = new GameMaster();
        }
        return instance;
    }

    private GameMaster() {
        try {
            this.map = new Map(Configuration.MAP_FILE_PATH);
            this.gameObjects = initGameObjects(map.getMap());
            wonImage = wonImage.getScaledInstance(this.map.getWidth() * Configuration.SPRITE_SIZE,
                    this.map.getHeight() * Configuration.SPRITE_SIZE, BufferedImage.SCALE_SMOOTH);
            gameOverImage = gameOverImage.getScaledInstance(this.map.getWidth() * Configuration.SPRITE_SIZE,
                    this.map.getHeight() * Configuration.SPRITE_SIZE, BufferedImage.SCALE_SMOOTH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<GameObject> initGameObjects(char[][] map) {
        List<GameObject> gameObjects = new ArrayList<>();
        Consumer<GameObject> addGameObject = gameObjects::add;
        Consumer<Enemy> addEnemy = enemy -> {
            if(Configuration.ENEMIES_ACTIVE) gameObjects.add(enemy);
        };

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                switch(map[i][j]) {
                    case Configuration.EXIT_CHARACTER -> addGameObject.accept(new Exit(j, i));
                    case Configuration.COIN_CHARACTER -> addGameObject.accept(new Coin(j, i));
                    case Configuration.ENEMY_CHARACTER -> addEnemy.accept(new Enemy(j, i));
                    case Configuration.PLAYER_CHARACTER -> addGameObject.accept(new Player(j, i));
                }
            }
        }

        return gameObjects;
    }

    public void renderFrame(Graphics graphics) {
        Player player = this.getPlayer();
        if(player.isWon()) {
            graphics.drawImage(GameMaster.wonImage, 0, 0, null);
            return;
        }
        if(player.isGameOver()) {
            graphics.drawImage(GameMaster.gameOverImage, 0, 0, null);
            return;
        }
        this.getMap().render(graphics);
        this.getStaticObjects().forEach(gameObject -> {
            if(gameObject instanceof Coin c && !c.isCollected()) {
                if(c.getXPosition() == player.getXPosition() && c.getYPosition() == player.getYPosition()) {
                    player.addCoins(1);
                    c.setCollected(true);
                }
            }
            gameObject.render(graphics);
        });
        this.getEnemies().forEach(enemy -> {
            if(enemy.getXPosition() == player.getXPosition() && enemy.getYPosition() == player.getYPosition()) {
                player.gameOver();
            }
            if(GameFrame.ticks % 15 == 0) {
                enemy.move(DynamicObject.Direction.getRandomDirection(), 1);
            }
            enemy.render(graphics);
        });
        player.render(graphics);
        graphics.setColor(Color.WHITE);
        graphics.drawString(player.toString(), 10, 20);
        graphics.drawString("Coins: " + player.getCoins() + "/9", 10, 35);
    }

    public Player getPlayer() {
        return (Player) gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Player)
                .findFirst()
                .orElseThrow();
    }

    private List<GameObject> getStaticObjects() {
        return gameObjects.stream()
                .filter(gameObject -> !(gameObject instanceof DynamicObject))
                .collect(Collectors.toList());
    }

    private List<Enemy> getEnemies() {
        return gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Enemy)
                .map(gameObject -> (Enemy) gameObject)
                .collect(Collectors.toList());
    }

    public Map getMap() {
        return this.map;
    }
}

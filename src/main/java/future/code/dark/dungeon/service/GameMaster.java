package future.code.dark.dungeon.service;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.domen.Coin;
import future.code.dark.dungeon.domen.DynamicObject;
import future.code.dark.dungeon.domen.Enemy;
import future.code.dark.dungeon.domen.Exit;
import future.code.dark.dungeon.domen.GameObject;
import future.code.dark.dungeon.domen.Map;
import future.code.dark.dungeon.domen.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GameMaster {

    private static GameMaster instance;

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
        this.getMap().render(graphics);
        this.getStaticObjects().forEach(gameObject -> gameObject.render(graphics));
        this.getEnemies().forEach(gameObject -> gameObject.render(graphics));
        Player player = this.getPlayer();
        player.render(graphics);
        graphics.setColor(Color.WHITE);
        graphics.drawString(player.toString(), 10, 20);
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

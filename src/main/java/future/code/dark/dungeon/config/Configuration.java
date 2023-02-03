package future.code.dark.dungeon.config;

public enum Configuration {
    ;

    public static final String GAME_NAME = "Dark Dungeon";
    public static final String MAP_FILE_PATH = "/maps/map.ber";
    public static final Boolean ENEMIES_ACTIVE = true;
    public static final int GAME_FRAMES_PER_SECOND = 40;
    public static final char WALL_CHARACTER = '1';
    public static final char EXIT_CHARACTER = 'E';
    public static final char LAND_CHARACTER = '0';
    public static final char PLAYER_CHARACTER = 'P';
    public static final char ENEMY_CHARACTER = 'G';
    public static final char COIN_CHARACTER = 'C';
    public static final Integer SPRITE_SIZE = 64;
    public static final String PLAYER_SPRITE = "/assets/hero/";
    public static final String GHOST_SPRITE = "/assets/ghost/";
    public static final String WALL_SPRITE = "/assets/land/wall.png";
    public static final String LAND_SPRITE = "/assets/land/ground.png";
    public static final String EXIT_SPRITE = "/assets/land/out.png";
    public static final String COIN_SPRITE = "/assets/land/collectible.png";
    public static final int MAX_COINS = 9;
    public static final int MAX_FRAMES = 4;
}

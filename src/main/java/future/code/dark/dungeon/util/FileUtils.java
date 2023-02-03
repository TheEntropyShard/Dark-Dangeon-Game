package future.code.dark.dungeon.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FileUtils {
    public static List<String> readFile(InputStream is) {
        Scanner scanner = new Scanner(is);
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        return lines;
    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(FileUtils.class.getResourceAsStream(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

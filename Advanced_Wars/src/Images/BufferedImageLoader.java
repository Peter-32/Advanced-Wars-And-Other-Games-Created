package Images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Peter on 8/14/2016.
 */
public class BufferedImageLoader {

    //// METHODS

    public BufferedImage loadImage(String path) throws IOException {

        URL url = new URL(path);
        BufferedImage img = ImageIO.read(url);
        return img;
    }

}

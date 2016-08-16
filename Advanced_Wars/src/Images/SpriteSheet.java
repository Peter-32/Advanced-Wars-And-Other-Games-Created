package Images;

import java.awt.image.BufferedImage;

/**
 * Created by Peter on 8/14/2016.
 */
public class SpriteSheet {

    //// FIELDS



    public BufferedImage sheet;

    //// CONSTRUCTOR

    public SpriteSheet(BufferedImage sheet) {

        this.sheet = sheet;


    } // END OF Images.SpriteSheet CONSTRUCTOR

    public BufferedImage grabSprite(int x, int y, int width, int height) {

        BufferedImage sprite = sheet.getSubimage(x, y, width, height);
        return sprite;

    }




}

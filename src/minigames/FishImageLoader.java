package minigames;

import pokeshinto.LoaderOfImages;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Loads all necessary images for the Minigames
 *
 * @author Jérémie Beaudoin-Dion
 *
 */
class FishImageLoader extends LoaderOfImages {

    static final int FISHROD_WIDTH = 40;

    static final int FISH_SMALL_SIZE = 23;
    static final int FISH_NORMAL_SIZE = 40;
    static final int FISH_RARE_SIZE = 31;

    private BufferedImage fishRod;
    private BufferedImage background;

    private BufferedImage fishNormal;
    private BufferedImage fishSmall;
    private BufferedImage fishRare;
    private BufferedImage fishRarest;

    /**
     * Basic constructor
     * @throws IOException : if the image is missing
     */
    FishImageLoader() throws IOException {

        loadAllImages();

    }

    /**
     * Loads all image from file
     * @throws IOException : if the image is missing
     */
    private void loadAllImages() throws IOException {

        background = toCompatibleImage(ImageIO.read(new File("FishBackground.png")));

        fishRod = toCompatibleImage(ImageIO.read(new File("FishRod.png")));

        fishNormal = toCompatibleImage(ImageIO.read(new File("Fish_Normal.png")));
        fishSmall = toCompatibleImage(ImageIO.read(new File("Fish_Small.png")));
        fishRare = toCompatibleImage(ImageIO.read(new File("Fish_Rare.png")));
        fishRarest = toCompatibleImage(ImageIO.read(new File("Fish_Rarest.png")));


    }

    /**
     * Getters
     */
    BufferedImage getBackground() {
        return background;
    }

    BufferedImage getFishRod() {
        return fishRod;
    }

    BufferedImage getFishNormal(int xInset) {
        return fishNormal.getSubimage(xInset, 0, FISH_NORMAL_SIZE, FISH_NORMAL_SIZE);
    }

    BufferedImage getFishSmall(int xInset) {
        return fishSmall.getSubimage(xInset, 0, FISH_SMALL_SIZE, FISH_SMALL_SIZE);
    }

    BufferedImage getFishRare(int xInset) {
        return fishRare.getSubimage(xInset, 0, FISH_RARE_SIZE, FISH_RARE_SIZE);
    }

    BufferedImage getFishRarest() {
        return fishRarest;
    }

}

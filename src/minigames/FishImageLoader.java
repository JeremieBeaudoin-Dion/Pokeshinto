package minigames;

import images.LoaderOfImages;

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
    private BufferedImage fishShark;
    private BufferedImage fishStar;

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

        background = toCompatibleImage(ImageIO.read(new File("Resources/FishBackground.png")));

        fishRod = toCompatibleImage(ImageIO.read(new File("Resources/FishRod.png")));

        fishNormal = toCompatibleImage(ImageIO.read(new File("Resources/Fish_Normal.png")));
        fishSmall = toCompatibleImage(ImageIO.read(new File("Resources/Fish_Small.png")));
        fishRare = toCompatibleImage(ImageIO.read(new File("Resources/Fish_Rare.png")));
        fishShark = toCompatibleImage(ImageIO.read(new File("Resources/Fish_Shark.png")));
        fishStar = toCompatibleImage(ImageIO.read(new File("Resources/Fish_Star.png")));

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

    BufferedImage getFishShark() {
        return fishShark;
    }

    BufferedImage getFishStar() {
        return fishStar;
    }

}

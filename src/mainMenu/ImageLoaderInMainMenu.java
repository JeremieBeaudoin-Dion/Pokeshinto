package mainMenu;

import pokeshinto.LoaderOfImages;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Loads main menu images
 *
 * @author Jérémie Beaudoin-Dion
 */
class ImageLoaderInMainMenu extends LoaderOfImages {

    private BufferedImage background;

    /**
     * Basic constructor
     * @throws IOException if an image is missing
     */
    ImageLoaderInMainMenu() throws IOException {

        loadAllImages();

    }

    /**
     * Loads all image from file
     * @throws IOException : if the image is missing
     */
    private void loadAllImages() throws IOException {

        background = toCompatibleImage(ImageIO.read(new File("MainMenu.png")));

    }

    BufferedImage getBackground() {
        return background;
    }

}

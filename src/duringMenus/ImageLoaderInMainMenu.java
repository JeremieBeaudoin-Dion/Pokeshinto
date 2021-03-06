package duringMenus;

import images.Position;
import images.LoaderOfImages;

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

    private BufferedImage sliderBright;
    private BufferedImage sliderDark;

    private Position sliderSize = new Position(290, 68);

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

        sliderBright = toCompatibleImage(ImageIO.read(new File("Resources/SliderBright.png")));
        sliderDark = toCompatibleImage(ImageIO.read(new File("Resources/SliderDark.png")));

    }

    BufferedImage getSliderBright() {
        return sliderBright;
    }

    BufferedImage getSliderDark() {
        return sliderDark;
    }

    Position getSliderSize() {
        return sliderSize;
    }
}

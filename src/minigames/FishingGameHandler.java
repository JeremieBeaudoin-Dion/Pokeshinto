package minigames;

import images.*;
import images.Image;
import pokeshinto.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple fishing game with a rod
 *
 * @author Jérémie Beaudoin-Dion
 */
class FishingGameHandler {

    private FishImageLoader imageLoader;
    private FishFactory fishFactory;

    private boolean gameIsFinished;

    private Position fishingRodPosition;

    private int amountOfMoney;

    private static final Font FONT_MONEY = new Font("Century Schoolbook", Font.BOLD, 35);
    private static final Color COLOR_TEXT_YELLOW = new Color(190, 170, 115);

    /**
     * Constructor
     *
     * @throws IOException : if an image is missing
     */
    FishingGameHandler() throws IOException {
        imageLoader = new FishImageLoader();
    }

    void startNewGame() {
        gameIsFinished = false;
        amountOfMoney = 0;
        fishFactory = new FishFactory();
        fishingRodPosition = new Position((Game.WINDOW_WIDTH / 2) - FishImageLoader.FISHROD_WIDTH, -Game.WINDOW_HEIGHT);
    }

    /**
     * Handles input and does the right action with it
     */
    void doInput(String myInput) {

        if(myInput.equals("Left")){
            // do nothing for now

        } else if(myInput.equals("Right")){
            // do nothing for now

        } else if(myInput.equals("Decide")){

            if (fishingRodPosition.getY() > -Game.WINDOW_HEIGHT - 10) {
                fishingRodPosition.addY(-8);
            }

        } else if(myInput.equals("Escape")){
            gameIsFinished = true;
        }

    }

    /**
     * Returns all physical objects to show in the game
     */
    List<PhysicalObject> get() {
        List<PhysicalObject> allObjects = new LinkedList<>();

        allObjects.add(new Image(new Position(0, 0), Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, imageLoader.getBackground()));

        allObjects.add(new Text(new Position(650, 50), "Money: " + amountOfMoney, FONT_MONEY, "Left", COLOR_TEXT_YELLOW));

        allObjects.add(new Image(fishingRodPosition, FishImageLoader.FISHROD_WIDTH, Game.WINDOW_HEIGHT, imageLoader.getFishRod()));

        allObjects.addAll(getAllFish());

        return allObjects;
    }

    private List<PhysicalObject> getAllFish() {
        List<PhysicalObject> allObjects = new LinkedList<>();
        Iterator<Fish> allFish = fishFactory.getAllFish().iterator();
        Fish currentFish;

        while(allFish.hasNext()) {
            currentFish = allFish.next();

            BufferedImage fishImage = null;

            if (currentFish instanceof CommonFish) {
                fishImage = getCommonFishImage((CommonFish) currentFish);
            } else if (currentFish instanceof Shark) {
                fishImage = imageLoader.getFishShark();

            } else if (currentFish instanceof StarFish) {
                fishImage = imageLoader.getFishStar();
            }

            allObjects.add(new Image(currentFish.getPosition(), currentFish.getSize().getX(), currentFish.getSize().getY(), fishImage));
        }


        return allObjects;
    }

    private BufferedImage getCommonFishImage(CommonFish fish) {
        int xSpriteModifier = 0;

        if (fish.isCaught()) {
            xSpriteModifier = fish.getSize().getX();

        } else if (fish.isReversed()) {
            xSpriteModifier = fish.getSize().getX() * 2;
        }

        if (fish.getType() == CommonFish.NORMAL_TYPE) {
            return imageLoader.getFishNormal(xSpriteModifier);

        } else if (fish.getType() == CommonFish.SMALL_TYPE) {
            return imageLoader.getFishSmall(xSpriteModifier);

        } else if (fish.getType() == CommonFish.RARE_TYPE) {
            return imageLoader.getFishRare(xSpriteModifier);
        }

        throw new IllegalArgumentException("The type of the fish " + fish.getType() + " is not handled in getCommonFishImage.");
    }

    /**
     * Called every frame
     */
    void update() {
        if (fishingRodPosition.getY() < 0) {
            fishingRodPosition.addY(1);
        }

        fishFactory.update(getFishingRodCatchPosition());

        if (fishFactory.hasEncounteredAShark()) {
            fishingRodPosition.setY(-Game.WINDOW_HEIGHT*2);
        }

        amountOfMoney += fishFactory.cashIn();
    }

    private Position getFishingRodCatchPosition() {
        return new Position(fishingRodPosition.getX() + FishImageLoader.FISHROD_WIDTH/2,
                fishingRodPosition.getY() + Game.WINDOW_HEIGHT - FishImageLoader.FISHROD_WIDTH/2);
    }

    boolean isFinished() {
        return gameIsFinished;
    }

    int getAmountOfMoney() {
        return amountOfMoney;
    }

}

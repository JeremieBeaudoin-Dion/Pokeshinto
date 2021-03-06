package minigames;

import images.PhysicalObject;
import pokeshinto.Player;

import java.io.IOException;
import java.util.List;

/**
 * Handles input and sends it to the correct minigame, and
 * returns all physical objects to show in imageHandler
 *
 * @author Jérémie Beaudoin-Dion
 */
public class MinigameHandler {

    public static final String fishingMiniGame = "Fishing";

    private String currentMiniGame;
    private boolean gameIsFinished;

    private FishingGameHandler fishingGameHandler;

    /**
     * Constructor
     *
     * @throws IOException : if an image is missing
     */
    public MinigameHandler() throws IOException {

        fishingGameHandler = new FishingGameHandler();
        gameIsFinished = true;
    }

    public void startMinigame(String minigame) {
        if (minigame.equals(fishingMiniGame)) {
            fishingGameHandler.startNewGame();

        } else {
            throw new IllegalStateException("The state " + minigame + " is not handled in startMinigame.");
        }

        gameIsFinished = false;
        currentMiniGame = minigame;
    }

    /**
     * Sends the input to the correct minigame
     */
    public void doInput(String myInput) {

        if (currentMiniGame.equals("Fishing")) {
            fishingGameHandler.doInput(myInput);

        }

    }

    /**
     * Returns all physical objects to show in the games
     */
    public List<PhysicalObject> get() {
        if (currentMiniGame.equals("Fishing")) {
            return fishingGameHandler.get();

        }

        throw new IllegalStateException("The state " + currentMiniGame + " is not handled in MinigameHandler.");
    }

    /**
     * Called every frame
     */
    public void update() {
        if (gameIsFinished) {
            return;
        }

        if (currentMiniGame.equals(fishingMiniGame)) {
            fishingGameHandler.update();

            gameIsFinished = fishingGameHandler.isFinished();

            if (gameIsFinished) {
                Player.money += fishingGameHandler.getAmountOfMoney();
            }
        }
    }

    /**
     * Getter
     */
    public boolean isGameFinished() {
        return gameIsFinished;
    }
}

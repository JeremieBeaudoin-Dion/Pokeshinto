package images;

import pokeshinto.Game;
import pokeshinto.Player;

/**
 * The camera object determines what the player is seeing. It has a position
 * according to the Player object or the current scene.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Camera {

    private Position position;

    private CardinalDirection insets = new CardinalDirection(Game.WINDOW_HEIGHT/3, Game.WINDOW_HEIGHT/2,
            Game.WINDOW_WIDTH/3, Game.WINDOW_WIDTH/3);

    public Camera() {
        this.position = new Position(0, 0);
    }

    public void placePlayerInTheCenterOfTheScreen() {
        this.position = new Position(Player.pixelPosition.getX() - Game.WINDOW_WIDTH/2,
                Player.pixelPosition.getY() - Game.WINDOW_HEIGHT/2);
    }

    /**
     * Places the camera at (0, 0)
     */
    public void setToZero() {
        this.position.setX(0);
        this.position.setY(0);
    }

    /**
     * Called every frame
     */
    public void update() {
        adjustPositionAccordingToPlayer();
    }

    /**
     * Adjust the camera's position according to the player object
     */
    private void adjustPositionAccordingToPlayer() {

        fixEastPosition();
        fixNorthPosition();
        fixWestPosition();
        fixSouthPosition();

    }

    // EAST
    private void fixEastPosition() {
        if (eastPositionIsWrong()) {
            setXPositionToPlayer();
            deduceXInsets();
            setXPositionOverZero();
        } else {
            setXPositionOverZero();
        }
    }

    private boolean eastPositionIsWrong() {
        return Player.pixelPosition.getX() - position.getX() < insets.getEast();
    }

    private void setXPositionToPlayer() {
        this.position.setX(Player.pixelPosition.getX());
    }

    private void deduceXInsets() {
        position.addX(-insets.getEast());
    }

    private void setXPositionOverZero() {
        if (position.getX() < 0) {
            position.setX(0);
        }
    }

    // NORTH
    private void fixNorthPosition() {
        if (northPositionIsWrong()) {
            setYPositionToPlayer();
            deduceYInsets();
            setYPositionOverZero();
        } else {
            setYPositionOverZero();
        }
    }

    private boolean northPositionIsWrong() {
        return Player.pixelPosition.getY() - position.getY() < insets.getNorth();
    }

    private void setYPositionToPlayer() {
        this.position.setY(Player.pixelPosition.getY());
    }

    private void deduceYInsets() {
        position.addY(-insets.getNorth());
    }

    private void setYPositionOverZero() {
        if (position.getY() < 0) {
            position.setY(0);
        }
    }

    // WEST
    private void fixWestPosition() {
        if (westPositionIsWrong()) {
            setWestPositionToPlayer();
            addXInset();
            setWestPositionInsideMapLimit();
        } else {
            setWestPositionInsideMapLimit();
        }
    }

    private boolean westPositionIsWrong() {
        return Player.pixelPosition.getX() - position.getX() > Game.WINDOW_WIDTH - insets.getWest();
    }

    private void setWestPositionToPlayer() {
        position.setX(Player.pixelPosition.getX() - Game.WINDOW_WIDTH);
    }

    private void addXInset() {
        position.addX(insets.getWest());
    }

    private void setWestPositionInsideMapLimit() {
        if (position.getX() > Player.currentMapSize.getX() - Game.WINDOW_WIDTH) {
            position.setX(Player.currentMapSize.getX() - Game.WINDOW_WIDTH);
        }
    }

    // SOUTH
    private void fixSouthPosition() {
        if (southPositionIsWrong()) {
            setSouthPositionToPlayer();
            addYInset();
            setSouthPositionInsideMapLimit();
        } else {
            setSouthPositionInsideMapLimit();
        }
    }

    private boolean southPositionIsWrong() {
        return Player.pixelPosition.getY() - position.getY() > Game.WINDOW_HEIGHT - insets.getSouth();
    }

    private void setSouthPositionToPlayer() {
        position.setY(Player.pixelPosition.getY() - Game.WINDOW_HEIGHT);
    }

    private void addYInset() {
        position.addY(insets.getSouth());
    }

    private void setSouthPositionInsideMapLimit() {
        if (position.getY() > Player.currentMapSize.getY() - Game.WINDOW_HEIGHT) {
            position.setY(Player.currentMapSize.getY() - Game.WINDOW_HEIGHT);
        }
    }

    // Getters
    public Position getPosition() {
        return position;
    }
}

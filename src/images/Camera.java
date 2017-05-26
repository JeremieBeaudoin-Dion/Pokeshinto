package images;

import pokeshinto.Game;
import pokeshinto.Player;
import world.WorldImageLoader;

/**
 * The camera object determines what the player is seeing. It has a position
 * according to the Player object or the current scene.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class Camera {

    private Player player;
    private Position position;
    private Position currentMapSize;

    private Position insets = new Position(Game.WINDOW_WIDTH/5, Game.WINDOW_HEIGHT/5);

    public Camera(Player player) {
        this.player = player;
        this.position = player.getPosition();
        currentMapSize = new Position(0, 0);
    }

    /**
     * @param size the size of the array of the background of the map
     */
    public void setCurrentMapSize(Position size) {
        this.currentMapSize.setX(size.getX() * WorldImageLoader.TILE_WIDTH);
        this.currentMapSize.setY(size.getY() * WorldImageLoader.TILE_WIDTH);
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
        }
    }

    private boolean eastPositionIsWrong() {
        return player.getPosition().getX() - position.getX() < insets.getX();
    }

    private void setXPositionToPlayer() {
        this.position.setX(player.getPosition().getX());
    }

    private void deduceXInsets() {
        position.addX(-insets.getX());
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
        }
    }

    private boolean northPositionIsWrong() {
        return player.getPosition().getY() - position.getY() < insets.getY();
    }

    private void setYPositionToPlayer() {
        this.position.setY(player.getPosition().getY());
    }

    private void deduceYInsets() {
        position.addY(-insets.getY());
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
        }
    }

    private boolean westPositionIsWrong() {
        return player.getPosition().getX() - position.getX() > Game.WINDOW_WIDTH - insets.getX();
    }

    private void setWestPositionToPlayer() {
        position.setX(player.getPosition().getX() - Game.WINDOW_WIDTH);
    }

    private void addXInset() {
        position.addX(insets.getX());
    }

    private void setWestPositionInsideMapLimit() {
        if (position.getX() > currentMapSize.getX() - Game.WINDOW_WIDTH) {
            position.setX(currentMapSize.getX() - Game.WINDOW_WIDTH);
        }
    }

    // SOUTH
    private void fixSouthPosition() {
        if (southPositionIsWrong()) {
            setSouthPositionToPlayer();
            addYInset();
            setSouthPositionInsideMapLimit();
        }
    }

    private boolean southPositionIsWrong() {
        return player.getPosition().getY() - position.getY() > Game.WINDOW_HEIGHT - insets.getY();
    }

    private void setSouthPositionToPlayer() {
        position.setY(player.getPosition().getY() - Game.WINDOW_HEIGHT);
    }

    private void addYInset() {
        position.addY(insets.getY());
    }

    private void setSouthPositionInsideMapLimit() {
        if (position.getY() > currentMapSize.getY() - Game.WINDOW_HEIGHT) {
            position.setY(currentMapSize.getY() - Game.WINDOW_HEIGHT);
        }
    }


    // Getters
    public Position getPosition() {
        return position;
    }
}

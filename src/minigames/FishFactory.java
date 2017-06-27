package minigames;

import images.Position;
import pokeshinto.Game;

import java.util.*;

/**
 * @author Jérémie Beaudoin-Dion
 */
class FishFactory {

    private final int SHORT_TIME = 100;
    private final int MEDIUM_TIME = 150;
    private final int LONG_TIME = 200;

    private final int[] allYPositions = new int[]{140, 226, 342, 468};
    private Stack<Integer> yPositions;
    private int lastPosition;

    private Random generator;

    private int timer;

    private List<Fish> allFish;

    private int moneyToCashIn;

    /**
     * Constructor
     */
    FishFactory() {
        allFish = new LinkedList<>();
        timer = LONG_TIME;
        generator = new Random();

        yPositions = new Stack<>();
        for (int i=0; i<allYPositions.length; i++) {
            yPositions.push(allYPositions[i]);
        }

        lastPosition = yPositions.pop();
    }

    /**
     * Called every frame
     */
    void update(Position rodPosition) {
        Iterator<Fish> iter = allFish.iterator();
        Fish currentFish;

        while (iter.hasNext()){
            currentFish = iter.next();

            currentFish.update(rodPosition);

            // Delete fish
            if (currentFish.getPosition().getY() <= 0) {
                moneyToCashIn += currentFish.getValue();
                iter.remove();
            }

        }

        timer--;

        if (timer == 0) {
            addANewFish();
        }
    }

    private void addANewFish() {
        int fishID = generator.nextInt(8);
        boolean isReversed = findIsReversed();

        Position fishPosition = getRandomPosition(isReversed);

        if (0 <= fishID && fishID < 4) {
            allFish.add(new Fish(Fish.NORMAL_TYPE, fishPosition, isReversed));

        } else if (4 <= fishID && fishID < 6) {
            allFish.add(new Fish(Fish.SMALL_TYPE, fishPosition, isReversed));

        } else if (fishID == 6) {
            allFish.add(new Fish(Fish.RARE_TYPE, fishPosition, isReversed));
        }

        resetTimer();
    }

    private boolean findIsReversed() {
        return generator.nextInt(4) == 0;
    }

    private Position getRandomPosition(boolean isReversed) {

        Collections.shuffle(yPositions);

        int x;
        int y;

        if (isReversed) {
            x = Game.WINDOW_WIDTH;
        } else {
            x = -60;
        }

        y = yPositions.pop();

        yPositions.push(lastPosition);

        lastPosition = y;

        return new Position(x, y);
    }

    private void resetTimer() {
        int nextTimer = generator.nextInt(2);

        if (nextTimer == 0) {
            timer = LONG_TIME;
        } else if (nextTimer == 1) {
            timer = SHORT_TIME;
        } else {
            timer = MEDIUM_TIME;
        }
    }

    /**
     * Getter
     */
    List<Fish> getAllFish() {
        return allFish;
    }

    int cashIn() {
        int amount = moneyToCashIn;
        moneyToCashIn = 0;

        return amount;
    }

}

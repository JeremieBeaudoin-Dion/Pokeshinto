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
    private final int LONGER_TIME = 300;

    private final int[] commonFishYPositions = new int[]{120, 270, 342, 468};
    private Stack<Integer> yPositions;
    private int lastPosition;

    private final int[] sharYPositions = new int[]{200, 420};
    private final int highestSharkYPosition = 50;
    private boolean spawnedLowShark;
    private boolean spawnedHighShark;

    private Random generator;

    private int commonFishTimer;
    private int starFishTimer;
    private int sharkFishTimer;

    private List<Fish> allFish;

    private int moneyToCashIn;

    private boolean hasEncounteredAShark;

    /**
     * Constructor
     */
    FishFactory() {
        allFish = new LinkedList<>();
        commonFishTimer = LONG_TIME;
        starFishTimer = SHORT_TIME;
        sharkFishTimer = MEDIUM_TIME;
        spawnedLowShark = false;
        spawnedHighShark = false;

        generator = new Random();

        yPositions = new Stack<>();
        for (int i = 0; i< commonFishYPositions.length; i++) {
            yPositions.push(commonFishYPositions[i]);
        }

        lastPosition = yPositions.pop();
    }

    /**
     * Called every frame
     */
    void update(Position rodPosition) {
        updateAllFish(rodPosition);

        addNewFish();
    }

    private void updateAllFish(Position rodPosition) {
        Iterator<Fish> iter = allFish.iterator();
        Fish currentFish;

        hasEncounteredAShark = false;

        while (iter.hasNext()){
            currentFish = iter.next();

            currentFish.update(rodPosition);

            if (currentFish instanceof Shark) {
                if (currentFish.isCaught()) {
                    hasEncounteredAShark = true;
                    moneyToCashIn += currentFish.getValue();
                    currentFish.setCaught(false);
                }

            } else if (currentFish.getPosition().getY() <= 0) {
                moneyToCashIn += currentFish.getValue();
                iter.remove();

            } else if (isOutOfBounds(currentFish)) {
                iter.remove();
            }

        }

        if (hasEncounteredAShark) {
            deleteAllCatchedFishes();
        }
    }

    private void deleteAllCatchedFishes() {
        Iterator<Fish> iter = allFish.iterator();
        Fish currentFish;

        while(iter.hasNext()) {
            currentFish = iter.next();

            if (currentFish.isCaught) {
                iter.remove();
            }
        }
    }

    private boolean isOutOfBounds(Fish fish) {
        if (fish.getPosition().getX() <= -100 || fish.getPosition().getX() >= Game.WINDOW_WIDTH * 2) {
            return true;
        }

        return false;
    }

    /**
     * Adds fishes according to time
     */
    private void addNewFish() {
        commonFishTimer--;
        starFishTimer--;
        sharkFishTimer--;

        if (commonFishTimer == 0) {
            addANewCommonFish();
        }
        if (starFishTimer == 0) {
            addANewStarFish();
        }
        if (sharkFishTimer == 0) {
            addNewShark();
        }
    }

    private void addANewCommonFish() {
        int fishID = generator.nextInt(8);
        boolean isReversed = getIsReversed();

        Position fishPosition = getRandomPosition(isReversed);

        if (0 <= fishID && fishID < 4) {
            allFish.add(new CommonFish(CommonFish.NORMAL_TYPE, fishPosition, isReversed));

        } else if (4 <= fishID && fishID < 6) {
            allFish.add(new CommonFish(CommonFish.SMALL_TYPE, fishPosition, isReversed));

        } else if (fishID == 6) {
            allFish.add(new CommonFish(CommonFish.RARE_TYPE, fishPosition, isReversed));

        }

        commonFishTimer = getNewRandomTimer();
    }

    private void addANewStarFish() {
        boolean isReversed = getIsReversed();
        Position fishPosition = getRandomPosition(isReversed);

        allFish.add(new StarFish(fishPosition, isReversed));

        starFishTimer = LONGER_TIME;
    }

    private void addNewShark() {
        int chancesToHaveALowShark = generator.nextInt(100);

        if (spawnedHighShark) {
            spawnedHighShark = false;

        } else if (chancesToHaveALowShark >= 80) {
            // create shark
            if (generator.nextInt(3) == 0) {
                add2Sharks();
            } else {
                allFish.add(new Shark(getSharkPosition()));
            }

            spawnedHighShark = true;
        }

        int chancesToHaveAHighShark = generator.nextInt(100);

        if (spawnedLowShark) {
            spawnedLowShark = false;

        } else if (chancesToHaveAHighShark >= 85) {
            allFish.add(new Shark(new Position(Game.WINDOW_WIDTH + 100, highestSharkYPosition)));
            spawnedLowShark = true;
        }

        sharkFishTimer = MEDIUM_TIME;
    }

    private void add2Sharks() {

        int firstSharkInset = generator.nextInt(5) * 10;
        int secondSharkInset = generator.nextInt(5) * 10;

        allFish.add(new Shark(new Position(Game.WINDOW_WIDTH + firstSharkInset, sharYPositions[0])));
        allFish.add(new Shark(new Position(Game.WINDOW_WIDTH + secondSharkInset, sharYPositions[1])));

    }

    /**
     * Returns randomly generated values to instanciate fishes
     */
    private boolean getIsReversed() {
        return generator.nextInt(3) == 0;
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

    private Position getSharkPosition() {
        int index = generator.nextInt(sharYPositions.length);

        return new Position(Game.WINDOW_WIDTH, sharYPositions[index]);
    }

    private int getNewRandomTimer() {
        int nextTimer = generator.nextInt(3);

        if (nextTimer == 0) {
            return LONGER_TIME;
        } else if (nextTimer == 1) {
            return LONG_TIME;
        } else {
            return MEDIUM_TIME;
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

    boolean hasEncounteredAShark() {
        return hasEncounteredAShark;
    }

}

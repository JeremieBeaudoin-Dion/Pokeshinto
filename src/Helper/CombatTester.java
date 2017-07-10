package Helper;

import combat.ActionHandlerInCombat;
import combat.ObjectHandlerInCombat;
import images.Camera;
import images.ImageHandler;
import combat.InfoHandler;
import pokeshinto.InputHandler;
import pokeshinto.MenuImageLoader;
import pokeshinto.Player;

import java.io.IOException;

/**
 * A class used to test combat
 *
 * @author Jérémie Beaudoin-Dion
 */
class CombatTester {


    /**
     * Enter your desired pokeshinto and your opponent's
     */
    String YOUR_POKESHINTO = "Kaizoyu";
    String OPPONENTS_POKESHINTO = "Shojeido";







    /**
     * Code starts here
     */

    ActionHandlerInCombat actionHandlerInCombat;
    ObjectHandlerInCombat objectHandlerInCombat;
    Player player;
    ImageHandler imageHandler;
    InputHandler inputHandler;

    public static void main(String[] args) {

        CombatTester combatTester = new CombatTester();

        while (true) {
            combatTester.update();
        }

    }

    private CombatTester() {
        objectHandlerInCombat = null;
        actionHandlerInCombat = new ActionHandlerInCombat();

        try {
            MenuImageLoader menuImageLoader = new MenuImageLoader();
            objectHandlerInCombat = new ObjectHandlerInCombat(actionHandlerInCombat, menuImageLoader);

        } catch (IOException e) {
            System.exit(0);
        }

        player = new Player();
        Player.addNewPokeshinto(InfoHandler.getPokeshinto(YOUR_POKESHINTO));
        Player.addNewPokeshinto(InfoHandler.getPokeshinto("Keyori"));

        Camera camera = new Camera();
        camera.setToZero();

        imageHandler = new ImageHandler(camera);
        inputHandler = new InputHandler(imageHandler);

        startCombat();
    }

    private void update() {
        // Gets current time for fps
        long time = System.currentTimeMillis();

        // Updates according to Game State
        imageHandler.update(objectHandlerInCombat.get());
        actionHandlerInCombat.update();

        // Handles player input
        String input = inputHandler.getInput();
        if (input != null) {
            actionHandlerInCombat.doInput(input);

            if (actionHandlerInCombat.doAction()) {
                System.out.println("Combat has ended.");
                System.out.println("Health of player: " + Player.health);
                System.exit(0);
            }
        }

        //  delay for each frame - time it took for one frame
        delayGame(System.currentTimeMillis() - time);
    }

    /**
     * Ensures that the fps is stable
     *
     * @param time in miliseconds
     */
    private void delayGame(long time){
        long sleepTime = (1000 / 30) - time;

        if (sleepTime >= 0){

            try{
                // Stop the game for a limited time
                Thread.sleep(sleepTime);
            }
            catch(Exception e){
                System.out.println("Time Error: Not handled");
            }

        } else {
            System.out.println("Game Error: FrameRate dropped below 30.");
        }
    }

    private void startCombat() {
        actionHandlerInCombat.startCombat(InfoHandler.getGenericAI(OPPONENTS_POKESHINTO));
    }

}

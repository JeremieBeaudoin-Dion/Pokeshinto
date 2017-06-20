package Helper;

import combat.ActionHandlerInCombat;
import combat.ObjectHandlerInCombat;
import images.Camera;
import images.ImageHandler;
import combat.InfoHandler;
import pokeshinto.InputHandler;
import pokeshinto.Player;

import java.io.IOException;

/**
 * @author Jérémie Beaudoin-Dion
 */
class CombatTester {


    /**
     * Enter your desired pokeshinto and your opponent's
     */
    String YOUR_POKESHINTO = "Kaizoyu";
    String OPPONENTS_POKESHINTO = "Keyori";







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
            objectHandlerInCombat = new ObjectHandlerInCombat(actionHandlerInCombat);

        } catch (IOException e) {
            System.exit(0);
        }

        player = new Player();
        Player.addNewPokeshinto(InfoHandler.getPokeshinto(YOUR_POKESHINTO));

        Camera camera = new Camera();
        camera.setToZero();

        imageHandler = new ImageHandler(camera);
        inputHandler = new InputHandler(imageHandler);

        startCombat();
    }

    private void update() {
        // Gets current time for fps
        long time = System.currentTimeMillis();

        inputHandler.update();

        // Updates according to Game State
        imageHandler.update(objectHandlerInCombat.get());

        // Handles player input
        String input = inputHandler.getInput("Combat");
        if (input != null) {
            actionHandlerInCombat.doInput(input);

            if (actionHandlerInCombat.update()) {
                System.out.println("Combat has ended.");
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
        long sleepTime = (1000 / 60) - time;

        if (sleepTime >= 0){

            try{
                // Stop the game for a limited time
                Thread.sleep(sleepTime);
            }
            catch(Exception e){
                System.out.println("Time Error: Not handled");
            }

        } else {
            System.out.println("Game Error: FrameRate dropped below 60");
        }
    }

    private void startCombat() {
        actionHandlerInCombat.startCombat(InfoHandler.getGenericAI(OPPONENTS_POKESHINTO));
    }

}

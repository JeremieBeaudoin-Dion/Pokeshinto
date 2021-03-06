package combat;

import combat.combatAI.PlayerCopy;
import pokeshinto.Action;
import duringMenus.Menu;
import pokeshinto.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * Before the combat starts, chooses the best pokeshinto
 *
 * @author Jérémie Beaudoin-Dion
 */
public class CombatHandlerBefore {

    private PlayerCopy player;
    private CombatAI opponent;
    private Menu currentMenu;

    private boolean startCombat;

    /**
     * Constructor
     */
    CombatHandlerBefore(PlayerCopy player, CombatAI opponent) {
        this.player = player;
        this.opponent = opponent;

        currentMenu = null;

        startCombat = false;

        instanciateFirstPokeshintos();
    }

    /**
     * Helper methods
     */
    private void instanciateFirstPokeshintos() {
        playersFirstPokeshinto();
        opponentFirstPokeshinto();

        if (opponent.getCurrentPokeshinto() != null) {
            if (player.getCurrentPokeshinto() != null || Player.allShintos.size() == 0) {
                startCombat = true;
            }
        }
    }

    private void opponentFirstPokeshinto() {
        if (opponent.getCurrentPokeshinto() == null) {
            if (opponent.getNumberOfPokeshinto() > 1) {
                opponent.chooseFirstPokeshinto(Player.equipedPokeshintos);
            }
        }
    }

    private void playersFirstPokeshinto() {
        // No pokeshinto is equiped
        if (player.getCurrentPokeshinto() == null) {
            if (player.getNumberOfPokeshinto() > 1) {
                currentMenu = getPokeshintoSelect(player.getNumberOfPokeshinto());
            }
        }
    }

    private Menu getPokeshintoSelect(int numberOfPokeshinto) {
        List<String> pokeshintosNames = new LinkedList<>();

        for (int i=0; i<numberOfPokeshinto; i++) {
            pokeshintosNames.add(Player.getAllShintos().get(i).getId());
        }

        return new Menu("Choose Pokeshinto", pokeshintosNames, "Choose your starting Pokeshinto");
    }

    /**
     * Called every time the player does an input
     */
    public void doInput(String myInput) {
        if(myInput.equals("Left")){
            currentMenu.goBack();

        } else if(myInput.equals("Right")){
            currentMenu.goForward();

        } else if(myInput.equals("Decide")){
            decide();

        } else if(myInput.equals("Escape")){
            // Does nothing for the moment
        }

    }

    private void decide() {
        Action<String> decision = currentMenu.decide();

        // If there is an action to do
        if (decision != null){
            player.equip(currentMenu.getSelected());
            startCombat = true;
        }
    }


    /**
     * Getters
     */
    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public String getMenuDescription() {
        return InfoHandler.getPokeshinto(currentMenu.getSelected()).getDescription();
    }

    public PlayerCopy getPlayer() {
        return player;
    }

    public CombatAI getOpponent() {
        return opponent;
    }

    public boolean isStartCombat() {
        return startCombat;
    }

}

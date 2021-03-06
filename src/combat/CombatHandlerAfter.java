package combat;

import duringMenus.Menu;
import pokeshinto.Player;
import pokeshinto.Pokeshinto;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles combat after the fight happened
 *
 * @author Jérémie Beaudoin-Dion
 */
public class CombatHandlerAfter {

    private Pokeshinto newPokeshinto;

    private int numberOfInput;

    private boolean allIsOver;
    private String outcome;

    private HashMap<String, Double> levelsToShow;

    private Menu currentMenu;

    /**
     * Constructor for a "Win" outcome
     */
    public CombatHandlerAfter(HashMap<String, Double> levelsBefore) {
        outcome = "Win";

        levelsToShow = levelsBefore;

        allIsOver = false;

        currentMenu = new Menu(outcome, new LinkedList<>(), null);
        currentMenu.setAllert("Leveling up");
        currentMenu.setAllert("You survived!");

        numberOfInput = 0;

        resetAllPokeshinto();
    }

    /**
     * Constructor for a "Capture" outcome
     */
    public CombatHandlerAfter(Pokeshinto newPokeshinto) {
        outcome = "Capture";

        allIsOver = false;

        currentMenu = new Menu(outcome, new LinkedList<>(), null);
        currentMenu.setAllert("You captured " + newPokeshinto.getId() + ", " + newPokeshinto.getDescription() + ".");
        updateAllLevels();

        this.newPokeshinto = newPokeshinto;

        resetAllPokeshinto();
    }

    private void resetAllPokeshinto() {
        Iterator<Pokeshinto> iter = Player.equipedPokeshintos.iterator();

        while(iter.hasNext()) {
            iter.next().resetAllSkills();
        }

    }

    public void doInput(String myInput) {
        if(myInput.equals("Decide") || myInput.equals("Escape")){
            currentMenu.decide();
            numberOfInput++;

            if (currentMenu.getAllert() == null) {
                allIsOver = true;
            }

            if (numberOfInput == 1) {
                updateAllLevels();
            }
        }
    }

    private void updateAllLevels() {
        List<Pokeshinto> allShintos = Player.getAllShintos();
        Iterator<Pokeshinto> iter = allShintos.iterator();
        Pokeshinto current;
        levelsToShow = new HashMap<>();

        while(iter.hasNext()) {
            current = iter.next();

            levelsToShow.put(current.getId(), current.getLevelCompletionPercentage());
        }
    }

    /**
     * Getters
     */
    public HashMap<String, Double> getLevelsToShow() {
        return levelsToShow;
    }

    public String getOutcome() {
        return outcome;
    }

    public boolean isAllOver() {
        return allIsOver;
    }

    public Pokeshinto getCapturedPokeshinto() {
        return newPokeshinto;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

}

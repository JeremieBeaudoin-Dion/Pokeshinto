package combat.combatAI;

import combat.Combat;
import combat.CombatAI;
import combat.CombatAttributes;
import combat.Element;
import pokeshinto.Action;
import pokeshinto.Pokeshinto;

import java.util.*;

/**
 * A more complex AI that takes good decisions 1 time out of 5.
 *
 * In order to do so, it works on a point system and does the
 * action which has the most points.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class AIManyPokeshinto extends CombatAI{

    private Random generator;

    /**
     * Constructor
     */
    public AIManyPokeshinto(List<Pokeshinto> equipedShintos, CombatAttributes combatAttributes, double health) {
        super(equipedShintos, null, combatAttributes, health, health, "Opponent");

        generator = new Random();
    }

    @Override
    public void decide(Combat combat) {
        List<Action<String>> bestChoices = getBestChoices(combat); //combat.getPlayerChoice()
        Action<String> myChoice;
        int luck = generator.nextInt(5);

        if (bestChoices.size() > 1 && luck == 0) {
            myChoice = bestChoices.get(bestChoices.size()-1);

        } else {
            myChoice = bestChoices.get(0);
        }

        choice = myChoice;
    }

    @Override
    public void setDecision(Action<String> action) {
        choice = action;
    }

    @Override
    public void chooseFirstPokeshinto(List<Pokeshinto> opponentsPokeshinto) {
        List<Element> mostPlausibleWeakness = HelperAI.getMostPlausibleWeakness(opponentsPokeshinto);

        Iterator<Element> iter = mostPlausibleWeakness.iterator();
        Pokeshinto goodPokeshintoAgainstElement;

        while(iter.hasNext()) {
            goodPokeshintoAgainstElement = getGoodPokeshintoAgainst(iter.next());

            if (goodPokeshintoAgainstElement != null) {
                currentPokeshinto = goodPokeshintoAgainstElement;
                return;
            }

        }

        // Did not find a good one
        currentPokeshinto = allPokeshintos.get(0);
    }

    /**
     * Return a good pokeshinto againts a certain Element
     */
    private Pokeshinto getGoodPokeshintoAgainst(Element element) {
        Iterator<Pokeshinto> iter = allPokeshintos.iterator();
        Pokeshinto pokeshinto;
        Element pokeshintoElement;

        while (iter.hasNext()) {
            pokeshinto = iter.next();
            pokeshintoElement = HelperAI.getElementDirectionFromPokeshinto(pokeshinto);

            if (pokeshintoElement != null) {
                if (element.compareTo(pokeshintoElement) > 0) {
                    return pokeshinto;
                }
            }
        }

        return null;
    }

    /**
     * Helper method to get the best possible choices to do.
     */
    private List<Action<String>> getBestChoices(Combat combat) {

        HashMap<Action<String>, Integer> mapOfChoices = new HashMap<>();

        // Insert here points for Items

        // Switch
        mapOfChoices.putAll(getSwitchActionsWithValue(combat.getPlayerPokeshinto()));

        // Skills

        // Build list with information
        return getTheBestChoices(mapOfChoices);
    }

    /**
     * Switch actions
     */
    private HashMap<Action<String>, Integer> getSwitchActionsWithValue(Pokeshinto opponentPokeshinto) {
        HashMap<Action<String>, Integer> mapOfSwitch = new HashMap<>();

        Iterator<Pokeshinto> iter = allPokeshintos.iterator();
        Pokeshinto currentPoke;

        while (iter.hasNext()) {
            currentPoke = iter.next();

            if (currentPoke != currentPokeshinto) {
                mapOfSwitch.put(new Action<>("Switch", currentPoke.getId()),
                        HelperAI.getPointsForSwitch(currentPoke, opponentPokeshinto));
            }
        }

        return mapOfSwitch;
    }

    /**
     * Sort and get the best choices
     */
    private List<Action<String>> getTheBestChoices(HashMap<Action<String>, Integer> mapOfChoices) {
        List<Integer> sortedValues = new LinkedList<>();

        sortedValues.addAll(mapOfChoices.values());
        Collections.sort(sortedValues);

        List<Integer> bestValues = keepOnlyBestValues(sortedValues);
        Iterator<Integer> iter = bestValues.iterator();
        List<Action<String>> listToReturn = new LinkedList<>();
        while(iter.hasNext()) {
            listToReturn.add(HelperAI.getKeyByValue(mapOfChoices, iter.next()));
        }

        return listToReturn;
    }

    private List<Integer> keepOnlyBestValues(List<Integer> sortedValues) {
        List<Integer> bestValues = new LinkedList<>();
        Iterator<Integer> iter = sortedValues.iterator();
        Integer currentValue;
        Integer lastValue = null;

        while(iter.hasNext()) {
            currentValue = iter.next();

            if (lastValue != null) {
                if (lastValue - currentValue > 5) {
                    return bestValues;
                }
            }
            bestValues.add(currentValue);
            lastValue = currentValue;
        }

        return bestValues;
    }
}

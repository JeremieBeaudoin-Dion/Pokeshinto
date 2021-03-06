package combat.combatAI;

import combat.Combat;
import combat.Dictionary;
import combat.Element;
import combat.InfoHandler;
import combat.combatActions.Damage;
import combat.combatActions.Skill;
import pokeshinto.Action;
import pokeshinto.KeyException;
import pokeshinto.Pokeshinto;

import java.util.*;

/**
 * A helper class that has many different static methods for AI
 * to help them make good decisions.
 *
 * @author Jérémie Beaudoin-Dion
 */
class HelperAI {

    public static void main(String[] args) {
        System.out.println(getPointsForSwitch(InfoHandler.getPokeshinto("Kohadai"), InfoHandler.getPokeshinto("Kumano")));
    }

    /**
     * Usefull to get a value from a key with a map
     */
    static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Usefull to get a grasp on the most plausible element from a list of pokeshinto
     */
    static Pokeshinto getBestPokeshintoAgainstOther(List<Pokeshinto> myPokeshintos, List<Pokeshinto> opponentPokeshintos) {
        if (getElementDirectionFromPokeshinto(opponentPokeshintos) == null) {
            return null;
        }

        return null;
    }

    static Element getElementDirectionFromPokeshinto(List<Pokeshinto> enemiesPokeshinto) {
        if (enemiesPokeshinto.size() == 1) {
            return getElementDirectionFromSkills(enemiesPokeshinto.get(0).getEquipedSkills());
        }

        return null;
    }

    static Element getElementDirectionFromPokeshinto(Pokeshinto pokeshinto) {
        return getElementDirectionFromSkills(pokeshinto.getEquipedSkills());
    }

    private static Element getElementDirectionFromSkills(List<Skill> allSkills) {
        List<Element> allElements = new LinkedList<>();

        Iterator<Skill> iter = allSkills.iterator();
        while(iter.hasNext()) {
            allElements.add(iter.next().getElement());
        }

        return getElementDirectionFromElements(allElements);
    }

    private static Element getElementDirectionFromElements(List<Element> allElements) {
        Dictionary elementPreference = new Dictionary();

        Iterator<Element> iter = allElements.iterator();
        while(iter.hasNext()) {
            elementPreference.push(iter.next().getGenericId(), 1);
        }

        return getMostPlausibleElementFromDictionary(elementPreference);

    }

    private static Element getMostPlausibleElementFromDictionary(Dictionary elementPreference) {
        elementPreference.sortWithIntegers();

        String mostPlausibleElement = elementPreference.getKeyByIndex(elementPreference.getSize()-1);
        if (elementPreference.getValueByKey(mostPlausibleElement) > 1) {
            return new Element(mostPlausibleElement);
        }

        return null;
    }

    /**
     * Usefull to understand the weaknesses of a list of Pokeshintos
     */
    static List<Element> getMostPlausibleWeakness(List<Pokeshinto> pokeshintos) {
        Iterator<Pokeshinto> iter = pokeshintos.iterator();
        Dictionary weakness = new Dictionary();

        while(iter.hasNext()) {
            addAllWeaknesses(weakness, iter.next());
        }

        weakness.sortWithIntegers();

        List<Element> allElements = new LinkedList<>();
        int i = weakness.getSize() - 1;
        int j = 3;

        while(i>0 && j>0) {
            allElements.add(new Element(weakness.getKeyByIndex(i)));

            i--;
            j--;
        }

        return allElements;
    }

    private static void addAllWeaknesses(Dictionary weakness, Pokeshinto pokeshinto) {
        Dictionary weaknessToAdd = pokeshinto.getCombatAttributes().getElementResistance();

        for(int i=0; i<weakness.getSize(); i++) {
            if (weaknessToAdd.getValueByIndex(i) > 0) {
                weakness.push(weaknessToAdd.getKeyByIndex(i), weaknessToAdd.getValueByIndex(i));
            }
        }
    }

    /**
     * Returns the AI value for switching to a particular pokeshinto,
     * considering the opponent's
     */
    static int getPointsForSwitch(Pokeshinto myPokeshinto, Pokeshinto opponentPokeshinto) {
        int score = 10;

        Iterator<Skill> iter = opponentPokeshinto.getEquipedSkills().iterator();
        while(iter.hasNext()) {
            try {
                score += myPokeshinto.getCombatAttributes().getElementResistance().getValueByKey(iter.next().getElement().getId()) / 5;
            } catch (KeyException e) {
                // add 0
            }
        }

        Iterator<Skill> iter2 = myPokeshinto.getEquipedSkills().iterator();
        while(iter2.hasNext()) {
            try {
                score -= opponentPokeshinto.getCombatAttributes().getElementResistance().getValueByKey(iter2.next().getElement().getId()) / 5;
            } catch (KeyException e) {
                // add 0
            }
        }

        return score;
    }

    /**
     * Returns the AI value for using a particular skill,
     * considering the opponent's action
     */
    static int getPointsForSkill(Combat combat, Skill mySkill, Action<String> enemyAction) {
        int currentPoints = getPointsForSkill(combat, mySkill);

        if (enemyAction == null) {
            return currentPoints;
        }

        // Change currentPoints according to enemy action (ex: Protection better vs Damage

        throw new UnsupportedOperationException("Not finished yet.");
    }

    private static int getPointsForSkill(Combat combat, Skill mySkill) {
        switch (getTypeFromSkill(mySkill.getId())){

            case "Damage":
                return getAllSkillDamageValue(combat, mySkill.getDamage());

            case "Protection":
                return 5;

            case "Restriction":
                return 10;

            case "Buff":
                return 20;

        }

        throw new IllegalArgumentException("The type " + getTypeFromSkill(mySkill.getId()) + " is unsupported.");
    }

    private static int getAllSkillDamageValue(Combat combat, List<Damage> allDamages) {
        int value = 0;

        Iterator<Damage> iter = allDamages.iterator();
        Damage currentDamage;

        while(iter.hasNext()) {
            currentDamage = iter.next();

            if (currentDamage.affectsMe()) {
                value -= (int) currentDamage.getTotalDamage(combat, "Opponent");
            } else {
                value += (int) currentDamage.getTotalDamage(combat, "Opponent");
            }
        }

        return value;
    }

    /**
     * Returns the type of skill
     */
    static String getTypeFromSkill(String id) {
        switch (id) {
            case "Slap":
                return "Damage";

            case "Dumb Luck":
                return "Damage";

            case "Quick Blade":
                return "Damage";

            case "Slice":
                return "Damage";

            case "Parry":
                return "Protection";

            case "Push Back":
                return "Protection";

            case "Breathe In":
                return "Buff";

            case "Disarm":
                return "Restriction";

            case "Decapitate":
                return "Damage";

            case "Raise Shield":
                return "Protection";

            case "Thrust":
                return "Damage";

            case "Halo of Courage":
                return "Protection";

            case "Spinning Strike":
                return "Damage";

            case "Heartseeker":
                return "Damage";

            case "Cannon Blast":
                return "Damage";

            case "Bone Spear":
                return "Damage";

            case "Clobber":
                return "Damage";

            case "Haunting Wail":
                return "Damage";

            case "Pillage":
                return "Damage";

            case "Bite":
                return "Damage";

            case "Healmcrusher":
                return "Damage";

            case "Devour":
                return "Damage";

            case "Charge In":
                return "Damage";

            case "Rampage":
                return "Buff";

        }

        throw new IllegalArgumentException("The skill " + id + " is not handled.");
    }

}

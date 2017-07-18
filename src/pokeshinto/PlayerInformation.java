package pokeshinto;

import combat.ActionDescriptor;
import combat.CombatAttributes;
import combat.InfoHandler;
import images.Position;
import world.items.Item;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * An object which stores the desired information about the player.
 *
 * It is mostly used to save the player's information into a file.
 *
 * @author Jérémie Beaudoin-Dion
 */
public class PlayerInformation implements Serializable {

    private boolean approximate;
    private int descriptionComplexity;

    private double health;
    private double maxHealth;
    private CombatAttributes combatAttributes;

    private List<Item> allItems;
    private List<Pokeshinto> allShintos;
    private List<Pokeshinto> equipedPokeshintos;

    private String facing;
    private Position pixelPosition;
    private String currentMapID;

    /**
     * Constructor
     */
    public PlayerInformation(boolean approximate, int descriptionComplexity, double health, double maxHealth, CombatAttributes combatAttributes, List<Item> allItems, List<Pokeshinto> allShintos, List<Pokeshinto> equipedPokeshintos, String facing, Position pixelPosition, String currentMapID) {
        this.approximate = approximate;
        this.descriptionComplexity = descriptionComplexity;
        this.health = health;
        this.maxHealth = maxHealth;
        this.combatAttributes = combatAttributes;
        this.allItems = allItems;
        this.allShintos = allShintos;
        this.equipedPokeshintos = equipedPokeshintos;
        this.facing = facing;
        this.pixelPosition = pixelPosition;
        this.currentMapID = currentMapID;
    }

    /**
     * Constructor for new Player
     */
    public PlayerInformation() {
        health = 250;
        maxHealth = 250;
        combatAttributes = InfoHandler.getBasicCombatAttributes();

        allShintos = new LinkedList<>();
        equipedPokeshintos = new LinkedList<>();
        allItems = new LinkedList<>();

        facing = "Down";
        pixelPosition = new Position(48, 432);
        currentMapID = "Sand";

        approximate = true;
        descriptionComplexity = ActionDescriptor.DESCRIPTION_COMPLEXITY_NORMAL;
    }

    /**
     * Getters
     */
    boolean isApproximate() {
        return approximate;
    }

    int getDescriptionComplexity() {
        return descriptionComplexity;
    }

    double getHealth() {
        return health;
    }

    double getMaxHealth() {
        return maxHealth;
    }

    CombatAttributes getCombatAttributes() {
        return combatAttributes;
    }

    List<Item> getAllItems() {
        return allItems;
    }

    List<Pokeshinto> getAllShintos() {
        return allShintos;
    }

    List<Pokeshinto> getEquipedPokeshintos() {
        return equipedPokeshintos;
    }

    String getFacing() {
        return facing;
    }

    Position getPixelPosition() {
        return pixelPosition;
    }

    String getCurrentMapID() {
        return currentMapID;
    }
}

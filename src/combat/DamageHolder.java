package combat;

import java.io.Serializable;

/**
 * Holds different types of damages for a CombatAI
 *
 * @author Jérémie Beaudoin-Dion
 */
public class DamageHolder implements Serializable {

    private double stun;
    private double pain;
    private double dizzy;
    private double seal;

    private double physical;
    private double magical;

    /**
     * Constructor with no arguments sets everything to 0
     */
    public DamageHolder() {
        this.stun = 0;
        this.pain = 0;
        this.dizzy = 0;
        this.seal = 0;
        this.physical = 0;
        this.magical = 0;
    }

    public DamageHolder(double stun, double pain, double dizzy, double seal, double physical, double magical) {
        this.stun = stun;
        this.pain = pain;
        this.dizzy = dizzy;
        this.seal = seal;
        this.physical = physical;
        this.magical = magical;
    }

    public double get(String id) {
        switch (id) {
            case "Stun":
                return stun;

            case "Pain":
                return pain;

            case "Dizzy":
                return dizzy;

            case "Seal":
                return seal;

            case "Physical":
                return physical;

            case "Magical":
                return magical;

            default:
                throw new IllegalArgumentException("The damage " + id + " is not valid.");
        }
    }

    public void add(String id, double value) {
        switch (id) {
            case "Stun":
                stun += value;
                break;

            case "Pain":
                pain += value;
                break;

            case "Dizzy":
                dizzy += value;
                break;

            case "Seal":
                seal += value;
                break;

            case "Physical":
                physical += value;
                break;

            case "Magical":
                magical += value;
                break;

            default:
                throw new IllegalArgumentException("The damage " + id + " is not valid.");
        }
    }

    public void set(String id, double value) {
        switch (id) {
            case "Stun":
                stun = value;
                break;

            case "Pain":
                pain = value;
                break;

            case "Dizzy":
                dizzy = value;
                break;

            case "Seal":
                seal = value;
                break;

            case "Physical":
                physical = value;
                break;

            case "Magical":
                magical = value;
                break;

            default:
                throw new IllegalArgumentException("The damage " + id + " is not valid.");
        }
    }

    public void reset(String id) {
        switch (id) {
            case "Stun":
                stun = 0;
                break;

            case "Pain":
                pain = 0;
                break;

            case "Dizzy":
                dizzy = 0;
                break;

            case "Seal":
                seal = 0;
                break;

            case "Physical":
                physical = 0;
                break;

            case "Magical":
                magical = 0;
                break;

            default:
                throw new IllegalArgumentException("The damage " + id + " is not valid.");
        }
    }

    public void add(DamageHolder other) {
        this.stun += other.get("Stun");
        this.pain += other.get("Pain");
        this.dizzy += other.get("Dizzy");
        this.seal += other.get("Seal");
        this.physical += other.get("Physical");
        this.magical += other.get("Magical");
    }

    public void resetAll() {
        this.stun = 0;
        this.pain = 0;
        this.dizzy = 0;
        this.seal = 0;
        this.physical = 0;
        this.magical = 0;
    }

    public double getAll() {
        return stun + pain + dizzy + seal + physical + magical;
    }

    public double getAllNonStatus() {
        return physical + magical;
    }

    public boolean getHasDamage() {
        return stun != 0 || pain != 0 || dizzy != 0 || seal != 0 || physical != 0 || magical != 0;
    }
}

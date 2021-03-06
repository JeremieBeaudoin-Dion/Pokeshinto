package combat.combatActions;

import pokeshinto.Copyable;

/**
 * A status has a cooldown, and conditions to be met. It can last
 * one or more turns. It does an effect on the desired shinto.
 * 
 * @author Jérémie Beaudoin-Dion
 */
public abstract class Status extends Conditional implements Copyable<Status> {

	protected boolean onMe;  // is it on me, or the other?
    protected int maxLength;
	protected int length;

    /**
     * Constructor
     *
     * @param onMe: if the status will take effect on the caster
     * @param buildUp: how many steps have to pass before it takes effect the first time
     * @param condition: if it must follow a certain condition
     * @param length: how long will the Status last
     */
	public Status(boolean onMe, int buildUp, Condition condition, int length) {
		super(0, buildUp, false, condition);
		this.onMe = onMe;
		this.length = length;
        maxLength = length;
	}
	
	/**
	 * Getters
	 * 
	 * @return if the Status is on the caster or not
	 */
	public boolean isOnMe() {
		return onMe;
	}

	public boolean isEnded() {
		return length < 0;
	}

	@Override
	public void resetCooldown() {
		super.resetCooldown();
        length = maxLength;
	}

    @Override
    public void update() {
        super.update();

        length--;
    }
}

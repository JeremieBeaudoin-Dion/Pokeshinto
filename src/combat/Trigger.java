package combat;

/**
 * A damage that will happen at a certain time
 * 
 * For the conditions, their value depend on when the check is made.
 * 		1) Start turn == check whole stat
 * 		2) Start encounter == check whole stat
 * 		3) End encounter == check only dmg done during encounter
 * 		4) End turn == check the whole stat
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Trigger<E> extends Conditional {
	
	private boolean isMe;  // if the trigger affects the owner
	private E effect;  // the effect it can do
    private String when;
	
	
	public Trigger (int cooldown, int buildup, boolean once, Condition condition
					) {
		super(cooldown, buildup, once, condition);
	}
	
	
}

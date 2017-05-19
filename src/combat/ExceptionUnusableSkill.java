package combat;

/**
 * An exception thrown when a skill is used when it cannot
 *
 * @author Jérémie Beaudoin-Dion
 */
class ExceptionUnusableSkill extends RuntimeException {
	
	ExceptionUnusableSkill(String skillID){
		super(skillID + " is not usable");
	}

}

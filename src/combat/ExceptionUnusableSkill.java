package combat;

public class ExceptionUnusableSkill extends RuntimeException {
	
	public ExceptionUnusableSkill(String skillID){
		super(skillID + " is not usable");
	}

}

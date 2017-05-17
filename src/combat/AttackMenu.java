package combat;
/**
 * For Now...
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class AttackMenu {
	
	// TODO: make it more general
	
	private int wait;
	private final int maxWait = 10;
	
	private String[] rootMenu;
	private String[][] baseMenus;
	private String allert;
	
	private int selected;
	
	public AttackMenu() {
		this.rootMenu = new String[]{"Attack", "Switch", "Item", "Inspect", "Meditate"};
		this.baseMenus = new String[][]{
			{"Punch", "Kick", "Stab"}, {"Other"}, {"Potion"}
		};
		
		this.allert = "Let the fight begin!";
		selected = 0;
	}
	
	public void setAllert(String allert){
		this.allert = allert;
	}
	
	public void decide() {
		if(allert == null){
			
		} else {
			allert = null;
		}
	}
	
	public void goRight() {
		
	}
	
	public void goLeft() {
		
	}
	
	

}
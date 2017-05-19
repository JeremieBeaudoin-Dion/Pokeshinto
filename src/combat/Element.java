package combat;

/**
 * Every skill has an element. During combat, the skill's element will
 * determine the "win" of the turn. The more the Player wins the element's
 * war during the turns, the easier the combat will be.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Element implements Comparable<Element> {
	
	private String id;
	private int elementGeneric;  // War, Art or Magic
	private int elementSpecific;  // Depens on the generic
	private int elementMoreSpecific;  // It is rare that an element has 2 specifications, but it happens
	
	/**
	 * This constructor 
	 * 
	 * @param element: The name of the element
	 */
	public Element(String element){
		// TODO: finish Element's wheel
		id = element;
		
		// Initialise non-existence of all
		elementGeneric = -1;
		elementSpecific = -1;
		elementMoreSpecific = -1;
		
		// Finds the value of all elements
		findValueOfElement(element);
		
	}
	
	public String getId() {
		return id;
	}
	public int getGeneric() {
		return elementGeneric;
	}
	public int getSpecific() {
		return elementSpecific;
	}
	public int getMoreSpecific() {
		return elementMoreSpecific;
	}

	/**
	 * Returns the outcome of the fight (of the elements)
	 * 
	 * @param other: the other element to compare with
	 * @return -1 if the fight is loss, 0 if tie or 1 for a win
	 */
	public int compareTo(Element other) {
		
		// Compare Generic
		if (this.elementGeneric != other.getGeneric()){
			return compareGeneric(other);
		} else {
			// Existence of Specific
			if (this.elementSpecific >= 0 && other.getSpecific() >= 0){
				// Compare Specific
				if (this.elementSpecific != other.getSpecific()){
					return compareSpecific(other);
				} else {
					// Existence of moreSpecific
					if (this.elementMoreSpecific >= 0 && other.getMoreSpecific() >= 0){
						return compareMoreSpecific(other);
					} else {
						// specific == specific and Not existence of moreSpecific
						return 0;
					}
				}
			} else {
				// generic == generic and Not existence of Specific
				return 0;
			}
		}
	}
	
	/**
	 * Compares two Elements with their generic value
	 * 
	 * @param other: the element to compare to
	 * @return -1 if the fight is loss, 0 if tie or 1 for a win
	 */
	private int compareGeneric(Element other) {
		int outcome = other.getGeneric() - this.elementGeneric;
		if(outcome == 2){
			outcome = -1;
		} else if (outcome == -2) {
			outcome = 1;
		}
		return outcome;
	}
	
	/**
	 * Compares two Elements with their specific value
	 * 
	 * @param other: the element to compare to
	 * @return -1 if the fight is loss, 0 if tie or 1 for a win
	 */
	private int compareSpecific(Element other) {
		int outcome = other.getSpecific() - this.elementSpecific;
		if(outcome == 2){
			outcome = -1;
		} else if (outcome == -2) {
			outcome = 1;
		}
		return outcome;
	}
	
	/**
	 * Compares two Elements with their moreSpecific value
	 * 
	 * @param other: the element to compare to
	 * @return -1 if the fight is loss, 0 if tie or 1 for a win
	 */
	private int compareMoreSpecific(Element other) {
		
		int outcome = other.getMoreSpecific() - this.elementMoreSpecific;
		
		if (-1 <= outcome && outcome <= 1) {
			return outcome;
		} else {
			// Different solutions than 1, 0, -1
			if (outcome == 3) {
				return -1;
			} else if (outcome == -3) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	
	/**
	 * This finds the int values from a string
	 * 
	 * War => Art => Magic
	 * 		War = 0
	 * 		Art = 1
	 * 		Magic = 2
	 * 
	 * In war:
	 * Pierce => Slash => Crush
	 * 		Pierce = 0
	 * 		Slash = 1
	 * 		Crush = 2
	 * 
	 * In Art:
	 * Dark == Light
	 * 		Dark = 0
	 * 		Light = 1
	 * 
	 * In Magic:
	 * Nature => Balance => Energy
	 * 
	 * In energy:
	 * 		Wind => Fire => Ice => Thunder
	 * 
	 * @param element: The name of the element
	 */
	private void findValueOfElement(String element) {
		// War
		if (element.equals("War")){
			elementGeneric = 0;
		} else if (element.equals("Pierce")){
			elementGeneric = 0;
			elementSpecific = 0;		
		} else if (element.equals("Slash")){
			elementGeneric = 0;
			elementSpecific = 1;
		} else if (element.equals("Crush")){
			elementGeneric = 0;
			elementSpecific = 2;
		}
		
		// Art
		if (element.equals("Art")){
			elementGeneric = 1;
		} else if (element.equals("Dark")) {
			elementGeneric = 1;
			elementSpecific = 0;
		} else if (element.equals("Light")) {
			elementGeneric = 1;
			elementSpecific = 1;
		}
		
		// Magic
		if (element.equals("Magic")){
			elementGeneric = 2;
		} else if (element.equals("Nature")) {
			elementGeneric = 2;
			elementSpecific = 0;
		} else if (element.equals("Balance")) {
			elementGeneric = 2;
			elementSpecific = 1;
		} else if (element.equals("Energy")) {
			elementGeneric = 2;
			elementSpecific = 2;
		} else if (element.equals("Wind")) {
			elementGeneric = 2;
			elementSpecific = 2;
			elementMoreSpecific = 0;
		} else if (element.equals("Fire")) {
			elementGeneric = 2;
			elementSpecific = 2;
			elementMoreSpecific = 1;
		} else if (element.equals("Ice")) {
			elementGeneric = 2;
			elementSpecific = 2;
			elementMoreSpecific = 2;
		} else if (element.equals("Thunder")) {
			elementGeneric = 2;
			elementSpecific = 2;
			elementMoreSpecific = 3;
		} 
	}
}
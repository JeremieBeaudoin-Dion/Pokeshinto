package pokeshinto;

/**
 * A choice binds a String action to a <T> element
 * 
 * @author Jérémie Beaudoin-Dion
 */
public class Action<T> {
	
	private T element;
	private String key;
	
	public Action(String key, T element) {
		this.element = element;
		this.key = key;
	}
	
	public T getValue() {
		return element;
	}
	
	public String getKey() {
		return key;
	}

}

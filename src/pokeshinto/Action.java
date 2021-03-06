package pokeshinto;

import java.io.Serializable;

/**
 * A choice binds a String action to a <T> value
 * 
 * @author Jérémie Beaudoin-Dion
 */
public class Action<T> implements Serializable {

	private String key;
	private T value;
	
	public Action(String key, T value) {
		this.value = value;
		this.key = key;
	}
	
	public T getValue() {
		return value;
	}
	
	public String getKey() {
		return key;
	}

	public boolean equals(Action<T> other) {
		return key.equals(other.getKey()) && value.equals(other.getValue());
	}

}

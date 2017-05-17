package pokeshinto;
import combat.ExceptionComparableArrays;

/**
 * Similar to the HashMap, this stores <type> data and
 * binds it to a String key.
 * 
 * @author Jérémie Beaudoin-Dion
 *
 */
public class Dictionary<T> {
	
	T[] values;
	String[] keys;
	
	/**
	 * Basic constructor with elements already created
	 * 
	 * @param key
	 * @param element
	 */
	public Dictionary(String[] key, T[] element){
		if(key.length == element.length){
			values = element;
			keys = key;
		} else {
			throw new ExceptionComparableArrays("The lenght of the elements don't match");
		}
		
	}
	
	/**
	 * Gets the element binded by the key. Returns null if the key is
	 * not in the dic.
	 * 
	 * @param key: the String attached to the element
	 * @return the <T> element
	 */
	public T getValueByKey(String key) throws KeyException{
		
		for(int i=0; i<keys.length; i++) {
			if(keys[i].equals(key)){
				return values[i];
			}
		}
		
		// Can't find the key in the dic
		throw new KeyException();
	}
	
	/**
	 * Gets the key at a specified location. Throws an exception if the index is not valid
	 * 
	 * @param index: the index of the key
	 * @return the <String> key
	 */
	public String getKeyByIndex(int index) throws ArrayIndexOutOfBoundsException{
		return keys[index];
	}
	
	/**
	 * Gets the value at a specified location. Throws an exception if the index is not valid
	 * 
	 * @param index: the index of the key
	 * @return the <T> element
	 */
	public T getValueByIndex(int index) throws ArrayIndexOutOfBoundsException{
		return values[index];
	}
	
	/**
	 * Adds a new element binded to a String key
	 * 
	 * @param key: String the key binded to the element
	 * @param value: type <t> the element to be stored in the dic
	 */
	public void push(String key, T value){
		String[] newKeys = new String[keys.length+1];
		@SuppressWarnings("unchecked")
		T[] newElements = (T[]) new Object[values.length+1];
		
		for(int i=0; i<keys.length; i++){
			newKeys[i] = keys[i];
			newElements[i] = values[i];
		}
		
		newKeys[keys.length] = key;
		newElements[values.length] = value;
		
		keys = newKeys;
		values = newElements;
		
	}
	
	

}

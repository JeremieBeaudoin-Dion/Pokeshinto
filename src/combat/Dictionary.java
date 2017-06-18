package combat;
import pokeshinto.Copyable;
import pokeshinto.KeyException;

import java.util.LinkedList;
import java.util.List;

/**
 * Similar to the HashMap, this stores <T> data and
 * binds it to a String key.
 * 
 * @author Jérémie Beaudoin-Dion
 */
public class Dictionary<T> implements Copyable<Dictionary<T>>{

    private List<String> keys;
    private List<T> values;

    public Dictionary() {
        keys = new LinkedList<>();
        values = new LinkedList<>();
    }

	/**
	 * Basic constructor with elements already created
	 */
	public Dictionary(List<String> key, List<T> value){
		if(key.size() == value.size()){
			values = value;
			keys = key;
		} else {
			throw new ExceptionComparableArrays("The lenght of the elements don't match");
		}

	}

	public int getSize() {
		return keys.size();
	}
	
	/**
	 * Gets the element binded by the key. Returns null if the key is
	 * not in the dic.
	 * 
	 * @param key: the String attached to the element
	 * @return the <T> element
	 */
	T getValueByKey(String key) throws KeyException{

	    if (keys.contains(key)) {
            int index = keys.indexOf(key);
            return values.get(index);
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
	String getKeyByIndex(int index) throws IndexOutOfBoundsException {
		return keys.get(index);
	}
	
	/**
	 * Gets the value at a specified location. Throws an exception if the index is not valid
	 * 
	 * @param index: the index of the key
	 * @return the <T> element
	 */
	T getValueByIndex(int index) throws IndexOutOfBoundsException {
		return values.get(index);
	}
	
	/**
	 * Adds a new element binded to a String key
	 * 
	 * @param key: String the key binded to the element
	 * @param value: owner <t> the element to be stored in the dic
	 */
    public void push(String key, T value){
	    if (keys == null) {
	        keys = new LinkedList<>();
	        values = new LinkedList<>();
        }

        keys.add(key);
	    values.add(value);
		
	}

	public Dictionary<T> copy() {
        List<String> newKeys = new LinkedList<>();
        newKeys.addAll(keys);

        LinkedList<T> newValues = new LinkedList<>();
        newValues.addAll(values);

        return new Dictionary<T>(newKeys, newValues);
    }

}

package world;

/**
 * A Fact binds any generic object to a boolean
 * 
 * @author Jeremie Beaudoin
 *
 */
public class Fact<E> {
	
	private E element;
	private boolean truth;
	
	/**
	 * Constructor
	 * 
	 * @param element: the ID of the element
	 * @param truth: if it is true or not
	 */
	public Fact(E element, boolean truth) {
		this.element = element;
		this.truth = truth;
	}
	
	/**
	 * Returns if the fact is true
	 * 
	 * @return
	 */
	public boolean isTrue() {
		return truth;
	}
	
	/**
	 * Returns the ID of the Fact
	 * 
	 * @return
	 */
	public E get() {
		return element;
	}

}

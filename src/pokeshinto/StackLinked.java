package pokeshinto;

/**
 * An implementation of a stack with linked elements
 * 
 * @author Jérémie Beaudoin-Dion
 *
 * @param <E> : the type of the element
 */
public class StackLinked<E>{
	
	private class Elem<T> {
		
		private T value;
		private Elem<T> next;
		
		private Elem(T value, Elem<T> next) {
			this.value = value;
			this.next = next;
		}
		
	}
	
	Elem<E> top;
	
	/**
	 * Checks f the stack is currently empty
	 * 
	 * @return: boolean if Stack is empty
	 */
	public boolean isEmpty() {
		return top == null;
	}
	
	/**
	 * Checks the top of the Stack
	 * 
	 * @return: the value of the element on top
	 */
	public E peek() {
		return top.value;
	}
	
	/**
	 * Removes an element from the Stack
	 * 
	 * @return: the value of the element
	 */
	public E pop() {
		if (isEmpty()) {
			throw new ExceptionEmptyStack();
		}
		
		E value = top.value;
		top = top.next;
		return value;
	}
	
	/**
	 * Pushes an element at the top of the stack
	 * 
	 * @param value: the value of the element of type <E>
	 */
	public void push(E value) {
		if (value == null) {
			throw new NullPointerException();
		}
		
		if (top != null) {
			Elem<E> temp = new Elem<E>(value, top);
			top = temp;
		} else {
			top = new Elem<E>(value, null);
		}
	}

}

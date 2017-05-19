package combat;

/**
 * Exception thrown when arrays are compared but are not the same
 *
 * @author Jérémie Beaudoin-Dion
 */
public class ExceptionComparableArrays extends RuntimeException {
	
	public ExceptionComparableArrays(String message) {
		super(message);
	}

}

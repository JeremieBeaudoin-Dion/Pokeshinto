package pokeshinto;

/**
 * My version of "Cloneable"
 *
 * Ensures that the copy is not shallow
 *
 * @author Jérémie Beaudoin-Dion
 */
public interface Copyable<E> {

    E copy();
}

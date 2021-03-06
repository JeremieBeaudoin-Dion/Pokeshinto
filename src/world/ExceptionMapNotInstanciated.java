package world;

/**
 * An error thrown when a Map is used but has not yet every object necessary to
 * run correctly.
 *
 * @author Jérémie Beaudoin-Dion
 */
class ExceptionMapNotInstanciated extends RuntimeException {

    ExceptionMapNotInstanciated (String message) {
        super(message);
    }

}

package Helper;

import combat.Element;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class ElementWarTester {

    public static void main(String[] args) {

        Element element1 = new Element("Balance");
        Element element2 = new Element("War");

        System.out.println(element1.getId() + " vs " + element2.getId() + " = " + element1.compareTo(element2));

    }

}

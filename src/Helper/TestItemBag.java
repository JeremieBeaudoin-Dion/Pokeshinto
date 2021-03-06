package Helper;

import world.items.HealthPotionPetty;
import world.items.HealthPotionPoor;
import world.items.Item;
import world.items.ItemBag;

/**
 * A test for the ItemBag
 *
 * @author Jérémie Beaudoin-Dion
 */
public class TestItemBag {

    public static void main(String[] args) {
        System.out.println("TestAddAndRemove : " + testAddAndRemove());

        System.out.println("TestConsumeAndRemove : " + testConsumeAndRemove());

    }

    private static boolean testAddAndRemove() {
        ItemBag itemBag = new ItemBag();

        Item potion = new HealthPotionPetty();

        itemBag.add(potion);
        itemBag.add(new HealthPotionPetty());

        itemBag.add(new HealthPotionPoor());

        itemBag.remove(potion);

        itemBag.remove(HealthPotionPetty.ID);

        if (itemBag.getAmount(HealthPotionPetty.ID) == 0 && itemBag.getAmount(HealthPotionPoor.ID) == 1) {
            return true;
        }

        return false;
    }

    private static boolean testConsumeAndRemove() {
        ItemBag itemBag = new ItemBag();

        HealthPotionPetty potion1 = new HealthPotionPetty();
        HealthPotionPetty potion2 = new HealthPotionPetty();

        itemBag.add(potion1);
        itemBag.add(potion2);

        potion2.use();

        itemBag.removeAllConsumedConsumable();

        if (itemBag.getAmount(HealthPotionPetty.ID) == 1) {
            return true;
        }

        return false;

    }

}

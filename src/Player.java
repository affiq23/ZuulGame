
import java.util.*;

public class Player {

    private HashMap<String, Item> inventory;

    private Room currentRoom;

    public Player() {
        inventory = new HashMap<String, Item>();
    }

    public void addItem(Item i) {
        inventory.put(i.getName(), i);
    }

    public void dropItems(Item i) {
        HashMap<String, Item> inventory = getInventory();
        inventory.remove(i);
        currentRoom.addItem(i.getName(), i);
    }

    public HashMap getInventory() {
        return inventory;
    }

    public String getInventoryString() {
        String s;
        if (inventory.isEmpty()) {
            s = "The " + this.getClass().getName() + " has no items";
        } else {
            s = "The " + this.getClass().getName() + " has the following items:\n";

            Set<String> keys = inventory.keySet();
            for (String i : keys) {
                s += " " + i;
                s += "\n";

            }
        }
        return s;
    }

    public boolean removeItem(Item i) {
        return inventory.remove(i.getName(), i);
    }

}

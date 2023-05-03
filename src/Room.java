
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via exits. The exits are labeled north, east, south, west. For
 * each direction, the room stores a reference to the neighboring room, or null
 * if there is no exit in that direction.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room {

    public String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;


    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new HashMap<String, Item>();

    }

    public void setExits(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return a description of the room's exits for example, "Exits: north
     * west".
     *
     * @return A description of the available exits.
     */
    public String getExitString() {

        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {

            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return a long description of this room, of the form: You are in the
     * kitchen Exits: north west
     *
     * @return A description of the room, including exits
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Add an item to the room
     */
    public void addItem(String name, Item item) {
        items.put(name, item);

    }

    public Item removeItem(String name) {
        return items.remove(name);
    }

    public String getItemString() {

        String returnString = "";

        if (items.isEmpty()) {
            returnString += "The room does not contain any items";
        } else {
            returnString += "Item: " + items;
        }

        return returnString;
          
}
    
    public String getItemDescription() {
        String returnItemString = "";
        
        if(items.isEmpty()) {
            returnItemString += "";
        } else {
            returnItemString += "Description: " + getLongDescription();
        }
        return returnItemString;
    }


}

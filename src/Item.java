/*
Affiq Mohammed
12/3/19
3A
*/

public class Item {


    private String name;
    private String itemDescription;


    public Item(String name, String itemDescription) {
        this.name = name;
        this.itemDescription = itemDescription;

    }
   
    public String getName() {
        return name;

    }
    
    public String getItemShortDescription() {
        return itemDescription;

    }

    public String getItemLongDescription() {

        return "This is the " + itemDescription;

    }

    
   
}
    
    
    
    


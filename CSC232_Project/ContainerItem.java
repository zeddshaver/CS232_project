import java.util.ArrayList;

public class ContainerItem extends Item {
    private ArrayList<Item> items;

    public ContainerItem(String name, String type, String description) {
        super(name, type, description);
        items = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean hasItem(String itemName) {
        for (Item a : items) {
            if (itemName.equalsIgnoreCase(a.getName())) {
                return true;
            }
        }
        return false;
    }

    public Item removeItem(String itemName) {
        if (hasItem(itemName)) {
            for (Item a : items) {
                if (itemName.equalsIgnoreCase(a.getName())) {
                    Item removedItem = a;
                    items.remove(a);
                    return removedItem;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String result = "";
        for (Item a : items) {
            result = result + "+ " + a.getName() + "\n";
        }
        return super.toString() + " that contains:\n" + result;
    }
}

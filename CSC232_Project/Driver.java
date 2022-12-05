import java.util.Scanner;

public class Driver {
    private static Location currLocation;
    private static ContainerItem myInventory;

    public static void main(String[] args) {
        createWorld();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to OUR GAME!");
        myInventory = new ContainerItem("Bag","Container","a magical bag which can fit anything in side it");
        while (true) {
            System.out.println("Enter a command:");
            String command = scanner.nextLine();
            String[] commands = command.split(" ");
            switch (commands[0].toLowerCase()) {
                case "quit":
                    System.out.println("Exiting~~~!");
                    scanner.close();
                    System.exit(0);
                case "look":
                    System.out.println(currLocation.getName() + " - " + currLocation.getDescription() + " It has the following items:");
                    for (int i = 0; i < currLocation.numItems(); i++) {
                        System.out.println("+ " + currLocation.getItem(i).getName());
                    }
                    break;
                case "examine":
                    // if user didnt type in item
                    if (commands.length == 1) {
                        System.out.println("Please insert your item!");
                    } else {
                        if (currLocation.hasItem(commands[1])) {
                            System.out.println(currLocation.getItem(commands[1]).toString());
                        } else {
                            System.out.println("Cannot find that item");
                        }
                    }
                    break;
                case "go":
                    if (commands.length == 1) {
                        System.out.println("Please insert your direction!");
                    } else {
                        String direction = commands[1].toLowerCase();
                        if (direction.equals("north") || direction.equals("south") || direction.equals("west")
                                || direction.equals("east")) {
                            if (currLocation.canMove(direction)) {
                                currLocation = currLocation.getLocation(direction);
                            } else {
                                System.out.println("Cannot move that direction");
                            }
                        } else {
                            System.out.println("Please enter a valid direction");
                        }
                    }
                    break;
                case "inventory":
                    System.out.println(myInventory.toString());
                    break;
                case "take":
                    if (commands.length == 1) {
                        System.out.println("Please insert your item!");
                    } else if (commands.length == 2) {
                        if (currLocation.hasItem(commands[1])) {
                            myInventory.addItem(currLocation.removeItem(commands[1]));
                        } else {
                            System.out.println("Cannot find that item here");
                        }
                    } else if (commands.length == 4 && commands[2].toLowerCase().equals("from")) {
                        if (!currLocation.hasItem(commands[3])) {
                            System.out.println("There is no " + commands[3].toLowerCase() + " here.");
                        } else if (!(currLocation.getItem(commands[3]) instanceof ContainerItem)) {
                            System.out.println("Cannot take an item from " + commands[3].toLowerCase() + " because it is not a container item.");
                        } else {
                            ContainerItem container = (ContainerItem) currLocation.getItem(commands[3]);
                            if (container.hasItem(commands[1])) {
                                myInventory.addItem(container.removeItem(commands[1]));
                                System.out.println("This item has been added to your inventory.");
                            } else {
                                System.out.println("There is no " + commands[1].toLowerCase() + " in " + commands[3].toLowerCase() + ".");
                            }
                        }
                    } else {
                        System.out.println("Please enter a valid command.");
                    }
                    break;
                case "drop":
                    if (commands.length == 1) {
                        System.out.println("Please insert your item!");
                    } else {
                        if (myInventory.hasItem(commands[1])) {
                            currLocation.addItem(myInventory.removeItem(commands[1]));
                        } else {
                            System.out.println("Cannot find that item in your inventory");
                        }
                    }
                    break;
                case "put":
                    if (commands.length == 4 && commands[2].toLowerCase().equals("in")) {
                        if (!currLocation.hasItem(commands[3])) {
                            System.out.println("There is no " + commands[3].toLowerCase() + " here.");
                        } else if (!(currLocation.getItem(commands[3]) instanceof ContainerItem)) {
                            System.out.println("Cannot put an item in " + commands[3].toLowerCase() + " because it is not a container item.");
                        } else {
                            ContainerItem container = (ContainerItem) currLocation.getItem(commands[3]);
                            if (myInventory.hasItem(commands[1])) {
                                container.addItem(myInventory.removeItem(commands[1]));
                                System.out.println("This item has been added to the " + container.getName().toLowerCase() + ".");
                            } else {
                                System.out.println("There is no " + commands[1].toLowerCase() + " in my inventory.");
                            }
                        }
                    } else {
                        System.out.println("Please enter a valid command.");
                    }
                    break;
                case "help":
                    printHelp();
                    break;
                default:
                    System.out.println("I don't know how to do that");
            }
        }

    }

    public static void createWorld() {
        // create locations
        Location kitchen = new Location("Kitchen", "A dark kitchen whose lights are flickering.");
        Location hallway = new Location("Hallway", "A hallway with a light on and a door at the end.");
        Location bedroom = new Location("Bedroom", "A bedroom with the bed already made and everything is well organized.");
        Location garden = new Location("Garden", "It is outside. The weather is nice today.");

        // add items
        kitchen.addItem(new Item("Knife", "Kitchenware", "A very sharp knife"));
        kitchen.addItem(new Item("Turkey", "Food", "Uncooked"));
        kitchen.addItem(new Item("Plate", "Tableware", "Already cleaned"));
        kitchen.addItem(new Item("Cupboard", "Furniture", "There are a few cups and plates inside."));

        hallway.addItem(new Item("Carpet", "Furniture", "An ash gray carpet."));
        hallway.addItem(new Item("Rack", "Furniture", "There are some pairs of shoes on it."));
        hallway.addItem(new Item("Key", "Houseware", "Hanging on the hook."));
        hallway.addItem(new Item("Painting", "Decoration", "Hanging on the wall mid-way through the hallway."));

        bedroom.addItem(new Item("Bed", "Furniture", "A king size bed that has already been nicely made."));
        bedroom.addItem(new Item("Closet", "Furniture", "Has the same color as the bed."));
        bedroom.addItem(new Item("Air-conditioner", "Electronic", "Not currently on."));
        bedroom.addItem(new Item("Curtain", "Furniture", "Partly opened, allowing some if the sunlight to shine through."));

        garden.addItem(new Item("Gloves", "Gardening tools", "Someone left them on the ground."));
        garden.addItem(new Item("Fertilizer", "Gardening tools", "There are 5 bags of them lying next to the wall."));
        garden.addItem(new Item("Pot", "Gardening tools", "There is half water inside."));
        garden.addItem(new Item("Seeds", "Gardening tools", "There are 3 bags of them on top of the fertilizers."));

        // adding ConatinerItems
        ContainerItem drawer = new ContainerItem("Drawer","Container", "A drawer next to the bed");
        drawer.addItem(new Item("Earrings","Accessories","A pair of silver colored earrings."));
        drawer.addItem(new Item("Charger", "Electronic", "A phone charger."));
        bedroom.addItem(drawer);
        ContainerItem chest = new ContainerItem("Chest", "Electronic","A mysterious chest next to the cupboard");
        chest.addItem(new Item("Gun", "Weapon", "A black gun."));
        chest.addItem(new Item("Sword", "Weapon", "A pretty long sword"));
        kitchen.addItem(chest);

        // connecting locations
        kitchen.connect("north", garden);
        garden.connect("south", kitchen);
        kitchen.connect("west", bedroom);
        bedroom.connect("east", kitchen);
        kitchen.connect("south", hallway);
        hallway.connect("north", kitchen);

        // assigning current location
        currLocation = hallway;
    }

    public static void printHelp() {
        System.out.println("The 'LOOK' command prints a description of your surroundings.");
        System.out.println("The 'EXAMINE' command prints a description of your surroundings items.");
        System.out.println("The 'GO' command moves to the direction the user chooses.");
        System.out.println("The 'TAKE' command takes an item from the current location and put it into inventory.");
        System.out.println("The 'DROP' command removes an item from inventory and place it at the current location.");
        System.out.println("The 'INVENTORY' command lists the objects in your possession. ");
        System.out.println("The 'QUIT' command exits the game.");
    }
}
package de.computercamp.rpg.entities;

import de.computercamp.rpg.Vector2D;

import java.util.*;

/**
 * Simple player class with position and up, down, right and left method
 */
public class Player extends BaseObject {

    public static final int MAX_HEALTH = 20;
    private static final int INVENTORY_SIZE = 10;

    private int health = MAX_HEALTH;
    private List<Item> inventory = new ArrayList<>(INVENTORY_SIZE);

    public Player(Vector2D position) {
        super(position);
        Timer healthTimer = new Timer(true);
        healthTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (health < MAX_HEALTH) {
                    health++;
                }
            }
        }, 0, 2000);
    }

    @Override
    public char render() {
        return 'X';
    }

    /**
     * Moves this player upwards.
     */
    public void up() {
        position.y--;
        map.onPlayerMove(this);
    }

    /**
     * Moves the player downwards.
     */
    public void down() {
        position.y++;
        map.onPlayerMove(this);
    }

    /**
     * Moves this player to the right.
     */
    public void right() {
        position.x++;
        map.onPlayerMove(this);
    }

    /**
     * Moves this player to the left.
     */
    public void left() {
        position.x--;
        map.onPlayerMove(this);
    }

    public void increaseHealth(int health) {
        this.health = this.health + health;
    }

    public void decreaseHealth(int health) {
        this.health = this.health - health;
    }

    public int getHealth() {
        return health;
    }

    public void collectItem(Item item) {
        if (map == null) {
            throw new NullPointerException("Player removed from map");
        }
        if (inventory.size() < INVENTORY_SIZE) {
            map.removeObject(item);
            inventory.add(item);
        }
    }

    /**
     * @param item the item to removeObject
     * @return Returns true if the inventory contained the specified item.
     */
    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public void printInventory() {
        System.out.println("Inventory: ");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.println(i + ". " + item.getSymbol() + " (" + item.getDisplayName() + ")");
        }
    }

    public void sendMessage(String message) {
        System.out.println("Message to player: " + message);
    }
}

package de.computercamp.rpg.entities.npcs;

import de.computercamp.rpg.Vector2D;
import de.computercamp.rpg.entities.Player;
import de.computercamp.rpg.entities.items.Item;
import de.computercamp.rpg.entities.items.Sword;
import de.computercamp.rpg.resources.Messages;

public class WeaponSmithNPC extends NPC {

    private Item item;
    private boolean used = false;

    public WeaponSmithNPC(Vector2D position) {
        super(position);
        item = new Sword(null);
    }

    @Override
    public void onHealthChange() {
        super.onHealthChange();
        if (isDead()) {
            item.setPosition(position);
            map.addObject(item);
        }
    }

    @Override
    protected void doAction(Player player) {
        if (!used) {
            player.sendMessage(Messages.npcWeaponsmith);
            player.collectItem(item);
            used = true;
        } else {
            player.sendMessage(Messages.npcWaitingForever);
        }
    }
}

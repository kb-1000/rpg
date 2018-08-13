package de.computercamp.rpg.entities.npcs;

import de.computercamp.rpg.Vector2D;
import de.computercamp.rpg.entities.LivingBaseObject;
import de.computercamp.rpg.entities.Player;
import de.computercamp.rpg.entities.items.Coin;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Ghost extends LivingBaseObject {

    private Random random = new Random();
    private boolean despawned = false;

    public Ghost(Vector2D position) {
        super(position);
    }

    public void startFighting(Player player) {
        Thread fightThread = new Thread(() -> {
            while (true) {
                if (health > 0) {
                    boolean move = true;

                    if (
                        player.equals(map.getObjectByPosition(position.withX(position.x + 1), true)) ||
                            player.equals(map.getObjectByPosition(position.withX(position.x - 1), true)) ||
                            player.equals(map.getObjectByPosition(position.withY(position.y + 1), true)) ||
                            player.equals(map.getObjectByPosition(position.withY(position.y - 1), true))
                    ) {
                        move = false;
                    }

                    if (move) { //Move
                        if (position.y > player.getPosition().y) {
                            up();
                        } else if (position.y < player.getPosition().y) {
                            down();
                        } else if (position.x > player.getPosition().x) {
                            left();
                        } else if (position.x < player.getPosition().x) {
                            right();
                        }
                    } else { //Hit
                        player.decreaseHealth(random.nextInt(5) + 5);
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    break;
                }
            }
        });
        fightThread.start();
    }

    @Override
    public void onHealthChange() {
        if (isDead() && !despawned) {
            Timer despawnTimer = new Timer(true);
            despawnTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    map.removeObject(Ghost.this);
                }
            }, 5000);
            map.addObject(new Coin(position));
            despawned = true;
        }
    }

    @Override
    public char render() {
        if (isDead()) {
            return 'X';
        }
        return '\u2e0e';
    }
}

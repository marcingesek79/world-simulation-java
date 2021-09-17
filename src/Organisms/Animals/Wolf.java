package Organisms.Animals;

import World.World;

import java.awt.*;

public class Wolf extends Animal{

    public Wolf(int x, int y, World world){
        super(9, 5, x, y, 0, 'W', world, Color.magenta);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newChild(int x, int y) {
        Wolf wolf = new Wolf(x, y, world);
        world.getMatrix()[y][x] = wolf;
        world.getOrganisms().add(wolf);
        String event = "Narodzil sie Wilk";
        world.getEventHandler().addEvent(event);
    }

    @Override
    public String getName() {
        return "'Wilk'";
    }
}

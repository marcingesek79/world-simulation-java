package Organisms.Plants;

import World.World;

import java.awt.*;

public class Grass extends Plant{

    public Grass(int x, int y, World world){
        super(0, 0, x, y, 0, 't', world, Color.gray);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newPlant(int x, int y) {
        Grass grass = new Grass(x, y, world);
        world.getMatrix()[y][x] = grass;
        world.getOrganisms().add(grass);
        String event = "Zasiano Trawe";
        world.getEventHandler().addEvent(event);
    }

    @Override
    public String getName() {
        return "'Trawa'";
    }
}

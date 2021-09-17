package Organisms.Plants;

import World.World;

import java.awt.*;

public class Dandelion extends Plant{

    public Dandelion(int x, int y, World world){
        super(0, 0, x, y, 0, 'd', world, Color.lightGray);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newPlant(int x, int y) {
        Dandelion dandelion = new Dandelion(x,y, world);
        world.getMatrix()[y][x] = dandelion;
        world.getOrganisms().add(dandelion);
        String event = "Zasiano Mlecz";
        world.getEventHandler().addEvent(event);
    }

    // Akcja
    @Override
    public void action(){
        for (int i = 0; i < 3; i++){
            super.action();
        }
    }

    @Override
    public String getName() {
        return "'Mlecz'";
    }
}

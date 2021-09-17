package Organisms.Plants;

import Organisms.Animals.Animal;
import Organisms.Organism;
import World.World;

import java.awt.*;

public class WolfBerries extends Plant{

    public WolfBerries(int x, int y, World world){
        super(99, 0, x, y, 0, 'x', world, Color.blue);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newPlant(int x, int y) {
        WolfBerries wolfBerries = new WolfBerries(x, y, world);
        world.getMatrix()[y][x] = wolfBerries;
        world.getOrganisms().add(wolfBerries);
        String event = "Zasiano Wilcze Jagody";
        world.getEventHandler().addEvent(event);
    }

    // Efekt specjalny
    @Override
    public boolean specialEffect(Organism attacker){
        if(((Animal)attacker).isHuman()){
            world.setHumanAlive(false);
        }
        world.makeOrganismInactive(this);
        world.getMatrix()[y][x] = null;
        int attX = attacker.getX();
        int attY = attacker.getY();
        world.makeOrganismInactive(attacker);
        world.getMatrix()[attY][attX] = null;
        String event = ((Animal) attacker).getName() + " zjadl " + getName() + " - oboje gina";
        world.getEventHandler().addEvent(event);
        return true;
    }

    @Override
    public String getName() {
        return "'Wilcze jagody'";
    }
}

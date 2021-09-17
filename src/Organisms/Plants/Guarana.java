package Organisms.Plants;

import Organisms.Organism;
import World.World;

import java.awt.*;

public class Guarana extends Plant{

    public Guarana(int x, int y, World world){
        super(0, 0, x, y, 0, 'g', world, Color.cyan);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newPlant(int x, int y) {
        Guarana guarana = new Guarana(x, y, world);
        world.getMatrix()[y][x] = guarana;
        world.getOrganisms().add(guarana);
        String event = "Zasiano Guarane";
        world.getEventHandler().addEvent(event);
    }

    // Efekt specjalny
    @Override
    public boolean specialEffect(Organism attacker){
        int attackerPower = attacker.getPower();
        attacker.setPower(attackerPower + 3);
        return false;
    }

    @Override
    public String getName() {
        return "'Guarana'";
    }
}

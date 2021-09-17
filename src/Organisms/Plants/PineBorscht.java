package Organisms.Plants;

import Organisms.Animals.Animal;
import Organisms.Animals.CyberSheep;
import Organisms.Animals.Human;
import Organisms.Organism;
import World.World;

import java.awt.*;
import java.util.List;

public class PineBorscht extends Plant{

    public PineBorscht(int x, int y, World world){
        super(10, 0, x, y, 0, 'b', world, Color.black);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newPlant(int x, int y) {
        PineBorscht pineBorscht = new PineBorscht(x,y, world);
        world.getMatrix()[y][x] = pineBorscht;
        world.getOrganisms().add(pineBorscht);
        String event = "Zasiano Barszcz";
        world.getEventHandler().addEvent(event);
    }

    // Efekt specjalny
    @Override
    public boolean specialEffect(Organism attacker){
        if(((Animal)attacker).isHuman()){
            world.setHumanAlive(false);
        }
        if(!(attacker instanceof CyberSheep)){
            int attX = attacker.getX();
            int attY = attacker.getY();
            world.makeOrganismInactive(attacker);
            world.getMatrix()[attY][attX] = null;
            String event = ((Animal) attacker).getName() + " zjadl " + getName() + " - oboje gina";
            world.getEventHandler().addEvent(event);
        } else {
            String event = ((Animal) attacker).getName() + " zjadl " + getName();
            world.getEventHandler().addEvent(event);
        }

        world.makeOrganismInactive(this);
        world.getMatrix()[y][x] = null;
        return true;
    }

    // Akcja
    @Override
    public void action(){
        super.action();

        List<List<Integer>> spacesWithOrganisms = world.spacesWithOrganismsAround(x, y);
        if (spacesWithOrganisms.size() <= 0) return;
        for (int i = 0; i < spacesWithOrganisms.size(); i++){
            int eX = spacesWithOrganisms.get(i).get(0);
            int eY = spacesWithOrganisms.get(i).get(1);
            if(world.getMatrix()[eY][eX] instanceof Human){
                if(((Human)world.getMatrix()[eY][eX]).isPowerActivated()){
                    continue;
                }
            }
            if(world.getMatrix()[eY][eX] instanceof CyberSheep){
                continue;
            }
            if(world.getMatrix()[eY][eX] instanceof Animal){
                String event = getName() + " zatrul " + ((Animal) world.getMatrix()[eY][eX]).getName();
                world.getEventHandler().addEvent(event);
                if(((Animal)world.getMatrix()[eY][eX]).isHuman()){
                    world.setHumanAlive(false);
                }
                world.makeOrganismInactive(world.getMatrix()[eY][eX]);
                world.getMatrix()[eY][eX] = null;
            }
        }
    }

    @Override
    public String getName() {
        return "'Barszcz sosn.'";
    }
}

package Organisms;

import Organisms.Animals.*;
import Organisms.Plants.*;
import World.World;

public class OrganismHandler {
    // Zwraca organizm w zaleznosci od symbolu
    public static Organism newOrganism(int x, int y, char symbol, World world){
        switch(symbol){
            case 'H':
                return new Human(x, y, world);
            case 'F':
                return new Fox(x, y, world);
            case 'A':
                return new Antelope(x, y, world);
            case 'T':
                return new Turtle(x, y, world);
            case 'W':
                return new Wolf(x, y, world);
            case 'S':
                return new Sheep(x, y, world);
            case 'Y':
                return new CyberSheep(x, y, world);
            case 't':
                return new Grass(x, y, world);
            case 'g':
                return new Guarana(x, y, world);
            case 'x':
                return new WolfBerries(x, y, world);
            case 'b':
                return new PineBorscht(x, y, world);
            case 'd':
                return new Dandelion(x, y, world);
            default:
                return new Grass(x, y, world);
        }

    }
}

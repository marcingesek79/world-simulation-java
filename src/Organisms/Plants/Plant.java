package Organisms.Plants;

import Organisms.Organism;
import World.World;

import java.awt.*;
import java.util.List;

public abstract class Plant extends Organism {

    public Plant(int power, int initiative, int x, int y, int age, char symbol, World world, Color color){
        super(power, initiative, x, y, age, symbol, world, color);
    }

    // Akcja
    @Override
    public void action() {
        int randomNumber = world.randomNumber(1, 101);
        if(randomNumber <= 90) return;
        List<List<Integer>> possibleSpaces = world.possibleSpacesAround(x, y);
        if (possibleSpaces.size() <= 0){
            return;
        }
        List<Integer> chosenSpace = world.getRandomPossibleSpace(possibleSpaces);
        int destX = chosenSpace.get(0);
        int destY = chosenSpace.get(1);
        newPlant(destX, destY);
    }

    // Kolizja
    @Override
    public void collision(Organism otherOrganism) {
        return;
    }

    // Efekt specjalny
    public boolean specialEffect(Organism attacker){
        return false;
    }

    // Rozprzestrzenianie sie
    public abstract void newPlant(int x, int y);
    public abstract String getName();

}

package Organisms.Animals;

import Organisms.Organism;
import World.World;

import java.awt.*;
import java.util.List;

public class Antelope extends Animal{

    public Antelope(int x, int y, World world){
        super(4, 4, x, y, 0, 'A', world, Color.yellow);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newChild(int x, int y) {
        Antelope antelope = new Antelope(x, y, world);
        world.getMatrix()[y][x] = antelope;
        world.getOrganisms().add(antelope);
        String event = "Narodzila sie Antylopa";
        world.getEventHandler().addEvent(event);
    }

    // Akcja
    @Override
    public void action(){
        List<List<Integer>> possibleSpaces = world.possibleSpacesToJump(x, y);
        List<Integer> chosenSpace = world.getRandomPossibleSpace(possibleSpaces);
        int destX = chosenSpace.get(0);
        int destY = chosenSpace.get(1);
        if (world.getMatrix()[destY][destX] == null) move(destX, destY);
        else {
            Organism otherOrganism = world.getMatrix()[destY][destX];
            collision(otherOrganism);
        }
    }

    // Efekt specjalny
    @Override
    public boolean hasEscaped() {
        int randomNumber = world.randomNumber(1, 101);
        if (randomNumber <= 50) {
            List<List<Integer>> possibleSpaces = world.possibleSpacesToJump(x, y);
            List<Integer> cell = world.getRandomPossibleSpace(possibleSpaces);
            move(cell.get(0), cell.get(1));
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "'Antylopa'";
    }
}

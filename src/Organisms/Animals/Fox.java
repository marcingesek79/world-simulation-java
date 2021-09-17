package Organisms.Animals;

import Organisms.Organism;
import World.World;

import java.awt.*;
import java.util.List;

public class Fox extends Animal{

    public Fox(int x, int y, World world){
        super(3, 7, x, y, 0,'F', world, Color.red);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newChild(int x, int y) {
        Fox fox = new Fox(x, y, world);
        world.getMatrix()[y][x] = fox;
        world.getOrganisms().add(fox);
        String event = "Narodzil sie Lis";
        world.getEventHandler().addEvent(event);
    }

    // Akcja
    @Override
    public void action(){
        List<List<Integer>> possibleSpaces = world.possibleSpacesToWalkSafe(x,y,this);
        if(possibleSpaces.size() <= 0) return;

        List<Integer> chosenSpace = world.getRandomPossibleSpace(possibleSpaces);
        int destX = chosenSpace.get(0);
        int destY = chosenSpace.get(1);
        if(world.getMatrix()[destY][destX] == null){
            move(destX, destY);
        } else{
            Organism otherOrganism = world.getMatrix()[destY][destX];
            collision(otherOrganism);
        }
    }

    @Override
    public String getName() {
        return "'Lis'";
    }
}

package Organisms.Animals;

import Organisms.Organism;
import Organisms.Plants.Plant;
import World.World;

import java.awt.*;
import java.util.List;

public abstract class Animal extends Organism {

    public Animal(int power, int initiative, int x, int y, int age, char symbol, World world, Color color){
        super(power, initiative, x, y, age, symbol, world, color);
    }

    @Override
    public void action() {
        List<List<Integer>> possibleSpaces = world.possibleSpacesToWalk(x, y);
        List<Integer> chosenSpace = world.getRandomPossibleSpace(possibleSpaces);
        int destX = chosenSpace.get(0);
        int destY = chosenSpace.get(1);
        if(world.getMatrix()[destY][destX] == null) move(destX, destY);
        else{
            Organism otherOrganism = world.getMatrix()[destY][destX];
            collision(otherOrganism);
        }
    }

    @Override
    public void collision(Organism otherOrganism) {
        if(otherOrganism.getSymbol() == symbol){
            List<List<Integer>> possibleSpaces = world.possibleSpacesAround(otherOrganism.getX(), otherOrganism.getY());
            if(possibleSpaces.size() <= 0){
                System.out.println("No empty space");
                return;
            }
            List<Integer> cell = world.getRandomPossibleSpace(possibleSpaces);
            newChild(cell.get(0), cell.get(1));
        }
        else{
            if(otherOrganism instanceof Animal){
                battle(otherOrganism);
            }
            else if (otherOrganism instanceof Plant){
                eatPlant(otherOrganism);
            }
        }
    }

    public void battle(Organism defender){
        int dX = defender.getX();
        int dY = defender.getY();

        if(((Animal)defender).hasDefended(power)) return;
        if(((Animal)defender).hasEscaped()){
            move(dX, dY);
            return;
        }

        if(power >= defender.getPower()){
            if(((Animal)defender).isHuman()){
                world.setHumanAlive(false);
            }
            world.makeOrganismInactive(defender);
            world.getMatrix()[dY][dX] = null;
            move(dX, dY);
            String event = getName() + " pokonuje " + ((Animal) defender).getName();
            world.getEventHandler().addEvent(event);
        }
        else{
            if(((Animal)this).isHuman()){
                world.setHumanAlive(false);
            }
            world.makeOrganismInactive(this);
            world.getMatrix()[y][x] = null;
            String event = ((Animal) defender).getName() + " pokonuje " + getName();
            world.getEventHandler().addEvent(event);
        }
    }

    public void eatPlant(Organism plant){
        int pX = plant.getX();
        int pY = plant.getY();
        boolean isEventAdded = false;
        if(((Plant)plant).specialEffect(this)){
            isEventAdded = true;
            return;
        }
        world.makeOrganismInactive(plant);
        world.getMatrix()[pY][pX] = null;
        move(pX, pY);
        if(!isEventAdded){
            String event = getName() + " zjada " + ((Plant) plant).getName();
            world.getEventHandler().addEvent(event);
        }
    }

    public void move(int destX, int destY){
        world.getMatrix()[destY][destX] = this;
        world.getMatrix()[y][x] = null;
        this.y = destY;
        this.x = destX;
    }

    // Rozprzestrzenianie sie
    public abstract void newChild(int x, int y);

    // Efekty specjalne
    public boolean hasDefended(int enemyPower){
        return false;
    }

    public boolean hasEscaped(){
        return false;
    }

    public boolean isHuman(){
        return false;
    }

    public abstract String getName();
}

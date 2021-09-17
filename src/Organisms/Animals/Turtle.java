package Organisms.Animals;

import World.World;

import java.awt.*;

public class Turtle extends Animal{

    public Turtle(int x, int y, World world){
        super(2, 1, x, y, 0, 'T', world, Color.orange);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newChild(int x, int y) {
        Turtle turtle = new Turtle(x, y, world);
        world.getMatrix()[y][x] = turtle;
        world.getOrganisms().add(turtle);
        String event = "Narodzil sie Zolw";
        world.getEventHandler().addEvent(event);
    }

    // Akcja
    @Override
    public void action(){
        int randomNumber = world.randomNumber(1, 101);
        if (randomNumber <= 75) return;
        super.action();
    }

    // Efekt specjalny
    @Override
    public boolean hasDefended(int enemyPower){
        if (enemyPower < 5){
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "'Zolw'";
    }
}

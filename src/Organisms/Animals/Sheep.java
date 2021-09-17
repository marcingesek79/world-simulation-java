package Organisms.Animals;

import World.World;

import java.awt.*;

public class Sheep extends Animal{

    public Sheep(int x, int y, World world){
        super(4, 4, x, y, 0, 'S', world, Color.pink);
    }

    // Rozprzestrzenianie sie
    @Override
    public void newChild(int x, int y) {
        Sheep sheep = new Sheep(x, y, world);
        world.getMatrix()[y][x] = sheep;
        world.getOrganisms().add(sheep);
        String event = "Narodzila sie Owca";
        world.getEventHandler().addEvent(event);
    }

    @Override
    public String getName() {
        return "'Owca'";
    }
}

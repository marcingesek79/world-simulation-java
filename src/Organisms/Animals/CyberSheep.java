package Organisms.Animals;

import Organisms.Organism;
import World.World;

import java.awt.*;
import java.util.List;


public class CyberSheep extends Animal{
    public CyberSheep(int x, int y, World world) {
        super(11, 4, x, y, 0, 'Y', world, new Color(153, 102, 51));
    }

    // Rozprzestrzenianie sie
    @Override
    public void newChild(int x, int y) {
        CyberSheep cyberSheep = new CyberSheep(x, y, world);
        world.getMatrix()[y][x] = cyberSheep;
        world.getOrganisms().add(cyberSheep);
        String event = "Narodzila sie Cyberowca";
        world.getEventHandler().addEvent(event);
    }

    // Akcja
    @Override
    public void action(){
        List<Integer> nearestPine = world.getNearestPineBorscht(x, y);
        if(nearestPine.size() <= 0){
            super.action();
            return;
        }
        int pineX = nearestPine.get(0);
        int pineY = nearestPine.get(1);
        int destX = x, destY = y;
        if(x > pineX) {
            destX = x-1;
            destY= y;
        }
        else if(x<pineX) {
            destX = x+1;
            destY = y;
        }
        else if(x==pineX){
            if(y > pineY) {
                destX = x;
                destY = y-1;
            }
            else if(y < pineY) {
                destX = x;
                destY = y+1;
            }
        }
        if(world.getMatrix()[destY][destX] == null) move(destX, destY);
        else{
            Organism otherOrganism = world.getMatrix()[destY][destX];
            collision(otherOrganism);
        }
    }

    @Override
    public String getName() {
        return "'Cyberowca'";
    }
}

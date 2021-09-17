package Organisms.Animals;

import Organisms.Organism;
import Organisms.Plants.Plant;
import World.World;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Human extends Animal{

    private char dir;
    private int powerCooldown;
    private int powerDuration;
    private boolean isPowerActivated;
    private boolean isPowerOnCooldown;

    // Konstruktor
    public Human(int x, int y, World world){
        super(5, 4, x, y, 0, 'H', world, Color.green);
        this.dir = 'v';
        this.powerCooldown = 5;
        this.powerDuration = 5;
        this.isPowerActivated = false;
        this.isPowerOnCooldown = false;
    }

    // Rozprzestrzenianie sie
    @Override
    public void newChild(int x, int y) {
        return;
    }

    // Wykonanie akcji
    @Override
    public void action(){
        if (isPowerActivated) powerDuration--;
        else if (isPowerOnCooldown) powerCooldown--;

        if (powerDuration <= 0){
            powerCooldown = 5;
            powerDuration = 5;
            isPowerActivated = false;
            isPowerOnCooldown = true;
        }
        if (powerCooldown <=0 && isPowerOnCooldown){
            isPowerOnCooldown = false;
            powerCooldown = 5;
        }

        List<Integer> chosenSpace = handleDir();
        if (chosenSpace.size() <= 0) return;
        int destX = chosenSpace.get(0);
        int destY = chosenSpace.get(1);
        if(world.getMatrix()[destY][destX] == null){
            move(destX, destY);
        }
        else{
            Organism otherOrganism = world.getMatrix()[destY][destX];
            collision(otherOrganism);
        }
    }

    // Ucieczka
    public void escape(){
        List<List<Integer>> possibleSpaces = world.possibleSpacesAround(x, y);
        if (possibleSpaces.size() <= 0){
            world.setHumanAlive(false);
            world.makeOrganismInactive(this);
            world.getMatrix()[y][x] = null;
            return;
        }
        List<Integer> cell = world.getRandomPossibleSpace(possibleSpaces);
        move(cell.get(0), cell.get(1));
    }

    @Override
    public boolean hasEscaped(){
        if(isPowerActivated){
            escape();
            return true;
        }
        return false;
    }

    // Handlowanie inputu
    public List<Integer> handleDir(){
        List<Integer> nextSpace = new ArrayList<>();
        switch(dir){
            case 'w':
                if(y>0){
                    nextSpace.add(x);
                    nextSpace.add(y-1);
                }
                break;
            case 'a':
                if(x>0){
                    nextSpace.add(x-1);
                    nextSpace.add(y);
                }
                break;
            case 's':
                if(y<world.getSizeY()-1){
                    nextSpace.add(x);
                    nextSpace.add(y+1);
                }
                break;
            case 'd':
                if(x<world.getSizeX()-1){
                    nextSpace.add(x+1);
                    nextSpace.add(y);
                }
                break;
            case '?':
                return nextSpace;
        }
        return nextSpace;
    }

    // Kolizja
    @Override
    public void collision(Organism otherOrganism){
        if(otherOrganism instanceof Animal){
            if(otherOrganism.getPower() > power && isPowerActivated){
                escape();
                return;
            }
            battle(otherOrganism);
        }
        else if(otherOrganism instanceof Plant){
            if(otherOrganism.getPower() > power && isPowerActivated){
                escape();
                return;
            }
            eatPlant(otherOrganism);
        }
    }

    // Gettery & settery
    @Override
    public boolean isHuman(){
        return true;
    }

    public char getDir() {
        return dir;
    }

    public void setDir(char dir) {
        this.dir = dir;
    }

    public int getPowerCooldown() {
        return powerCooldown;
    }

    public void setPowerCooldown(int powerCooldown) {
        this.powerCooldown = powerCooldown;
    }

    public int getPowerDuration() {
        return powerDuration;
    }

    public void setPowerDuration(int powerDuration) {
        this.powerDuration = powerDuration;
    }

    public boolean isPowerActivated() {
        return isPowerActivated;
    }

    public void setPowerActivated(boolean powerActivated) {
        isPowerActivated = powerActivated;
    }

    public boolean isPowerOnCooldown() {
        return isPowerOnCooldown;
    }

    public void setPowerOnCooldown(boolean powerOnCooldown) {
        isPowerOnCooldown = powerOnCooldown;
    }

    @Override
    public String getName() {
        return "'Czlowiek'";
    }

    // Zapisywanie do pliku
    @Override
    public void writeToFile(PrintStream file) throws IOException {
        super.writeToFile(file);
        file.println(powerCooldown);
        file.println(powerDuration);
        file.println(isPowerActivated);
        file.println(isPowerOnCooldown);
    }

    // :(
    /*
    @Override
    public Organism loadFromFile(Scanner sc, World newWorld){
        Organism newOrganism = super.loadFromFile(sc, newWorld);
        int powerCooldown, powerDuration;
        boolean isPowerActivated, isPowerOnCooldown;
        powerCooldown = sc.nextInt();
        powerDuration = sc.nextInt();
        isPowerActivated = sc.nextBoolean();
        isPowerOnCooldown = sc.nextBoolean();
        newWorld.addHuman((Human)newOrganism);
        newWorld.getHuman().setPowerActivated(isPowerActivated);
        newWorld.getHuman().setPowerOnCooldown(isPowerOnCooldown);
        newWorld.getHuman().setPowerCooldown(powerCooldown);
        newWorld.getHuman().setPowerDuration(powerDuration);
        return newOrganism;
    }
    */
}

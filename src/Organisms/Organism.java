package Organisms;

import World.World;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public abstract class Organism {
    protected int power;
    protected int initiative;
    protected int x;
    protected int y;
    protected int age;
    protected char symbol;
    protected World world;
    protected Color color;

    // Konstruktor
    public Organism(int power, int initiative, int x, int y, int age, char symbol, World world, Color color){
        this.power = power;
        this.initiative = initiative;
        this.x = x;
        this.y = y;
        this.age = age;
        this.symbol = symbol;
        this.world = world;
        this.color = color;
    }

    // Wykonanie akcji
    public abstract void action();
    // Kolizja
    public abstract void collision(Organism otherOrganism);

    // Postarzenie
    public void makeOlder(){
        this.age++;
    }

    // Gettery & settery
    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSymbol(){
        return symbol;
    }

    public Color getColor() { return color; }

    // Zapisanie do pliku
    public void writeToFile(PrintStream file) throws IOException {
        file.println(symbol);
        file.println(power);
        file.println(initiative);
        file.println(age);
        file.println(x);
        file.println(y);
    }

    /*
    public Organism loadFromFile(Scanner sc, World newWorld){
        int nX, nY, nAge, nPower, nInitiative;
        char nSymbol;
        nSymbol = sc.next().charAt(0);
        nPower = sc.nextInt();
        nInitiative = sc.nextInt();
        nAge = sc.nextInt();
        nX = sc.nextInt();
        nY = sc.nextInt();
        Organism newOrganism = OrganismHandler.newOrganism(nX, nY, nSymbol, newWorld);
        newOrganism.setPower(nPower);
        newOrganism.setAge(nAge);
        newOrganism.setInitiative(nInitiative);
        return newOrganism;
    }
    */
}

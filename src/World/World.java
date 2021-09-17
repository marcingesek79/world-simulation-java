package World;

import Organisms.Animals.Human;
import Organisms.Organism;
import Organisms.OrganismHandler;
import Organisms.Plants.PineBorscht;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class World {
    private final int sizeX;
    private final int sizeY;
    private int turn;
    private Organism[][] matrix;
    private List<Organism> organisms;
    private Human human;
    private boolean isHumanAlive;
    private GameFrame frame;
    private EventHandler eventHandler;

    // Konstruktor
    public World(int n, int m){
        this.sizeX = n;
        this.sizeY = m;
        this.turn = 0;
        this.human = null;
        this.matrix = new Organism[sizeY][sizeX];
        organisms = new ArrayList<Organism>();
        this.isHumanAlive = false;
        eventHandler = new EventHandler();
        frame = new GameFrame(this);
    }

    // Dodawanie organizmu
    public void addOrganism(Organism organism){
        int x = organism.getX();
        int y = organism.getY();
        if(matrix[y][x] == null){
            matrix[y][x] = organism;
            organisms.add(organism);
        } else {
            System.out.println("No space for this organism.");
        }
    }

    // Dodawanie czlowieka
    public void addHuman(Human human){
        int x = human.getX();
        int y = human.getY();
        if(matrix[y][x] == null){
            matrix[y][x] = human;
            organisms.add(human);
            this.human = human;
            isHumanAlive = true;
        }
    }

    // Nadanie organizmowi statusu do usuniecia
    public void makeOrganismInactive(Organism organism){
        for(int i=0; i<organisms.size(); i++){
            if (organisms.get(i) == null) continue;
            if (organisms.get(i).getX() == organism.getX() && organisms.get(i).getY() == organism.getY()){
                organisms.set(i, null);
                return;
            }
        }
    }

    // Usuwanie z organizmow
    public void deleteFromOrganisms(){
        if(organisms.isEmpty()) return;
        for (int i=organisms.size() - 1; i >= 0; i--){
            if(organisms.get(i) == null){
                organisms.remove(i);
            }
        }
    }

    // Sortowanie organizmow
    public void sortOrganisms(){
        Collections.sort(organisms, new OrganismComparator());
    }

    // Zwraca losowego inta
    public int randomNumber(int rangeStart, int rangeEnd){
        Random rand = new Random();
        int number = rand.nextInt(rangeEnd) + rangeStart;
        return number;
    }

    // Zwraca mozliwe miejsca do przejscia
    public List<List<Integer>> possibleSpacesToWalk(int x, int y){
        List<List<Integer>> possibleSpaces = new ArrayList<>();
        int[] possibilities = {-1,0,1};
        for (int i =0; i < 3; i++){
            int toAddX = possibilities[i];
            for (int j = 0; j < 3; j++){
                int toAddY = possibilities[j];
                if (toAddX == toAddY) continue;
                else if (toAddX == -toAddY) continue;
                else if (x + toAddX >= sizeX || x + toAddX < 0) continue;
                else if (y + toAddY >= sizeY || y + toAddY < 0) continue;
                else{
                    List<Integer> possibleSpace = Arrays.asList(x+toAddX, y+toAddY);
                    possibleSpaces.add(possibleSpace);
                }
            }
        }
        return possibleSpaces;
    }

    // Zwraca mozliwe miejsca dookola jakiegos pola
    public List<List<Integer>> possibleSpacesAround(int x, int y){
        List<List<Integer>> possibleSpaces = new ArrayList<>();
        int[] possibilities = {-1,0,1};
        for (int i = 0; i < 3; i++){
            int toAddX = possibilities[i];
            for (int j = 0; j < 3; j++){
                int toAddY = possibilities[j];
                if (x + toAddX >= sizeX || x + toAddX < 0) continue;
                else if (y + toAddY >= sizeY || y + toAddY < 0) continue;
                else if (toAddY == 0 && toAddX == 0) continue;
                else if (matrix[y+toAddY][x+toAddX] != null) continue;
                else{
                    List<Integer> possibleSpace = Arrays.asList(x+toAddX, y+toAddY);
                    possibleSpaces.add((possibleSpace));
                }
            }
        }
        return possibleSpaces;
    }

    // Zwraca mozliwe miejsca do skoku (antylopa)
    public List<List<Integer>> possibleSpacesToJump(int x, int y){
        List<List<Integer>> possibleSpaces = new ArrayList<>();
        int[] possibilities = {-2,0,2};
        for (int i = 0; i < 3; i++){
            int toAddX = possibilities[i];
            for (int j = 0; j < 3; j++){
                int toAddY = possibilities[j];
                if (toAddX == toAddY) continue;
                else if (toAddX == -toAddY) continue;
                else if (x + toAddX >= sizeX || x + toAddX < 0) continue;
                else if (y + toAddY >= sizeY || y + toAddY < 0) continue;
                else {
                    List<Integer> possibleSpace = Arrays.asList(x+toAddX, y+toAddY);
                    possibleSpaces.add(possibleSpace);
                }
            }
        }
        return possibleSpaces;
    }

    // Zwraca mozliwe miejsca do przejscia (lis)
    public List<List<Integer>> possibleSpacesToWalkSafe(int x, int y, Organism org){
        List<List<Integer>> possibleSpaces = new ArrayList<>();
        int[] possibilities = {-1,0,1};
        for (int i = 0; i < 3; i++){
            int toAddX = possibilities[i];
            for (int j = 0; j < 3; j++){
                int toAddY = possibilities[j];
                if (toAddX == toAddY) continue;
                else if (toAddX == -toAddY) continue;
                else if (x + toAddX >= sizeX || x + toAddX < 0) continue;
                else if (y + toAddY >= sizeY || y + toAddY < 0) continue;
                else {
                    if (matrix[y+toAddY][x+toAddX] != null){
                        if (matrix[y+toAddY][x+toAddX].getPower() > org.getPower()) continue;
                    }
                    List<Integer> possibleSpace = Arrays.asList(x+toAddX, y+toAddY);
                    possibleSpaces.add(possibleSpace);
                }
            }
        }
        return possibleSpaces;
    }

    // Zwraca miejsca z organizmami dookola danego organizmu
    public List<List<Integer>> spacesWithOrganismsAround(int x, int y){
        List<List<Integer>> possibleSpaces = new ArrayList<>();
        int[] possibilities = {-1,0,1};
        for (int i = 0; i < 3; i++){
            int toAddX = possibilities[i];
            for (int j = 0; j < 3; j++){
                int toAddY = possibilities[j];
                if(x + toAddX >= sizeX || x + toAddX < 0) continue;
                else if (y + toAddY >= sizeY || y + toAddY < 0) continue;
                else if (toAddY == 0 && toAddX == 0) continue;
                else if (matrix[y+toAddY][x+toAddX] != null){
                    List<Integer> possibleSpace = Arrays.asList(x+toAddX, y+toAddY);
                    possibleSpaces.add(possibleSpace);
                }
            }
        }
        return possibleSpaces;
    }

    // Zwraca wszystkie miejsca na swiecie
    public List<List<Integer>> allSpacesInMatrix(){
        List<List<Integer>> allSpaces = new ArrayList<>();
        for (int i =0; i < sizeY; i++){
            for (int j = 0; j < sizeX; j++){
                List<Integer> space = Arrays.asList(j, i);
                allSpaces.add(space);
            }
        }
        return allSpaces;
    }

    // Generuje losowy swiat
    public void generateRandomWorld(){
        int count = 11;
        int times = 2;
        List<Character> symbols = Arrays.asList('A', 'F', 'S', 'T', 'W', 'Y', 'd', 't', 'g', 'b', 'x');
        List<List<Integer>> spacesInMatrix = allSpacesInMatrix();
        for (int i = 0; i < count; i++){
            for (int j = 0; j < times; j++){
                char symbol = symbols.get(i);
                List<Integer> space = getRandomPossibleSpace(spacesInMatrix);
                while(matrix[space.get(1)][space.get(0)] != null){
                    space = getRandomPossibleSpace(spacesInMatrix);
                }
                Organism newOrganism = OrganismHandler.newOrganism(space.get(0), space.get(1), symbol, this);
                addOrganism(newOrganism);
            }
        }
    }


    // Zwraca losowa pozycje z listy
    public List<Integer> getRandomPossibleSpace(List<List<Integer>> list){
        int randomIndex = randomNumber(0, list.size());
        List<Integer> chosenSpace = list.get(randomIndex);
        return chosenSpace;
    }

    // Zwraca najblizszy barszcz (cyberowca)
    public List<Integer> getNearestPineBorscht(int x, int y){
        List<Integer> cell = new ArrayList<>();
        int nearestX = -1, nearestY = -1;
        double minDistance = 0;
        for (int i = 0; i < organisms.size(); i++){
            if(organisms.get(i) instanceof PineBorscht){
                int pineX = organisms.get(i).getX();
                int pineY = organisms.get(i).getY();
                double distance = Math.sqrt(Math.abs(Math.pow(pineX-x, 2)+Math.pow(pineY-y,2)));
                if(minDistance == 0 || distance < minDistance){
                    minDistance = distance;
                    nearestX = pineX;
                    nearestY = pineY;
                }
            }
        }
        System.out.println(minDistance);
        if(nearestX != -1 && nearestY != -1){
            cell.add(nearestX);
            cell.add(nearestY);
        }
        return cell;
    }

    // Gettery & settery
    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void addTurn(){
        turn++;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurns(int turn){
        this.turn = turn;
    }

    public Organism[][] getMatrix() {
        return matrix;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public Human getHuman() {
        return human;
    }

    public boolean isHumanAlive() {
        return isHumanAlive;
    }

    public void setHumanAlive(boolean humanAlive) {
        isHumanAlive = humanAlive;
    }

    public GameFrame getFrame(){
        return frame;
    }

    public EventHandler getEventHandler(){
        return eventHandler;
    }
}

// Porownuje organizmy przy sortowaniu
class OrganismComparator implements Comparator<Organism>{
    @Override
    public int compare(Organism o1, Organism o2) {
        if (o1.getInitiative() > o2.getInitiative()) {
            return 1;
        }
        else if (o1.getInitiative() == o1.getInitiative()){
            if (o1.getAge() > o2.getAge()) return 1;
        }
        return 0;
    }
}

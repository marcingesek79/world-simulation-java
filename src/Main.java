import Organisms.Animals.Human;
import World.World;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        System.out.println("Podaj n: ");
        n = scanner.nextInt();
        System.out.println("Podaj m: ");
        m = scanner.nextInt();
        World world = new World(n, m);
        Human human = new Human(n-1, m-1, world);
        world.addHuman(human);
        world.generateRandomWorld();
    }
}

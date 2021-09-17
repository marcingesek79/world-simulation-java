package World;

import Organisms.Animals.Human;
import Organisms.Organism;
import Organisms.OrganismHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Scanner;

public class GamePanel extends JPanel implements KeyListener {
    private final int WIDTH;
    private final int HEIGHT;
    private final int CELL_SIZE = 20;
    private World world;
    private JButton newTurnButton;
    private JButton activatePowerButton;
    private JButton saveToFileButton;
    private JButton loadWorldButton;
    private JLabel turnLabel;
    private JLabel eventsLabel;
    private boolean canMove;
    private int dir;

    // Konstruktor
    public GamePanel(World world){
        this.world = world;
        this.WIDTH = world.getSizeX() * CELL_SIZE + 200;
        this.HEIGHT = world.getSizeY() * CELL_SIZE + 200;
        this.canMove = false;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setFocusable(true);

        // Dodawanie buttonow i tekstu
        newTurnButton = new JButton("Nowa tura");
        setNewTurnButton();
        add(newTurnButton);

        activatePowerButton = new JButton("Specjalna moc");
        setActivatePowerButton();
        add(activatePowerButton);

        saveToFileButton = new JButton("Zapisz");
        setSaveToFileButton();
        add(saveToFileButton);

        loadWorldButton = new JButton("Wczytaj");
        setLoadWorldButton();
        add(loadWorldButton);

        turnLabel = new JLabel();
        setTurnLabel();
        add(turnLabel);

        eventsLabel = new JLabel();
        eventsLabel.setVerticalAlignment(SwingConstants.TOP);
        setEventsLabel();
        add(eventsLabel);

        addKeyListener(this);

    }

    // Przygotowuje tekst z tura
    public void setTurnLabel(){
        int turn = world.getTurn();
        turnLabel.setBounds(world.getSizeX()*CELL_SIZE + 50, 20, 100, 50);
        turnLabel.setText("Tura: " + turn);
    }

    // Przygotowuje tekst z eventami
    public void setEventsLabel(){
        eventsLabel.setBounds( 20, world.getSizeY()*CELL_SIZE + 20, 200, 200);
        eventsLabel.setText(world.getEventHandler().printEvents());
    }

    // Przygotowuje button nowej tury
    public void setNewTurnButton(){
        newTurnButton.setEnabled(true);
        newTurnButton.setBounds(world.getSizeX()*CELL_SIZE + 50, 100, 100, 50);
        newTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(canMove || !world.isHumanAlive()){
                        takeTurn();
                        requestFocus();
                    }
                    requestFocus();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
    }

    // Przygotowuje button mocy specjalnej
    public void setActivatePowerButton(){
        activatePowerButton.setEnabled(true);
        activatePowerButton.setBounds(world.getSizeX()*CELL_SIZE + 50, 160, 125, 50);
        activatePowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.getHuman().setPowerActivated(true);
                System.out.println("Aktywowano umiejetnosc");
                activatePowerButton.setEnabled(false);
                requestFocus();
            }
        });
    }

    // Przygotowuje button do zapisywania
    public void setSaveToFileButton(){
        saveToFileButton.setEnabled(true);
        saveToFileButton.setBounds(world.getSizeX()*CELL_SIZE + 50, 220, 100, 50);
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveToFile();
                    requestFocus();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    // Przygotowuje button do wczytania swiata
    public void setLoadWorldButton(){
        loadWorldButton.setEnabled(true);
        loadWorldButton.setBounds(world.getSizeX()*CELL_SIZE + 50, 280, 100, 50);
        loadWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    load();
                    requestFocus();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    // Malowanie po JPanelu
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawWorld(g);
    }

    public void drawWorld(Graphics g){
        for (int i = 0; i < world.getSizeY(); i++){
            for (int j = 0; j < world.getSizeX(); j++){
                if (world.getMatrix()[i][j] == null){
                    g.setColor(Color.white);
                } else {
                    g.setColor(world.getMatrix()[i][j].getColor());
                }

                g.fillRect(j * CELL_SIZE + 20, i * CELL_SIZE + 20, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    // Zapisywanie swiata
    public void saveToFile() throws IOException {
        PrintStream file = new PrintStream(new File("save.txt"));
        file.println(world.getSizeX());
        file.println(world.getSizeY());
        file.println(world.getTurn());
        file.println(world.isHumanAlive());
        file.println(world.getOrganisms().size());
        for (int i = 0; i < world.getOrganisms().size(); i++){
            world.getOrganisms().get(i).writeToFile(file);
        }
        System.out.println("Zapisano swiat");
        file.close();
    }

    // Wczytywanie swiata
    public void load() throws IOException {
        Scanner sc = new Scanner(new File("save.txt"));
        int sizeX, sizeY, turn, n;
        boolean isHumanAlive;
        sizeX = sc.nextInt();
        sizeY = sc.nextInt();
        World newWorld = new World(sizeX, sizeY);
        turn = sc.nextInt();
        newWorld.setTurns(turn);
        isHumanAlive = sc.nextBoolean();
        newWorld.setHumanAlive(isHumanAlive);
        n = sc.nextInt();
        for (int i = 0; i < n; i++){
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
            if (nSymbol == 'H'){
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
                continue;
            }
            newWorld.addOrganism(newOrganism);
        }
        sc.close();
        System.out.println("Wczytano swiat");
        world.getFrame().setVisible(false);
        world.getFrame().dispose();
        this.world = newWorld;
    }

    // Wykonanie tury
    public void takeTurn() throws InterruptedException {
        newTurnButton.setEnabled(false);
        activatePowerButton.setEnabled(false);
        revalidate();
        repaint();

        if(world.getTurn() == 0){
            world.deleteFromOrganisms();
            world.sortOrganisms();
        }

        int size = world.getOrganisms().size();

        for(int i = 0; i<size;i++){
            if(world.getOrganisms().get(i) == null) continue;
            world.getOrganisms().get(i).makeOlder();
            world.getOrganisms().get(i).action();
            revalidate();
            updateUI();
            repaint();
        }

        if(world.isHumanAlive()){
            if(world.getHuman().isPowerActivated()){
                System.out.println("Pozostałe tury: " + world.getHuman().getPowerDuration());
            }
            else if (world.getHuman().isPowerOnCooldown()){
                System.out.println("Do odnowienia: " + world.getHuman().getPowerCooldown());
            }
        }

        world.addTurn();
        setTurnLabel();
        repaint();
        revalidate();
        System.out.println("Wykonano ture: " + world.getTurn());
        world.deleteFromOrganisms();
        world.sortOrganisms();
        newTurnButton.setEnabled(true);
        if(world.getHuman().isPowerActivated() || world.getHuman().isPowerOnCooldown()){
            activatePowerButton.setEnabled(false);
        }
        else{
            activatePowerButton.setEnabled(true);
        }
        requestFocus();
        canMove = false;
        setEventsLabel();
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Input czlowieka
    @Override
    public void keyPressed(KeyEvent e) {
        if(!canMove && world.isHumanAlive()){
            dir = e.getKeyCode();
            canMove = true;
            if(dir == KeyEvent.VK_LEFT){
                world.getHuman().setDir('a');
            }
            else if(dir == KeyEvent.VK_UP){
                world.getHuman().setDir('w');
            }
            else if(dir == KeyEvent.VK_DOWN){
                world.getHuman().setDir('s');
            }
            else if(dir == KeyEvent.VK_RIGHT){
                world.getHuman().setDir('d');
            }
            else{
                world.getHuman().setDir('?');
                System.out.println("Zle");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

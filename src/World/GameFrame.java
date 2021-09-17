package World;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(World world) {
        add(new GamePanel(world));
        setTitle("Marcin Gesek 184580");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

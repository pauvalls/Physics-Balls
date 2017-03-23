/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicballs;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import rules.SpaceRules;
import server.Server;

/**
 *
 * @author Liam-Portatil
 */
public class PhysicBalls extends JFrame {

    /**
     * Global parameters
     */
    private Container content;
    private Server server;
    private Space space = new Space(650, 400, 10);
    String prueba;
    /**
     * Constructor
     */
    public PhysicBalls() {
        init();
        new Thread(space).start();
        if (SpaceRules.randomGeneratorOn) {
            new RandomGenerator(space).start();
        }
        if (SpaceRules.serverOn) {
            server = new Server(space);
        }

    }

    public void init() {
        //initial values
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(150, 150);
        setLayout(new GridLayout(1, 1));

        //Main panel
        content = getContentPane();
        content.add(space);

        pack();
        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PhysicBalls();
    }

}

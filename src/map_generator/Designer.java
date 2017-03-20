/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map_generator;

import javax.swing.JFrame;

/**
 *
 * @author Mokiductions
 */
public class Designer extends JFrame {
    
    public Designer() {
        super("PhysicsBalls - Map designer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Designer());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Designer();
    }
}

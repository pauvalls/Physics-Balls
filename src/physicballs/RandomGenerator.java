/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicballs;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Liam-Portatil
 */
public class RandomGenerator extends Thread {
    
    Space parent;

    public RandomGenerator(Space parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        while (true) {
            try {
                parent.addBall();
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RandomGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

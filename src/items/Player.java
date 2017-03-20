/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import physicballs.Space;

/**
 *
 * @author Liam-Portatil
 */
public class Player extends Ball {

    public Player(float x, float y, float speedx, float speedy, float radius, float mass, Space parent) {
        super(x, y, speedx, speedy, radius, mass, parent);
        color = Color.red;
    }

    public void moveUp() {
        y -= speedy;
    }

    public void moveDown() {
        y += speedy;
    }

    public void moveLeft() {
        x += speedx;
    }

    public void moveRight() {
        x -= speedx;
    }

    @Override
    public void run() {

        while (true) {
            try {
                parent.checkCollision(this);
//                movement();
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Ball.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Liam-Portatil
 */
public class Obstaculo {

    /**
     * Global parameters
     */
    private float x;
    private float y;

    private float width;

//    private Space parent;
    /**
     *
     * @param x
     * @param y
     * @param width
     */
    public Obstaculo(float x, float y, float width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    /**
     * Draw the ball in the graphics context g. Note: The drawing color in g is
     * changed to the color of the ball.
     *
     */
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) x, (int) y, (int) width, (int) width);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public synchronized boolean inRange(Ball b) {
        return b.getY() - b.getRadius() < y + width
                && b.getY() + b.getRadius() > y
                && b.getX() - b.getRadius() < x + width
                && b.getX() + b.getRadius() > x;
    }
}

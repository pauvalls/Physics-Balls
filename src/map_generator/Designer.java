/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map_generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author PC_15
 */
public class Designer extends JSplitPane {

    // @todo: Cambiar estas listas genéricas por listas de los objetos en concreto
    private ArrayList<Rectangle> obstacles = new ArrayList<>();
    private Rectangle obstacle;
    private ArrayList<Point> balls = new ArrayList<>();
    private Point ball;

    // @todo: esto sobrará
    private int radius = 10;

    private String item = "BALL";

    // Pila usada para el botón de deshacer
    private Stack<String> undoList = new Stack<>();

    // Los dos paneles en los que se separa la ventana, el de los controles y
    // el del mapa
    private JPanel controlPanel;
    private JPanel mapPanel;

    // Botones del panel de controles
    private JButton undoBtn;
    private JButton ballBtn;
    private JButton obsBtn;
    private JButton eraseBtn;

    public Designer() throws IOException {
        super(JSplitPane.VERTICAL_SPLIT);
        setDividerSize(0);

        createControlPanel();
        createMapPanel();

        setLeftComponent(controlPanel);
        setRightComponent(mapPanel);
    }

    private void createControlPanel() throws IOException {
        // Botón para cambiar de item
        controlPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 200, 1000, 3);
                g.fillRect(503, 0, 3, 200);
            }
        };
        controlPanel.setPreferredSize(new Dimension(1000, 203));
        controlPanel.setLayout(null);

        // Botón para cambiar de item
        ballBtn = new JButton();
        ballBtn.setSize(40, 40);
        ballBtn.setLocation(5, 5);
        ballBtn.setBorder(new RoundedBorder(10));
        ballBtn.setForeground(Color.GRAY);
        ballBtn.setText("Ba");
        ballBtn.addActionListener((ActionEvent e) -> {
            if (!item.equals("BALL")) {
                item = "BALL";
                ballBtn.setEnabled(false);
                obsBtn.setEnabled(true);
            }
        });
        controlPanel.add(ballBtn);

        // Botón para cambiar de item
        obsBtn = new JButton();
        obsBtn.setSize(40, 40);
        obsBtn.setLocation(5, 50);
        obsBtn.setBorder(new RoundedBorder(10));
        obsBtn.setForeground(Color.GRAY);
        obsBtn.setText("Ob");
        obsBtn.addActionListener((ActionEvent e) -> {
            if (!item.equals("OBSTACLE")) {
                item = "OBSTACLE";
                obsBtn.setEnabled(false);
                ballBtn.setEnabled(true);
            }
        });
        controlPanel.add(obsBtn);

        // Botón de deshacer
        undoBtn = new JButton(new ImageIcon("img/undo.png"));
        undoBtn.setEnabled(false);
        undoBtn.setSize(40, 40);
        undoBtn.setLocation(455, 110);
        undoBtn.setBorder(new RoundedBorder(10));
        undoBtn.setForeground(Color.GRAY);
        undoBtn.addActionListener((ActionEvent e) -> {
            if (!undoList.isEmpty()) {
                String toUndo = undoList.pop();
                switch (toUndo) {
                    case "BALL":
                        balls.remove(balls.size() - 1);
                        break;
                    case "OBSTACLE":
                        obstacles.remove(obstacles.size() - 1);
                        break;
                    default:
                        // nada
                        break;
                }
            }
            if (undoList.isEmpty()) {
                undoBtn.setEnabled(false);
            }
            repaint();
        });
        controlPanel.add(undoBtn);

        // Botón de borrar al completo
        eraseBtn = new JButton(new ImageIcon("img/erase.png"));
        eraseBtn.setSize(40, 40);
        eraseBtn.setLocation(455, 155);
        eraseBtn.setBorder(new RoundedBorder(10));
        eraseBtn.setForeground(Color.GRAY);
        eraseBtn.addActionListener((ActionEvent e) -> {

            repaint();
        });
        controlPanel.add(eraseBtn);
    }

    private void createMapPanel() {
        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintMap(g);
            }
        };
        mapPanel.setPreferredSize(new Dimension(666, 500));
        mapPanel.setBackground(Color.GRAY);

        MouseAdapter ma = new MouseAdapter() {

            private Point clickPoint;

            @Override
            public void mousePressed(MouseEvent e) {
                clickPoint = e.getPoint();
                switch (item) {
                    case "BALL":
                        ball = clickPoint;
                        break;
                    case "OBSTACLE":
                        obstacle = null;
                        break;
                    default:
                        // nada
                        break;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point dragPoint = e.getPoint();
                switch (item) {
                    case "BALL":
                        ball = dragPoint;
                        break;
                    case "OBSTACLE":
                        int x = Math.min(clickPoint.x, dragPoint.x);
                        int y = Math.min(clickPoint.y, dragPoint.y);

                        int width = Math.max(clickPoint.x, dragPoint.x) - x;
                        int height = Math.max(clickPoint.y, dragPoint.y) - y;

                        if (obstacle == null) {
                            obstacle = new Rectangle(x, y, width, height);
                        } else {
                            obstacle.setBounds(x, y, width, height);
                        }
                        break;
                    default:
                        // nada
                        break;
                }
                mapPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                undoList.push(item);
                switch (item) {
                    case "BALL":
                        balls.add(ball);
                        ball = null;
                        break;
                    case "OBSTACLE":
                        obstacles.add(obstacle);
                        obstacle = null;
                        break;
                    default:
                        // nada
                        break;
                }
                undoBtn.setEnabled(true);
                mapPanel.repaint();
            }

        };

        // Añade los escuchadores de clicks
        mapPanel.addMouseListener(ma);
        mapPanel.addMouseMotionListener(ma);
    }

    private void paintMap(Graphics g) {

        // @todo: Habrá que cambiar los pintados por los métodos de pintar de los objetos
        // Pintado de los objetos ya existentes
        for (Rectangle r : obstacles) {
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
            g2d.fill(r);
            g2d.dispose();
            g2d = (Graphics2D) g.create();
            g2d.draw(r);
        }
        for (Point p : balls) {
            g.setColor(Color.BLUE);
            g.fillOval(p.x - radius, p.y - radius, radius * 2, radius * 2);
        }

        // Pintado del objeto seleccionado
        if (ball != null) {
            g.setColor(Color.BLUE);
            g.fillOval(ball.x - radius, ball.y - radius, radius * 2, radius * 2);
        }
        if (obstacle != null) {
            //g.setColor(UIManager.getColor("List.obstacleBackground"));
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
            g2d.fill(obstacle);
            g2d.dispose();
            g2d = (Graphics2D) g.create();
            g2d.draw(obstacle);
            g2d.dispose();
        }
    }

}

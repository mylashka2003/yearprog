package ru.yearprog.yearprog.input;

import ru.yearprog.yearprog.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class HandInputWindow extends JFrame {
    public static DrawPanel panel;
    public static boolean drawCoordinatePlane = false;

    public HandInputWindow() {
        super("Hand Input");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        new InputMiniWindow();

        panel = new DrawPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    static class DrawPanel extends JPanel implements MouseListener {
        public DrawPanel() {
            super(true);
            this.setPreferredSize(new Dimension(Main.fieldSize, Main.fieldSize));
            this.addMouseListener(this);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Main.drawCoordinateLines(g, Main.fieldSize);
            if (drawCoordinatePlane) Main.drawCoordinatePlane(g, Main.fieldSize);

            for (int i = 0; i < Main.count; i++) {
                drawPoint(Color.BLACK, Main.points[i], g);
                System.out.println(this.getWidth() + " " + this.getHeight());
            }
        }

        public void drawPoint(Color color, Point point, Graphics g) {
            g.setColor(color);
            g.fillOval(point.x - 3, point.y - 3, 6, 6);
            g.setColor(Color.BLACK);
            g.drawOval(point.x - 3, point.y - 3, 6, 6);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            Point p = new Point(x, y);
            if (Arrays.asList(Main.points).contains(p)) {
                JOptionPane.showMessageDialog(this, "Repeated point!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                Main.points[Main.count] = p;
                Main.count++;
            }
            this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

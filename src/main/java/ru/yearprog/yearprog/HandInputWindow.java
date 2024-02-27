package ru.yearprog.yearprog;

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
        new KeyboardInput();

        panel = new DrawPanel();
        this.add(panel);
        this.pack();
        panel.setLayout(null);

        this.setVisible(true);
        this.setResizable(false);
    }

    static class DrawPanel extends JPanel implements MouseListener {
        public DrawPanel() {
            super(true);
            this.setPreferredSize(new Dimension(1000, 1000));
            this.addMouseListener(this);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            drawCoordinateLines(g);
            if (drawCoordinatePlane) drawCoordinatePlane(g);

            for (int i = 0; i < Main.indexOf; i++) {
                drawPoint(Color.BLACK, Main.points[i], g);
            }
        }

        public void drawPoint(Color color, Point point, Graphics g) {
            g.setColor(color);
            g.fillOval(point.x - 3, point.y - 3, 6, 6);
            g.setColor(Color.BLACK);
            g.drawOval(point.x - 3, point.y - 3, 6, 6);
        }

        private void drawCoordinateLines(Graphics g) {
            g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
            g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());

            g.drawLine(this.getWidth(), this.getHeight() / 2, this.getWidth() - 5, this.getHeight() / 2 - 5);
            g.drawLine(this.getWidth(), this.getHeight() / 2, this.getWidth() - 5, this.getHeight() / 2 + 5);

            g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2 - 5, 5);
            g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2 + 5, 5);

            for (int i = 50; i < 1000; i += 50) {
                g.drawLine(i, this.getHeight() / 2 - 3, i, this.getHeight() / 2 + 3);
                g.drawString(String.valueOf(i - this.getWidth() / 2), i - 10, this.getHeight() / 2 + 15);

                if (i != this.getHeight() / 2) {
                    g.drawLine(this.getWidth() / 2 - 3, i, this.getWidth() / 2 + 3, i);
                    g.drawString(String.valueOf(i - this.getHeight() / 2), this.getWidth() / 2 - 30, this.getHeight() - i + 5);
                }
            }
        }

        private void drawCoordinatePlane(Graphics g) {
            for (int i = 50; i < 1000; i += 50) {
                //g.drawLine(i, this.getHeight() / 2 - 3, i, this.getHeight() / 2 + 3);
                drawDashedLine(g, i, 0, i, this.getHeight());

                if (i != this.getHeight() / 2) {
                    g.drawLine(this.getWidth() / 2 - 3, i, this.getWidth() / 2 + 3, i);
                    drawDashedLine(g, 0, i, this.getWidth(), i);
                }
            }
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
                Main.points[Main.indexOf] = p;
                Main.indexOf++;
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
        public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2){
            Graphics2D g2d = (Graphics2D) g.create();

            float[] dashingPattern1 = {2, 2};
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    1, dashingPattern1, 0);
            g2d.setStroke(dashed);

            g2d.drawLine(x1, y1, x2, y2);
            g2d.dispose();
        }
    }
}

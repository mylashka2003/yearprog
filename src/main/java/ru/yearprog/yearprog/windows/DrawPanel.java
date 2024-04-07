package ru.yearprog.yearprog.windows;

import ru.yearprog.yearprog.MainProperties;
import ru.yearprog.yearprog.data.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import static ru.yearprog.yearprog.windows.MainFrame.*;

public class DrawPanel extends JPanel implements MouseListener {
    public DrawPanel() {
        super(true);
        this.setPreferredSize(new Dimension(MainProperties.getFieldSize(), MainProperties.getFieldSize()));
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (drawCoordinateLines) drawCoordinateLines(g, MainProperties.getFieldSize());
        if (drawCoordinatePlane) drawCoordinatePlane(g, MainProperties.getFieldSize(), coordinatePlaneSkip);

        for (int i = 0; i < Data.getCountOfPoints(); i++) {
            drawPoint(Color.BLACK, Data.getPoints()[i], g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX();
            int y = e.getY();

            Point p = new Point(x, y);
            if (Arrays.asList(Data.getPoints()).contains(p)) {
                JOptionPane.showMessageDialog(this, "Repeated point!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                if (Data.getCountOfPoints() < Data.getMaxPoints()) {
                    Data.addPoint(p);
                    MainFrame.setLastHandInputed(true);
                }
                else JOptionPane.showMessageDialog(this, "Too many points!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            this.repaint();
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            Data.resetPoints();
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

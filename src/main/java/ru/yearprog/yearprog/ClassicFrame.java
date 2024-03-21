package ru.yearprog.yearprog;

import ru.yearprog.yearprog.result.DrawingCycle;

import javax.management.remote.JMXAddressable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class ClassicFrame extends JFrame {
    public static ClassicFrame.DrawPanel panel;
    public static boolean drawCoordinatePlane = true;
    public static boolean drawCoordinateLines = true;

    public ClassicFrame() {
        super("Input");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createInputMenu());
        menuBar.add(createActionsMenu());
        setJMenuBar(menuBar);

        panel = new ClassicFrame.DrawPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    private JMenu createActionsMenu() {
        JMenu actions = new JMenu("Actions");

        JMenuItem finish = new JMenuItem("Finish");
        JMenuItem clear = new JMenuItem("Clear");
        JCheckBoxMenuItem line  = new JCheckBoxMenuItem("Line");
        line.setState(true);
        JCheckBoxMenuItem plane  = new JCheckBoxMenuItem("Plane");
        plane.setState(true);

        actions.add(finish);
        actions.addSeparator();
        actions.add(clear);
        actions.addSeparator();
        actions.add(line);
        actions.addSeparator();
        actions.add(plane);

        line.addActionListener(e -> {
            ClassicFrame.drawCoordinateLines = line.getState();
            this.repaint();
        });

        plane.addActionListener(e -> {
            ClassicFrame.drawCoordinatePlane = plane.getState();
            this.repaint();
        });

        clear.addActionListener(e -> {
            Main.points = new Point[1000];
            Main.countOfPoints = 0;
            this.repaint();
        });

        finish.addActionListener(e -> {
            if (Main.countOfPoints < 4) {
                JOptionPane.showMessageDialog(this, "Not enough points!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                this.dispose();
                try {
                    new DrawingCycle();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return actions;
    }

    private JMenu createInputMenu() {
        JMenu input = new JMenu("Input");

        JMenuItem file = new JMenuItem("File");
        JMenuItem keyboard = new JMenuItem("Keyboard");
        JMenuItem random = new JMenuItem("Random");

        input.add(file);
        input.addSeparator();
        input.add(keyboard);
        input.addSeparator();
        input.add(random);

        file.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setDialogTitle("Choose file");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Input.readFile(fileChooser.getSelectedFile(), this);
            }
        });

        keyboard.addActionListener(e -> new InputMiniWindow());

        random.addActionListener(e -> {
            Input.getRandomPoints();
        });

        return input;
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

            if (drawCoordinateLines) Main.drawCoordinateLines(g, Main.fieldSize);
            if (drawCoordinatePlane) Main.drawCoordinatePlane(g, Main.fieldSize);

            for (int i = 0; i < Main.countOfPoints; i++) {
                Main.drawPoint(Color.BLACK, Main.points[i], g);
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
                Main.points[Main.countOfPoints] = p;
                Main.countOfPoints++;
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

package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private static DrawPanel panel;
    private static boolean drawCoordinatePlane = true;
    private static int coordinatePlaneSkip = 50;
    private static boolean drawCoordinateLines = true;

    public static DrawPanel getPanel() {
        return panel;
    }

    public MainFrame() {
        super("Input");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createInputMenu());
        menuBar.add(createActionsMenu());
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);

        panel = new DrawPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        open.addActionListener(e -> {
            /* JFileChooser fileChooser = new JFileChooser();

            fileChooser.setDialogTitle("Choose file");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Input.readFile(fileChooser.getSelectedFile(), this);
            } */

            File file1 = FileWorker.openFile(this, null);
            if (file1 != null) {
                Input.readFile(file1, this);
            }
        });
        save.addActionListener(e -> {
            File file1 = FileWorker.openFile(this, null);
            if (file1 != null) {
                FileWorker.writeObjectToFile(file1, Main.getPoints());
            }
        });
        file.add(open);
        file.addSeparator();
        file.add(save);
        return file;
    }

    private JMenu createActionsMenu() {
        JMenu actions = new JMenu("Actions");

        JMenuItem finish = new JMenuItem("Finish");
        JMenuItem clear = new JMenuItem("Clear");
        JCheckBoxMenuItem line  = new JCheckBoxMenuItem("Line");
        line.setState(true);
        JMenu plane  = new JMenu("Plane");
        JRadioButtonMenuItem skip25 = new JRadioButtonMenuItem("25 step");
        JRadioButtonMenuItem skip50 = new JRadioButtonMenuItem("50 step");
        JRadioButtonMenuItem skip100 = new JRadioButtonMenuItem("100 step");
        JRadioButtonMenuItem off = new JRadioButtonMenuItem("off");
        ButtonGroup group = new ButtonGroup();
        skip50.setSelected(true);
        group.add(skip25);
        group.add(skip50);
        group.add(skip100);
        group.add(off);

        plane.add(skip25);
        plane.add(skip50);
        plane.add(skip100);
        plane.add(off);

        actions.add(finish);
        actions.addSeparator();
        actions.add(clear);
        actions.addSeparator();
        actions.add(line);
        actions.addSeparator();
        actions.add(plane);

        skip25.addActionListener(e -> {
            MainFrame.coordinatePlaneSkip = 25;
            MainFrame.drawCoordinatePlane = true;
            this.repaint();
        });
        skip50.addActionListener(e -> {
            MainFrame.coordinatePlaneSkip = 50;
            MainFrame.drawCoordinatePlane = true;
            this.repaint();
        });
        skip100.addActionListener(e -> {
            MainFrame.coordinatePlaneSkip = 100;
            MainFrame.drawCoordinatePlane = true;
            this.repaint();
        });
        off.addActionListener(e -> {
            MainFrame.drawCoordinatePlane = false;
            this.repaint();
        });

        line.addActionListener(e -> {
            MainFrame.drawCoordinateLines = line.getState();
            this.repaint();
        });

        clear.addActionListener(e -> {
            Main.resetPoints();
            this.repaint();
        });

        finish.addActionListener(e -> {
            if (Main.getCountOfPoints() < 4) {
                JOptionPane.showMessageDialog(this, "Not enough points!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                this.dispose();
                new CountCycle();
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
            /* JFileChooser fileChooser = new JFileChooser();

            fileChooser.setDialogTitle("Choose file");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Input.readFile(fileChooser.getSelectedFile(), this);
            } */

            File file1 = FileWorker.openFile(this, null);
            if (file1 != null) {
                Input.readFile(file1, this);
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
            this.setPreferredSize(new Dimension(Main.getFieldSize(), Main.getFieldSize()));
            this.addMouseListener(this);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            if (drawCoordinateLines) drawCoordinateLines(g, Main.getFieldSize());
            if (drawCoordinatePlane) drawCoordinatePlane(g, Main.getFieldSize(), coordinatePlaneSkip);

            for (int i = 0; i < Main.getCountOfPoints(); i++) {
                drawPoint(Color.BLACK, Main.getPoints()[i], g);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                int x = e.getX();
                int y = e.getY();

                Point p = new Point(x, y);
                if (Arrays.asList(Main.getPoints()).contains(p)) {
                    JOptionPane.showMessageDialog(this, "Repeated point!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (Main.getCountOfPoints() < Main.getMaxPoints()) Main.addPoint(p);
                    else JOptionPane.showMessageDialog(this, "Слишком много точек!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                this.repaint();
            } else if (e.getButton() == MouseEvent.BUTTON2) {
                Main.resetPoints();
                this.repaint();
            }
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

    public static void drawPoint(Color color, Point point, Graphics g) {
        g.setColor(color);
        g.fillOval(point.x - 3, point.y - 3, 6, 6);
        g.setColor(Color.BLACK);
        g.drawOval(point.x - 3, point.y - 3, 6, 6);
    }

    public static void drawCoordinateLines(Graphics g, int size) {
        g.drawLine(0, size / 2, size, size / 2);
        g.drawLine(size / 2, 0, size / 2, size);

        g.drawLine(size, size / 2, size - 5, size / 2 - 5);
        g.drawLine(size, size / 2, size - 5, size / 2 + 5);
        g.drawLine(size / 2, 0, size / 2 - 5, 5);
        g.drawLine(size / 2, 0, size / 2 + 5, 5);

        for (int i = 50; i < size; i += 50) {
            g.drawLine(i, size / 2 - 3, i, size / 2 + 3);
            g.drawString(String.valueOf(i - size / 2), i - 10, size / 2 + 15);

            if (i != size / 2) {
                g.drawLine(size / 2 - 3, i, size / 2 + 3, i);
                g.drawString(String.valueOf(i - size / 2), size / 2 - 30, size - i + 5);
            }
        }
    }

    public static void drawCoordinatePlane(Graphics g, int size, int skip) {
        for (int i = 0; i < size / 2; i += skip) {
            drawDashedLine(g, Main.getFieldSize() / 2 + i, 0, Main.getFieldSize() / 2 + i, size);
            drawDashedLine(g, Main.getFieldSize() / 2 - i, 0, Main.getFieldSize() / 2 - i, size);
            drawDashedLine(g, 0, Main.getFieldSize() / 2 + i, size, Main.getFieldSize() / 2 + i);
            drawDashedLine(g, 0, Main.getFieldSize() / 2 - i, size, Main.getFieldSize() / 2 - i);
        }
    }

    public static void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g.create();

        float[] dashingPattern1 = {2, 2};
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                1, dashingPattern1, 0);
        g2d.setStroke(dashed);

        g2d.drawLine(x1, y1, x2, y2);
        g2d.dispose();
    }
}

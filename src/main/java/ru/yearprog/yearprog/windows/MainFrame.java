package ru.yearprog.yearprog.windows;

import ru.yearprog.yearprog.*;
import ru.yearprog.yearprog.data.Data;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    public static DrawPanel panel;
    public static boolean drawCoordinatePlane = true;
    public static int coordinatePlaneSkip = 50;
    public static boolean drawCoordinateLines = true;

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
        menuBar.add(createGraphicsMenu());
        setJMenuBar(menuBar);

        panel = new DrawPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    private JMenu createGraphicsMenu() {
        JMenu graphics = new JMenu("Graphics");
        JCheckBoxMenuItem line  = new JCheckBoxMenuItem("Line");
        line.setState(true);
        JMenu plane  = new JMenu("Plane");
        JRadioButtonMenuItem skip25 = new JRadioButtonMenuItem("25 step");
        JRadioButtonMenuItem skip50 = new JRadioButtonMenuItem("50 step");
        JRadioButtonMenuItem skip100 = new JRadioButtonMenuItem("100 step");
        JRadioButtonMenuItem off = new JRadioButtonMenuItem("off");
        ButtonGroup group = new ButtonGroup();
        skip50.setSelected(true);
        setupSkips(group, plane, skip25, 25);
        setupSkips(group, plane, skip50, 50);
        setupSkips(group, plane, skip100, 100);
        graphics.add(line);
        graphics.add(plane);
        off.addActionListener(e -> {
            MainFrame.drawCoordinatePlane = false;
            this.repaint();
        });
        line.addActionListener(e -> {
            MainFrame.drawCoordinateLines = line.getState();
            this.repaint();
        });
        return graphics;
    }

    private void setupSkips(ButtonGroup group, JMenu plane, JRadioButtonMenuItem skip, int skipSize) {
        group.add(skip);
        plane.add(skip);
        skip.addActionListener(e -> {
            MainFrame.coordinatePlaneSkip = skipSize;
            MainFrame.drawCoordinatePlane = true;
            this.repaint();
        });
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        open.addActionListener(e -> {

            File file1 = FileWorker.openFile(this, null, false);
            if (file1 != null) {
                Input.readFile(file1, this);
            }
        });
        save.addActionListener(e -> {
            File file1 = FileWorker.openFile(this, null, false);
            if (file1 != null) {
                FileWorker.writeObjectToFile(file1, Data.getPoints());
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


        actions.add(finish);
        actions.addSeparator();
        actions.add(clear);

        clear.addActionListener(e -> {
            Data.resetPoints();
            this.repaint();
        });

        finish.addActionListener(e -> {
            if (Data.getCountOfPoints() < 4) {
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
            File file1 = FileWorker.openFile(this, null, false);
            if (file1 != null) {
                Input.readFile(file1, this);
            }
        });

        keyboard.addActionListener(e -> new InputMiniWindow());

        random.addActionListener(e -> Input.getRandomPoints(this));

        return input;
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

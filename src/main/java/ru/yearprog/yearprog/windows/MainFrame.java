package ru.yearprog.yearprog.windows;

import ru.yearprog.yearprog.*;
import ru.yearprog.yearprog.data.Data;
import ru.yearprog.yearprog.input.Input;
import ru.yearprog.yearprog.input.InputMiniWindow;
import ru.yearprog.yearprog.input.IntegerInput;
import ru.yearprog.yearprog.workers.CountCycle;
import ru.yearprog.yearprog.workers.FileWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainFrame extends JFrame {
    public static DrawPanel panel;
    public static boolean drawCoordinatePlane = true;
    public static int coordinatePlaneSkip = 50;
    public static boolean drawCoordinateLines = true;
    private static boolean ctrlPressed = false;
    private static boolean zPressed = false;
    private static boolean lastHandInputed = false;
    private static JMenuBar menuBar;
    private static InputMiniWindow inputMiniWindow = null;
    private static IntegerInput inputRandomWindow = null;

    public static void setLastHandInputed(boolean lastHandInputed) {
        MainFrame.lastHandInputed = lastHandInputed;
    }

    public static DrawPanel getPanel() {
        return panel;
    }

    public MainFrame() {
        super("Input");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        menuBar.add(createInputMenu());
        menuBar.add(createActionsMenu());
        menuBar.add(createFileMenu());
        menuBar.add(createGraphicsMenu());
        menuBar.add(createLocalizationMenu());
        setJMenuBar(menuBar);
        panel = new DrawPanel();
        panel.setLayout(null);
        this.addKeyListener(createKeyAdapter());
        this.getContentPane().add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    private JMenu createLocalizationMenu() {
        JMenu localization = new JMenu(Localization.getMessages().getString("localization.menu"));
        localization.setName("localization.menu");
        JRadioButtonMenuItem ru = new JRadioButtonMenuItem("ru");
        JRadioButtonMenuItem en = new JRadioButtonMenuItem("en");
        ButtonGroup group = new ButtonGroup();
        group.add(ru);
        group.add(en);
        en.setSelected(true);
        localization.add(ru);
        localization.add(en);
        ActionListener listener = e -> {
            Localization.setCurrentLocale(e.getActionCommand());
            //System.out.println(Localization.getCurrentLocale());
            updateComponents(menuBar);
        };
        ru.addActionListener(listener);
        en.addActionListener(listener);
        return localization;
    }

    private void updateComponents(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JMenu jMenu) {
                jMenu.setText(Localization.getMessages().getString(jMenu.getName()));
            }
        }
        this.revalidate();
        this.repaint();
    }

    private KeyListener createKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) ctrlPressed = true;
                if (e.getKeyCode() == KeyEvent.VK_Z) zPressed = true;
                if (ctrlPressed && zPressed && lastHandInputed) {
                    lastHandInputed = false;
                    Data.removeLast();
                    MainProperties.getF().repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) ctrlPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_Z) zPressed = false;
            }
        };
    }

    private JMenu createGraphicsMenu() {
        JMenu graphics = new JMenu(Localization.getMessages().getString("graphics.menu"));
        graphics.setName("graphics.menu");
        JCheckBoxMenuItem line  = new JCheckBoxMenuItem("Line");
        line.setState(true);
        JMenu plane  = new JMenu("Plane");
        JRadioButtonMenuItem skip25 = new JRadioButtonMenuItem("25 step");
        JRadioButtonMenuItem skip50 = new JRadioButtonMenuItem("50 step");
        JRadioButtonMenuItem skip100 = new JRadioButtonMenuItem("100 step");
        JRadioButtonMenuItem off = new JRadioButtonMenuItem("off");
        ButtonGroup group = new ButtonGroup();
        skip50.setSelected(true);
        group.add(off);
        plane.add(off);
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
        JMenu file = new JMenu(Localization.getMessages().getString("file.menu"));
        file.setName("file.menu");
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
        JMenu actions = new JMenu(Localization.getMessages().getString("actions.menu"));
        actions.setName("actions.menu");
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
                this.disposeAll();
                this.dispose();
                MainProperties.setCountCycle(new CountCycle());
            }
        });

        return actions;
    }

    private JMenu createInputMenu() {
        JMenu input = new JMenu(Localization.getMessages().getString("input.menu"));
        input.setName("input.menu");

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

        keyboard.addActionListener(e -> {
            if (inputMiniWindow == null) inputMiniWindow = new InputMiniWindow();
            else inputMiniWindow.setVisible(true);
        });

        random.addActionListener(e -> Input.getRandomPoints(this));

        return input;
    }

    public static void setInputRandomWindow(IntegerInput inputRandomWindow) {
        MainFrame.inputRandomWindow = inputRandomWindow;
    }

    public static IntegerInput getInputRandomWindow() {
        return inputRandomWindow;
    }

    public static InputMiniWindow getInputMiniWindow() {
        return inputMiniWindow;
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
            drawDashedLine(g, MainProperties.getFieldSize() / 2 + i, 0, MainProperties.getFieldSize() / 2 + i, size);
            drawDashedLine(g, MainProperties.getFieldSize() / 2 - i, 0, MainProperties.getFieldSize() / 2 - i, size);
            drawDashedLine(g, 0, MainProperties.getFieldSize() / 2 + i, size, MainProperties.getFieldSize() / 2 + i);
            drawDashedLine(g, 0, MainProperties.getFieldSize() / 2 - i, size, MainProperties.getFieldSize() / 2 - i);
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

    public void disposeAll() {
        if (inputMiniWindow != null) inputMiniWindow.dispose();
        if (inputRandomWindow != null) inputRandomWindow.dispose();
    }
}

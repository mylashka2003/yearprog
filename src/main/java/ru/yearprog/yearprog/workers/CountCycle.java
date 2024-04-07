package ru.yearprog.yearprog.workers;

import ru.yearprog.yearprog.MainProperties;
import ru.yearprog.yearprog.data.Data;
import ru.yearprog.yearprog.data.Quadrilateral;
import ru.yearprog.yearprog.windows.MainFrame;
import ru.yearprog.yearprog.windows.QuadrilateralInfo;
import ru.yearprog.yearprog.windows.WIP;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class CountCycle extends JFrame {
    private static int index;
    private static Quadrilateral quadrilateral;
    private static File file = null;
    private static DrawCyclePanel panel;
    private static JLabel label;
    private final CountDownLatch countDownLatch = new CountDownLatch(2);
    private WIP wip;

    public CountCycle() {
        super("Result");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new DrawCyclePanel();
        label = new JLabel();
        panel.setLayout(null);
        label.setFont(new Font("Liberation Mono", Font.BOLD, 13));
        label.setForeground(Color.DARK_GRAY);
        label.setBounds(0, 0, 300, 20);
        panel.add(label);
        this.setContentPane(panel);
        this.pack();
        this.setResizable(false);

        SwingUtilities.invokeLater(() -> runProcesses(this));
    }

    public void runProcesses(JFrame parentFrame) {
        wip = new WIP();
        startLongProcess();
        file = FileWorker.openFile(parentFrame, countDownLatch, true);

        Thread tt = new Thread(() -> {
            try {
                countDownLatch.await();
                try {
                    Quadrilateral[] top10 = longProcessingTask.get();
                    if (file != null && top10 != null) {
                        this.setVisible(true);
                        FileWorker.writeObjectToFile(file, top10);
                        new QuadrilateralInfo();
                        draw(panel, top10);
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        tt.start();
    }

    private void startLongProcess() {
        longProcessingTask.execute();
    }

    private final SwingWorker<Quadrilateral[], Integer> longProcessingTask = new SwingWorker<>() {
        @Override
        protected Quadrilateral[] doInBackground() {
            PriorityQueue<Quadrilateral> topShapes = new PriorityQueue<>(10,
                    Comparator.comparingDouble(Quadrilateral::getArea));

            int index = 0;
            for (int a = 0; a < Data.getCountOfPoints() - 3; a++) {
                for (int b = a + 1; b < Data.getCountOfPoints() - 2; b++) {
                    for (int c = b + 1; c < Data.getCountOfPoints() - 1; c++) {
                        for (int d = c + 1; d < Data.getCountOfPoints(); d++) {
                            Quadrilateral r = Geometry.calculateQuadrilateralArea(Data.getPoints()[a], Data.getPoints()[b], Data.getPoints()[c], Data.getPoints()[d]);
                            if (topShapes.size() < 10) {
                                topShapes.add(r);
                            } else if (r.getArea() > (topShapes.peek() != null ? topShapes.peek().getArea() : 0)) {
                                topShapes.poll();
                                topShapes.add(r);
                            }
                            publish(index);
                            index++;
                        }
                    }
                }
            }
            countDownLatch.countDown();
            Quadrilateral[] shapesArray = new Quadrilateral[topShapes.size()];
            int i = 0;
            while (!topShapes.isEmpty()) {
                shapesArray[i++] = topShapes.poll();
            }

            return shapesArray;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            for (int progress : chunks) {
                wip.barSet(progress);
            }
        }

        @Override
        protected void done() {
            wip.dispose();
        }
    };

    public static int binomialCoefficient(int n) {
        int coefficient = 1;
        for (int i = 1; i <= 4; i++) {
            coefficient *= n - (4 - i);
            coefficient /= i;
        }
        return coefficient;

    }

    private static void draw(JPanel panel, Quadrilateral[] top) {
        index = 0;
        Timer timer = new Timer(500, e -> {
            if (index < top.length - 1) {
                quadrilateral = top[index];
                QuadrilateralInfo.updateTable(quadrilateral);
                panel.repaint();
                index++;
            } else if (index == top.length - 1) {
                quadrilateral = top[index];
                QuadrilateralInfo.updateTable(quadrilateral);
                label.setText("area: " + quadrilateral.getArea() +" | type: " + quadrilateral.getType());
                panel.repaint();
                index++;
                SwingUtilities.invokeLater(CountCycle::saveShot);
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private static void saveShot() {
        BufferedImage img = getScreenShot(MainProperties.getCountCycle().getContentPane());
        try {
            ImageIO.write(
                    img,
                    "png",
                    new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4) + ".png"));
        } catch(Exception err) {
            throw new RuntimeException(err);
        }
    }

    public static BufferedImage getScreenShot(Component component) {
        BufferedImage image = new BufferedImage(
                component.getWidth(),
                component.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        component.paint( image.getGraphics() );
        return image;
    }

    static class DrawCyclePanel extends JPanel {
        public DrawCyclePanel() {
            super(true);
            this.setPreferredSize(new Dimension(MainProperties.getFieldSize(), MainProperties.getFieldSize()));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            MainFrame.drawCoordinateLines(g, MainProperties.getFieldSize());
            MainFrame.drawCoordinatePlane(g, MainProperties.getFieldSize(), 50);
            drawAllPoints(g);
            if (quadrilateral != null) quadrilateral.draw(g);
        }

        private void drawAllPoints(Graphics g) {
            for (int i = 0; i < Data.getCountOfPoints(); i++) {
                MainFrame.drawPoint(Color.BLACK, Data.getPoints()[i], g);
            }
        }
    }
}

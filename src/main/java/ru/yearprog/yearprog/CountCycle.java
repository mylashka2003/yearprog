package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class CountCycle extends JFrame {
    private static int index;
    private static Quadrilateral quadrilateral;
    //private static Quadrilateral[] top10 = null;
    private static File file = null;
    private static DrawCyclePanel panel;
    private final CountDownLatch countDownLatch = new CountDownLatch(2);
    private WIP wip;
    public CountCycle() {
        super("Result");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new DrawCyclePanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        this.pack();
        this.setResizable(false);

        // 1
        SwingUtilities.invokeLater(() -> runProcesses(this));
        // 3
        /* quickSort(quadrilaterals, 0, quadrilaterals.length - 1);

        int length = Math.min(10, quadrilaterals.length);
        Quadrilateral[] top10 = new Quadrilateral[length];
        System.arraycopy(quadrilaterals, 0, top10, 0, length);
        java.util.List<Quadrilateral> tops = Arrays.asList(top10);
        Collections.reverse(tops);
        top10 = tops.toArray(new Quadrilateral[0]); */
    }

    public void runProcesses(JFrame parentFrame) {
        wip = new WIP();
        startLongProcess();
        //processingThread.start();
        file = FileWorker.openFile(parentFrame, countDownLatch);

        Thread tt = new Thread(() -> {
            try {
                // Ждем завершения двух событий: обработки данных и выбора файла
                countDownLatch.await();
                // Проверяем, выбран ли файл и обработаны ли данные
                try {
                    Quadrilateral[] top10 = longProcessingTask.get(); // Получаем результат выполнения
                    if (file != null && top10 != null) {
                        this.setVisible(true);
                        FileWorker.writeObjectToFile(file, top10);
                        new QuadrilateralInfo();
                        draw(panel, top10);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
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
        protected Quadrilateral[] doInBackground() throws Exception {
            // Здесь реализация вашего длительного процесса
            // Во время которого вы регулярно будете вызывать publish(progress)
            PriorityQueue<Quadrilateral> topShapes = new PriorityQueue<>(10, Comparator.comparingDouble(Quadrilateral::getArea));
            int index = 0;
            for (int a = 0; a < Main.getCountOfPoints() - 3; a++) {
                for (int b = a + 1; b < Main.getCountOfPoints() - 2; b++) {
                    for (int c = b + 1; c < Main.getCountOfPoints() - 1; c++) {
                        for (int d = c + 1; d < Main.getCountOfPoints(); d++) {
                            Quadrilateral r = Geometry.calculateQuadrilateralArea(Main.getPoints()[a], Main.getPoints()[b], Main.getPoints()[c], Main.getPoints()[d]);
                            if (topShapes.size() < 10) {
                                topShapes.add(r);
                            } else if (r.getArea() > (topShapes.peek() != null ? topShapes.peek().getArea() : 0)) {
                                // Если новая фигура больше, чем наименьшая в очереди, заменить их
                                topShapes.poll(); // Удаляем фигуру с наименьшей площадью
                                topShapes.add(r); // Добавляем новую фигуру
                            }
                            publish(index);
                            //System.out.println("publed");
                            index++;
                        }
                    }
                }
            }
            countDownLatch.countDown();
            return topShapes.toArray(new Quadrilateral[0]);
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            // Эта функция будет вызываться в EDT
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

    private void generateRs(Point[] points, int count) {
        WIP wip = new WIP();
        PriorityQueue<Quadrilateral> topShapes = new PriorityQueue<>(10, Comparator.comparingDouble(Quadrilateral::getArea));
        for (int a = 0; a < count - 3; a++) {
            for (int b = a + 1; b < count - 2; b++) {
                for (int c = b + 1; c < count - 1; c++) {
                    for (int d = c + 1; d < count; d++) {
                        Quadrilateral r = Geometry.calculateQuadrilateralArea(points[a], points[b], points[c], points[d]);
                        if (topShapes.size() < 10) {
                            topShapes.add(r);
                        } else if (r.getArea() > (topShapes.peek() != null ? topShapes.peek().getArea() : 0)) {
                            // Если новая фигура больше, чем наименьшая в очереди, заменить их
                            topShapes.poll(); // Удаляем фигуру с наименьшей площадью
                            topShapes.add(r); // Добавляем новую фигуру
                        }
                        //publish(i);
                    }
                }
            }
        }
        //op10 = topShapes.toArray(new Quadrilateral[0]);
        countDownLatch.countDown();
    }

    private static void draw(JPanel panel, Quadrilateral[] top) {
        index = 0;
        Timer timer = new Timer(1000, e -> {
            if (index < top.length) {
                quadrilateral = top[index];
                panel.repaint();
                QuadrilateralInfo.updateTable(quadrilateral);
                index++;
            } else {
                ((Timer)e.getSource()).stop();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private static void quickSort(Quadrilateral[] shapes, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(shapes, begin, end);

            quickSort(shapes, begin, partitionIndex - 1);
            quickSort(shapes, partitionIndex + 1, end);
        }
    }

    private static int partition(Quadrilateral[] shapes, int begin, int end) {
        double pivot = shapes[end].getArea();
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (shapes[j].getArea() >= pivot) {
                i++;
                Quadrilateral swapTemp = shapes[i];
                shapes[i] = shapes[j];
                shapes[j] = swapTemp;
            }
        }

        Quadrilateral swapTemp = shapes[i + 1];
        shapes[i + 1] = shapes[end];
        shapes[end] = swapTemp;

        return i + 1;
    }

    static class DrawCyclePanel extends JPanel {
        public DrawCyclePanel() {
            super(true);
            this.setPreferredSize(new Dimension(Main.getFieldSize(), Main.getFieldSize()));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            MainFrame.drawCoordinateLines(g, Main.getFieldSize());
            MainFrame.drawCoordinatePlane(g, Main.getFieldSize(), 50);
            drawAllPoints(g);
            if (quadrilateral != null) quadrilateral.draw(g);
        }

        private void drawAllPoints(Graphics g) {
            for (int i = 0; i < Main.getCountOfPoints(); i++) {
                MainFrame.drawPoint(Color.BLACK, Main.points[i], g);
            }
        }
    }
}

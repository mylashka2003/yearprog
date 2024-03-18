package ru.yearprog.yearprog.input;

import ru.yearprog.yearprog.result.DrawingCycle;
import ru.yearprog.yearprog.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputSelection extends JFrame {
    public static HandInputWindow handInputWindow;
    private final JPanel panel;
    private final JButton fileButton;
    private final JButton handInputButton;

    public InputSelection() {
        super("Input Selection");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setBounds(10, 10, 1100, 600);

        panel = new JPanel(true);
        this.add(panel);
        panel.setLayout(null);

        Font BigFont = new Font("Liberation Mono", Font.BOLD, 30);
        fileButton = new JButton("File Input");
        handInputButton = new JButton("Hand Input");
        fileButton.setFont(BigFont);
        handInputButton.setFont(BigFont);

        panel.add(fileButton);
        panel.add(handInputButton);

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setDialogTitle("Choose file");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                boolean res = GetFromFile.readFile(fileChooser.getSelectedFile(), this);

                if (res) {
                    try {
                        new DrawingCycle();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        handInputButton.addActionListener(e -> {
            handInputWindow = new HandInputWindow();
            this.dispose();
        });


        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resized();
            }
        });

        setVisible(true);
    }

    private void resized() {
        Main.setRelativeSize(0.2, 0.3, 0.6, 0.2, fileButton, panel);
        Main.setRelativeSize(0.2, 0.6, 0.6, 0.2, handInputButton, panel);
    }
}
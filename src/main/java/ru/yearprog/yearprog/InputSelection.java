package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputSelection extends JFrame {
    private final JPanel panel;
    private final JButton fileButton;
    private final JButton handInputButton;

    public InputSelection() {
        super("Выбор способа ввода");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setBounds(10, 10, 1100, 600);

        panel = new JPanel(true);
        this.add(panel);
        panel.setLayout(null);

        fileButton = new JButton("File Input");
        fileButton.setFont(Main.BigFont);

        handInputButton = new JButton("Hand Input");
        handInputButton.setFont(Main.BigFont);

        panel.add(fileButton);
        panel.add(handInputButton);

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setDialogTitle("Choose file");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                boolean res = ContentFile.readFile(fileChooser.getSelectedFile(), this);

                if (res) {
                    // open final window
                }
            }
        });

        handInputButton.addActionListener(e -> {
            new HandInputWindow();
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
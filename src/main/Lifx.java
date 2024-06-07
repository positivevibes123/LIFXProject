package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Lifx extends JFrame implements ActionListener {
    private JLabel label;
    private JButton powerButton;
    private JButton colorButton;

    @Override
    public void actionPerformed(ActionEvent e) {
        // If the button to toggle power has been pressed, try to toggle power in a separate thread
        if (e.getSource() == powerButton) {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Background task
                    System.out.println("Toggle power button pressed.");
                    // Do a task
                    ClientSingleton.getInstance().togglePower();
                    return null;
                }

                @Override
                protected void done() {
                    // Code to run on the Event Dispatch Thread after background task is done
                    System.out.println("Background task completed.");
                }
            };
            worker.execute();
        }

        if (e.getSource() == colorButton) {
            Color color = JColorChooser.showDialog(null, "Choose a color", Color.WHITE);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Background task
                    System.out.println("Toggle color button pressed.");
                    // Do a task
                    ClientSingleton.getInstance().setColor(color);
                    return null;
                }

                @Override
                protected void done() {
                    // Code to run on the Event Dispatch Thread after background task is done
                    System.out.println("Background task completed.");
                }
            };
            worker.execute();
        }
    }

    public Lifx(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        powerButton = new JButton("Toggle Power");
        powerButton.addActionListener(this);

        colorButton = new JButton("Choose Color");
        colorButton.addActionListener(this);

        label = new JLabel();
        label.setBackground(Color.WHITE);
        label.setText("Test text");
        label.setOpaque(true);

        this.add(powerButton);
        this.add(colorButton);
        this.add(label);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        String path = "./token.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String token = br.readLine();
            System.out.println("Firstline is : " + token);
            br.close();
            ClientSingleton.getInstance().init(token);
            Lifx lifx = new Lifx("LIFX");
        } catch (IOException e) {
            System.out.println("Error attempting to read .txt file for token");
        }
    }
}

package Coin1;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class CoinTossAnimation extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JLabel coinLabel = new JLabel();
    private final ImageIcon headsImage = new ImageIcon("heads.png");
    private final ImageIcon tailsImage = new ImageIcon("tails.png");
    private final JButton tossButton = new JButton("Toss Coin");
    private Clip clip;

    public CoinTossAnimation() {
        super("Coin Toss Animation");

        JPanel panel = new JPanel();
        panel.add(tossButton);
        panel.add(coinLabel);
        add(panel);

        tossButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound();
                tossCoin();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        // Load the sound file
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("tosscoin.wav")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSound() {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    private void tossCoin() {
        tossButton.setEnabled(false);
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 55; i++) {
                    coinLabel.setIcon(getRandomImage());
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                coinLabel.setIcon(getRandomImage());
                tossButton.setEnabled(true);
                stopSound();
                Toolkit.getDefaultToolkit().beep(); // Play a sound
            }
        });
        t.start();
    }

    private void stopSound() {
        clip.stop();
        clip.setFramePosition(0);
    }

    private ImageIcon getRandomImage() {
        if (Math.random() >= 0.5)
            return tailsImage;
        else
            return headsImage;
    }

    public static void main(String[] args) {
        CoinTossAnimation app = new CoinTossAnimation();
    }
}

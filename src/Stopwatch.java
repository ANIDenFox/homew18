import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("ALL")
public class Stopwatch extends JFrame {
    private JButton timeButton;
    private JButton startButton;
    private JButton stopButton;
    private TimerThread timerThread;

    public Stopwatch() {
        setTitle("Секундомір");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        timeButton = new JButton("00");
        timeButton.setFont(new Font("Arial", Font.PLAIN, 24));
        timeButton.setEnabled(false);
        add(timeButton, BorderLayout.CENTER);

        startButton = new JButton("Старт");
        stopButton = new JButton("Стоп");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    startTimer();
                } else if (e.getSource() == stopButton) {
                    stopTimer();
                }
            }
        };

        startButton.addActionListener(buttonListener);
        stopButton.addActionListener(buttonListener);

        add(startButton, BorderLayout.WEST);
        add(stopButton, BorderLayout.EAST);

        setVisible(true);
    }

    private void startTimer() {
        if (timerThread == null || !timerThread.isAlive()) {
            timerThread = new TimerThread();
            timerThread.start();
        }
    }

    private void stopTimer() {
        if (timerThread != null) {
            timerThread.stopTimer();
        }
    }

    private class TimerThread extends Thread {
        private boolean running = true;
        private int seconds = 0;

        public void run() {
            while (running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                seconds++;
                updateTime(seconds);
            }
        }

        public void stopTimer() {
            running = false;
        }
    }

    private void updateTime(int seconds) {
        timeButton.setText(Integer.toString(seconds));
    }
}
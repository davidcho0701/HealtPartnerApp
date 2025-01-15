package 헬스파트너;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class InbodyGraphFrame extends JFrame {
    private Vector<String> dates;
    private Vector<Integer> scores;

    public InbodyGraphFrame(Vector<String> dates, Vector<Integer> scores) {
        this.dates = dates;
        this.scores = scores;

        setTitle("인바디 그래프");
        setSize(800, 500);
        setLocationRelativeTo(null);

        add(new GraphPanel());
        setVisible(true);
    }

    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int maxScore = 100;
            int barWidth = 40;

            setBackground(new Color(30, 30, 30));

            // Draw grid lines and score labels
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            for (int i = 0; i <= maxScore; i += 10) {
                int y = height - 50 - (int) ((i / (double) maxScore) * (height - 100));
                g2d.drawLine(50, y, width - 50, y);
                g2d.drawString(String.valueOf(i), 25, y);
            }

            // Draw axis labels
            g2d.drawString("Score", 10, 20);
            g2d.drawString("Date", width / 2 - 30, height - 5);

            // Draw bars
            int x = 100;
            for (int i = 0; i < scores.size(); i++) {
                int score = scores.get(i);
                int barHeight = (int) ((score / (double) maxScore) * (height - 100));

                // Draw bar
                g2d.setColor(new Color(70, 130, 180));
                g2d.fillRect(x, height - barHeight - 50, barWidth, barHeight);

                // Draw score value
                g2d.setColor(Color.WHITE);
                g2d.drawString(String.valueOf(score),
                        x + (barWidth / 2) - 10,
                        height - barHeight - 55);

                // Draw date
                g2d.drawString(dates.get(i),
                        x + (barWidth / 2) - 20,
                        height - 20);

                x += barWidth + 30;
            }
        }
    }
}
package 헬스파트너;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

public class InbodyWeeklyPanel extends JPanel {
    private UIStyleManager styleManager;

    public InbodyWeeklyPanel(UIStyleManager styleManager) {
        this.styleManager = styleManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(18, 18, 18));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Weekly Report",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));
    }

    public void updateReport(Vector<String> dates, Vector<Integer> scores) {
        removeAll();

        for (int i = 0; i < dates.size(); i++) {
            add(createDatePanel(dates.get(i), scores.get(i), i % 2 == 0));
        }

        revalidate();
        repaint();
    }

    private JPanel createDatePanel(String date, int score, boolean alternateColor) {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        datePanel.setBackground(alternateColor ? new Color(50, 50, 50) : new Color(70, 70, 70));

        JLabel dateLabel = new JLabel("<html><b>" + date + "</b></html>");
        dateLabel.setForeground(Color.WHITE);

        JLabel scoreLabel = new JLabel("점수: " + score + "점");
        scoreLabel.setForeground(Color.WHITE);

        datePanel.add(dateLabel);
        datePanel.add(scoreLabel);

        return datePanel;
    }
}
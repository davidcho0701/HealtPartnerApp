package 헬스파트너;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class WeeklyReportPanel extends JPanel {
    private UIStyleManager styleManager;

    public WeeklyReportPanel(UIStyleManager styleManager) {
        this.styleManager = styleManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(18, 18, 18));

        TitledBorder border = BorderFactory.createTitledBorder("Weekly Report");
        border.setTitleColor(Color.WHITE);
        setBorder(border);
    }

    public void updateReport(Map<String, ArrayList<ExerciseData>> data) {
        removeAll();
        boolean alternateColor = true;

        for (Map.Entry<String, ArrayList<ExerciseData>> entry : data.entrySet()) {
            String date = entry.getKey();
            ArrayList<ExerciseData> exercises = entry.getValue();

            JPanel datePanel = createDatePanel(date, exercises, alternateColor);
            add(datePanel);
            alternateColor = !alternateColor;
        }

        revalidate();
        repaint();
    }

    private JPanel createDatePanel(String date, ArrayList<ExerciseData> exercises, boolean alternateColor) {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BorderLayout());
        datePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        datePanel.setBackground(alternateColor ? new Color(50, 50, 50) : new Color(70, 70, 70));

        // 날짜 레이블
        JLabel dateLabel = new JLabel("<html><b>" + date + "</b></html>");
        dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dateLabel.setForeground(Color.WHITE);
        datePanel.add(dateLabel, BorderLayout.NORTH);

        // 운동 목록 패널
        JPanel exerciseListPanel = createExerciseListPanel(exercises);
        datePanel.add(exerciseListPanel, BorderLayout.CENTER);

        return datePanel;
    }

    private JPanel createExerciseListPanel(ArrayList<ExerciseData> exercises) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        for (ExerciseData exercise : exercises) {
            JPanel exerciseEntryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
            exerciseEntryPanel.setOpaque(false);

            JLabel exerciseLabel = new JLabel(exercise.getName());
            exerciseLabel.setForeground(Color.WHITE);

            JButton detailsButton = styleManager.createStyledButton("Details");
            detailsButton.setMargin(new Insets(0, 0, 0, 0));
            detailsButton.addActionListener(e ->
                    new ExerciseDetailsFrame(exercise.getName(),
                            exercise.getDate(),
                            exercise.getSetData(),
                            styleManager));

            exerciseEntryPanel.add(exerciseLabel);
            exerciseEntryPanel.add(detailsButton);
            panel.add(exerciseEntryPanel);
        }

        return panel;
    }

    public void reset() {
        removeAll();
        revalidate();
    }
}
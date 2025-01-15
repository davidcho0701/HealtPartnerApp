package 헬스파트너;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExerciseDetailsFrame extends JFrame {
    private UIStyleManager styleManager;

    public ExerciseDetailsFrame(String exerciseName, String date,
                                ArrayList<String[]> setData, UIStyleManager styleManager) {
        this.styleManager = styleManager;

        setTitle("운동 세부 정보");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(18, 18, 18));

        add(createBasicInfoPanel(exerciseName, date), BorderLayout.NORTH);
        add(createSetInfoPanel(setData), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createBasicInfoPanel(String exerciseName, String date) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBackground(new Color(42, 42, 42));
        panel.setBorder(BorderFactory.createTitledBorder("운동 정보"));

        JLabel exerciseLabel = new JLabel("운동명:");
        exerciseLabel.setForeground(Color.WHITE);
        JTextField exerciseField = styleManager.createStyledTextField(exerciseName);
        exerciseField.setEditable(false);

        JLabel dateLabel = new JLabel("요일/날짜:");
        dateLabel.setForeground(Color.WHITE);

        // 날짜 표시 필드
        JTextField dateField = styleManager.createStyledTextField(date);
        dateField.setEditable(false);

        panel.add(exerciseLabel);
        panel.add(exerciseField);
        panel.add(dateLabel);
        panel.add(dateField);

        return panel;
    }

    private JPanel createSetInfoPanel(ArrayList<String[]> setData) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("세트 정보"));

        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 5, 5));
        gridPanel.setBackground(new Color(42, 42, 42));

        // 헤더 추가
        String[] headers = {"세트수", "무게", "횟수"};
        for (String header : headers) {
            JLabel headerLabel = new JLabel("<html><b>" + header + "</b></html>",
                    SwingConstants.CENTER);
            headerLabel.setForeground(Color.WHITE);
            gridPanel.add(headerLabel);
        }

        // 세트 데이터 추가
        for (String[] set : setData) {
            for (String value : set) {
                JTextField field = styleManager.createStyledTextField(value);
                field.setEditable(false);
                field.setHorizontalAlignment(SwingConstants.CENTER);
                gridPanel.add(field);
            }
        }

        panel.add(new JScrollPane(gridPanel), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));

        JButton closeButton = styleManager.createStyledButton("닫기");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton);

        return panel;
    }
}
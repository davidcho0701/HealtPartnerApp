package 헬스파트너;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DietPanel extends JPanel {
    private JDateChooser dateChooser;  // JTextField dateField를 JDateChooser로 변경
    private JTextField breakfastCalField, breakfastProteinField;
    private JTextField lunchCalField, lunchProteinField;
    private JTextField dinnerCalField, dinnerProteinField;
    private UIStyleManager styleManager;
    private DietDataManager dataManager;
    private DietWindow parentWindow;  // 추가

    public DietPanel(UIStyleManager styleManager, DietDataManager dataManager, DietWindow parentWindow) {        this.styleManager = styleManager;
        this.dataManager = dataManager;
        this.parentWindow = parentWindow;

        setLayout(new BorderLayout());
        setBackground(new Color(42, 42, 42));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Today Report");
        titledBorder.setTitleColor(Color.WHITE);
        setBorder(titledBorder);

        // 날짜 입력 패널
        add(createDatePanel(), BorderLayout.NORTH);

        // 식사 입력 패널
        add(createMealPanel(), BorderLayout.CENTER);

        // 저장 버튼
        JButton saveButton = styleManager.createStyledButton("저장");
        saveButton.addActionListener(e -> saveDietInfo());
        add(saveButton, BorderLayout.SOUTH);
    }

    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(new Color(42, 42, 42));

        JLabel dateLabel = new JLabel("요일/날짜:");
        dateLabel.setForeground(Color.WHITE);

        // JDateChooser 생성 및 설정
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy/MM/dd (E)");
        dateChooser.setPreferredSize(new Dimension(150, 25));
        dateChooser.setBackground(new Color(42, 42, 42));
        dateChooser.setForeground(Color.WHITE);
        dateChooser.getCalendarButton().setBackground(new Color(60, 60, 60));
        dateChooser.getCalendarButton().setForeground(Color.WHITE);
        dateChooser.setDate(new Date()); // 현재 날짜로 초기화

        JButton rouletteButton = styleManager.createStyledButton("식단 룰렛");
        rouletteButton.addActionListener(e ->
                SwingUtilities.invokeLater(() -> new DietRoulette(styleManager)));

        datePanel.add(dateLabel);
        datePanel.add(dateChooser);
        datePanel.add(rouletteButton);

        return datePanel;
    }

    private JPanel createMealPanel() {
        JPanel mealPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        mealPanel.setBackground(new Color(42, 42, 42));

        // 아침
        mealPanel.add(createLabel("아침:"));
        breakfastCalField = createInputField("칼로리(kcal)", 10);
        breakfastProteinField = createInputField("단백질(g)", 10);
        mealPanel.add(breakfastCalField);
        mealPanel.add(breakfastProteinField);

        // 점심
        mealPanel.add(createLabel("점심:"));
        lunchCalField = createInputField("칼로리(kcal)", 10);
        lunchProteinField = createInputField("단백질(g)", 10);
        mealPanel.add(lunchCalField);
        mealPanel.add(lunchProteinField);

        // 저녁
        mealPanel.add(createLabel("저녁:"));
        dinnerCalField = createInputField("칼로리(kcal)", 10);
        dinnerProteinField = createInputField("단백질(g)", 10);
        mealPanel.add(dinnerCalField);
        mealPanel.add(dinnerProteinField);

        return mealPanel;
    }

    private JTextField createInputField(String placeholderText, int columns) {
        JTextField field = new JTextField(columns);
        field.setBackground(Color.DARK_GRAY);
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                placeholderText,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    private void saveDietInfo() {
        // 날짜 형식 변환
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd (E)");
        String date = dateChooser.getDate() != null ? sdf.format(dateChooser.getDate()) : "";

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "날짜를 선택해주세요.",
                    "입력 오류",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<String> meals = new ArrayList<>();
        meals.add(String.format("아침 - 칼로리: %skcal, 단백질: %sg",
                breakfastCalField.getText(), breakfastProteinField.getText()));
        meals.add(String.format("점심 - 칼로리: %skcal, 단백질: %sg",
                lunchCalField.getText(), lunchProteinField.getText()));
        meals.add(String.format("저녁 - 칼로리: %skcal, 단백질: %sg",
                dinnerCalField.getText(), dinnerProteinField.getText()));

        dataManager.saveDietRecord(date, meals);
        clearInputFields();
        parentWindow.updateWeeklyReport();
    }

    private void clearInputFields() {
        dateChooser.setDate(new Date()); // 현재 날짜로 초기화
        breakfastCalField.setText("");
        breakfastProteinField.setText("");
        lunchCalField.setText("");
        lunchProteinField.setText("");
        dinnerCalField.setText("");
        dinnerProteinField.setText("");
    }
}
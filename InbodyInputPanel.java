package 헬스파트너;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InbodyInputPanel extends JPanel {
    private JDateChooser dateChooser;
    private JTextField heightField, weightField, skeletalMuscleField, bodyFatField;
    private UIStyleManager styleManager;
    private InbodyDataManager dataManager;
    private InbodyScoreCalculator scoreCalculator;
    private InbodyWindow parentWindow;  // 추가

    public InbodyInputPanel(UIStyleManager styleManager, InbodyDataManager dataManager, InbodyWindow parentWindow) {
        this.styleManager = styleManager;
        this.dataManager = dataManager;
        this.scoreCalculator = new InbodyScoreCalculator();
        this.parentWindow = parentWindow;  // 추가


        setLayout(new GridLayout(6, 1, 5, 10));
        setBackground(new Color(42, 42, 42));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "InBody 정보 입력",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));

        initializeFields();
        addSaveButton();
    }

    private void initializeFields() {
        // 날짜 선택기 패널 생성
        JPanel datePanel = new JPanel();
        datePanel.setBackground(new Color(42, 42, 42));
        datePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "요일/날짜:",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));

        // JDateChooser 설정
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy/MM/dd (E)");
        dateChooser.setPreferredSize(new Dimension(150, 25));
        dateChooser.setBackground(new Color(42, 42, 42));
        dateChooser.setForeground(Color.WHITE);
        dateChooser.getCalendarButton().setBackground(new Color(60, 60, 60));
        dateChooser.getCalendarButton().setForeground(Color.WHITE);
        dateChooser.setDate(new Date()); // 현재 날짜로 초기화
        datePanel.add(dateChooser);

        heightField = createInputField("키 (cm):");
        weightField = createInputField("몸무게 (kg):");
        skeletalMuscleField = createInputField("골격근량 (kg):");
        bodyFatField = createInputField("체지방률 (%):");

        add(datePanel);
        add(heightField);
        add(weightField);
        add(skeletalMuscleField);
        add(bodyFatField);
    }

    private JTextField createInputField(String labelText) {
        JTextField field = new JTextField(15);
        field.setBackground(Color.DARK_GRAY);
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                labelText,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));
        return field;
    }

    private void addSaveButton() {
        JButton saveButton = styleManager.createStyledButton("저장");
        saveButton.addActionListener(e -> saveInbodyData());
        add(saveButton);
    }

    private void saveInbodyData() {
        try {
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

            double height = Double.parseDouble(heightField.getText()) / 100;
            double weight = Double.parseDouble(weightField.getText());
            double skeletalMuscle = Double.parseDouble(skeletalMuscleField.getText());
            double bodyFat = Double.parseDouble(bodyFatField.getText());

            int totalScore = scoreCalculator.calculateTotalScore(height, weight, skeletalMuscle, bodyFat);
            dataManager.addRecord(date, totalScore);

            clearFields();
            parentWindow.updateDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "올바른 숫자 형식을 입력해주세요.",
                    "입력 오류",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        dateChooser.setDate(new Date()); // 현재 날짜로 초기화
        heightField.setText("");
        weightField.setText("");
        skeletalMuscleField.setText("");
        bodyFatField.setText("");
    }
}
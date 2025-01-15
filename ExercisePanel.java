package 헬스파트너;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExercisePanel extends JPanel {
    private ArrayList<JPanel> exercisePanels;
    private UIStyleManager styleManager;
    private JPanel exercisePanelContainer;

    public ExercisePanel(UIStyleManager styleManager) {
        this.styleManager = styleManager;
        this.exercisePanels = new ArrayList<>();

        JTextField dummyField = new JTextField();
        dummyField.setText(" ");
        dummyField.setText("");

        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 18));

        exercisePanelContainer = new JPanel();
        exercisePanelContainer.setLayout(new BoxLayout(exercisePanelContainer, BoxLayout.Y_AXIS));
        exercisePanelContainer.setBackground(new Color(18, 18, 18));

        add(exercisePanelContainer, BorderLayout.CENTER);
        addExercisePanel(); // 첫 운동 패널 추가
    }

    public void addExercisePanel() {
        String lastDate = getLastEnteredDate();
        JPanel exercisePanel = createExercisePanel(lastDate);
        exercisePanelContainer.add(exercisePanel);
        exercisePanels.add(exercisePanel);
        revalidate();
        repaint();
    }

    private JPanel createExercisePanel(String initialDate) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(42, 42, 42));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Today Report");
        titledBorder.setTitleColor(Color.WHITE);
        panel.setBorder(titledBorder);

        // 기본 정보 입력 패널
        JPanel basicInfoPanel = createBasicInfoPanel(initialDate);
        panel.add(basicInfoPanel, BorderLayout.NORTH);

        // 세트 입력 패널
        JPanel setInputPanel = new JPanel();
        setInputPanel.setLayout(new BoxLayout(setInputPanel, BoxLayout.Y_AXIS));
        setInputPanel.setBackground(new Color(42, 42, 42));
        addSetRow(setInputPanel);
        panel.add(setInputPanel, BorderLayout.CENTER);

        // 세트 추가 버튼
        JButton addSetButton = styleManager.createStyledButton("세트 추가");
        addSetButton.addActionListener(e -> addSetRow(setInputPanel));
        panel.add(addSetButton, BorderLayout.SOUTH);

        // 컴포넌트 참조 저장
        panel.putClientProperty("setInputPanel", setInputPanel);

        return panel;
    }

    private JPanel createBasicInfoPanel(String initialDate) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(42, 42, 42));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 운동명 라벨과 필드
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel exerciseLabel = new JLabel("운동명:");
        exerciseLabel.setForeground(Color.WHITE);
        panel.add(exerciseLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField exerciseField = styleManager.createStyledTextField(15);
        panel.add(exerciseField, gbc);

        // 날짜 선택기
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel dateLabel = new JLabel("요일/날짜:");
        dateLabel.setForeground(Color.WHITE);
        panel.add(dateLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;

        // JDateChooser 생성 및 스타일 설정
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy/MM/dd (E)");
        dateChooser.setBackground(new Color(42, 42, 42));
        dateChooser.setForeground(Color.WHITE);

        // 초기 날짜 설정
        if (!initialDate.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd (E)");
                Date date = sdf.parse(initialDate);
                dateChooser.setDate(date);
            } catch (Exception e) {
                dateChooser.setDate(new Date());
            }
        } else {
            dateChooser.setDate(new Date());
        }

        panel.add(dateChooser, gbc);

        // dateChooser를 나중에 참조할 수 있도록 저장
        panel.putClientProperty("dateChooser", dateChooser);

        return panel;
    }

    private void addSetRow(JPanel setInputPanel) {
        // 기존 패널 생성 코드를 수정
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // 간격 조정
        rowPanel.setBackground(new Color(42, 42, 42));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); // 높이 제한 추가

        String[] labels = {"세트수:", "무게:", "횟수:"};
        for (String label : labels) {
            JLabel setLabel = new JLabel(label);
            setLabel.setForeground(Color.WHITE);
            JTextField field = styleManager.createStyledTextField(5);
            field.setText(" ");
            field.setText("");

            // 텍스트필드 크기 제한
            field.setPreferredSize(new Dimension(50, 25));
            field.setMaximumSize(new Dimension(50, 25));

            rowPanel.add(setLabel);
            rowPanel.add(field);
        }

        setInputPanel.add(rowPanel);
        setInputPanel.revalidate();
        setInputPanel.repaint();
    }

    private String getLastEnteredDate() {
        if (exercisePanels.isEmpty()) return "";
        JPanel lastPanel = exercisePanels.get(exercisePanels.size() - 1);
        Component[] components = ((JPanel)lastPanel.getComponent(0)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field.getText().contains("/")) {
                    return field.getText();
                }
            }
        }
        return "";
    }

    public ArrayList<ExerciseData> getExerciseData() {
        ArrayList<ExerciseData> dataList = new ArrayList<>();
        for (JPanel panel : exercisePanels) {
            JPanel basicInfoPanel = (JPanel) panel.getComponent(0);
            JTextField exerciseField = (JTextField) basicInfoPanel.getComponent(1);
            JDateChooser dateChooser = (JDateChooser) basicInfoPanel.getClientProperty("dateChooser");
            JPanel setInputPanel = (JPanel) panel.getClientProperty("setInputPanel");

            String exerciseName = exerciseField.getText().trim();

            // 날짜 형식 변환
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd (E)");
            String date = dateChooser.getDate() != null ? sdf.format(dateChooser.getDate()) : "";
            ArrayList<String[]> setDataList = new ArrayList<>();

            if (!exerciseName.isEmpty() && !date.isEmpty()) {
                for (Component comp : setInputPanel.getComponents()) {
                    if (comp instanceof JPanel) {
                        JPanel setRow = (JPanel) comp;
                        ArrayList<String> setData = new ArrayList<>();
                        for (Component field : setRow.getComponents()) {
                            if (field instanceof JTextField) {
                                setData.add(((JTextField) field).getText().trim());
                            }
                        }
                        if (setData.size() == 3 && !setData.get(0).isEmpty()
                                && !setData.get(1).isEmpty() && !setData.get(2).isEmpty()) {
                            setDataList.add(setData.toArray(new String[0]));
                        }
                    }
                }

                if (!setDataList.isEmpty()) {
                    dataList.add(new ExerciseData(exerciseName, date, setDataList));
                }
            }
        }
        return dataList;
    }

    public void reset() {
        exercisePanelContainer.removeAll();
        exercisePanels.clear();
        addExercisePanel();
        revalidate();
        repaint();
    }
}
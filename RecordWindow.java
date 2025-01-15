package 헬스파트너;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;    // 추가
import java.util.ArrayList;        // 추가
import java.util.Map;             // 추가

public class RecordWindow extends JFrame {
    private ExercisePanel exercisePanel;
    private WeeklyReportPanel weeklyReportPanel;
    private ExerciseDataManager dataManager;
    private UIStyleManager styleManager;

    public RecordWindow() {
        setTitle("기록");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        styleManager = new UIStyleManager();
        dataManager = new ExerciseDataManager();

        // 로고 패널 추가
        add(createLogoPanel(), BorderLayout.NORTH);

        // 메인 패널 (운동 입력 + 주간 보고서)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(new Color(18, 18, 18));

        // 운동 입력 패널
        exercisePanel = new ExercisePanel(styleManager);
        JScrollPane exerciseScrollPane = new JScrollPane(exercisePanel);
        exerciseScrollPane.getViewport().setBackground(new Color(18, 18, 18));

        // 주간 보고서 패널
        weeklyReportPanel = new WeeklyReportPanel(styleManager);
        JScrollPane reportScrollPane = new JScrollPane(weeklyReportPanel);
        reportScrollPane.getViewport().setBackground(new Color(18, 18, 18));

        mainPanel.add(exerciseScrollPane);
        mainPanel.add(reportScrollPane);
        add(mainPanel, BorderLayout.CENTER);

        // 버튼 패널
        add(createButtonPanel(), BorderLayout.SOUTH);

        // 저장된 데이터 로드
        loadSavedData();

        setVisible(true);
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(18, 18, 18));
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel logoLabel = new JLabel("Health Partner");
        logoLabel.setFont(new Font("Brush Script MT", Font.BOLD, 28));
        logoLabel.setForeground(Color.RED);
        logoPanel.add(logoLabel);

        return logoPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(18, 18, 18));

        JButton addExerciseButton = styleManager.createStyledButton("운동 추가");
        addExerciseButton.addActionListener(e -> exercisePanel.addExercisePanel());

        JButton saveButton = styleManager.createStyledButton("저장");
        saveButton.addActionListener(e -> saveData());

        JButton resetButton = styleManager.createStyledButton("초기화");
        resetButton.addActionListener(e -> resetAllData());

        buttonPanel.add(addExerciseButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);

        return buttonPanel;
    }

    private void saveData() {
        dataManager.saveExerciseData(exercisePanel.getExerciseData());
        weeklyReportPanel.updateReport(dataManager.getExerciseData());  // loadExistingData() -> getExerciseData()
    }

    private void loadSavedData() {
        weeklyReportPanel.updateReport(dataManager.getExerciseData());  // loadExistingData() -> getExerciseData()
    }
    private void resetAllData() {
        // 커스텀 다이얼로그 생성
        JDialog dialog = new JDialog(this, "초기화 확인", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(30, 30, 30));
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        // 아이콘 패널
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setBackground(new Color(30, 30, 30));

        // 경고 아이콘 (텍스트로 대체)
        JLabel iconLabel = new JLabel("⚠");  // 문자열로 변경
        iconLabel.setForeground(Color.YELLOW);
        iconLabel.setFont(new Font("Dialog", Font.BOLD, 48));  // 크기 키움
        iconPanel.add(iconLabel);

        // 메시지 패널
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(30, 30, 30));
        JLabel messageLabel = new JLabel("모든 운동 기록을 초기화하시겠습니까?");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        messagePanel.add(messageLabel);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));

        JButton noButton = new JButton("취소");
        noButton.setBackground(new Color(60, 60, 60));
        noButton.setForeground(Color.WHITE);
        noButton.setFocusPainted(false);
        noButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        JButton yesButton = new JButton("초기화");
        yesButton.setBackground(new Color(200, 50, 50));
        yesButton.setForeground(Color.WHITE);
        yesButton.setFocusPainted(false);
        yesButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);

        // 액션 리스너 추가
        noButton.addActionListener(e -> dialog.dispose());
        yesButton.addActionListener(e -> {
            dataManager.clearData();
            exercisePanel.reset();
            weeklyReportPanel.updateReport(new LinkedHashMap<>());

            dialog.dispose();

            // 초기화 완료 메시지
            showCompletionDialog();
        });

        // 다이얼로그에 컴포넌트 추가
        dialog.add(iconPanel, BorderLayout.NORTH);
        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showCompletionDialog() {
        JDialog completionDialog = new JDialog(this, "초기화 완료", true);
        completionDialog.setLayout(new BorderLayout());
        completionDialog.getContentPane().setBackground(new Color(30, 30, 30));
        completionDialog.setSize(300, 150);
        completionDialog.setLocationRelativeTo(this);

        // 메시지 라벨
        JLabel messageLabel = new JLabel("모든 운동 기록이 초기화되었습니다.");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // 확인 버튼 스타일 수정
        JButton okButton = new JButton("확인");
        okButton.setPreferredSize(new Dimension(100, 30));  // 버튼 크기 설정
        okButton.setBackground(new Color(50, 50, 50));      // 어두운 회색 배경
        okButton.setForeground(Color.WHITE);                // 흰색 텍스트
        okButton.setFocusPainted(false);                    // 포커스 테두리 제거
        okButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));  // 회색 테두리
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 오버 시 손가락 커서

        // 마우스 오버 효과
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                okButton.setBackground(new Color(70, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                okButton.setBackground(new Color(50, 50, 50));
            }
        });

        okButton.addActionListener(e -> completionDialog.dispose());

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));  // 아래쪽 여백 추가
        buttonPanel.add(okButton);

        completionDialog.add(messageLabel, BorderLayout.CENTER);
        completionDialog.add(buttonPanel, BorderLayout.SOUTH);
        completionDialog.setVisible(true);
    }
}
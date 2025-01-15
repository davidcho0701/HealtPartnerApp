package 헬스파트너;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

public class InbodyWindow extends JFrame {
    private InbodyInputPanel inputPanel;
    private InbodyWeeklyPanel weeklyPanel;
    private InbodyDataManager dataManager;
    private UIStyleManager styleManager;

    public InbodyWindow() {
        setTitle("InBody 기록");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        styleManager = new UIStyleManager();
        dataManager = new InbodyDataManager();

        // 로고 패널
        add(createLogoPanel(), BorderLayout.NORTH);

        // 메인 패널 (입력 + 주간 보고서)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // 입력 패널
        inputPanel = new InbodyInputPanel(styleManager, dataManager, this);

        // 주간 보고서 패널
        weeklyPanel = new InbodyWeeklyPanel(styleManager);
        JScrollPane reportScrollPane = new JScrollPane(weeklyPanel);
        reportScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        mainPanel.add(inputPanel);
        mainPanel.add(reportScrollPane);

        // 버튼 패널
        JPanel buttonPanel = createButtonPanel();

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateDisplay();
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(18, 18, 18));

        JButton viewGraphButton = styleManager.createStyledButton("인바디 그래프 보러가기");
        viewGraphButton.addActionListener(e ->
                new InbodyGraphFrame(dataManager.getDates(), dataManager.getScores()));

        JButton resetButton = styleManager.createStyledButton("초기화");
        resetButton.addActionListener(e -> confirmReset());

        buttonPanel.add(viewGraphButton);
        buttonPanel.add(resetButton);

        return buttonPanel;
    }

    private void confirmReset() {
        // 커스텀 다이얼로그 생성
        JDialog dialog = new JDialog(this, "초기화 확인", true);  // JFrame이므로 this 직접 사용
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(30, 30, 30));
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        // 아이콘 패널
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setBackground(new Color(30, 30, 30));

        // 경고 아이콘
        JLabel iconLabel = new JLabel("⚠");
        iconLabel.setForeground(Color.YELLOW);
        iconLabel.setFont(new Font("Dialog", Font.BOLD, 48));
        iconPanel.add(iconLabel);

        // 메시지 패널
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(30, 30, 30));
        JLabel messageLabel = new JLabel("모든 인바디 기록을 초기화하시겠습니까?");
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
            dataManager.clearRecords();
            updateDisplay();  // weeklyPanel 업데이트
            dialog.dispose();
            showCompletionDialog();
        });

        dialog.add(iconPanel, BorderLayout.NORTH);
        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showCompletionDialog() {
        JDialog completionDialog = new JDialog(this, "초기화 완료", true);  // JFrame이므로 this 직접 사용
        completionDialog.setLayout(new BorderLayout());
        completionDialog.getContentPane().setBackground(new Color(30, 30, 30));
        completionDialog.setSize(300, 150);
        completionDialog.setLocationRelativeTo(this);

        // 메시지 라벨
        JLabel messageLabel = new JLabel("모든 인바디 기록이 초기화되었습니다.");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // 확인 버튼
        JButton okButton = new JButton("확인");
        okButton.setPreferredSize(new Dimension(100, 30));
        okButton.setBackground(new Color(50, 50, 50));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        buttonPanel.add(okButton);

        completionDialog.add(messageLabel, BorderLayout.CENTER);
        completionDialog.add(buttonPanel, BorderLayout.SOUTH);
        completionDialog.setVisible(true);
    }

    public void updateDisplay() {
        weeklyPanel.updateReport(dataManager.getDates(), dataManager.getScores());
    }
}
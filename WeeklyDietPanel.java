package 헬스파트너;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public class WeeklyDietPanel extends JPanel {
    private UIStyleManager styleManager;
    private DietDataManager dataManager;  // DietDataManager 추가
    private DietWindow parentWindow;      // DietWindow 참조 추가
    private JPanel contentPanel;


    public WeeklyDietPanel(UIStyleManager styleManager, DietDataManager dataManager, DietWindow parentWindow) {  // 생성자 수정
        this.styleManager = styleManager;
        this.dataManager = dataManager;
        this.parentWindow = parentWindow;

        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 18));

        // 내용을 담을 패널 생성
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(18, 18, 18));

        // 스크롤 패널에 contentPanel 추가
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBackground(new Color(18, 18, 18));
        scrollPane.getViewport().setBackground(new Color(18, 18, 18));
        add(scrollPane, BorderLayout.CENTER);

        // 초기화 버튼 패널 생성
        JPanel resetButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resetButtonPanel.setBackground(new Color(18, 18, 18));

        JButton resetButton = styleManager.createStyledButton("초기화");
        resetButton.addActionListener(e -> confirmReset());
        resetButtonPanel.add(resetButton);

        // 초기화 버튼 패널을 SOUTH에 추가
        add(resetButtonPanel, BorderLayout.SOUTH);

        TitledBorder border = BorderFactory.createTitledBorder("Weekly Report");
        border.setTitleColor(Color.WHITE);
        setBorder(border);
    }


    public void updateReport(Map<String, ArrayList<String>> dietRecords) {
        contentPanel.removeAll();  // contentPanel의 내용만 제거
        boolean alternateColor = true;

        for (Map.Entry<String, ArrayList<String>> entry : dietRecords.entrySet()) {
            JPanel datePanel = createDatePanel(entry.getKey(), entry.getValue(), alternateColor);
            contentPanel.add(datePanel);
            alternateColor = !alternateColor;
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createDatePanel(String date, ArrayList<String> meals, boolean alternateColor) {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 추가
        datePanel.setBackground(alternateColor ? new Color(50, 50, 50) : new Color(70, 70, 70));

        // 날짜 표시 형식 통일
        JLabel dateLabel = new JLabel("<html><b>" + date + "</b></html>");
        dateLabel.setForeground(Color.WHITE);
        datePanel.add(dateLabel);

        for (String meal : meals) {
            JLabel mealLabel = new JLabel(meal);
            mealLabel.setForeground(Color.WHITE);
            datePanel.add(Box.createRigidArea(new Dimension(0, 5))); // 간격 추가
            datePanel.add(mealLabel);
        }

        return datePanel;
    }

    private void confirmReset() {
        // 커스텀 다이얼로그 생성
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "초기화 확인", true);
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
        JLabel messageLabel = new JLabel("모든 식단 기록을 초기화하시겠습니까?");
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
            dataManager.clearRecords();  // DietDataManager의 메서드 사용
            updateReport(new LinkedHashMap<>());  // 빈 데이터로 업데이트
            dialog.dispose();
            showCompletionDialog();
        });

        dialog.add(iconPanel, BorderLayout.NORTH);
        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showCompletionDialog() {
        JDialog completionDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "초기화 완료", true);
        completionDialog.setLayout(new BorderLayout());
        completionDialog.getContentPane().setBackground(new Color(30, 30, 30));
        completionDialog.setSize(300, 150);
        completionDialog.setLocationRelativeTo(this);

        // 메시지 라벨
        JLabel messageLabel = new JLabel("모든 식단 기록이 초기화되었습니다.");
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
}
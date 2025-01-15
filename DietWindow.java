package 헬스파트너;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class DietWindow extends JFrame {
    private DietPanel dietPanel;
    private WeeklyDietPanel weeklyPanel;
    private DietDataManager dataManager;
    private UIStyleManager styleManager;

    public DietWindow() {
        setTitle("식단 기록");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        styleManager = new UIStyleManager();
        dataManager = new DietDataManager();

        // 로고 패널 추가
        add(createLogoPanel(), BorderLayout.NORTH);

        // 메인 패널 (식단 입력 + 주간 보고서)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(new Color(18, 18, 18));

        // 식단 입력 패널
        dietPanel = new DietPanel(styleManager, dataManager, this);  // this 추가

        // 주간 보고서 패널
        weeklyPanel = new WeeklyDietPanel(styleManager, dataManager, this);  // this와 dataManager 전달
        JScrollPane reportScrollPane = new JScrollPane(weeklyPanel);
        reportScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        mainPanel.add(dietPanel);
        mainPanel.add(reportScrollPane);
        add(mainPanel, BorderLayout.CENTER);

        // 저장된 데이터 로드 및 표시
        updateWeeklyReport();

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

    public void updateWeeklyReport() {
        Map<String, ArrayList<String>> records = dataManager.loadDietRecords();
        weeklyPanel.updateReport(records);
    }
}
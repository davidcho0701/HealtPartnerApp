package 헬스파트너;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

public class HealthPartnerApp extends JFrame {
    private JLabel scoreLabel;
    private JLabel tierLabel;
    private JLabel tierImageLabel;
    private Timer updateTimer;
    private InbodyDataManager dataManager;
    private Clip soundClip; // 소리 클립 추가

    public HealthPartnerApp() {
        setTitle("Health Partner");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // InbodyDataManager 초기화
        dataManager = new InbodyDataManager();

        // 어두운 배경색 설정
        getContentPane().setBackground(new Color(53, 51, 51));

        // 상단 제목 패널
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(18, 18, 18));

        // 로고 텍스트 설정
        JLabel titleLabel = new JLabel("Health Partner", JLabel.CENTER);
        titleLabel.setFont(new Font("Brush Script MT", Font.BOLD, 48));
        titleLabel.setForeground(Color.RED);
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(18, 18, 18));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        // "기록" 버튼
        JPanel recordButton = createRoundPanel("기록");
        recordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RecordWindow();
            }
        });

        // "식단" 버튼
        JPanel dietButton = createRoundPanel("식단");
        dietButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new DietWindow();
            }
        });

        // "INBODY 기록" 버튼
        JPanel inbodyButton = createRoundPanel("INBODY 기록");
        inbodyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new InbodyWindow();
            }
        });

        // 버튼을 패널에 추가
        buttonPanel.add(recordButton);
        buttonPanel.add(dietButton);
        buttonPanel.add(inbodyButton);

        // 메인 패널에 버튼 패널 추가
        add(buttonPanel, BorderLayout.CENTER);

        // 점수 및 티어 패널
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(new Color(18, 18, 18));
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));

        scoreLabel = new JLabel("점수: 아직 측정되지 않음");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tierLabel = new JLabel("티어: 아직 측정되지 않음");
        tierLabel.setForeground(Color.WHITE);
        tierLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tierLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tierImageLabel = new JLabel();
        tierImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scorePanel.add(Box.createVerticalStrut(20));
        scorePanel.add(scoreLabel);
        scorePanel.add(Box.createVerticalStrut(10));
        scorePanel.add(tierLabel);
        scorePanel.add(Box.createVerticalStrut(10));
        scorePanel.add(tierImageLabel);
        scorePanel.add(Box.createVerticalStrut(20));

        add(scorePanel, BorderLayout.SOUTH);

        // 주기적으로 점수 업데이트
        updateTimer = new Timer(1000, e -> updateScoreAndTier());
        updateTimer.start();

        // 소리 재생
        playSound("src/Ikoliks-Break-It-Down.wav");

        setVisible(true);
    }

    private void playSound(String soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile));
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
            soundClip.start(); // 소리 재생 시작
        } catch (Exception e) {
            System.err.println("소리 재생 실패: " + e.getMessage());
        }
    }

    private ImageIcon loadTierImage(String tier) {
        try {
            // resources 폴더의 이미지 경로
            String imagePath = "src/main/resources/images/" + tier + ".png";
            System.out.println("이미지 경로: " + imagePath);

            // 리소스 URL 가져오기
            java.net.URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl == null) {
                System.err.println("이미지를 찾을 수 없습니다: " + imagePath);
                // 대체 경로 시도
                imagePath = "src/main/resources/images/" + tier + ".png";
                System.out.println("대체 경로 시도: " + imagePath);
                return new ImageIcon(imagePath);
            }

            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image image = originalIcon.getImage();

            if (image == null) {
                System.err.println("이미지 로드 실패: " + tier);
                return null;
            }

            Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            System.err.println("티어 이미지 로드 실패: " + tier);
            e.printStackTrace();
            return null;
        }
    }

    private void updateScoreAndTier() {
        // Vector에서 최신 점수 가져오기
        Vector<Integer> scores = dataManager.getScores();
        if (!scores.isEmpty()) {
            int latestScore = scores.lastElement();
            String tier = calculateTier(latestScore);

            scoreLabel.setText("점수: " + latestScore);
            tierLabel.setText("티어: " + tier);

            // 티어 이미지 업데이트
            ImageIcon tierIcon = loadTierImage(tier);
            if (tierIcon != null) {
                tierImageLabel.setIcon(tierIcon);
            }
        }
    }

    private JPanel createRoundPanel(String text) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(42, 42, 42));
                g2.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        panel.setPreferredSize(new Dimension(100, 100));
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        panel.add(label);

        panel.setOpaque(false);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return panel;
    }

    public String calculateTier(int totalScore) {
        if (totalScore <= 40) return "아이언";
        if (totalScore <= 60) return "브론즈";
        if (totalScore <= 80) return "실버";
        if (totalScore <= 85) return "골드";
        if (totalScore <= 90) return "플레티넘";
        if (totalScore <= 95) return "다이아";
        return "마스터";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HealthPartnerApp());
    }
}
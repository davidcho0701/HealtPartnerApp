package 헬스파트너;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class DietRoulette extends JFrame {
    private static final String[] DIETS = {
            "샐러드", "닭가슴살", "현미밥", "오트밀", "채소 스프",
            "연어구이", "스무디 볼", "고구마", "콩나물국밥", "불고기 샐러드",
            "두부샐러드", "아보카도 토스트", "잡곡밥", "토마토파스타", "그릭요거트",
            "시금치 오믈렛", "쌈채소와 닭고기", "비건 타코", "저염 치킨 스테이크", "과일 샐러드"
    };

    public DietRoulette(UIStyleManager styleManager) {
        setTitle("식단 룰렛");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel roulettePanel = new JPanel();
        roulettePanel.setBackground(new Color(42, 42, 42));
        roulettePanel.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roulettePanel.add(imageLabel, BorderLayout.CENTER);

        add(roulettePanel);

        startRouletteAnimation(imageLabel);
        setVisible(true);
    }

    private void startRouletteAnimation(JLabel imageLabel) {
        ImageIcon[] images = {
                new ImageIcon("src/그림1.png"),
                new ImageIcon("src/그림2.png"),
                new ImageIcon("src/그림3.png"),
                new ImageIcon("src/그림4.png")
        };

        Timer timer = new Timer(100, null);
        final int[] index = {0};
        timer.addActionListener(e -> {
            imageLabel.setIcon(images[index[0]]);
            index[0] = (index[0] + 1) % images.length;
        });

        timer.start();
        playSound("src/Ticking-Clock-Sound.wav");

        Timer stopTimer = new Timer(5000, e -> {
            timer.stop();
            dispose();
            showResult(DIETS[new Random().nextInt(DIETS.length)]);
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
    }

    private void playSound(String filePath) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
                clip.start();
                Thread.sleep(5000);
                clip.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showResult(String selectedDiet) {
        JFrame resultFrame = new JFrame("추천 식단");
        resultFrame.setSize(300, 200);
        resultFrame.setLocationRelativeTo(null);

        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(Color.BLACK);
        resultPanel.setLayout(new BorderLayout());

        JLabel resultLabel = new JLabel(
                "<html><div style='text-align:center; color:white;'>추천 식단:<br>" +
                        selectedDiet + "</div></html>",
                SwingConstants.CENTER
        );
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        resultFrame.add(resultPanel);
        resultFrame.setVisible(true);
    }
}
package 헬스파트너;

import java.io.*;
import java.util.Vector;

public class InbodyDataManager {
    private static final String DATA_FILE = "inbody_data.txt";
    private Vector<String> dates;
    private Vector<Integer> scores;

    public InbodyDataManager() {
        dates = new Vector<>();
        scores = new Vector<>();
        loadDataFromFile();
    }

    public void addRecord(String date, int score) {
        dates.add(date);
        scores.add(score);

        // 최대 7일간의 기록만 유지
        if (dates.size() > 7) {
            dates.remove(0);
            scores.remove(0);
        }

        saveDataToFile();
    }

    public Vector<String> getDates() {
        return new Vector<>(dates);
    }

    public Vector<Integer> getScores() {
        return new Vector<>(scores);
    }

    public void clearData() {
        dates.clear();
        scores.clear();
        saveDataToFile();
    }

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (int i = 0; i < dates.size(); i++) {
                writer.write(dates.get(i) + "," + scores.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                dates.add(parts[0]);
                scores.add(Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            // 파일이 없거나 읽기 오류 발생 시 무시
        }
    }
    public void clearRecords() {
        // 모든 인바디 기록 초기화
        dates.clear();
        scores.clear();
        saveDataToFile();  // 파일에 저장
    }
}
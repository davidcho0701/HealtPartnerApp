package 헬스파트너;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DietDataManager {
    private static final String DATA_FILE = "diet_records.txt";
    private Map<String, ArrayList<String>> dietRecords;

    public DietDataManager() {
        dietRecords = new LinkedHashMap<>();
        loadFromFile();
    }

    public void saveDietRecord(String date, ArrayList<String> meals) {
        dietRecords.put(date, meals);
        if (dietRecords.size() > 7) {
            String oldestDate = dietRecords.keySet().iterator().next();
            dietRecords.remove(oldestDate);
        }
        saveToFile();
    }

    public Map<String, ArrayList<String>> loadDietRecords() {
        return new LinkedHashMap<>(dietRecords);
    }

    public void clearRecords() {
        dietRecords.clear();
        saveToFile();
    }

    private void loadFromFile() {
        try (Reader reader = new FileReader(DATA_FILE)) {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<Map<String, ArrayList<String>>>(){}.getType();
            Map<String, ArrayList<String>> loaded = gson.fromJson(reader, type);
            if (loaded != null) {
                dietRecords = loaded;
            }
        } catch (IOException e) {
            // 파일이 없으면 무시
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(DATA_FILE)) {
            new Gson().toJson(dietRecords, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
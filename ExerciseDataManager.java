package 헬스파트너;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;  // 추가
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ExerciseDataManager {
    private static final String SAVE_FILE_PATH = "exercise_records.json";
    private Map<String, ArrayList<ExerciseData>> exerciseData;
    private Vector<String> dates;

    public ExerciseDataManager() {
        dates = new Vector<>();
        exerciseData = new LinkedHashMap<>();
        loadFromFile();
    }

    public void saveExerciseData(ArrayList<ExerciseData> exerciseDataList) {
        for (ExerciseData newData : exerciseDataList) {
            String date = newData.getDate();
            String exerciseName = newData.getName();

            if (!dates.contains(date)) {
                dates.add(date);
                if (dates.size() > 7) {
                    String oldestDate = dates.remove(0);
                    exerciseData.remove(oldestDate);
                }
            }

            ArrayList<ExerciseData> dateExercises = exerciseData.computeIfAbsent(date, k -> new ArrayList<>());

            boolean updated = false;
            for (int i = 0; i < dateExercises.size(); i++) {
                ExerciseData existingData = dateExercises.get(i);
                if (existingData.getName().equals(exerciseName)) {
                    dateExercises.set(i, newData);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                dateExercises.add(newData);
            }
        }
        saveToFile();
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(SAVE_FILE_PATH)) {
            Map<String, Object> jsonData = new LinkedHashMap<>();
            jsonData.put("dates", dates);
            jsonData.put("exercises", exerciseData);

            Gson gson = new Gson();
            gson.toJson(jsonData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        // 파일이 없거나 비어있으면 새로 시작
        File file = new File(SAVE_FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            dates = new Vector<>();
            exerciseData = new LinkedHashMap<>();
            return;
        }

        try (Reader reader = new FileReader(SAVE_FILE_PATH)) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Gson gson = new Gson();

            try {
                Map<String, Object> jsonData = gson.fromJson(reader, type);

                if (jsonData != null && jsonData.containsKey("dates") && jsonData.containsKey("exercises")) {
                    // dates 복원
                    ArrayList<String> datesList = gson.fromJson(
                            gson.toJson(jsonData.get("dates")),
                            new TypeToken<ArrayList<String>>(){}.getType()
                    );
                    dates = new Vector<>(datesList);

                    // exerciseData 복원
                    Type exerciseType = new TypeToken<Map<String, ArrayList<ExerciseData>>>(){}.getType();
                    exerciseData = gson.fromJson(
                            gson.toJson(jsonData.get("exercises")),
                            exerciseType
                    );

                    // 7일 제한 적용
                    while (dates.size() > 7) {
                        String oldestDate = dates.remove(0);
                        exerciseData.remove(oldestDate);
                    }
                } else {
                    // JSON 형식이 맞지 않으면 새로 시작
                    dates = new Vector<>();
                    exerciseData = new LinkedHashMap<>();
                }
            } catch (JsonSyntaxException e) {
                // JSON 파싱 실패시 새로 시작
                dates = new Vector<>();
                exerciseData = new LinkedHashMap<>();
            }
        } catch (IOException e) {
            // 파일 읽기 실패시 새로 시작
            dates = new Vector<>();
            exerciseData = new LinkedHashMap<>();
        }
    }

    public Map<String, ArrayList<ExerciseData>> getExerciseData() {
        return new LinkedHashMap<>(exerciseData);
    }

    public void clearData() {
        dates.clear();
        exerciseData.clear();
        saveToFile();
    }

    public Vector<String> getDates() {
        return new Vector<>(dates);
    }
}
package 헬스파트너;

import java.util.ArrayList;

public class ExerciseData {
    private String name;
    private String date;
    private ArrayList<String[]> setData;

    // 기본 생성자 (Gson 필요)
    public ExerciseData() {
    }

    public ExerciseData(String name, String date, ArrayList<String[]> setData) {
        this.name = name;
        this.date = date;
        this.setData = setData;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String[]> getSetData() {
        return setData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSetData(ArrayList<String[]> setData) {
        this.setData = setData;
    }
}
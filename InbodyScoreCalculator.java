package 헬스파트너;

public class InbodyScoreCalculator {

    public int calculateTotalScore(double height, double weight, double skeletalMuscle, double bodyFat) {
        int bmiScore = calculateBMIScore(weight, height);
        int smiScore = calculateSMIScore(skeletalMuscle, height);
        int bodyFatScore = calculateBodyFatScore(bodyFat);

        int totalScore = bmiScore + smiScore + bodyFatScore;
        return Math.min(totalScore, 100);
    }

    private int calculateBMIScore(double weight, double height) {
        double bmi = weight / (height * height);
        System.out.println("BMI: " + bmi);

        if (bmi >= 18.5 && bmi <= 24.9) return 33;  // 정상
        if (bmi >= 25 && bmi <= 29.9) return 25;    // 과체중
        if (bmi >= 30) return 17;                    // 비만
        return 25;                                   // 저체중
    }

    private int calculateSMIScore(double skeletalMuscle, double height) {
        double smi = skeletalMuscle / (height * height);
        System.out.println("골격근량: " + skeletalMuscle + "kg (SMI: " + smi + ")");

        if (skeletalMuscle >= 40 && skeletalMuscle <= 45) return 33;       // 우수
        if ((skeletalMuscle >= 35 && skeletalMuscle < 40) ||
                (skeletalMuscle > 45 && skeletalMuscle <= 50)) return 25;     // 양호
        return 17;                                   // 부족
    }

    private int calculateBodyFatScore(double bodyFatPercentage) {
        System.out.println("체지방률: " + bodyFatPercentage + "%");

        if (bodyFatPercentage >= 8 && bodyFatPercentage <= 12) return 34;    // 우수
        if (bodyFatPercentage > 12 && bodyFatPercentage <= 15) return 30;    // 양호
        if (bodyFatPercentage > 15 && bodyFatPercentage <= 20) return 25;    // 보통
        if (bodyFatPercentage > 20 && bodyFatPercentage <= 25) return 20;    // 경계
        if (bodyFatPercentage > 25 && bodyFatPercentage <= 30) return 17;    // 주의
        return 14;                                                            // 위험
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

    // 테스트용 메인 메서드
    public static void main(String[] args) {
        InbodyScoreCalculator calculator = new InbodyScoreCalculator();

        // 테스트 케이스 1: 매우 좋은 상태
        System.out.println("\n테스트 1 - 매우 좋은 상태");
        int score1 = calculator.calculateTotalScore(1.75, 70.0, 42.0, 10.0);
        System.out.println("총점: " + score1);
        System.out.println("티어: " + calculator.calculateTier(score1));

        // 테스트 케이스 2: 양호한 상태
        System.out.println("\n테스트 2 - 양호한 상태");
        int score2 = calculator.calculateTotalScore(1.70, 65.0, 38.0, 14.0);
        System.out.println("총점: " + score2);
        System.out.println("티어: " + calculator.calculateTier(score2));

        // 테스트 케이스 3: 보통 상태
        System.out.println("\n테스트 3 - 보통 상태");
        int score3 = calculator.calculateTotalScore(1.80, 85.0, 32.0, 18.0);
        System.out.println("총점: " + score3);
        System.out.println("티어: " + calculator.calculateTier(score3));
    }
}
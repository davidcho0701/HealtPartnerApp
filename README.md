# HealtPartnerApp
객체지향언어2(Java) 프로젝트

<클래스> /n
1.ExerciseDataManager
기능: 운동 기록 데이터를 파일에 저장하고 불러오는 관리자 클래스
핵심 메서드:
saveExerciseData(ArrayList<ExerciseData>) // 운동 데이터 저장
loadExistingData() // 저장된 운동 데이터 불러오기
clearData() // 모든 운동 데이터 초기화

2.ExerciseDetailsFrame
기능: 각 운동의 상세 정보를 보여주는 팝업 창
핵심 메서드:createBasicInfoPanel() // 운동 기본 정보 패널 생성
createSetInfoPanel() // 세트 정보 패널 생성

3.ExercisePanel
기능: 운동 입력을 위한 메인 패널
핵심 메서드:addExercisePanel() // 새로운 운동 입력 패널 추가
getExerciseData() // 입력된 운동 데이터 가져오기
reset() // 패널 초기화

4.HealthPartnerApp
기능: 앱의 메인 창과 전체적인 UI 관리
핵심 메서드:calculateTier() // 점수에 따른 티어 계산
updateScoreAndTier() // 점수와 티어 업데이트

5.InbodyDataManager
기능: 인바디 측정 데이터 관리
핵심 메서드:addRecord() // 새로운 인바디 기록 추가
getScores() // 저장된 점수 가져오기
clearData() // 데이터 초기화

6. InbodyInputPanel

7.InbodyGraphFrame
기능: 인바디 측정 결과를 그래프로 표시
핵심 메서드:saveInbodyData() // 인바디 데이터 저장
clearInputFields() // 입력 필드 초기화

8.InbodyScoreCalculator
기능: 인바디 측정값을 바탕으로 점수 계산
핵심 메서드:calculateTotalScore() // 총점 계산
calculateBMIScore() // BMI 점수 계산
calculateSMIScore() // 골격근량 점수 계산

9.InbodyWeeklyPanel
기능: 주간 인바디 기록 표시
핵심 메서드:updateReport() // 주간 보고서 업데이트
createDatePanel() // 날짜별 패널 생성

10.InbodyWindow
기능: 인바디 기록 관리 창
핵심 메서드:updateDisplay() // 화면 업데이트
confirmReset() // 초기화 확인 다이얼로그

11.RecordWindow
기능: 운동 기록 관리 창
핵심 메서드:saveData() // 데이터 저장
resetAllData() // 모든 데이터 초기화

12.UIStyleManager
기능: UI 컴포넌트의 스타일 관리
핵심 메서드:createStyledButton() // 스타일이 적용된 버튼 생성
createStyledTextField() // 스타일이 적용된 텍스트필드 생성

13.WeeklyDietPanel
기능: 주간 식단 기록 표시
핵심 메서드:updateReport() // 주간 식단 보고서 업데이트
createDatePanel() // 날짜별 패널 생성

14.WeeklyReportPanel
기능: 주간 운동 기록 표시
핵심 메서드updateReport() // 주간 운동 보고서 업데이트
createDatePanel() // 날짜별 패널 생성
createExerciseListPanel() // 운동 목록 패널 생성

15.DietDataManager
기능: 식단 기록 데이터를 관리하고 파일로 저장/불러오기
핵심 메서드saveDietRecord() // 식단 기록 저장
loadDietRecords() // 저장된 식단 기록 불러오기
clearRecords() // 모든 식단 기록 초기화

16.DietPanel
기능: 식단 입력을 위한 UI 패널
핵심 메서드createMealPanel() // 식사 입력 패널 생성
saveDietInfo() // 식단 정보 저장
clearInputFields() // 입력 필드 초기화

17.DietRoulette
기능: 랜덤 식단 추천 룰렛 기능
핵심 메서드startRouletteAnimation() // 룰렛 애니메이션 시작
playSound() // 효과음 재생
showResult() // 결과 표시

18.DietWindow
기능: 식단 관리 메인 창
핵심 메서드updateWeeklyReport() // 주간 식단 보고서 업데이트
createLogoPanel() // 로고 패널 생성

19.ExerciseData
기능: 운동 데이터를 저장하는 모델 클래스
핵심 메서드:getName() // 운동 이름 반환
getDate() // 운동 날짜 반환
getSetData() // 세트 데이터 반환

<추가 설명>
파일처리
    1. 기록 (JSON방식)-저장 내용이 .JSON파일에 저장
    2. 식단 (.txt방식+JSON형식) - 파일 확장자는 .txt로 저장되지만 파일 내용은 JSON형식(텍스트 파일로 어떤 에디터로도 열어볼 수 있고, JSON 형식은 데이터 구조를 잘 표현할 수 있음)
    3. 인바디 (.txt 방식) - 저장 내용이 .TXT파일에 저장

고급스윙
1. 요일/날짜 달력 
    1. JDateChooser을 사용해 날짜 선택 UI구성
2. 식단 룰렛 애니메이션 효과
    1.  타이머를 이용한 이미지 전환
    2. 자동 종료
3. 인바디 점수를 시각화하는 커스텀 막대 그래프 생성
    1. 안티앨리어싱 
    2. 그리드 라인
    3. 동적 크기 조절

벡터
1. 기록
	-순서 유지: 날짜가 입력된 순서 그대로 유지
	-FIFO(First In First Out): 7일 제한 시 가장 오래된 데이터부터 제거
	-데이터 정렬: 날짜 순서대로 데이터를 저장하고 불러오는데 사용
2. 인바디
	-dates: 인바디 측정 날짜들을 순서대로 저장
	-scores: 각 날짜에 해당하는 점수를 같은 순서로 저장
		-두 백터의 같은 인덱스가 하나의 인바디 기록을 구성

패널(총 6개)
1. 기록 
    - Today Report(입력 패널)
    - Weekly Report(주간 기록 패널)
        - Details버튼 누르면 (프레임 생성)
2. 식단
	-Today Report(입력 패널)
    - Weekly Report(주간 기록 패널)
        - Details버튼 누르면 (프레임 생성)
3. 인바디 기록
	-입력 패널
	-Weekly Report(주간 기록 패널)
	-점수보러가기 버튼 누르면 (프레임 생성)

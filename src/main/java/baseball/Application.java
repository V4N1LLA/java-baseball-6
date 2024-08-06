package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다."); // 게임 시작 메시지 출력
        boolean play = true; // 게임 진행 여부를 확인하는 변수
        while (play) {
            playGame(); // 게임 진행
            play = isRestartGame(); // 게임 재시작 여부 확인
        }
    }

    private static void playGame() {
        List<Integer> computer = generateComputerNumbers(); // 컴퓨터 숫자 생성
        boolean gameWon = false; // 게임 승리 여부 확인 변수
        while (!gameWon) {
            String userInput = getUserInput(); // 사용자 입력 받기
            String result = compareNumbers(computer, userInput); // 입력 숫자와 컴퓨터 숫자 비교
            System.out.println(result); // 결과 출력
            if (result.contains("3스트라이크")) { // 3스트라이크 시 게임 승리
                gameWon = true;
            }
        }
    }

    private static List<Integer> generateComputerNumbers() {
        List<Integer> computer = new ArrayList<>(); // 컴퓨터 숫자를 저장할 리스트
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9); // 1에서 9 사이의 숫자 랜덤 선택
            if (!computer.contains(randomNumber)) { // 중복 숫자 방지
                computer.add(randomNumber); // 리스트에 숫자 추가
            }
        }
        return computer; // 컴퓨터 숫자 리스트 반환
    }

    private static String getUserInput() {
        System.out.print("숫자를 입력해주세요: "); // 사용자 입력 안내 메시지 출력
        String input = Console.readLine(); // 사용자 입력 받기
        if (!isValidInput(input)) { // 입력 검증
            throw new IllegalArgumentException("잘못된 입력입니다."); // 잘못된 입력 시 예외 발생
        }
        return input; // 검증된 입력 반환
    }

    private static boolean isValidInput(String input) {
        if (input.length() != 3) return false; // 입력 길이 확인
        Set<Character> uniqueChars = new HashSet<>(); // 입력 문자의 중복을 확인할 집합
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c) || c == '0' || !uniqueChars.add(c)) return false; // 숫자, 0 제외, 중복 문자 확인
        }
        return true; // 입력이 유효하면 true 반환
    }

    private static String compareNumbers(List<Integer> computer, String userInput) {
        int strike = 0; // 스트라이크 개수
        int ball = 0; // 볼 개수

        for (int i = 0; i < 3; i++) { // 3자리 숫자 비교
            int userDigit = Character.getNumericValue(userInput.charAt(i)); // 사용자 입력의 각 자리 숫자 추출
            if (computer.get(i) == userDigit) { // 같은 위치에 같은 숫자일 경우
                strike++;
            } else if (computer.contains(userDigit)) { // 다른 위치에 같은 숫자일 경우
                ball++;
            }
        }

        if (strike == 0 && ball == 0) return "낫싱"; // 스트라이크와 볼이 모두 없는 경우
        if (strike == 3) return "3스트라이크\n3개의 숫자를 모두 맞히셨습니다! 게임 종료"; // 3스트라이크일 경우

        return String.format("%d볼 %d스트라이크", ball, strike); // 스트라이크와 볼 개수 반환
    }

    private static boolean isRestartGame() {
        while (true) { // 유효한 입력을 받을 때까지 반복
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요."); // 게임 재시작 안내 메시지 출력
            String input = Console.readLine(); // 사용자 입력 받기
            if (input.equals("1")) return true; // 1 입력 시 게임 재시작
            if (input.equals("2")) return false; // 2 입력 시 게임 종료
            System.out.println("잘못된 입력입니다. 다시 입력해주세요."); // 잘못된 입력 메시지 출력
        }
    }
}

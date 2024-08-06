package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");
        boolean play = true;
        while (play) {
            playGame();
            play = isRestartGame();
        }
    }

    private static void playGame() {
        List<Integer> computer = generateComputerNumbers();
        boolean gameWon = false;
        while (!gameWon) {
            String userInput = getUserInput();
            String result = compareNumbers(computer, userInput);
            System.out.println(result);
            if (result.contains("3스트라이크")) {
                gameWon = true;
            }
        }
    }

    private static List<Integer> generateComputerNumbers() {
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        return computer;
    }

    private static String getUserInput() {
        System.out.print("숫자를 입력해주세요: ");
        String input = Console.readLine();
        if (!isValidInput(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        return input;
    }

    private static boolean isValidInput(String input) {
        if (input.length() != 3) return false;
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c) || c == '0' || !uniqueChars.add(c)) return false;
        }
        return true;
    }

    private static String compareNumbers(List<Integer> computer, String userInput) {
        int strike = 0;
        int ball = 0;

        for (int i = 0; i < 3; i++) {
            int userDigit = Character.getNumericValue(userInput.charAt(i));
            if (computer.get(i) == userDigit) {
                strike++;
            } else if (computer.contains(userDigit)) {
                ball++;
            }
        }

        if (strike == 0 && ball == 0) return "낫싱";
        if (strike == 3) return "3스트라이크\n3개의 숫자를 모두 맞히셨습니다! 게임 종료";

        return String.format("%d볼 %d스트라이크", ball, strike);
    }

    private static boolean isRestartGame() {
        while (true) {
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            String input = Console.readLine();
            if (input.equals("1")) return true;
            if (input.equals("2")) return false;
            System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        }
    }
}

import java.util.Scanner;
import java.lang.Math;

public class GuessNumberGame {
    public static void main(String[] args) {
        int noOfGuess = 10;
        int randomNum = 1 + (int) (Math.random() * 100);
        System.out.println("Guess the number between 1 and 100: ");
        while (noOfGuess > 0) {
            Scanner guessedNum = new Scanner(System.in);
            int guess = guessedNum.nextInt();
            if (guess == randomNum) {
                System.out.println("Booyah!, correcttttttt");
                break;
            } else {
                noOfGuess -= 1;
                if (guess > randomNum) {
                    System.out.println("It is higher than true number");
                } else {
                    System.out.println("It is lower than true number");
                }
            }

        }

        if (noOfGuess == 0) {
            System.out.println("Sorry the number was: " + randomNum);
        }

    }
}
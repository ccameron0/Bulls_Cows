package bullscows;
import java.util.*;

/** Note:  Unlike previously assigned projects, this one didn't require me to use
 * loops to prompt the user again for correct input, instead the assignment
 * wanted me to simply stop the system when it reaches an error**/

public class Main {

    static String generateSecretCode(int numberOfDigits, int possibleChars) {

        Scanner scan = new Scanner(System.in);

        /**the entered amount has to be less than eleven to have only unique digits**/
        while(numberOfDigits > 10) {
            System.out.println("Error: can't generate a secret number with a length of "
                    + numberOfDigits + " because there aren't enough unique digits.");
            numberOfDigits = scan.nextInt();
        }

        StringBuilder secretCode = new StringBuilder();

        /** while loop adds chars to secret code until it's the correct length  **/
        while (secretCode.length() < numberOfDigits) {

            Random random = new Random();

            /** Generates random number from input, to as high as 36 **/
            int randomInt = random.nextInt(possibleChars);
            char randomChar;

            /** 0-9 are the ascii chars 0-9. 10-36 are the ascii chars a-z **/
            if(randomInt <= 9) {
                randomChar = (char)(randomInt+48);
            }
            else {
                randomChar = (char)(randomInt+86);
            }

            /** Converts char to String to compare it to the string that
             * has already been built, and appends the char if it is unique **/
            String compare = Character.toString(randomChar);

            if(secretCode.length() == 0) {
                secretCode.append(randomChar);
            }
            if(!secretCode.toString().contains(compare)) {
                secretCode.append(randomChar);
            }

        }
        return secretCode.toString();
    }

    static void gradeInput (String secretCode, String input) {

        int bulls = 0;
        int cows = 0;

        /** First loop checks for bulls, second loop for cows  **/
        for(int i = 0; i < input.length(); i++) {

            if (secretCode.charAt(i) == input.charAt(i)) {
                bulls++;
            }

            for (int j = i+1; j < input.length(); j++) {
                if(secretCode.charAt(j) == input.charAt(i)) {
                    cows++;
                }
            }
        }

        System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s).");
    }

    /** method asks user for length of String and checks to make sure it's an
     * int, and that it's greater than 0. **/
    static int isInt () {
        Scanner scan = new Scanner(System.in);
        String codeLengthInString;
        int codeLength = 0;

        codeLengthInString = scan.next();
        try {
            codeLength = Integer.parseInt(codeLengthInString);
        }
        catch (NumberFormatException e) {
            System.out.println("Error: " + scan.nextInt() + " isn't a valid number.");
            System.exit(0);
        }
        if(codeLength == 0) {
            System.out.println("Error: Code length has to be greater than 0.");
            System.exit(0);
        }

        return codeLength;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        int codeLength = isInt();

        System.out.println("Input the number of possible symbols in the code:");
        int possibleChars = scan.nextInt();

        if(possibleChars > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }

        if(codeLength > possibleChars) {
            System.out.println("Error: it's not possible to generate a code with a length of "+
                    codeLength + " with " + possibleChars + " unique symbols.");
            System.exit(0);
        }


        String secretCode = generateSecretCode(codeLength, possibleChars);

        System.out.print("The secret is prepared: " + "*".repeat(codeLength));

        if(possibleChars < 10) {
            System.out.println(" (0-" + possibleChars + ").");
        }
        else {
            char charConvert = (char)(86+possibleChars);
            System.out.println(" (0-9, " + "a-" + charConvert + ").");
        }

        System.out.println("Okay, let's start a game!");
        String guess = "";
        int turn = 1;

        /** prompts users to guess code until it's correct with all bulls **/
        while (!guess.equals(secretCode)) {
            System.out.println("Turn " + turn + ":");
            guess = scan.next();

            gradeInput(secretCode, guess);
            turn++;
        }

        System.out.println("Congratulations! You guessed the secret code.");
    }
}

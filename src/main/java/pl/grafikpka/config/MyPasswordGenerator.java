package pl.grafikpka.config;

import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Configuration
public class MyPasswordGenerator {
    private final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private final String DIGIT = "0123456789";
    //private static final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
    // private static final String OTHER_SYMBOL = "~$^+=<>";
    //private static final String OTHER_SPECIAL = OTHER_PUNCTUATION +CHAR_LOWERCASE;
    private final int PASSWORD_LENGTH = 8;

    private final String PASSWORD_ALLOW =
            CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT;

    private SecureRandom random = new SecureRandom();

    public String generateStrongPassword() {

        StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

        // at least 2 chars (lowercase)
        String strLowerCase = generateRandomString(CHAR_LOWERCASE, 3);
        //       System.out.format("%-20s: %s%n", "Chars (Lowercase)", strLowerCase);
        result.append(strLowerCase);

        // at least 2 chars (uppercase)
        String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, 3);
        //       System.out.format("%-20s: %s%n", "Chars (Uppercase)", strUppercaseCase);
        result.append(strUppercaseCase);

        // at least 2 digits
        String strDigit = generateRandomString(DIGIT, 2);
//        System.out.format("%-20s: %s%n", "Digits", strDigit);
        result.append(strDigit);

        // at least 2 special characters (punctuation + symbols)
//        String strSpecialChar = generateRandomString(OTHER_SPECIAL, 2);
//        System.out.format("%-20s: %s%n", "Special chars", strSpecialChar);
//        result.append(strSpecialChar);

        // remaining, just random
//

        String password = result.toString();
//        // combine all
//        System.out.format("%-20s: %s%n", "Password", password);
        // shuffle again
        shuffleString(password);
        //          System.out.format("%-20s: %s%n", "Final Password", shuffleString(password));
        //System.out.format("%-20s: %s%n%n", "Password Length", password.length());

        return password;
    }

    // generate a random char[], based on `input`
    private String generateRandomString(String input, int size) {

        if (input == null || input.length() <= 0)
            throw new IllegalArgumentException("Invalid input.");
        if (size < 1) throw new IllegalArgumentException("Invalid size.");

        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            // produce a random order
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }

    // for final password, make it more random
    public String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);
        // java 8
        return result.stream().collect(Collectors.joining());
    }
}
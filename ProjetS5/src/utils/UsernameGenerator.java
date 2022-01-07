package utils;

public abstract class UsernameGenerator {
    public static String usernameGenerator(String firstName, String lastName) {
        char[] threeChars = new char[3];
        lastName.getChars(0, 3, threeChars, 0);
        return firstName.toLowerCase() + String.valueOf(threeChars).toUpperCase();
    }
}
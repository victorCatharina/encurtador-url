package br.com.victorCatharina.encurtador_url.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomId {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 8; // Length of the short ID

    public static String generateRandomId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

}

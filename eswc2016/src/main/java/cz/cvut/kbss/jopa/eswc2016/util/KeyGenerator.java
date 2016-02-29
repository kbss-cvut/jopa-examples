package cz.cvut.kbss.jopa.eswc2016.util;

public class KeyGenerator {

    private KeyGenerator() {
        throw new AssertionError();
    }

    public static Long generateKey() {
        return System.currentTimeMillis();
    }
}

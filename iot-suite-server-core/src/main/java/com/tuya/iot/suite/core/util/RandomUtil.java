package com.tuya.iot.suite.core.util;

import java.util.Random;
import java.util.UUID;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
public class RandomUtil {

    private static Random rd = new Random();

    public static String getStringByUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getStringWithNumber(int n) {
        int arg[] = new int[] {'0', '9' + 1};
        return getString(n, arg);
    }

    public static String getStringWithCharacter(int n) {
        int arg[] = new int[] {'a', 'z' + 1, 'A', 'Z' + 1};
        return getString(n, arg);
    }

    private static String getString(int n, int arg[]) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            res.append(getChar(arg));
        }
        return res.toString();
    }

    private static char getChar(int arg[]) {
        int size = arg.length;
        int c = rd.nextInt(size / 2);
        c = c * 2;
        return (char) (getIntegerBetween(arg[c], arg[c + 1]));
    }

    public static int getIntegerBetween(int n, int m) {
        if (m == n) {
            return n;
        }
        int res = getIntegerMoreThanZero();
        return n + res % (m - n);
    }

    public static int getIntegerMoreThanZero() {
        int res = rd.nextInt();
        while (res <= 0) {
            res = rd.nextInt();
        }
        return res;
    }
}

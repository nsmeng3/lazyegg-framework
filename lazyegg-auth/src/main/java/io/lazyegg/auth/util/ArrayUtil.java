package io.lazyegg.auth.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * ArrayUtil
 *
 * @author DifferentW  nsmeng3@163.com
 */

public abstract class ArrayUtil {
    private static final Logger log = LoggerFactory.getLogger(ArrayUtil.class);

    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }


        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;

        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
}


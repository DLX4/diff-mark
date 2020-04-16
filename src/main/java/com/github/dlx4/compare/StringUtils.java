package com.github.dlx4.compare;

import com.sun.istack.internal.Nullable;

public class StringUtils {

    public static boolean isEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }
}

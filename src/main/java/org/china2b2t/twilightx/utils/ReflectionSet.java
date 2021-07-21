package org.china2b2t.twilightx.utils;

/**
 * Reflection
 */
public class ReflectionSet {
    public static Object[] getEnumConstants(Class<?> clazz) {
        if (clazz.isEnum()) {
            return clazz.getEnumConstants();
        }

        throw new IllegalStateException(clazz.getName() + " is not a ENUM");
    }
}

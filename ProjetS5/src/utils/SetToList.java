package utils;

import java.util.NavigableSet;

public abstract class SetToList {
    public static Object[] convert(NavigableSet<?> setToSort) {
        Object[] list = new Object[setToSort.size()];
        int i = 0;
        for (Object o : setToSort) {
            list[i] = o;
            i++;
        }
        return list;
    }
}
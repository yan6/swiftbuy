package com.ywj.swiftbuy.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class ListUtils {

    public static <T> List<T> getSubList(List<T> list, int start, int num) {
        if (list == null) {
            return Lists.newArrayList();
        }
        int maxIndex = list.size() - 1;
        int end = start + num;
        if (start < 0 || start > maxIndex || num <= 0) {
            return Collections.emptyList();
        }
        if (start + num > maxIndex) {
            end = maxIndex + 1;
        }
        return list.subList(start, end);
    }

    /**
     * 通过下标取sublist， end<0 默认是取到最后
     *
     * @param list
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    public static <T> List<T> getSubListByIndex(List<T> list, int start, int end) {
        if (list == null) {
            return Lists.newArrayList();
        }
        int maxIndex = list.size() - 1;

        if (end < 0 || end > maxIndex) {
            end = maxIndex;
        }

        if (start < 0 || start > maxIndex || start > end) {
            return Collections.emptyList();
        }
        return list.subList(start, end);
    }

    public static <T> List<T> getRandomListFromTop(List<T> list, int range, int num) {
        if (list == null) {
            return Collections.emptyList();
        }
        List<T> pool = getSubList(list, 0, range);
        Collections.shuffle(pool);
        return getSubList(pool, 0, num);
    }

    public static <T> void addNotNullElement(List<T> list, T element) {
        if (element != null) {
            list.add(element);
        }
    }

    public static <T> void add(List<T> list, T element, int idx) {
        idx = idx >= 1 ? idx : 1;
        idx = idx <= list.size() + 1 ? idx : list.size() + 1;

        list.add(idx - 1, element);
    }

    public static List<Integer> toIntegerList(String intListString) {
        return Arrays.asList(intListString.split(","))
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
    public static List<String> toStringList(String listString) {
        return Arrays.asList(listString.split(","));
    }

    public static String toString(List<Integer> list) {
        return StringUtils.join(list.toArray(), ",");
    }

    public static void removeDuplicate(List list) {
        if (CollectionUtils.isEmpty(list))
            return;
        HashSet set = new HashSet(list);
        list.clear();
        list.addAll(set);
    }
}

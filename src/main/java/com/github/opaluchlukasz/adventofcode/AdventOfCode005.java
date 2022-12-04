package com.github.opaluchlukasz.adventofcode;

import org.apache.commons.lang3.mutable.MutableLong;
import org.eclipse.collections.impl.set.mutable.primitive.CharHashSet;

import java.util.List;

public class AdventOfCode005 {

    public static void main(String[] args) {
        List<String> lines = FileUtils.read("input_003");
        int totalPoints = 0;

        for (String line : lines) {
            totalPoints += solve(line);
        }

        System.out.println("total points: " + totalPoints);
    }

    private static long solve(String input) {
        String first = input.substring(0, input.length() / 2);
        String second = input.substring(input.length() / 2);

        CharHashSet firstSet = CharHashSet.newSetWith(first.toCharArray());
        CharHashSet duplicates = new CharHashSet();

        for (char item : second.toCharArray()) {
            if (firstSet.contains(item)) {
                duplicates.add(item);
            }
        }
        return prioritise(duplicates);
    }

    private static long prioritise(CharHashSet duplicates) {
        MutableLong sum = new MutableLong();
        duplicates.forEach(
                item -> sum.add(priorityOf(item))
        );
        return sum.longValue();
    }

    private static int priorityOf(char item) {
        if (item >= 'a') {
            return item - 'a' + 1;
        }
        return item - 'A' + 27;
    }
}

package com.github.opaluchlukasz.adventofcode;

import org.apache.commons.lang3.mutable.MutableLong;
import org.eclipse.collections.impl.set.mutable.primitive.CharHashSet;

import java.util.ArrayList;
import java.util.List;

public class AdventOfCode006 {

    public static void main(String[] args) {
        List<String> lines = FileUtils.read("input_003");
        int totalPoints = 0;

        List<String> pack = new ArrayList<>();
        for (int i = 0; i < lines.size(); i = i + 3) {
            pack.add(lines.get(i));
            pack.add(lines.get(i + 1));
            pack.add(lines.get(i + 2));

            totalPoints += solve(pack);
            pack.clear();
        }

        System.out.println("total points: " + totalPoints);
    }

    private static long solve(List<String> input) {
        CharHashSet firstSet = CharHashSet.newSetWith(input.get(0).toCharArray());
        CharHashSet secondSet = CharHashSet.newSetWith(input.get(1).toCharArray());
        CharHashSet thirdSet = CharHashSet.newSetWith(input.get(2).toCharArray());

        CharHashSet select = firstSet.select(c -> secondSet.contains(c) && thirdSet.contains(c));

        return prioritise(select);
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

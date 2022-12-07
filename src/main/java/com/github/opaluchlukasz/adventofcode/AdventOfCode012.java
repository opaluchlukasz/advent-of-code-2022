package com.github.opaluchlukasz.adventofcode;

import org.eclipse.collections.impl.set.mutable.primitive.CharHashSet;

import java.util.List;

public class AdventOfCode012 {

    private static final int MESSAGE_LENGTH = 14;

    public static void main(String[] args) {
        List<String> lines = FileUtils.read("input_006");
        System.out.println(solve(lines.get(0)));
    }

    private static int solve(String line) {
        for (int i = 0; i < line.length() - MESSAGE_LENGTH; i++) {
            CharHashSet set = new CharHashSet(line.substring(i, i  + MESSAGE_LENGTH).toCharArray());
            if (set.size() == MESSAGE_LENGTH) {
                return i + MESSAGE_LENGTH;
            }
        }
        return 0;
    }
}
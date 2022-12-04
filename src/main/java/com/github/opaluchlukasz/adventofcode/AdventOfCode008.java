package com.github.opaluchlukasz.adventofcode;

import java.util.List;

public class AdventOfCode008 {

    public static void main(String[] args) {
        List<String> lines = FileUtils.read("input_004");
        int totalPoints = 0;

        for (String line : lines) {
            totalPoints += solve(line.split(","));
        }

        System.out.println(totalPoints);
    }

    private static int solve(String[] input) {
        String[] firstElf = input[0].split("-");
        String[] secondElf = input[1].split("-");
        Range firstRange = new Range(Integer.parseInt(firstElf[0]), Integer.parseInt(firstElf[1]));
        Range secondRange = new Range(Integer.parseInt(secondElf[0]), Integer.parseInt(secondElf[1]));

        if (firstRange.overlap(secondRange)) {
            return 1;
        }
        return 0;
    }
}

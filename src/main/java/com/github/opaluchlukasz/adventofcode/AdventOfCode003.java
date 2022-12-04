package com.github.opaluchlukasz.adventofcode;

import java.util.List;

public class AdventOfCode003 {

    public static void main(String[] args) {
        int totalPoints = 0;
        List<String> lines = FileUtils.read("input_002");

        for (String line : lines) {
            totalPoints += solve(line.split("\\s"));
        }

        System.out.println("total points: " + totalPoints);
    }

    private static int solve(String[] input) {
        char firstElement = 'A';
        char pointsBaseLine = (char) (firstElement - 1);
        char diff = 'X' - 'A';
        char opponent = input[0].charAt(0);
        char me = (char) (input[1].charAt(0) - diff);

        int pointForWinning = 0;
        if (me == opponent) {
            pointForWinning = 3;
        } else if (me == 'C' && opponent == 'B') {
            pointForWinning = 6;
        } else if (me == 'B' && opponent == 'A') {
            pointForWinning = 6;
        } else if (me == 'A' && opponent == 'C') {
            pointForWinning = 6;
        }
        int pointsForSymbol = me - pointsBaseLine;
        return pointsForSymbol + pointForWinning;
    }
}

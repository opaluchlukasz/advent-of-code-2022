package com.github.opaluchlukasz.adventofcode;

import java.util.List;

public class AdventOfCode004 {

    public static void main(String[] args) {
        List<String> lines = FileUtils.read("input_002");
        int totalPoints = 0;

        for (String line : lines) {
            totalPoints += solve(line.split("\\s"));
        }

        System.out.println("total points: " + totalPoints);
    }

    private static int solve(String[] input) {
        char firstElement = 'A';
        char pointsBaseLine = (char) (firstElement - 1);
        char opponentSymbol = input[0].charAt(0);
        char result = input[1].charAt(0);
        char mySymbol = mySymbol(opponentSymbol, result);

        int pointForWinning = pointsForWinning(result);

        int pointsForSymbol = mySymbol - pointsBaseLine;
        return pointsForSymbol + pointForWinning;
    }

    private static int pointsForWinning(char result) {
        if (result == 'Y') {
            return 3;
        } else if (result == 'Z') {
            return 6;
        }
        return 0;
    }

    private static char mySymbol(char opponentSymbol, char result) {
        if (result == 'Y') {
            return opponentSymbol;
        } else if (result == 'X') {
            if (opponentSymbol == 'A') {
                return 'C';
            } else if (opponentSymbol == 'B') {
                return 'A';
            } else {
                return 'B';
            }
        } else {
            if (opponentSymbol == 'A') {
                return 'B';
            } else if (opponentSymbol == 'B') {
                return 'C';
            } else {
                return 'A';
            }
        }
    }
}

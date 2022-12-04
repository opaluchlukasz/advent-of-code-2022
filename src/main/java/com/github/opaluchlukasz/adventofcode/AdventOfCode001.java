package com.github.opaluchlukasz.adventofcode;

import java.util.List;

public class AdventOfCode001 {
    public static void main(String[] args) {
        long maxCalories = 0;
        long currentCalories = 0;

        List<String> lines = FileUtils.read("input_001");

        for (String line : lines) {
            if (line.isEmpty()) {
                if (currentCalories > maxCalories) {
                    maxCalories = currentCalories;
                }
                currentCalories = 0;
            } else {
                currentCalories += Long.parseLong(line);
            }
        }
        if (currentCalories > maxCalories) {
            maxCalories = currentCalories;
        }

        System.out.println("max calories: " + maxCalories);
    }
}

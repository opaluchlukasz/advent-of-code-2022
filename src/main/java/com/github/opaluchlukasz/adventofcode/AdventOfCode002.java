package com.github.opaluchlukasz.adventofcode;

import java.util.*;

public class AdventOfCode002 {

    public static void main(String[] args) {
        Set<Long> calories = new TreeSet<>(Comparator.<Long>naturalOrder().reversed());

        long maxCalories = 0;
        long currentCalories = 0;

        List<String> lines = FileUtils.read("input_001");

        for (String line : lines) {
            if (line.isEmpty()) {
                if (currentCalories != 0) {
                    calories.add(currentCalories);
                }
                currentCalories = 0;
            } else {
                currentCalories += Long.parseLong(line);
            }
        }
        calories.add(currentCalories);

        Iterator<Long> iterator = calories.iterator();

        for (int i = 0; i < 3; i++) {
            if (iterator.hasNext()) {
                maxCalories += iterator.next();
            }

        }
        System.out.println("max top 3 calories: " + maxCalories);
    }
}

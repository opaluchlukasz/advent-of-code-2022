package com.github.opaluchlukasz.adventofcode;

import org.eclipse.collections.api.tuple.primitive.IntIntPair;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.util.*;

public class AdventOfCode030 {
    public static final int MIN = 0;
    public static final int MAX = 4_000_000;

    public static void main(String[] args) {
        solve("input_015");
    }

    static void solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        System.out.println(solve(lines));
    }

    static long solve(List<String> input) {
        Map<Coordinates, Coordinates> sensorToBeacon = toSensorToBeacon(input);

        for (int i = MIN; i <= MAX; i++) {
            OptionalInt y = findDistressLocationIn(sensorToBeacon, i);
            if (y.isPresent()) {
               return i + 4_000_000L * y.getAsInt();
            }
        }
        return 0;
    }

    private static Map<Coordinates, Coordinates> toSensorToBeacon(List<String> input) {
        Map<Coordinates, Coordinates> s2b = new HashMap<>();
        for (String line : input) {
            line = line.replaceAll("Sensor at x=", "")
                    .replaceAll("closest beacon is at x=", "")
                    .replaceAll("y=", "")
                    .replaceAll(" ", "");
            String[] pairOfCoords = line.split(":");
            s2b.put(asCoordinates(pairOfCoords[0]), asCoordinates(pairOfCoords[1]));

        }
        return s2b;
    }

    private static OptionalInt findDistressLocationIn(Map<Coordinates, Coordinates> sensorToBeacon, int row) {
        List<IntIntPair> coveredArea = new ArrayList<>();
        sensorToBeacon.forEach(
                (s, b) -> {
                    int distance = distance(s, b);
                    if (s.x() - distance <= row && s.x() + distance >= row) {
                        int toCover = Math.abs(distance - Math.abs(s.x() - row));
                        coveredArea.add(PrimitiveTuples.pair(s.y() - toCover, s.y() + toCover));
                    }
                }
        );
        coveredArea.sort(Comparator.comparingInt(IntIntPair::getOne));
        return distressLocation(coveredArea);
    }

    private static OptionalInt distressLocation(List<IntIntPair> coveredArea) {
        int last = 0;
        for (int i = 0; i < coveredArea.size() - 1; i++) {
            if (coveredArea.get(i).getTwo() > last) {
                last = coveredArea.get(i).getTwo();
            }
            if (coveredArea.get(i + 1).getOne() - last == 2) {
               return OptionalInt.of(last + 1);
            }
        }
        return OptionalInt.empty();
    }

    private static int distance(Coordinates s, Coordinates b) {
        return Math.abs(s.x() - b.x()) + Math.abs(s.y() - b.y());
    }

    public static Coordinates asCoordinates(String s) {
        String[] coordinates = s.split(",");
        return new Coordinates(Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[0]));
    }
}
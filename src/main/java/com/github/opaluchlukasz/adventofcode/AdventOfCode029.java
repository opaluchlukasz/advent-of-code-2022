package com.github.opaluchlukasz.adventofcode;

import org.eclipse.collections.api.tuple.primitive.IntIntPair;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.util.*;
import java.util.stream.IntStream;

public class AdventOfCode029 {

    public static final int ROW_NUMBER = 2000000;

    public static void main(String[] args) {
        solve("input_015");
    }

    static void solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        System.out.println(solve(lines));
    }

    static long solve(List<String> input) {
        Map<Coordinates, Coordinates> sensorToBeacon = toSensorToBeacon(input);

        return findCovered(sensorToBeacon, ROW_NUMBER);
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

    private static long findCovered(Map<Coordinates, Coordinates> sensorToBeacon, int row) {
        Set<IntIntPair> coveredArea = new HashSet<>();
        sensorToBeacon.forEach(
                (s, b) -> {
                    int distance = distance(s, b);
                    if (s.x() - distance <= row && s.x() + distance >= row) {
                        int toCover = Math.abs(distance - Math.abs(s.x() - row));
                        coveredArea.add(PrimitiveTuples.pair(s.y() - toCover, s.y() + toCover));
                    }

                }
        );
        return coveredArea.stream()
                .flatMap(pair -> IntStream.range(pair.getOne(), pair.getTwo() + 1).mapToObj(i -> new Coordinates(row, i)))
                .distinct()
                .filter(c -> !sensorToBeacon.containsValue(c) && !sensorToBeacon.containsKey(c))
                .count();
    }

    private static int distance(Coordinates s, Coordinates b) {
        return Math.abs(s.x() - b.x()) + Math.abs(s.y() - b.y());
    }

    public static Coordinates asCoordinates(String s) {
        String[] coordinates = s.split(",");
        return new Coordinates(Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[0]));
    }
}
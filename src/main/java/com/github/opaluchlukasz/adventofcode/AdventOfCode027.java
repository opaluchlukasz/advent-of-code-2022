package com.github.opaluchlukasz.adventofcode;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.*;

public class AdventOfCode027 {

    public static final Coordinates STARTING_POINT = new Coordinates(0, 500);

    public static void main(String[] args) {
        solve("input_014");
    }

    static void solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        System.out.println(solve(lines));
    }

    static int solve(List<String> input) {
        Set<Coordinates> caveMap = toCaveMap(input);

        return followSand(caveMap);
    }

    private static int followSand(Set<Coordinates> rocks) {
        Set<Coordinates> sands = new HashSet<>();

        int abyss = rocks.stream().mapToInt(Coordinates::x).max().orElse(Integer.MAX_VALUE) + 1;

        Optional<Coordinates> sand = Optional.of(STARTING_POINT);
        while (!reachedEnd(sand)) {
            sand = moveSandTillItStops(Optional.of(STARTING_POINT), rocks, sands, abyss);
            sand.ifPresent(sands::add);
            draw(rocks, sands);
        }

        return sands.size();
    }

    private static void draw(Collection<Coordinates> rocks, Set<Coordinates> sands) {
        MutableInt minX = new MutableInt(0);
        MutableInt maxX = new MutableInt(0);
        MutableInt minY = new MutableInt(500);
        MutableInt maxY = new MutableInt(500);
        rocks.forEach(coordinate -> {
            if (coordinate.x() < minX.intValue()) {
                minX.setValue(coordinate.x());
            }
            if (coordinate.x() > maxX.intValue()) {
                maxX.setValue(coordinate.x());
            }
            if (coordinate.y() < minY.intValue()) {
                minY.setValue(coordinate.y());
            }
            if (coordinate.y() > maxY.intValue()) {
                maxY.setValue(coordinate.y());
            }
        });

        for (int i = minX.intValue(); i <= maxX.intValue(); i++) {
            StringBuilder row = new StringBuilder();
            for (int j = minY.intValue(); j <= maxY.intValue(); j++) {
                if (rocks.contains(new Coordinates(i, j))) {
                    row.append("#");
                } else if (sands.contains(new Coordinates(i, j))) {
                    row.append("o");
                } else {
                    row.append(".");
                }
            }
            System.out.println(row);
        }
        System.out.println();
    }

    private static Optional<Coordinates> moveSandTillItStops(Optional<Coordinates> sand,
                                                             Set<Coordinates> rocks,
                                                             Set<Coordinates> sands,
                                                             int abyss) {
        Optional<Coordinates> current = sand;
        Coordinates prev = null;
        while(current.isPresent()) {
            prev = current.get();
            current = nextMove(prev, rocks, sands);
            if (intoAbyss(current, abyss)) {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(prev);
    }

    private static boolean intoAbyss(Optional<Coordinates> current, int abyss) {
        return current.isPresent() && current.get().x() >= abyss;
    }

    private static Optional<Coordinates> nextMove(Coordinates sand, Set<Coordinates> rocks, Set<Coordinates> sands) {
        Coordinates nextPossiblePositionDown = sand.move(Coordinates.Direction.R);
        if (movePossible(rocks, sands, nextPossiblePositionDown)) {
            Coordinates nextPossiblePositionDiagonal = sand.move(Coordinates.Direction.D).move(Coordinates.Direction.R);
            if (movePossible(rocks, sands, nextPossiblePositionDiagonal)) {
                Coordinates nextPossiblePositionDiagonalRight = sand.move(Coordinates.Direction.U).move(Coordinates.Direction.R);
                if (movePossible(rocks, sands, nextPossiblePositionDiagonalRight)) {
                    return Optional.empty();
                }
                return Optional.of(nextPossiblePositionDiagonalRight);
            }
            return Optional.of(nextPossiblePositionDiagonal);
        }
        return Optional.of(nextPossiblePositionDown);
    }

    private static boolean movePossible(Set<Coordinates> rocks, Set<Coordinates> sands, Coordinates nextMove) {
        return rocks.contains(nextMove) || sands.contains(nextMove);
    }

    private static boolean reachedEnd(Optional<Coordinates> sand) {
        return sand.isEmpty();
    }

    private static Set<Coordinates> toCaveMap(List<String> input) {
        Set<Coordinates> caveMap = new HashSet<>();
        for (String s : input) {
            String[] split = s.split(" -> ");
            for (int i = 0; i < split.length - 1; i++) {
                Coordinates from = asCoordinates(split[i]);
                Coordinates to = asCoordinates(split[i + 1]);
                for (int x = Math.min(from.x(), to.x()); x <= Math.max(from.x(), to.x()); x++) {
                    for (int y = Math.min(from.y(), to.y()); y <= Math.max(from.y(), to.y()); y++) {
                        caveMap.add(new Coordinates(y, x));
                    }
                }
            }
        }
        return caveMap;
    }

    private static Coordinates asCoordinates(String s) {
        String[] coordinates = s.split(",");
        return new Coordinates(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
    }
}
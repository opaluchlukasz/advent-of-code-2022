package com.github.opaluchlukasz.adventofcode;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.*;

public class AdventOfCode018 {
    private static final int NUMBER_OF_KNOTS = 10;
    private static final Coordinates STARTING_POINT = new Coordinates(0, 0);

    public static void main(String[] args) {
        System.out.println(solve("input_009"));
    }

    static long solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        return solve(lines);
    }

    private static long solve(List<String> input) {
        List<Move> moves = asMoves(input);

        return countVisitedPoints(moves).size();
    }

    private static List<Move> asMoves(List<String> input) {
        return input.stream().map(row ->  {
            String[] move = row.split(" ");
            return new Move(Coordinates.Direction.valueOf(move[0]), Integer.parseInt(move[1]));
        }).toList();
    }

    private static Set<Coordinates> countVisitedPoints(List<Move> moves) {
        Set<Coordinates> visited = new HashSet<>();
        List<Coordinates> rope = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_KNOTS; i++) {
            rope.add(STARTING_POINT);
        }
        visited.add(STARTING_POINT);

        for (Move move : moves) {
            System.out.println(move);
            for (int s = 0; s < move.distance; s++) {
                rope.set(0, rope.get(0).move(move.direction));
                for (int i = 1; i < NUMBER_OF_KNOTS; i++) {
                    Coordinates prev = rope.get(i - 1);
                    Coordinates current = rope.get(i);
                    if (tooFarApart(prev, current)) {
                        rope.set(i, follow(current, prev));
                    }
                    if (i == NUMBER_OF_KNOTS - 1) {
                        visited.add(rope.get(i));
                    }
                }
            }
            draw(rope);
        }

        return visited;
    }

    private static Coordinates follow(Coordinates tail, Coordinates head) {
        if (tail.equals(head)) {
            return tail;
        }
        if (tail.x() == head.x()) {
            if (tail.y() < head.y()) {
                return new Coordinates(tail.x(), tail.y() + 1);
            } else {
                return new Coordinates(tail.x(), tail.y() - 1);
            }
        }
        if (tail.y() == head.y()) {
            if (tail.x() < head.x()) {
                return new Coordinates(tail.x() + 1, tail.y());
            } else {
                return new Coordinates(tail.x() - 1, tail.y());
            }
        }
        if (tail.y() < head.y()) {
            if (tail.x() < head.x()) {
                return new Coordinates(tail.x() + 1, tail.y() + 1);
            }
            return new Coordinates(tail.x() - 1, tail.y() + 1);
        }

        if (tail.x() > head.x()) {
            return new Coordinates(tail.x() - 1, tail.y() - 1);
        }
        return new Coordinates(tail.x() + 1, tail.y() - 1);
    }

    private static boolean tooFarApart(Coordinates head, Coordinates tail) {
        return Math.abs(head.x() - tail.x()) > 1 || Math.abs(head.y() - tail.y()) > 1;
    }

    private static void draw(Collection<Coordinates> coordinates) {
        MutableInt minX = new MutableInt();
        MutableInt maxX = new MutableInt();
        MutableInt minY = new MutableInt();
        MutableInt maxY = new MutableInt();
        coordinates.forEach(coordinate -> {
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
                if (i == 0 && j == 0) {
                    row.append("s");
                } else if (coordinates.contains(new Coordinates(i, j))) {
                    row.append("#");
                } else {
                    row.append(".");
                }
            }
            System.out.println(row);
        }
        System.out.println();
    }

    public record Move(Coordinates.Direction direction, int distance) { }
}
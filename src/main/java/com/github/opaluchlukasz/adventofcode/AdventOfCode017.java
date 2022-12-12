package com.github.opaluchlukasz.adventofcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdventOfCode017 {

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
        Coordinates head = new Coordinates(0, 0);
        Coordinates tail = new Coordinates(0, 0);
        visited.add(tail);

        for (Move move : moves) {
            for (int i = 0; i < move.distance; i++) {
                head = head.move(move.direction);
                tail = follow(tail, head);
                visited.add(tail);
            }
        }

        return visited;
    }

    private static Coordinates follow(Coordinates tail, Coordinates head) {
        if (tail.equals(head) || !tooFarApart(head, tail)) {
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

    public record Move(Coordinates.Direction direction, int distance) { }
}
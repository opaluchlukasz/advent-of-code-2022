package com.github.opaluchlukasz.adventofcode;

import java.util.stream.Stream;

public record Coordinates(int x, int y) {
    public enum Direction {
        U, D, R, L
    }

    public Coordinates move(Direction direction) {
        return switch (direction) {
            case D -> new Coordinates(x, y - 1);
            case U -> new Coordinates(x, y + 1);
            case L -> new Coordinates(x - 1, y);
            case R -> new Coordinates(x + 1, y);
        };
    }

    public Stream<Coordinates> moveInAllDirections() {
        return Stream.of(move(Direction.U), move(Direction.D), move(Direction.L), move(Direction.R));
    }
}

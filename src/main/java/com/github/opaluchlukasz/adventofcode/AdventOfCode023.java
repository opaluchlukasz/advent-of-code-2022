package com.github.opaluchlukasz.adventofcode;

import java.util.*;

public class AdventOfCode023 {

    public static void main(String[] args) {
        solve("input_012");
    }

    static void solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        solve(lines);
    }

    private static void solve(List<String> input) {
        char[][] grid = asGrid(input);

        int shortestPath = findShortestPath(grid);
        System.out.println(shortestPath);
    }

    private static int findShortestPath(char[][] grid) {
        int width = grid[0].length;
        int height = grid.length;

        Queue<Coordinates> coordinates = new LinkedList<>();
        Map<Coordinates, Integer> distance = new HashMap<>();

        Coordinates end = null;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 'S') {
                    grid[i][j] = 'a';
                    Coordinates start = new Coordinates(i, j);
                    coordinates.add(start);
                    distance.put(start, 0);
                }
                if (grid[i][j] == 'E') {
                    end = new Coordinates(i, j);
                    grid[i][j] = 'z';
                }
            }
        }

        while(!coordinates.isEmpty()) {
            Coordinates current = coordinates.poll();
            int currentDistance = distance.get(current);
            List<Coordinates> possibleMoves = current.moveInAllDirections()
                    .filter(move -> !isOutsideOfTheGrid(width, height, move) && notTooFar(grid, current, move))
                    .filter(key -> !distance.containsKey(key))
                    .toList();
            possibleMoves.forEach(c -> {
                distance.put(c, currentDistance + 1);
                coordinates.add(c);
            });
            if (current.equals(end)) {
               return distance.get(end);
            }
        }
        return 0;
    }

    private static boolean isOutsideOfTheGrid(int width, int height, Coordinates move) {
        return move.x() < 0 || move.x() >= height || move.y() < 0 || move.y() >= width;
    }

    private static boolean notTooFar(char[][] grid, Coordinates last, Coordinates current) {
        return grid[last.x()][last.y()] >= grid[current.x()][current.y()] ||
                grid[last.x()][last.y()] == grid[current.x()][current.y()] - 1;
    }

    private static char[][] asGrid(List<String> input) {
        int height = input.get(0).length();
        char[][] grid = new char[input.size()][height];
        for (int i = 0; i < input.size(); i++) {
            char[] toCharArray = input.get(i).toCharArray();
            System.arraycopy(toCharArray, 0, grid[i], 0, toCharArray.length);
        }
        return grid;
    }
}
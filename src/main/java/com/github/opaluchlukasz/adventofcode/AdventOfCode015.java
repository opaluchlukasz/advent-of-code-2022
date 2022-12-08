package com.github.opaluchlukasz.adventofcode;

import java.util.List;

public class AdventOfCode015 {

    public static void main(String[] args) {
        System.out.println(solve("input_008"));
    }

    static long solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        return solve(lines);
    }

    private static long solve(List<String> rows) {
        int[][] forestMap = toForestMap(rows);

        return countVisibleTrees(forestMap);
    }

    private static long countVisibleTrees(int[][] forestMap) {
        int x = forestMap.length;
        int y = forestMap[0].length;
        int visibleTrees = x * y;
        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                if (hidden(i, j, forestMap)) {
                    visibleTrees--;
                }
            }
        }
        return visibleTrees;
    }

    private static boolean hidden(int x, int y, int[][] forestMap) {
        int xLength = forestMap.length;
        int yLength = forestMap[0].length;
        int heightOfTheTree = forestMap[x][y];
        boolean hiddenLeft = false, hiddenRight = false, hiddenTop = false, hiddenBottom = false;
        for (int i = 0; i < x; i++) {
            if (forestMap[i][y] >= heightOfTheTree) {
                hiddenLeft = true;
                break;
            }
        }
        for (int i = x + 1; i < xLength; i++) {
            if (forestMap[i][y] >= heightOfTheTree) {
                hiddenRight = true;
                break;
            }
        }
        for (int i = 0; i < y; i++) {
            if (forestMap[x][i] >= heightOfTheTree) {
                hiddenTop = true;
                break;
            }
        }
        for (int i = y + 1; i < yLength; i++) {
            if (forestMap[x][i] >= heightOfTheTree) {
                hiddenBottom = true;
                break;
            }
        }
        return hiddenLeft && hiddenRight && hiddenTop && hiddenBottom;
    }

    private static int[][] toForestMap(List<String> lines) {
        int[][] map = new int[lines.get(0).length()][lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = Integer.parseInt(line.substring(j, j +1));
            }
        }
        return map;
    }
}
package com.github.opaluchlukasz.adventofcode;

import java.util.List;

public class AdventOfCode016 {

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
        int maxScenicScore = 0;
        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int scenicView = scenicView(i, j, forestMap);
                if (scenicView > maxScenicScore) {
                    maxScenicScore = scenicView;
                }
            }
        }
        return maxScenicScore;
    }

    private static int scenicView(int x, int y, int[][] forestMap) {
        int xLength = forestMap.length;
        int yLength = forestMap[0].length;
        int heightOfTheTree = forestMap[x][y];
        int scenicScoreLeft = 0, scenicScoreRight = 0, scenicScoreTop = 0, scenicScoreBottom = 0;
        for (int i = x - 1; i >= 0; i--) {
            scenicScoreLeft++;
            if (forestMap[i][y] >= heightOfTheTree) {
                break;
            }
        }
        for (int i = x + 1; i < xLength; i++) {
            scenicScoreRight++;
            if (forestMap[i][y] >= heightOfTheTree) {
                break;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            scenicScoreTop++;
            if (forestMap[x][i] >= heightOfTheTree) {
                break;
            }
        }
        for (int i = y + 1; i < yLength; i++) {
            scenicScoreBottom++;
            if (forestMap[x][i] >= heightOfTheTree) {
                break;
            }
        }
        return scenicScoreRight * scenicScoreLeft * scenicScoreBottom * scenicScoreTop;
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
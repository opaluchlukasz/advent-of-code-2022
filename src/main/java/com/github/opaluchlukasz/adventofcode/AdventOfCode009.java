package com.github.opaluchlukasz.adventofcode;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class AdventOfCode009 {

    public static void main(String[] args) {
        List<String> lines = FileUtils.read("input_005");

        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        List<Iterable<String>> crates = new ArrayList<>();
        List<Move> instructions = new ArrayList<>();
        int numberOfStacks = 0;

        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            if (line.contains("[")) {
                crates.add(Splitter.fixedLength(4).split(line));
            } else if (line.contains("move")) {
                instructions.add(asInstructions(line));
            } else {
                numberOfStacks = line.split("  ").length;
            }
        }

        List<Stack<Character>> stacks = buildStacks(numberOfStacks, crates);

        move(stacks, instructions);

        return stacks.stream().map(stack -> stack.peek().toString()).collect(Collectors.joining());
    }

    private static void move(List<Stack<Character>> stacks, List<Move> instructions) {
        for (Move move: instructions) {
            move.perform(stacks);
        }
    }

    private static List<Stack<Character>> buildStacks(int numberOfStacks, List<Iterable<String>> crates) {
        List<Stack<Character>> stacks = new ArrayList<>(numberOfStacks);
        IntStream.range(0, numberOfStacks).forEach(i -> stacks.add(new Stack<>()));
        for (int i = crates.size() - 1; i >= 0 ; i--) {
            Iterable<String> row = crates.get(i);
            MutableInt counter = new MutableInt();
            row.forEach(element -> {
                if (element.contains("[")) {
                   stacks.get(counter.intValue()).add(element.charAt(1));
                }
                counter.increment();
            });
        }
        return stacks;
    }

    private static Move asInstructions(String line) {
        String[] split = CharMatcher.inRange('0', '9').or(CharMatcher.is(' ')).retainFrom(line).split("  ");

        return new Move(parseInt(split[0].replace(" ", "")), parseInt(split[1]), parseInt(split[2]));
    }

    public record Move(int which, int from, int to) {
        public void perform(List<Stack<Character>> stacks) {
            Stack<Character> stackFrom = stacks.get(from - 1);
            Stack<Character> stackTo = stacks.get(to - 1);
            for(int i = 0; i < which; i++) {
                stackTo.push(stackFrom.pop());
            }
        }
    }

}
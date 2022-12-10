package com.github.opaluchlukasz.adventofcode;

import lombok.RequiredArgsConstructor;
import org.eclipse.collections.api.set.primitive.LongSet;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;

import java.util.List;

public class AdventOfCode019 {

    public static void main(String[] args) {
        System.out.println(solve("input_010"));
    }

    static long solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        return solve(lines);
    }

    private static long solve(List<String> input) {
        List<Op> ops = asOps(input);

        return runAndCalculateStrength(ops, LongHashSet.newSetWith(20, 60, 100, 140, 180, 220));
    }

    private static long runAndCalculateStrength(List<Op> ops, LongSet checkpoints) {
        long register = 1;
        long clock = 1;
        long strengthAtCheckpoints = 0;
        for (Op op : ops) {
            for (int i = 0; i < op.clocks(); i++) {
                clock++;
                if (op instanceof AddX o && i == op.clocks() - 1) {
                    register += o.value;
                }
                if (checkpoints.contains(clock)) {
                    strengthAtCheckpoints += clock * register;
                }
            }
        }

        return strengthAtCheckpoints;
    }

    private static List<Op> asOps(List<String> input) {
        return input.stream().map(row ->  {
            if (row.startsWith("noop")) {
                return new Noop();
            } else {
                String[] add = row.split(" ");
                return new AddX(Integer.parseInt(add[1]));
            }
        }).toList();
    }

    static abstract sealed class Op {
        abstract int clocks();
    }

    static final class Noop extends Op {
        @Override
        int clocks() {
            return 1;
        }
    }
    @RequiredArgsConstructor
    static final class AddX extends Op {
        private final int value;
        @Override
        int clocks() {
            return 2;
        }
    }
}
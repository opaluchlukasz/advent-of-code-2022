package com.github.opaluchlukasz.adventofcode;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.eclipse.collections.api.set.primitive.IntSet;
import org.eclipse.collections.api.set.primitive.LongSet;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;

import java.util.List;

public class AdventOfCode020 {

    public static void main(String[] args) {
        solve("input_010");
    }

    static void solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        solve(lines);
    }

    private static void solve(List<String> input) {
        List<Op> ops = asOps(input);

        runAndDrawMessage(ops);
    }

    private static void runAndDrawMessage(List<Op> ops) {
        long register = 1;
        long clock = 1;
        LongHashSet drawn = new LongHashSet();
        for (Op op : ops) {
            for (int i = 0; i < op.clocks(); i++) {
                long cursor = (clock - 1) % 240;
                long cursorVsSprite = cursor % 40;
                if (cursorVsSprite >= register - 1 && cursorVsSprite <= register +1) {
                    drawn.add(clock - 1);
                }
                clock++;
                if (op instanceof AddX o && i == op.clocks() - 1) {
                    register += o.value;
                }
            }
        }

        draw(drawn);
    }

    private static void draw(LongHashSet drawn) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 40; j++) {
                if (drawn.contains(j + (i * 40))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
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

    @ToString
    static final class Noop extends Op {
        @Override
        int clocks() {
            return 1;
        }
    }

    @RequiredArgsConstructor
    @ToString
    static final class AddX extends Op {
        private final int value;
        @Override
        int clocks() {
            return 2;
        }
    }
}
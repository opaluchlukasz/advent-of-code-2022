package com.github.opaluchlukasz.adventofcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.eclipse.collections.api.block.function.primitive.LongToBooleanFunction;
import org.eclipse.collections.api.block.function.primitive.LongToLongFunction;
import org.eclipse.collections.api.set.primitive.LongSet;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;

import java.util.*;
import java.util.function.LongConsumer;
import java.util.stream.Collectors;

public class AdventOfCode021 {

    public static void main(String[] args) {
        solve("input_011");
    }

    static void solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        solve(lines);
    }

    private static void solve(List<String> input) {
        List<Monkey> monkeys = asMonkeys(input);

        runMonkeyBusiness(monkeys);
    }

    private static void runMonkeyBusiness(List<Monkey> monkeys) {
        for (int i = 0; i < 20; i++) {
            for (Monkey monkey: monkeys) {
                monkey.inspectAllItems(monkeys);
            }
        }
        TreeSet<Integer> monkeyActivity = monkeys.stream().map(monkey -> monkey.itemsInspected).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(monkeyActivity.pollLast() * monkeyActivity.pollLast());
    }

    private static List<Monkey> asMonkeys(List<String> input) {
        List<Monkey> monkeys = new ArrayList<>();
        Monkey.MonkeyBuilder monkeyBuilder = Monkey.builder();
        for (int i = 1; i < input.size(); i++) {
            String line = input.get(i);
            if (line.startsWith("Monkey")) {
                monkeys.add(monkeyBuilder.build());
                monkeyBuilder = new Monkey.MonkeyBuilder();
            } else if (line.contains("  Starting items: ")) {
                String itemsAsLine = line.replace("  Starting items: ", "").replaceAll(" ", "");
                String[] items = itemsAsLine.split(",");
                monkeyBuilder.itemsWithWorryLevel(LongArrayList.newListWith(Arrays.stream(items).mapToLong(Long::parseLong).toArray()));
            } else if (line.contains("  Operation: new = old ")) {
                String[] operation = line.replace("  Operation: new = old ", "").split(" ");
                OptionalLong operand = operation[1].equals("old") ? OptionalLong.empty() : OptionalLong.of(Long.parseLong(operation[1]));
                if ("+".equals(operation[0])) {
                    monkeyBuilder.inspectOperation((LongToLongFunction) l -> l + operand.orElse(l));
                }
                if ("*".equals(operation[0])) {
                    monkeyBuilder.inspectOperation((LongToLongFunction) l -> l * operand.orElse(l));
                }
            }  else if (line.contains("  Test: divisible by ")) {
                int operand = Integer.parseInt(line.replace("  Test: divisible by ", ""));
                monkeyBuilder.test((LongToBooleanFunction) l -> l % operand == 0);
            } else if (line.contains("    If true: throw to monkey ")) {
                int to = Integer.parseInt(line.replace("    If true: throw to monkey ", ""));
                monkeyBuilder.throwIfTrue(to);
            } else if (line.contains("    If false: throw to monkey ")) {
                int to = Integer.parseInt(line.replace("    If false: throw to monkey ", ""));
                monkeyBuilder.throwIfFalse(to);
            }

        }
        monkeys.add(monkeyBuilder.build());
        return monkeys;
    }

    @ToString
    @AllArgsConstructor
    @Builder
    static class Monkey {
        private final LongArrayList itemsWithWorryLevel;
        private final LongToLongFunction inspectOperation;
        private final LongToBooleanFunction test;
        private final int throwIfTrue;
        private final int throwIfFalse;
        private int itemsInspected = 0;

        public void inspectAllItems(List<Monkey> monkeys) {
            for (int i = 0; i < itemsWithWorryLevel.size(); i++) {
                itemsInspected++;
                long newItemWorryLevel = inspectOperation.applyAsLong(itemsWithWorryLevel.get(i)) / 3;
                int throwTo = test.valueOf(newItemWorryLevel) ? throwIfTrue : throwIfFalse;
                monkeys.get(throwTo).catchItem(newItemWorryLevel);
            }
            itemsWithWorryLevel.clear();
        }

        private void catchItem(long item) {
                this.itemsWithWorryLevel.add(item);
        }
    }
}
package com.github.opaluchlukasz.adventofcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.eclipse.collections.api.block.function.primitive.LongToBooleanFunction;
import org.eclipse.collections.api.block.function.primitive.LongToLongFunction;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class AdventOfCode022 {

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
        long maxDivisibleValue = monkeys.stream().mapToLong(m -> m.divisible).reduce(1, (l, l1) -> l * l1);
        for (int i = 0; i < 10_000; i++) {
            for (Monkey monkey: monkeys) {
                monkey.inspectAllItems(monkeys, maxDivisibleValue);
            }
        }
        TreeSet<BigInteger> monkeyActivity = monkeys.stream().map(monkey -> monkey.itemsInspected).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(monkeyActivity.pollLast().multiply(monkeyActivity.pollLast()));
    }

    private static List<Monkey> asMonkeys(List<String> input) {
        List<Monkey> monkeys = new ArrayList<>();
        Monkey.MonkeyBuilder monkeyBuilder = aMonkey();
        for (int i = 1; i < input.size(); i++) {
            String line = input.get(i);
            if (line.startsWith("Monkey")) {
                monkeys.add(monkeyBuilder.build());
                monkeyBuilder = aMonkey();
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
                monkeyBuilder
                        .test((LongToBooleanFunction) l -> l % operand == 0)
                        .divisible(operand);
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

    private static Monkey.MonkeyBuilder aMonkey() {
        return Monkey.builder().itemsInspected(BigInteger.ZERO);
    }

    @ToString
    @AllArgsConstructor
    @Builder
    static class Monkey {
        private final LongArrayList itemsWithWorryLevel;
        private final LongToLongFunction inspectOperation;
        private final LongToBooleanFunction test;
        private final int divisible;
        private final int throwIfTrue;
        private final int throwIfFalse;
        private BigInteger itemsInspected;

        public void inspectAllItems(List<Monkey> monkeys, long maxDivisibleValue) {
            for (int i = 0; i < itemsWithWorryLevel.size(); i++) {
                itemsInspected = itemsInspected.add(BigInteger.ONE);
                long newItemWorryLevel = inspectOperation.applyAsLong(itemsWithWorryLevel.get(i)) % maxDivisibleValue;
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
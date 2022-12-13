package com.github.opaluchlukasz.adventofcode;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AdventOfCode025 {

    public static void main(String[] args) {
        solve("input_013");
    }

    static void solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        System.out.println(solve(lines));
    }

    static int solve(List<String> input) {
        List<Pair<SignalComposite, SignalComposite>> pairs = toPairs(input);

        return findPairsInOrder(pairs);
    }

    private static int findPairsInOrder(List<Pair<SignalComposite, SignalComposite>> pairs) {
       int validSignals = 0;
        for (int i = 0; i < pairs.size(); i++) {
            Pair<SignalComposite, SignalComposite> signalPair = pairs.get(i);
            if (compare(signalPair.getLeft(), signalPair.getRight()) < 0) {
                validSignals += i + 1;
            }
        }
        return validSignals;
    }

    private static int compare(SignalComposite left, SignalComposite right) {
        if (left instanceof Element el1 && right instanceof Element el2) {
            return Integer.compare(el1.leaf(), el2.leaf());
        }
        if (left instanceof Array arr && right instanceof Array arr2) {
            for (int i = 0; i < Math.min(arr.children().size(), arr2.children().size()); i++) {
                int compare = compare(arr.children().get(i), arr2.children().get(i));
                if (compare == 0) {
                    continue;
                }
                return compare;
            }
            if (arr2.children().size() < arr.children().size()) {
                return 1;
            }
            if (arr2.children().size() > arr.children().size()) {
                return -1;
            }
        }
        if (left instanceof Array arr && right instanceof Element el) {
            Array arr2 = new Array(List.of(el));
            return compare(arr, arr2);
        }
        if (left instanceof Element el && right instanceof Array arr) {
            Array arr2 = new Array(List.of(el));
            return compare(arr2, arr);
        }
        return 0;
    }

    private static List<Pair<SignalComposite, SignalComposite>> toPairs(List<String> input) {
        List<Pair<SignalComposite, SignalComposite>> pairs = new ArrayList<>();
        for (int i = 0; i < input.size(); i+=3) {
            SignalComposite left = asSignalComposite(input.get(i).toCharArray(), 1).getRight();
            SignalComposite right = asSignalComposite(input.get(i + 1).toCharArray(), 1).getRight();
            pairs.add(Pair.of(left, right));
        }
        return pairs;
    }

    private static Pair<Integer, SignalComposite> asSignalComposite(char[] elements, int startingPoint) {
        List<SignalComposite> currentElements = new ArrayList<>();
        for (int i = startingPoint; i < elements.length - 1; i++) {
            if (elements[i] == '[') {
                Pair<Integer, SignalComposite> positionAndSignalCompositePair = asSignalComposite(elements, i + 1);
                currentElements.add(positionAndSignalCompositePair.getRight());
                i = positionAndSignalCompositePair.getLeft();
            } else if (elements[i] == ']') {
                return Pair.of(i, new Array(currentElements));
            } else if(Character.isDigit(elements[i]) && Character.isDigit(elements[i + 1])) {
                currentElements.add(new Element(Integer.valueOf(elements[i] + String.valueOf(elements[i + 1]))));
                i++;
            } else if(Character.isDigit(elements[i])) {
                currentElements.add(new Element(Integer.valueOf(String.valueOf(elements[i]))));
            }
        }
        return Pair.of(elements.length, new Array(currentElements));
    }

    interface SignalComposite { }

    record Array(List<? extends SignalComposite> children) implements SignalComposite {
        @Override
        public String toString() {
            return children().stream().map(Objects::toString).collect(Collectors.joining(",", "[", "]"));
        }
    }

    record Element(Integer leaf) implements SignalComposite {
        @Override
        public String toString() {
            return String.valueOf(leaf);
        }
    }
}
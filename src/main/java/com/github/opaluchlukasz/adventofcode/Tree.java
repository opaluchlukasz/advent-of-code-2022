package com.github.opaluchlukasz.adventofcode;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class Tree<T> {

    @Getter
    private final T data;
    @Getter
    private final Tree<T> parent;
    private final List<Tree<T>> children;

    public Tree(T data, Tree<T> parent) {
        this.parent = parent;
        this.data = data;
        this.children = new LinkedList<>();
    }

    public void addChild(T child) {
        this.children.add(new Tree<>(child, this));
    }

    public Tree<T> findInChildren(Predicate<T> predicate) {
        for (Tree<T> child : children) {
            if (predicate.test(child.data)) {
                return child;
            }
        }
        throw new IllegalArgumentException("node not matching predicate");
    }

    public void traverse(Consumer<Tree<T>> consumer) {
        consumer.accept(this);
        for (Tree<T> child: children) {
            child.traverse(consumer);
        }
    }
}
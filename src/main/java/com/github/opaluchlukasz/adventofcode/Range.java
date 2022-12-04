package com.github.opaluchlukasz.adventofcode;

public record Range(int start, int end) {
    public boolean overlap(Range interval) {
        return !(end < interval.start) && !(start > interval.end);
    }

    public boolean fullyContains(Range interval) {
        return start >= interval.start && end <= interval.end;
    }
}
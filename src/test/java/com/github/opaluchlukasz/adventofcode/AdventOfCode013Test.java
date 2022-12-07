package com.github.opaluchlukasz.adventofcode;


import org.junit.jupiter.api.Test;

import static com.github.opaluchlukasz.adventofcode.AdventOfCode013.solve;
import static org.assertj.core.api.Assertions.assertThat;

class AdventOfCode013Test {

    @Test
    public void shouldCalculateForGivenExample() {
        //when
        long result = solve("007_1");

        //then
        assertThat(result).isEqualTo(95437L);
    }

    @Test
    public void shouldCalculateForSameNamedDirectories() {
        //when
        long result = solve("007_2");

        //then
        assertThat(result).isEqualTo(95437L + 2557L);
    }
}
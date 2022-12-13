package com.github.opaluchlukasz.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.opaluchlukasz.adventofcode.AdventOfCode025.solve;
import static org.assertj.core.api.Assertions.assertThat;

class AdventOfCode025Test {

    @Test
    public void shouldComparePair1() {
        //when
        int result = solve(List.of("[1,1,3,1,1]", "[1,1,5,1,1]"));

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void shouldComparePair2() {
        //when
        int result = solve(List.of("[[1],[2,3,4]]", "[[1],4]"));

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void shouldComparePair6() {
        //when
        int result = solve(List.of("[]", "[3]"));

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void shouldComparePair7() {
        //when
        int result = solve(List.of("[[[]]]", "[[]]"));

        //then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void shouldComparePair8() {
        //when
        int result = solve(List.of(
                "[1,[2,[3,[4,[5,6,7]]]],8,9]",
                "[1,[2,[3,[4,[5,6,0]]]],8,9]"
        ));

        //then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void shouldComparePairsWithDoubleDigits() {
        //when
        int result = solve(List.of("[10,1,3,1,1]", "[10,1,10,1,1]"));

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testFromInput1() {
        //when
        int result = solve(List.of(
                "[[4,[[],[2],[4,1,5,1],5,[2,7,5,7]]],[3,[],3,8],[[4,[4,7],[4,7],5,[5,3,1,5]],8,[1],9,6]]",
                "[[[4]],[[[],0,[9,5,2,9,5]],9,[[8,1,7,5],10],6,[3,[5,2],8,[9,3,2,5]]],[[[],9,5,[0,7,8]]]]"));

        //then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testFromInput2() {
        //when
        int result = solve(List.of(
                "[[],[9,7,9],[[[]],[3,[]],[]]]",
                "[[[2],10,10],[8,[8,[10,0],0]],[0,[[0,10,8,1],[2,2,10],[3]],7,6],[]]"));

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testFromInput3() {
        //when
        int result = solve(List.of(
                "[[[[9,8],[10,10],[0,8,1],[0,2]]],[[0,[3],7,[8],[7,10,2]]]]", "[[9,8,5,1]]"));

        //then
        assertThat(result).isEqualTo(0);
    }
}
package com.example.generator;

import java.util.Arrays;
import java.util.List;

import com.example.model.ReferenceString;

public class TestGenerator extends ReferenceStringGenerator{

    public TestGenerator(String name) {
        super(name);
    }

    @Override
    public ReferenceString generate(int minPageNumber, int maxPageNumber, int length) {
        Integer[] testRefs = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        List<Integer> refs = Arrays.asList(testRefs);
        return new ReferenceString(refs);
    }
}

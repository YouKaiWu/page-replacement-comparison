package com.example.generator;

import java.util.ArrayList;

import com.example.model.ReferenceString;

public class RandomGenerator extends ReferenceStringGenerator {

    public RandomGenerator(String name) {
        super(name);
    }

    @Override
    public ReferenceString generate(int minPageNumber, int maxPageNumber, int length) {
        ArrayList<Integer> refs = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            refs.add(this.random.nextInt(minPageNumber, maxPageNumber + 1)); 
        }
        return new ReferenceString(refs);
    }
}

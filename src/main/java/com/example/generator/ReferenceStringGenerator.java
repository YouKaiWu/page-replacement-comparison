package com.example.generator;

import java.util.Random;

import com.example.model.ReferenceString;

public abstract class ReferenceStringGenerator{

    protected Random random;
    protected String name;

    public ReferenceStringGenerator(String name) {
        this.random = new Random();
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    // abstract generate method
    public abstract ReferenceString generate(int minPageNumber, int maxPageNumber, int length);
}


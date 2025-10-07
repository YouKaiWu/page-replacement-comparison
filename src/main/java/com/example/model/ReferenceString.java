package com.example.model;

import java.util.List;

public class ReferenceString{
    
    private final List<Integer> sequence;

    public ReferenceString(List<Integer> sequence){
        this.sequence = sequence;
    }

    public List<Integer> getSequence(){
        return this.sequence;
    }

    @Override
    public String toString(){
        return "reference string:" + this.sequence.toString();
    }
}


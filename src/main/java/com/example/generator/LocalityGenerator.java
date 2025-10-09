package com.example.generator;

import java.util.ArrayList;

import com.example.model.ReferenceString;

public class LocalityGenerator extends ReferenceStringGenerator{

    public LocalityGenerator(String name) {
        super(name);
    }

    @Override
    public ReferenceString generate(int minPageNumber, int maxPageNumber, int length) {
        ArrayList<Integer> refs = new ArrayList<>();
        int minLen = 20, maxLen = 100;         // length 20 ~ 100
        int minRange = 10, maxRange = 50;      // range 10 ~ 50
        while(refs.size() < length){
            int pageStart = random.nextInt(minPageNumber, maxPageNumber+1);     // random start position
            int len = random.nextInt(maxLen - minLen + 1) + minLen;             // random length
            int range = random.nextInt(maxRange - minRange + 1) + minRange;     // random range
            for(int i = 0 ; i < len  && refs.size() < length; i++){             // i represents the current page generated, and the total number of pages can't exceed the limit.
                int maxPage = Math.min(maxPageNumber, pageStart + range - 1);   // maxPage can't exceed maxPageNumber 
                refs.add(random.nextInt(pageStart, maxPage+1));                 // random pick in the selected range
            }
        } 
        return new ReferenceString(refs);
    }
}

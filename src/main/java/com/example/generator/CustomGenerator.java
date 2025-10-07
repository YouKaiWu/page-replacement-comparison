package com.example.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.model.ReferenceString;

// 產生高頻率(90%)的 hotPages，與低頻率的 coldPages
public class CustomGenerator extends ReferenceStringGenerator{

    public CustomGenerator(String name) {
        super(name);
    }
    
    @Override
    public ReferenceString generate(int minPageNumber, int maxPageNumber, int length) {
        List<Integer> refs = new ArrayList<>();
        List<Integer> allPages = new ArrayList<>();
        for(int i = minPageNumber; i <= maxPageNumber; i++){
            allPages.add(i);
        }   
        Collections.shuffle(allPages);
        int hotPageLen = 100;                                                   // hotPage length set to 100
        int coldPageLen = (maxPageNumber - minPageNumber + 1) - hotPageLen;     // remaining pages are cold pages
        int[] hotPages = new int[hotPageLen]; 
        int[] coldPages = new int[coldPageLen];      
        for(int i = 0 ; i < maxPageNumber; i++){    
            if(i < hotPageLen){
                hotPages[i] = allPages.get(i);
            }else{
                coldPages[i-hotPageLen] = allPages.get(i);
            }
        } 
        for(int i = 0; i < length; i++){
            if(this.random.nextDouble() < 0.9){                                 // 90 % are hotPages
                refs.add(hotPages[this.random.nextInt(hotPageLen)]);
            }else{
                refs.add(coldPages[this.random.nextInt(coldPageLen)]);
            }
        }
        return new ReferenceString(refs);
    }
}

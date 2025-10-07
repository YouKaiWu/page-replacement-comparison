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
        int minLen = 20, maxLen = 100;         // 長度 20 ~ 100
        int minRange = 10, maxRange = 50;      // 範圍 10 ~ 50
        while(refs.size() < length){
            int pageStart = random.nextInt(minPageNumber, maxPageNumber+1);     // 隨機起始位置
            int len = random.nextInt(maxLen - minLen + 1) + minLen;             // 隨機長度
            int range = random.nextInt(maxRange - minRange + 1) + minRange;     // 隨機範圍
            for(int i = 0 ; i < len  && refs.size() < length; i++){             // i 為當前產生到第 i 頁， 總頁數不能超過範圍   
                int maxPage = Math.min(maxPageNumber, pageStart + range - 1);   // 最大頁數不可超過 maxPageNumber
                refs.add(random.nextInt(pageStart, maxPage+1));                 // 在範圍內隨機挑選
            }
        } 
        return new ReferenceString(refs);
    }
}

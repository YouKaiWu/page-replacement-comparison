package com.example.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.model.ReferenceString;
import com.example.result.Data;

// freq + time decay
public class Custom extends Algorithm {

    private ArrayList<Integer> frames;
    private int[] freq;
    private int timerInterval;   // when the timer reached the timeInterval, every elements in frequent list decay by a factor of 2

    public Custom(String name) {
        super(name);
    }

    @Override
    public void reset(int frameSize) {
        super.reset(frameSize);
        this.frames = new ArrayList<>();
        this.freq = new int[frameSize];
        Arrays.fill(freq, 0);
        this.timerInterval = 1000;
    }

    @Override
    public Data execute(ReferenceString refs) {
        int timer = 0;
        for(Integer page: refs.getSequence()){
            if(!frames.contains(page)){                 // if page is not in memory
                this.interruptCnt++;                    // page fault interrupt
                this.pageFaultCnt++;                    // update page fault counter
                if(frames.size() >= this.frameSize){
                    int victimIdx = getVictim();   
                    clearFreqList(victimIdx);
                    frames.set(victimIdx, page);
                    this.diskWriteCnt++;                // write victim frame back to disk
                    this.interruptCnt++;                // disk write interrupt    
                }else{
                    frames.add(page);
                }
            }
            int pageIdx = frames.indexOf(page);
            freq[pageIdx]++;
            timer++;
            if(timer == this.timerInterval){
                this.interruptCnt++;
                decayFreq();
                timer = 0;
            }
        }
        return new Data(this.frameSize, this.pageFaultCnt, this.diskWriteCnt, this.interruptCnt);
    }

    private int getVictim(){
        int minIdx = 0;
        int minFreq = Integer.MAX_VALUE;      
        for(int i = 0; i < frames.size(); i++){ 
            int curFreq = freq[i];
            if(curFreq == 0)
                continue;
            if(curFreq < minFreq){
                minIdx = i;
                minFreq = curFreq;
            } 
        }
        return minIdx;
    }

    private void clearFreqList(int victimIdx){
        this.freq[victimIdx] = 0;
    }

    private void decayFreq(){
        for(int i = 0; i < freq.length; i++){
            freq[i] >>= 1;
        }
    }
}
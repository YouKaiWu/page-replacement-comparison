package com.example.algorithm;

import java.util.ArrayList;

import com.example.model.ReferenceString;
import com.example.result.Data;

// Additional-Reference-Bits algorithm
public class ARB extends Algorithm {

    private ArrayList<Integer> frames;      // save frame
    private ArrayList<Integer> ARBList;     // save ARB
    private ArrayList<Boolean> visited;     
    private int timeInterval;               // timer interval


    public ARB(String name) {
        super(name);
    }

    @Override
    public void reset(int frameSize){
        super.reset(frameSize);
        this.frames = new ArrayList<>();
        this.ARBList = new ArrayList<>();
        this.visited = new ArrayList<>();
        this.timeInterval = 8;
    }

    @Override
    public Data execute(ReferenceString refs) {
        int timer = 0;                                              
        for (Integer page : refs.getSequence()) {   
            if(!frames.contains(page)){                             // if page is not in memory
                this.interruptCnt++;                                // page fault interrupt
                this.pageFaultCnt++;                                // update page fault count
                if(frames.size() >= this.frameSize){                // if memory is full of frames
                    int index = getIndexOfMinARB(ARBList);          // search for the index which has minimum ARB value as victim frame
                    this.diskWriteCnt++;                            // write victim frame back to disk
                    this.interruptCnt++;                            // disk write interrupt
                    frames.set(index, page);                        // add new frame
                    ARBList.set(index, 0);                          
                    visited.set(index, true);
                }else{
                    frames.add(page);                               // add new frame
                    ARBList.add(0);
                    visited.add(true);
                }
            }       
            timer++;                                                // update timer                                                
            if(timer == timeInterval){                              // when the timer reaches the specified time interval
                this.interruptCnt++;                                // cause a timer interrupt
                updateARB(ARBList, visited);                        
                timer = 0;                                          // reset timer
            }
        }
        return new Data(this.frameSize, this.pageFaultCnt, this.diskWriteCnt, this.interruptCnt);
    }

    private int getIndexOfMinARB(ArrayList<Integer> ARBList){
        int minIndex = 0;
        int minARB = 0xFF + 1;
        for(int i = 0; i < this.frameSize; i++){
            if(ARBList.get(i) < minARB){
                minIndex = i;
                minARB = ARBList.get(i);
            }
        }
        return minIndex;
    }

    private void updateARB(ArrayList<Integer> ARBList, ArrayList<Boolean> visited){  
        for(int i = 0; i < ARBList.size(); i++){
            int curARB = ARBList.get(i);
            curARB >>=1;                                            // shift ARB right by 1 bit
            if(visited.get(i)){                                     // if frame has been visited in last time interval 
                curARB |= 0x80;                                     // set the highest bit to 1 
            }                                                                                                                           
            ARBList.set(i, curARB);                                 // write back to ARBList
            visited.set(i, false);                                  // clear 
        }
    }
}



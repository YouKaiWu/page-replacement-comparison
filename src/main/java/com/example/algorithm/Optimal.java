package com.example.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.example.model.ReferenceString;
import com.example.result.Data;

// Optimal algorithm
public class Optimal extends Algorithm{
    
    private ArrayList<Integer> frames;

    public Optimal(String name){
        super(name);
    }

    @Override
    public void reset(int frameSize){
        super.reset(frameSize);
        frames = new ArrayList<>();
    }

    @Override
    public Data execute(ReferenceString refs){
        List<Integer> sequence= refs.getSequence();
        for(int i = 0; i < sequence.size(); i++){
            int page = sequence.get(i);
            if(!frames.contains(page)){                             // if page is not in memory
                this.interruptCnt++;                                // page fault interrupt
                this.pageFaultCnt++;                                // update page fault counter
                if(frames.size() >= this.frameSize){                // if memory is full of frames
                    this.interruptCnt++;                            // os interrupt to calculate when the current frames will use memory again
                    int maxIdx = 0;                                 // index of frame which will not be used for the long period
                    int maxDis = 0;                                 // distance between the longest period of selected frame and current page
                    for(int j = 0; j < frames.size(); j++){                  
                        int nextUseDis = getDis(sequence, j, i);    // nextUse distance between selected frame and current page
                        if(nextUseDis > maxDis){                    // update if distance is farther
                            maxIdx = j;
                            maxDis = nextUseDis;
                        }
                    }
                    frames.set(maxIdx, page);       // swap page with max distance, which means that frame will not be used for the longest period  
                    this.diskWriteCnt++;            // write victim frame back to disk
                    this.interruptCnt++;            // disk write interrupt
                }else{
                    frames.add(page);               // add new frame
                }
            }
        }
        return new Data(this.frameSize, this.pageFaultCnt, this.diskWriteCnt, this.interruptCnt);
    }

    private int getDis(List<Integer> sequence, int curFrameIndex, int curSequenceIndex){
        int curFrame = this.frames.get(curFrameIndex);
        for(int i = curSequenceIndex + 1; i < sequence.size(); i++){
            if(curFrame == sequence.get(i)){
                return i - curSequenceIndex;                            // distance between selected frame and current page
            }
        }
        return Integer.MAX_VALUE;                                          // if not occur again return the MAX_VALUE, which means it has the highest weight.
    }
}

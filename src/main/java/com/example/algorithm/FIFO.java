package com.example.algorithm;

import java.util.LinkedList;
import java.util.Queue;

import com.example.model.ReferenceString;
import com.example.result.Data;

// FIFO algorithm
public class FIFO extends Algorithm{
    
    private Queue<Integer> frames; // use queue to implement FIFO

    public FIFO(String name){ 
        super(name);
    }

    @Override
    public void reset(int frameSize){
        super.reset(frameSize);
        this.frames = new LinkedList<>();
    }

    @Override
    public Data execute(ReferenceString refs){
        for (int page : refs.getSequence()) {        
            if (!frames.contains(page)) {                   // page is not in memory
                this.interruptCnt++;                        // page fault interrupt
                this.pageFaultCnt++;                        // update page fault counter
                if (frames.size() >= this.frameSize) {      // if memory is full of frames
                    frames.poll();                          // remove a victim frame 
                    this.diskWriteCnt++;                    // write victim frame back to disk
                    this.interruptCnt++;                    // disk write interrupt 
                }
                frames.add(page);                           // add new page into memory
            }
        }
        return new Data(this.frameSize, this.pageFaultCnt, this.diskWriteCnt, this.interruptCnt);
    }
}

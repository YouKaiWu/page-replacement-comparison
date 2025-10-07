package com.example.algorithm;



import com.example.model.ReferenceString;
import com.example.result.Data;

// abstract Algorithm class
public abstract class Algorithm{
    protected int frameSize;  
    protected int pageFaultCnt;
    protected int interruptCnt;
    protected int diskWriteCnt;
    protected String name;

    public Algorithm(String name) {
        this.name = name;
    }
    
    // reset counter and framesize
    public void reset(int frameSize) {
        this.pageFaultCnt = 0;
        this.interruptCnt = 0;
        this.diskWriteCnt = 0;
        this.frameSize = frameSize;
    }
    
    // every algorithm must override "execute" this abstract method
    public abstract Data execute(ReferenceString refs);

    public String getName(){
        return this.name;
    }

    public int getPageFaultCnt() {
        return this.pageFaultCnt;
    }

    public int getInterruptCnt(){
        return this.interruptCnt;
    }   

    public int getDiskWriteCnt(){
        return this.diskWriteCnt;
    }

    //  print out statistics
    public void report() {
        System.out.println("Page Faults: " + this.pageFaultCnt);
        System.out.println("Disk Writes: " + this.diskWriteCnt);
        System.out.println("Interrupts: " + this.interruptCnt);
        System.out.println("==============================");
    }
}


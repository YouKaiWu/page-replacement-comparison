package com.example.result;

public class Data {
    // statistics 
    private final int frameSize;        
    private final int pageFaults;       
    private final int diskWrites;       
    private final int interrupts;       

    public Data(Data data){
        this.frameSize = data.frameSize;
        this.pageFaults = data.pageFaults;
        this.diskWrites = data.diskWrites;
        this.interrupts = data.interrupts;
    }

    public Data(int frameSize, int pageFaults, int diskWrites, int interrupts) {
        this.frameSize = frameSize;
        this.pageFaults = pageFaults;
        this.diskWrites = diskWrites;
        this.interrupts = interrupts;
    }

    public int getFrameSize() {
        return this.frameSize;
    }

    public int getPageFaults() {
        return this.pageFaults;
    }

    public int getDiskWrites() {
        return this.diskWrites;
    }

    public int getInterrupts() {
        return this.interrupts;
    }
}

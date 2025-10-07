package com.example.runner;

import java.util.ArrayList;
import java.util.List;

import com.example.algorithm.Algorithm;
import com.example.generator.ReferenceStringGenerator;
import com.example.result.Data;
import com.example.result.Result;

public class AlgorithmRunner {
    private final List<ReferenceStringGenerator> generators;
    private final List<Algorithm> algorithms;
    private final int[] frameSizes = {30, 60, 90, 120, 150}; // different frame size
    // private final int[] frameSizes = {3,4,5};

    public AlgorithmRunner(List<ReferenceStringGenerator> generators, List<Algorithm> algorithms) {
        this.generators = generators;
        this.algorithms = algorithms;
    }

    public ArrayList<Result> runAll() {
        ArrayList<Result> allResults = new ArrayList<>();
        for (ReferenceStringGenerator generator : generators) {
            var refs = generator.generate(1, 1500, 300000); // minPageNumber = 1, maxPageNumber = 1500, length = 300000
            System.out.println("Generator: " + generator.getName());
            // System.out.println("Reference String sample: " + refs);

            for (Algorithm algo : algorithms) {
                System.out.println("\n=== Algorithm: " + algo.getName() + " ===");
                for (int frameSize : frameSizes) {
                    System.out.println("\n=== framesize: " + frameSize + " ===");
                    algo.reset(frameSize);              // reset counter and framesize
                    Data data = algo.execute(refs);
                    Result result = new Result(algo.getName(), generator.getName(), data);
                    allResults.add(result);             // collect result
                }   
            }
        }
        return allResults;
    }
}

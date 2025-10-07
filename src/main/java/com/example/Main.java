package com.example;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.algorithm.ARB;
import com.example.algorithm.Algorithm;
import com.example.algorithm.Custom;
import com.example.algorithm.FIFO;
import com.example.algorithm.Optimal;
import com.example.generator.CustomGenerator;
import com.example.generator.LocalityGenerator;
import com.example.generator.RandomGenerator;
import com.example.generator.ReferenceStringGenerator;
import com.example.result.ResultExporter;
import com.example.runner.AlgorithmRunner;

public class Main {
    public static void main(String[] args) {
        // build different types of generators
        List<ReferenceStringGenerator> generators = List.of(
            // new TestGenerator("Test")
            new RandomGenerator("Random"), 
            new LocalityGenerator("Locality"),
            new CustomGenerator("Custom")
        );

        // build different types of algorithm
        List<Algorithm> algorithms = List.of(
            new FIFO("FIFO"),
            new ARB("Additional-Reference-Bits"),
            new Optimal("Optimal"),
            new Custom("Custom")
        );

        // build runner and run it
        AlgorithmRunner runner = new AlgorithmRunner(generators, algorithms);
        
        // build result exporter, and save the result into result.excel
        ResultExporter resultReporter = new ResultExporter();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String now = LocalDateTime.now().format(dtf);
        new java.io.File("result").mkdirs();
        String fileOutputPath = Paths.get("result", "result_" + now + ".xlsx").toString();
        try {
            resultReporter.exportToExcel(runner.runAll(), fileOutputPath);
        } catch (IOException e) {
        }
    }
}

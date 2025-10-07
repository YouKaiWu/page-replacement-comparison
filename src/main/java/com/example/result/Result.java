package com.example.result;

public class Result {
    private final String algorithmName;      
    private final String generatorName;
    private final Data data;

    public Result(String algorithmName, String generatorName, Data data) {
        this.algorithmName = algorithmName;
        this.generatorName = generatorName;
        this.data = new Data(data);
    }

    public String getAlgorithmName() {
        return this.algorithmName;
    }
    
    public String getGeneratorName() {
       return this.generatorName;
    }

    public Data getData(){
        return this.data;
    }
}

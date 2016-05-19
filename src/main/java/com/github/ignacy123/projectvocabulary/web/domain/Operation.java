package com.github.ignacy123.projectvocabulary.web.domain;

/**
 * Created by ignacy on 14.04.16.
 */
public class Operation {
    private Double numberOne;
    private Double numberTwo;
    private String operation;

    public Double getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(Double numberOne) {
        this.numberOne = numberOne;
    }

    public Double getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(Double numberTwo) {
        this.numberTwo = numberTwo;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}

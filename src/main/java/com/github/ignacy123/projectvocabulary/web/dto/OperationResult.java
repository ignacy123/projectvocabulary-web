package com.github.ignacy123.projectvocabulary.web.dto;

/**
 * Created by ignacy on 14.04.16.
 */
public class OperationResult {
    private String description;
    private Double result;
    private Double numberOne;
    private Double numberTwo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Double getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(Double numberOne) {
        this.numberOne = numberOne;
    }
}

package com.github.ignacy123.projectvocabulary.web.domain;

/**
 * Created by ignacy on 21.04.16.
 */
public class Expression {
    private Expression expressionOne;
    private Expression expressionTwo;
    private Double value;
    private Operation operation;

    public Expression getExpressionOne() {
        return expressionOne;
    }

    public void setExpressionOne(Expression expressionOne) {
        this.expressionOne = expressionOne;
    }

    public Expression getExpressionTwo() {
        return expressionTwo;
    }

    public void setExpressionTwo(Expression expressionTwo) {
        this.expressionTwo = expressionTwo;
    }

    public String getOperation() {
        return String.valueOf(operation);
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double compute() {
        if (operation == null) {
            return value;
        } else {
            double valueOne = expressionOne.compute();
            double valueTwo = expressionTwo.compute();
            switch (operation.getOperation()) {
                case "/":
                    return valueOne / valueTwo;
                case "+":
                    return valueOne + valueTwo;
                case "-":
                    return valueOne - valueTwo;
                case "*":
                    return valueOne * valueTwo;
                default:
            }
        }
        return null;
    }
}
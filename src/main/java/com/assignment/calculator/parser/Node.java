package com.assignment.calculator.parser;

public class Node {
    private Type type;
    private Node leftChild;
    private Node middleChild;
    private Node rightChild;
    private Integer numberValue;

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Integer numberValue) {
        this.numberValue = numberValue;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    private String variableName;

    public Node() {
        this.type = null;
        this.leftChild = null;
        this.middleChild = null;
        this.rightChild = null;
    }

    public Node(Type type) {
        this.type = type;
        this.leftChild = null;
        this.middleChild = null;
        this.rightChild = null;
    }

    public Node getMiddleChild() {
        return middleChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setMiddleChild(Node middleChild) {
        this.middleChild = middleChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Type getType() {
        return type;
    }
}

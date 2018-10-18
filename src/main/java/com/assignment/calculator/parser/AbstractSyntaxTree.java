package com.assignment.calculator.parser;

/**
 * Tree implementation with one root node.
 */
public class AbstractSyntaxTree {
    private Node root;

    public AbstractSyntaxTree(Type type) {
        root = new Node(type);
    }

    /**
     *
     * @param currentNode Node to append new node to
     * @param newNode Newly created node to add as the child of an existing node.
     * @return currentNode with a newly created child node. The only time a middle child is set is for a let expression.
     */
    public Node addChildNodeToCurrentNode(Node currentNode, Node newNode) {
        if (currentNode != null && newNode != null) {
            if (currentNode.getLeftChild() == null) {
                currentNode.setLeftChild(newNode);
            } else if (Type.let == currentNode.getType() && currentNode.getMiddleChild() == null) {
                currentNode.setMiddleChild(newNode);
            } else if (currentNode.getRightChild() == null) {
                currentNode.setRightChild(newNode);
            }
        }
        return newNode;
    }

    public Node getRoot() {
        return root;
    }
}

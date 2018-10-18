package com.assignment.calculator.parser;

/**
 * Represents all the potential types of tokens that can be parsed from the input expression.
 */
public enum Type {
    add,
    sub,
    mul,
    div,
    let,
    var,
    digit,
    comma,
    lparenth,
    rparenth,
    invalid
}

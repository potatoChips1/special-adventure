package com.assignment.calculator;

import com.assignment.calculator.parser.exceptions.CalculatorExpressionParserException;
import java.util.Scanner;

public class Calculator {

    public static void main(String args[]) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter an expression to be calculated followed by enter: ");
            while(scanner.hasNextLine()) {
                String inputExpression = scanner.nextLine();
                Calculate calculate = new Calculate();
                System.out.println("Input expression: " + inputExpression);
                try {
                    System.out.println("Answer is: " + calculate.performCalculation(inputExpression));
                    System.out.println();
                } catch(CalculatorExpressionParserException cep) {
                    System.out.println(cep.getMessage());
                } catch (ArithmeticException ae) {
                    System.out.println(ae.getMessage());
                }
            }
            scanner.close();
    }
}

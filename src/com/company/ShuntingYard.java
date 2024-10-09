package com.company;

import java.lang.reflect.Array;
import java.util.*;

public class ShuntingYard {


    public static String infixToPostFix(String expression) {
        Stack<String> stack = new Stack();
        Queue<String> queue = new LinkedList<>();
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(expression.split(" ")));

        for(int i=0; i<tokens.size(); i++) {

            String charString = tokens.get(i);
            if(isNumber(charString)) { // number things
                queue.add(charString);
            } else if(isOperator(charString)) { // operator things
                while(stack.size() != 0 && isOperator(stack.peek()) && hasPriority(stack.peek(), charString)){
                    queue.add(stack.pop());
                }
                stack.push(charString);
            } else if(isLeftParenthesis(charString)) {
                stack.push(charString);
            } else if (isRightParenthesis(charString)) {
                while(!isLeftParenthesis(stack.peek())) {
                    queue.add(stack.pop());
                }
                stack.pop();
            } else {
                System.out.println("None of the above");
            }
        }

        while(stack.size() != 0) {
            queue.add(stack.pop());
        }
        System.out.println(queue);
        System.out.println(stack);
        String toReturn = "";
        while(queue.size() != 0) {
            toReturn += queue.remove();
        }
        return toReturn;
    }

    public static String postFixToInfix(String expression) {
        Stack<String> stack = new Stack();
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(expression.split(" ")));

        for(int i=0; i<tokens.size(); i++) {
            if (isNumber(tokens.get(i))) {
                stack.push(tokens.get(i));
            } else if (isOperator(tokens.get(i))) {
                String token2 = stack.pop();
                String token1 = stack.pop();
                String newString = "(" + token1 + tokens.get(i) + token2 + ")";
                stack.push(newString);
            }

        }

        return stack.pop();
    }

    public static boolean isValidExpression(String expressionToCheck) {


        return false;
    }

    public static double evaluatePostFix(String expression) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(expression.split(" ")));
        Stack<String> stack = new Stack<>();

        for(int i=0; i<tokens.size(); i++) {
            if (isNumber(tokens.get(i))) {
                stack.push(tokens.get(i));
            } else if (isOperator(tokens.get(i))) {
                double token2 = Double.parseDouble(stack.pop());
                double token1 = Double.parseDouble(stack.pop());
                if(tokens.get(i).equals("/")) {
                    stack.push(Double.toString(token1/token2));
                } else if (tokens.get(i).equals("*")) {
                    stack.push(Double.toString(token1*token2));
                } else if (tokens.get(i).equals("+")) {
                    stack.push(Double.toString(token1+token2));
                } else if (tokens.get(i).equals("-")) {
                    stack.push(Double.toString(token1-token2));
                }
            }

        }

        return Double.parseDouble(stack.pop());
    }

    public static boolean isOperator(String token) {
        ArrayList<String> operators = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
        return operators.contains(token);

    }

    public static boolean isNumber(String token) {
        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        return numbers.contains(token.substring(0, 1));

    }

    public static boolean isLeftParenthesis(String token) {
        return token.equals("(");
    }

    public static boolean isRightParenthesis(String token) {
        return token.equals(")");
    }

    public static boolean hasPriority(String token1, String token2) {
        ArrayList<String> operators = new ArrayList<>(Arrays.asList("*", "/", "+", "-"));
        int index1 = operators.indexOf(token1);
        int index2 = operators.indexOf(token2);
        if(index1 <= index2 || (index2 == 0 && index1 == 1) || (index2 == 2 && index1 == 3)) {
            return true;
        } else {
            return false;
        }
    }
}

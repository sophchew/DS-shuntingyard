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
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(expressionToCheck.split(" ")));
        boolean foundLeft = false;
        int lefts = 0;
        int rights = 0;

        if(!(isNumber(tokens.get(0)) || isLeftParenthesis(tokens.get(0))) || !(isNumber(tokens.get(tokens.size()-1)) || isRightParenthesis(tokens.get(tokens.size()-1)))) {
            System.out.println("1");
            return false;
        }

        if(isLeftParenthesis(tokens.get(0))) {
            lefts++;
            foundLeft = true;
        }

        for(int i = 1; i<tokens.size()-1; i++) {
           if (isNumber(tokens.get(i))) {
               if(!((isOperator(tokens.get(i-1)) || isParenthesis(tokens.get(i-1))) && (isOperator(tokens.get(i+1)) || isParenthesis(tokens.get(i+1))))) {
                   System.out.println("2");
                   return false;
               }
           } else if (isOperator(tokens.get(i))) {
               if(!((isRightParenthesis(tokens.get(i-1)) || isNumber(tokens.get(i-1))) && (isLeftParenthesis(tokens.get(i+1)) || isNumber(tokens.get(i+1))))) {
                   System.out.println("3");
                   return false;
               }
           } else if (isLeftParenthesis(tokens.get(i))) {
               foundLeft = true;
               lefts++;
               if(!((isOperator(tokens.get(i-1)) || isLeftParenthesis(tokens.get(i-1))) && (isNumber(tokens.get(i+1)) || isLeftParenthesis(tokens.get(i+1))))) {
                   System.out.println("4");
                   return false;
               }
           } else if (isRightParenthesis(tokens.get(i))) {
               if(foundLeft) { // matching pair
                   foundLeft = false;
                   rights++;
                   if(!((isNumber(tokens.get(i-1)) || isRightParenthesis(tokens.get(i-1))) && (isOperator(tokens.get(i+1)) || isRightParenthesis(tokens.get(i+1))))) {
                       System.out.println("5");
                       return false;
                   }
                   if(rights<lefts) {
                       foundLeft = true;
                   }
               } else {
                   System.out.println("6");
                   return false;
               }
           } else {
               System.out.println("7");
               return false;
           }
        }

        // check end token for parenthesis
        if(isRightParenthesis(tokens.get(tokens.size()-1))) {
            if(foundLeft) {
                foundLeft = false;
                rights++;
            } else {
                System.out.println("8");
                return false;
            }
        }

        if (foundLeft) { // no matching right
            System.out.println("9");
            return false;
        } else if(lefts != rights) {
            System.out.println("10");
            return false;
        } else {
            return true;
        }

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

        if(token == "") {
            return false;
        }

        return numbers.contains(token.substring(0, 1));

    }

    public static boolean isLeftParenthesis(String token) {

        if(token == "") {
            return false;
        }

        return token.equals("(");
    }

    public static boolean isRightParenthesis(String token) {
        if(token == "") {
            return false;
        }
        return token.equals(")");
    }

    public static boolean isParenthesis(String token) {
        if(token == "") {
            return false;
        }
        return isLeftParenthesis(token) || isRightParenthesis(token);
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

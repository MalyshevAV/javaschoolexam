package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.*;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        String result = null;
        List<String> postfix = null;
        if (statement != null) {
            postfix = convertToPostfix(statement);
            if (postfix != null) {
                Double number = evaluatePostfix(postfix);
                if (number != null) {
                    result = new DecimalFormat("#.####").format(number);
                }
            }
        }
        System.out.println("statement = " + statement + " postfix = " + postfix + " result = " + result);
        return result;
    }

    private List<String> convertToPostfix(String statement) {
        Deque<Character> stack = new ArrayDeque<>();
        List<String> postfix = new ArrayList<>();
        boolean digit = false;
        char prev = 0;
        for (int i = 0; i < statement.length(); i++) {
            char ch = statement.charAt(i);
            if (ch == ' ' || ch == ',' || (isOperator(ch) && isOperator(prev))) {
                return null;
            }
            if (ch == '(') {
                stack.push(ch);
                digit = false;
            }
            else if (ch == ')') {
                digit = false;
                while (!stack.isEmpty()) {
                    if (stack.peek() == '(') {
                        stack.pop();
                        break;
                    }
                    else {
                        postfix.add(String.valueOf(stack.pop()));
                    }
                }
            }
            else if (isOperator(ch)) {
                digit = false;
                if (stack.isEmpty()) {
                    stack.push(ch);
                }
                else {
                    while (!stack.isEmpty() &&
                            getOperatorPriority(stack.peek()) >= getOperatorPriority(ch)) {
                        postfix.add(String.valueOf(stack.pop()));
                    }
                    stack.push(ch);
                }
            }
            else {
                if (digit) {
                    String number = postfix.get(postfix.size() - 1);
                    number += ch;
                    postfix.set(postfix.size() - 1, number);
                }
                else {
                    postfix.add(String.valueOf(ch));
                }
                digit = true;
            }
            prev = ch;
        }
        while (!stack.isEmpty()) {
            postfix.add(String.valueOf(stack.pop()));
        }
        return postfix;
    }

    private int getOperatorPriority(char ch) {
        if (ch == '+' || ch == '-') {
            return 1;
        }
        else if (ch == '*' || ch == '/') {
            return 2;
        }
        return -1;
    }

    private boolean isOperator(char ch) {
        return getOperatorPriority(ch) > 0;
    }

    private Double evaluatePostfix(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String token : postfix) {
            char ch = token.charAt(0);
            if (token.length() == 1 && isOperator(ch)) {
                double right = stack.pop();
                double left = stack.pop();
                if (ch == '+') {
                    stack.push(left + right);
                }
                else if (ch == '-') {
                    stack.push(left - right);
                }
                else if (ch == '*') {
                    stack.push(left * right);
                }
                else if (ch == '/') {
                    if (right == 0) return null;
                    stack.push(left / right);
                }
            }
            else {
                try {
                    stack.push(Double.parseDouble(token));
                }
                catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return stack.peek();
    }
}

package com.folautech.assignment.valid_parentheses;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 
 * Time Complexity: O(N), Iteration over the string of size N one time.<br>
 * Auxiliary Space: O(N) for stack.
 *
 */
public class ValidParentheses {

  public static void main(String[] args) {

    List<String> strs = Arrays.asList("{{}}()[()]", "(][)", ")");

    for (String str : strs) {
      boolean valid = containValidParentheses(str);
      System.out.println("str: " + str + " -> valid: " + valid);
      System.out.println("\n");
    }
  }


  public static boolean containValidParentheses(String str) {

    /**
     * Use stack so you can easily check the top for the corresponding open parentheses once finding
     * a closing parentheses.
     */
    Stack<Character> stack = new Stack<>();

    for (char ch : str.toCharArray()) {

      /**
       * look for closing, and add all open characters to your stack
       */
      if (ch == '(' || ch == '{' || ch == '[') {
        stack.push(ch);
      } else {

        /**
         * no opening character to match with closing
         */
        if (stack.isEmpty()) {
          return false;
        }

        /**
         * Once you find a closing then, peek at the top of the stack to see if you have the
         * corresponding closing character, if true, pop the stack to remove character. if false,
         * then you have an invalid pair of parentheses and so return false
         */
        char top = stack.peek();

        if ((ch == ')' && top == '(') || (ch == '}' && top == '{') || (ch == ']' && top == '[')) {
          stack.pop();
        } else {
          return false;
        }

      }
    }

    /**
     * if stack is not empty, which means there might be some open characters without corresponding
     * closing characters so you have an invalid pair of parentheses and so return false
     */
    if (!stack.isEmpty()) {
      return false;
    }

    return true;
  }

}

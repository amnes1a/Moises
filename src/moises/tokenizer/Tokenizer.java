/*
 * Copyright (C) 2016 Yamil Elías <yamileliassoto@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package moises.tokenizer;

import java.util.List;
import java.util.ArrayList;

/**
 * In this class is tokenized each part of the code, so the parser can use it
 * later depending on the labels.
 */
public class Tokenizer {

    /**
     * This method will help the tokenizer to identify Operators in the code.
     * @param chr
     * @return 
     */
    public boolean isAnOperator(char chr) {
        boolean addOp = (chr == '+' || chr == '-');
        boolean compOp = (chr == '<' || chr == '>' || chr == '=');
        boolean lgicOp = (chr == '!' || chr == '|' || chr == '&');
        boolean mulOp = (chr == '*' || chr == '/' || chr == '%');
        boolean powOp = (chr == '^');
        return addOp || mulOp || compOp || lgicOp || powOp;
    }

    /**
     * Set a label for the operator type.
     * @param firstOperator
     * @param nextChar
     * @return 
     */
    public TokenType findOperatorType(char firstOperator, char nextChar) {
        TokenType type = TokenType.UNKNOWN;
        switch (firstOperator) {
            case '+':
                type = TokenType.ADD;
                break;
            case '-':
                type = TokenType.SUBTRACT;
                break;
            case '*':
                type = TokenType.MULTIPLY;
                break;
            case '/':
                type = TokenType.DIVIDE;
                break;
            case '^':
                type = TokenType.POW;
                break;
            case '%':
                type = TokenType.MOD;
                break;
            case '<':
                type = TokenType.LESS;
                if (nextChar == '=')
                    type = TokenType.LESSEQUAL;
                break;
            case '>':
                type = TokenType.GREATER;
                if (nextChar == '=')
                    type = TokenType.GREATEREQUAL;
                break;
            case '=':
                type = TokenType.ASSIGNMENT;
                if (nextChar == '=')
                    type = TokenType.EQUAL;
                break;
            case '!':
                type = TokenType.NOT;
                if (nextChar == '=')
                    type = TokenType.NOTEQUAL;
                break;
            case '|':
                type = TokenType.OR;
                break;
            case '&':
                type = TokenType.AND;
                break;
        }
        return type;
    }

    /**
     * Get start of parenthesis or brackets.
     * @param chr
     * @return 
     */
    public boolean isParen(char chr) {
        boolean prntOp = (chr == '(' || chr == ')');
        boolean brktOp = (chr == '[' || chr == ']');
        boolean puncOp = (chr == ',');
        return prntOp || brktOp || puncOp;
    }

    /**
     * Manage Punctuation.
     * @param chr
     * @return 
     */
    public boolean IsPunc(char chr) {
        boolean puncOp = chr == ',';
        return puncOp;
    }

    /**
     * Find type of punctuation used in the code.
     * @param firstOperator
     * @return 
     */
    public TokenType FindPuncType(char firstOperator) {
        TokenType type = TokenType.UNKNOWN;
        switch (firstOperator) {
            case ',':
                type = TokenType.COMMA;
                break;
        }
        return type;
    }

    /**
     * Get the type of the parenthesis used on the code, if is open or closed.
     * @param chr
     * @return 
     */
    public TokenType FindParenType(char chr) {
        TokenType type = TokenType.UNKNOWN;
        switch (chr) {
            case '(':
                type = TokenType.LEFT_PAREN;
                break;
            case ')':
                type = TokenType.RIGHT_PAREN;
                break;
            case '[':
                type = TokenType.LEFT_BRACKET;
                break;
            case ']':
                type = TokenType.RIGHT_BRACKET;
                break;
            case ',':
                type = TokenType.COMMA;
                break;
        }
        return type;
    }


    /**
     * This function takes a script as a string of characters and chunks it into
     * a sequence of tokens. Each token is a meaningful unit of program, like a
     * variable name, a number, a string, or an operator.
     * @param source
     * @return 
     */
    public List < Token > Tokenize(String source) {
        List < Token > tokens = new ArrayList<>();
        Token token = null;
        String tokenText = "";
        char firstOperator = '\0';
        TokenizeState state = TokenizeState.DEFAULT;

        // Scan through the code one character at a time, building up the list
        // of tokens.
        for (int i = 0; i < source.length(); i++) {
            char chr = source.charAt(i);

            switch (state) {

                case DEFAULT:
                    if (isAnOperator(chr)) {
                        firstOperator = chr;
                        TokenType opType = findOperatorType(firstOperator, '\0');
                        token = new Token(Character.toString(chr), opType);
                        state = TokenizeState.OPERATOR;
                    } else if (isParen(chr)) {
                        TokenType parenType = FindParenType(chr);
                        tokens.add(new Token(Character.toString(chr), parenType)); 
                    } else if (Character.isDigit(chr)){
                        tokenText += chr;
                        state = TokenizeState.NUMBER;
                    } else if (Character.isLetter(chr)) {
                        tokenText += chr;
                        state = TokenizeState.KEYWORD;
                    }else if (chr == '"') {
                        state = TokenizeState.STRING;
                    } else if (chr == '#') {
                        state = TokenizeState.COMMENT;
                    }else if (IsPunc(chr)) {
                        TokenType puncType = FindPuncType(chr);
                        tokens.add(new Token(Character.toString(chr), puncType)); 
                    }

                    break;

                case NUMBER:
                    if (Character.isDigit(chr) || chr == '.') {
                        tokenText += chr;
                    } else {
                        tokens.add(new Token(tokenText, TokenType.NUMBER));
                        tokenText = "";
                        state = TokenizeState.DEFAULT;
                        i--; // Reprocess this character in the default state.
                    }
                    break;


                case KEYWORD:
                    if (Character.isLetterOrDigit(chr)) {
                        tokenText += chr;
                    } else{
                        TokenType type = FindStatementType(tokenText);
                        if (type == TokenType.UNKNOWN)
                            type = FindConstantType(tokenText);
                        tokens.add(new Token(tokenText, type));
                        tokenText = "";
                        state = TokenizeState.DEFAULT;
                        i--; // Reprocess this character in the default state.
                    }
                    break;   

                case OPERATOR:
                    if (isAnOperator(chr)) {
                        TokenType opType = findOperatorType(firstOperator, chr);
                        token = new Token(Character.toString(firstOperator) + Character.toString(chr), opType);
                    } else {
                        tokens.add(token);
                        state = TokenizeState.DEFAULT;
                        i--; // Reprocess this character in the default state.
                    }
                    break;

                case STRING:
                    if (chr == '"' ) {
                        tokens.add(new Token(tokenText, TokenType.STRING));
                        tokenText = "";
                        state = TokenizeState.DEFAULT;
                    } else {
                        tokenText += chr;
                    }
                    break;

                case COMMENT:
                    if (chr == '\n')
                        state = TokenizeState.DEFAULT;
                    break;
            }
        }
        return tokens;
    }

    /**
     * Get the label of the token, different for each statement.
     * @param str
     * @return 
     */
    public TokenType FindStatementType(String str) {
        TokenType type = TokenType.UNKNOWN;
        switch (str) {
            case "end":
                type = TokenType.END;
                break;
            case "script":
                type = TokenType.SCRIPT;
                break;
            case "while":
                type = TokenType.WHILE;
            break;
            case "if":
                type = TokenType.IF;
            break;
            case "else":
                type = TokenType.ELSE;
            break;
            case "func":
                type = TokenType.DEF;
            break;
        }
        return type;
    }

    /**
     * This will generate the constant labels used to identify them on code.
     * @param str
     * @return 
     */
    public TokenType FindConstantType(String str){
        TokenType type;
        switch (str){
            case "PI":
                type = TokenType.PI;
                break;
            case "EULER":
                type = TokenType.EULER;
                break;
            default:
                type = TokenType.KEYWORD;
        }
        return type;
    }
}

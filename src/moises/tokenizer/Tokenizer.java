package moises.tokenizer;

import java.util.List;
import java.util.ArrayList;

public class Tokenizer {

    public boolean isAnOperator(char chr) {
        boolean addOp = (chr == '+' || chr == '-');
        boolean compOp = (chr == '<' || chr == '>' || chr == '=');
        boolean lgicOp = (chr == '!' || chr == '|' || chr == '&');
        boolean mulOp = (chr == '*' || chr == '/' || chr == '%');
        boolean powOp = (chr == '^');
        return addOp || mulOp || compOp || lgicOp || powOp;
    }

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

    public boolean isParen(char chr) {
        boolean prntOp = (chr == '(' || chr == ')');
        boolean brktOp = (chr == '[' || chr == ']');
        boolean puncOp = (chr == ',');
        return prntOp || brktOp || puncOp;
    }

    public boolean IsPunc(char chr) {
        boolean puncOp = chr == ',';
        return puncOp;
    }

    public TokenType FindPuncType(char firstOperator) {
        TokenType type = TokenType.UNKNOWN;
        switch (firstOperator) {
            case ',':
                type = TokenType.COMMA;
                break;
        }
        return type;
    }

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


    public List < Token > Tokenize(String source) {
        List < Token > tokens = new ArrayList<>();
        Token token = null;
        String tokenText = "";
        char firstOperator = '\0';
        TokenizeState state = TokenizeState.DEFAULT;

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
                        i--;
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
                        i--;
                    }
                    break;   

                case OPERATOR:
                    if (isAnOperator(chr)) {
                        TokenType opType = findOperatorType(firstOperator, chr);
                        token = new Token(Character.toString(firstOperator) + Character.toString(chr), opType);
                    } else {
                        tokens.add(token);
                        state = TokenizeState.DEFAULT;
                        i--;
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

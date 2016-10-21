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

/**
 * This defines the different kinds of tokens or meaningful chunks of code
 * that the parser knows how to consume. These let us distinguish, for
 * example, between a string "foo" and a variable named "foo".
 */
public enum TokenType {
    NUMBER,
    EOF,
    UNKNOWN,
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    MOD,
    LEFT_PAREN,
    RIGHT_PAREN,
    LESS,
    GREATER,
    EQUAL,
    NOTEQUAL,
    LESSEQUAL,
    GREATEREQUAL,
    ASSIGNMENT,
    NOT,
    OR,
    AND,
    KEYWORD,
    END,
    SCRIPT,
    STRING,
    WHILE,
    IF,
    ELSE,
    PI,
    EULER,
    DEF,
    COMMA,
    LEFT_BRACKET,
    RIGHT_BRACKET
}
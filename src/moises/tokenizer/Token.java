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
 * This is a single meaningful chunk of code. It is created by the tokenizer
 * and consumed by the parser.
 * @author Yamil Elías <yamileliassoto@gmail.com>
 */
public class Token {
    /**
     * This is the content of the token, depending of what is assigned
     */
    public String text;
    
    /**
     * Type of the Token
     * @see TokenType
     */
    public TokenType type;

    /**
     * Token Constructor with a text and a TokenType initial values.
     * @param text
     * @param type 
     */
    public Token(String text, TokenType type) {
        this.text = text;
        this.type = type;
    }

    /**
     * 
     * @return 
     */
    public TokenType getType(){
    	return type;
    }
}

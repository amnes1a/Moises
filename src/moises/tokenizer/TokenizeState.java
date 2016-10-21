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
 * This defines the different states the tokenizer can be in while it's
 * scanning through the source code. Tokenizers are state machines, which
 * means the only data they need to store is where they are in the source
 * code and this one "state" or mode value.
 * 
 * One of the main differences between tokenizing and parsing is this
 * regularity. Because the tokenizer stores only this one state value, it
 * can't handle nesting (which would require also storing a number to
 * identify how deeply nested you are). The parser is able to handle that.
 * 
 */
enum TokenizeState {
    DEFAULT,
    OPERATOR,
    NUMBER,
    KEYWORD,
    STRING,
    COMMENT
}

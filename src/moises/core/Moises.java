/*
 * Copyright (C) 2016 Moises Language
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
package moises.core;

import moises.node.Node;
import moises.tokenizer.Token;
import moises.tokenizer.Tokenizer;
import moises.util.Util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

public class Moises {

    /**
     * 
     * @param args 
     */
    public static void main(String args[]) {
        boolean debug = false;
        String filename = args[0];
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        
        if(!extension.equals("moi")){
            Util.Writeln("Please use a valid Moises file with extension .moi");
            return;
        }
        if (args.length < 1) {
            Util.Writeln("Usage: Demo <script>");
            return;
        }
        if (args.length > 1) {
            if (args[1].equals("debug")) debug = true;
        }
        Moises interpreter = new Moises();
        String sourceCode = interpreter.ReadFile(args[0]);
        interpreter.Interpret(sourceCode, debug);
    }

    /**
     * Code Interpreter of the Moises file.
     * @param source
     * @param debug 
     */
    public void Interpret(String source, boolean debug) {
        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser(tokenizer.Tokenize(source));
        if (debug) 
            DumpTokens(parser);
        Node program = parser.Program();
        program.eval();
    }
    
    /**
     * This method is to show up the tokens.
     * @param parser 
     */
    public void DumpTokens(Parser parser) {
        System.out.println(":::::::::::::: TOKENS ::::::::::::::");
        for (Token token: parser.getTokens())
            Util.Writeln(token.type + " (" + token.text + ")");
        System.out.println("::::::::::::::::::::::::::::::::::::\n");
    }

    /**
     * File reader for user input.
     * @param path
     * @return 
     */
    private String ReadFile(String path) {
        FileInputStream stream = null;
        InputStreamReader input = null;
        try {
            stream = new FileInputStream(path);
            input = new InputStreamReader(stream, Charset.defaultCharset());
            Reader reader = new BufferedReader(input);
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            builder.append(" ");
            return builder.toString();
        } catch (FileNotFoundException e) {
            String errMsg = "FILE NOT FOUND. ";
            String sourceInfo = "Error in Moises.java->" +
                "ReadFile(String path) method. ";
            Util.Writeln(sourceInfo + errMsg);
            System.exit(0);
        } catch (IOException e) {
            String errMsg = "Error while reading the script. ";
            String sourceInfo = "Error in Moises.java->" +
                "ReadFile(String path) method. ";
            Util.Writeln(sourceInfo + errMsg);
            System.exit(0);
        } catch (Exception e) {
            String errMsg = "Error while reading the script. ";
            String sourceInfo = "Error in Moises.java->" +
                "ReadFile(String path) method. ";
            Util.Writeln(sourceInfo + errMsg + e);
            System.exit(0);
        } finally {
            try {
                input.close();
                stream.close();
            } catch (Exception e) {
                String errMsg = "Error while closing a stream or a stream reader. ";
                String sourceInfo = "Error in Moises.java->" +
                    "ReadFile(String path) method. ";
                Util.Writeln(sourceInfo + errMsg + e);
                System.exit(0);
            }
        }
        return null;
    }
}
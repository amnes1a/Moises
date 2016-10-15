package moises;

import moises.nodes.Node;
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

public class Interpreter {

    public static void main(String args[]) {
        boolean debug = false;
        if (args.length < 1) {
            Util.Writeln("Usage: Demo <script>");
            return;
        }
        if (args.length > 1) {
            if (args[1].equals("debug")) debug = true;
        }
        Interpreter interpreter = new Interpreter();
        String sourceCode = interpreter.ReadFile(args[0]);
        interpreter.Interpret(sourceCode, debug);
    }

    public void Interpret(String source, boolean debug) {
        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser(tokenizer.Tokenize(source));
        if (debug) 
            DumpTokens(parser);
        Node program = parser.Program();
        program.eval();
    }
    
    public void DumpTokens(Parser parser) {
        for (Token token: parser.getTokens())
            Util.Writeln("Type: " + token.type + " Text: " + token.text + " ");
        Util.Writeln();
    }

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
            //Keep the space in the end
            builder.append(" ");
            return builder.toString();
        } catch (FileNotFoundException e) {
            String errMsg = "FILE NOT FOUND. ";
            String sourceInfo = "Error in Interpreter.java->" +
                "ReadFile(String path) method. ";
            Util.Writeln(sourceInfo + errMsg);
            System.exit(0);
        } catch (IOException e) {
            String errMsg = "Error while reading the script. ";
            String sourceInfo = "Error in Interpreter.java->" +
                "ReadFile(String path) method. ";
            Util.Writeln(sourceInfo + errMsg);
            System.exit(0);
        } catch (Exception e) {
            String errMsg = "Error while reading the script. ";
            String sourceInfo = "Error in Interpreter.java->" +
                "ReadFile(String path) method. ";
            Util.Writeln(sourceInfo + errMsg + e);
            System.exit(0);
        } finally {
            try {
                input.close();
                stream.close();
            } catch (Exception e) {
                String errMsg = "Error while closing a stream or a stream reader. ";
                String sourceInfo = "Error in Interpreter.java->" +
                    "ReadFile(String path) method. ";
                Util.Writeln(sourceInfo + errMsg + e);
                System.exit(0);
            }
        }
        return null;
    }
}
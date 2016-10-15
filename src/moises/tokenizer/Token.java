package moises.tokenizer;

public class Token {
    public String text;
    public TokenType type;

    public Token(String text, TokenType type) {
        this.text = text;
        this.type = type;
    }

    public String toString() {
        return "Text: " + text + " Type: " + type;
    }

    public TokenType getType(){
    	return type;
    }
}

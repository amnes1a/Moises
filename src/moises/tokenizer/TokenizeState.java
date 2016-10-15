package moises.tokenizer;

enum TokenizeState {
    DEFAULT,
    OPERATOR,
    NUMBER,
    KEYWORD,
    STRING,
    COMMENT
}

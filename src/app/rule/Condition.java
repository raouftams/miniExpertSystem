package app.rule;

public class Condition {
    public int index ;
    public String symbol ;

    public Condition(String Symbol) {
        symbol = Symbol ;
        if (Symbol.equals("=")) index = 1 ;
        else if (Symbol.equals(">")) index = 2 ;
        else if (Symbol.equals("<")) index = 3 ;
        else if (Symbol.equals("!=")) index = 4 ;
        else if (Symbol.equals("<=")) index = 5 ;
        else if (Symbol.equals(">=")) index = 6 ;
        else if (Symbol.equals("<=D")) index = 7;
        else index = -1 ;
    }

    String asString() {
        String temp = "";
        switch (index) {
            case 1: temp = "=" ;
                break;
            case 2: temp = ">" ;
                break ;
            case 3: temp = "<" ;
                break;
            case 4: temp = "!=" ;
                break;
            case 5: temp = "<=";
                break;
            case 6: temp = ">=";
                break;
            case 7:
                temp = "<=D";
        }
        return temp ;
    }
}

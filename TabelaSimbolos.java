import java.util.Hashtable;

/*
 * Carlos Augusto Alves
 */
public class TabelaSimbolos {
    private Hashtable<String, String[]> tabelaSimbolos;

    TabelaSimbolos() {
        tabelaSimbolos = new Hashtable<>();
    }

    void construirTabelaInicial() {
        tabelaSimbolos.put("end", new String[]{"end", "", ""});
        tabelaSimbolos.put("then", new String[]{"then", "", ""});
        tabelaSimbolos.put("true", new String[]{"true", "", ""});
        tabelaSimbolos.put("false", new String[]{"false", "", ""});
        tabelaSimbolos.put("boolean", new String[]{"boolean", "", ""});
        tabelaSimbolos.put("main", new String[]{"main", "", ""});
        tabelaSimbolos.put("begin", new String[]{"begin", "", ""});
        tabelaSimbolos.put("not", new String[]{"not", "", ""});
        tabelaSimbolos.put("or", new String[]{"or", "", ""});
        tabelaSimbolos.put("and", new String[]{"and", "", ""});
        tabelaSimbolos.put("while", new String[]{"while", "", ""});
        tabelaSimbolos.put("if", new String[]{"if", "", ""});
        tabelaSimbolos.put("else", new String[]{"else", "", ""});
        tabelaSimbolos.put("readln", new String[]{"readln", "", ""});
        tabelaSimbolos.put("writeln", new String[]{"writeln", "", ""});
        tabelaSimbolos.put("write", new String[]{"write", "", ""});
        tabelaSimbolos.put("string", new String[]{"string", "", ""});
        tabelaSimbolos.put("byte", new String[]{"byte", "", ""});
        tabelaSimbolos.put("integer", new String[]{"integer", "", ""});
        tabelaSimbolos.put("const", new String[]{"const", "", ""});
        tabelaSimbolos.put("EOF", new String[]{"65535", "", ""});
        tabelaSimbolos.put(";", new String[]{";", "", ""});
        tabelaSimbolos.put(",", new String[]{",", "", ""});
        tabelaSimbolos.put("(", new String[]{"(", "", ""});
        tabelaSimbolos.put(")", new String[]{")", "", ""});
        tabelaSimbolos.put("<", new String[]{"<", "", ""});
        tabelaSimbolos.put(">", new String[]{">", "", ""});
        tabelaSimbolos.put("-", new String[]{"-", "", ""});
        tabelaSimbolos.put("+", new String[]{"+", "", ""});
        tabelaSimbolos.put("*", new String[]{"*", "", ""});
        tabelaSimbolos.put("/", new String[]{"/", "", ""});
        tabelaSimbolos.put("!=", new String[]{"!=", "", ""});
        tabelaSimbolos.put(">=", new String[]{">=", "", ""});
        tabelaSimbolos.put("<=", new String[]{"<=", "", ""});
        tabelaSimbolos.put("==", new String[]{"==", "", ""});
        tabelaSimbolos.put("=", new String[]{"=", "", ""});
    }

    public String procurar(String lexema) {
        return tabelaSimbolos.get(lexema)[0];
    }

    public void inserir(String lexema, String classe, String tipo) {
        tabelaSimbolos.put("id", new String[]{lexema, classe, tipo});
    }
}

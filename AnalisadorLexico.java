import java.io.*;
import java.util.HashSet;

/*
 * Carlos Augusto Alves
 */
public class AnalisadorLexico {
    private final char EOF = 65535;

    public static final String TIPO_INTEIRO = "integer";
    public static final String TIPO_STRING = "string";
    public static final String TIPO_BYTE = "byte";
    public static final String TIPO_LOGICO = "boolean";

    public static final String CLASSE_CONST =  "const";
    public static final String CLASSE_VAR = "var";

    private LineNumberReader leitor;
    private HashSet<Character> caracteresPermitidos;
    private RegistroLexico registroLexico;
    private TabelaSimbolos tabelaSimbolos;

    AnalisadorLexico(String nomeArquivo, RegistroLexico registroLexico, TabelaSimbolos tabelaSimbolos) {
        try {
            this.registroLexico = registroLexico;
            this.tabelaSimbolos = tabelaSimbolos;
            leitor = new LineNumberReader(new FileReader(nomeArquivo));
            caracteresPermitidos = new HashSet<>();
            inicializarTabela();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * Estado Inicial = 0
     * Estado de Aceitação = 2
     */
    void verificar() {
        char C = '#';
        int estado = 0;
        int estado_previo = 0;
        String lexema = "";
        try {
            loop:
            while (true) {
                leitor.mark(0);
                if (estado != 2) C = (char) leitor.read();
                if (!validarCaractere(C)) {
                    mostrarErro("caractere invalido.");
                } else {
                    switch (estado) {
                        case 0:
                            if (Character.isLetter(C)) {
                                lexema += C;
                                estado = 1;
                            } else if (C == ')' || C == '(' || C == '+' || C == '-' || C == '*' || C == ';' || C == ',') {
                                lexema += C;
                                estado = 2;
                            } else if (C == '_') {
                                lexema += C;
                                estado = 3;
                            } else if (C == '0') {
                                lexema += C;
                                estado = 4;
                            } else if (C == '!') {
                                lexema += C;
                                estado = 6;
                            } else if (C == '\'') {
                                lexema += C;
                                estado = 7;
                            } else if (C == '<' || C == '>' || C == '=') {
                                lexema += C;
                                estado = 9;
                            } else if (C == '/') {
                                lexema += C;
                                estado = 10;
                            } else if (Character.isDigit(C)) {
                                lexema += C;
                                estado = 12;
                            } else if (C == EOF) {
                                System.exit(0);
                            }
                            break;
                        case 1:
                            if (!Character.isLetter(C) && !Character.isDigit(C) && C != '_') {
                                estado_previo = estado;
                                estado = 2;
                                leitor.reset();
                            } else if (Character.isLetter(C) || Character.isDigit(C) || C == '_') {
                                lexema += C;
                            } else {
                                mostrarErro("lexema nao identificado [" + lexema + "]");
                            }
                            break;
                        case 2:
                            registroLexico.setLexema(lexema);
                            
                            if (estado_previo == 13) {
                                registroLexico.setToken("constantes");
                            }else if(estado_previo == 12) {
                                registroLexico.setToken("constantes");
                            }else if (estado_previo == 4) {
                                registroLexico.setToken("constantes");
                            } else if (estado_previo == 8) {
                                registroLexico.setToken("constantes");
                            } else {
                                ERRO

                                if (tabelaSimbolos.procurar(lexema) == null) {
                                    tabelaSimbolos.inserir(lexema, CLASSE_VAR, tipo);

                                    registroLexico.setToken("id");
                                    registroLexico.setClasse(CLASSE_VAR);
                                } else {
                                    registroLexico.setToken(lexema);
                                }
                            }

                            break loop;
                        case 3:
                            if (Character.isLetter(C) || Character.isDigit(C)) {
                                lexema += C;
                                estado = 1;
                            } else {
                                mostrarErro("lexema nao identificado [" + lexema + "]");
                            }
                            break;
                        case 4:
                            if (Character.isDigit(C)) {
                                lexema += C;
                                estado = 12;
                            } else if (C == 'h') {
                                lexema += C;
                                estado = 5;
                            } else if (C == EOF) {
                                mostrarErro("fim de arquivo nao esperado.");
                            } else {
                                estado_previo = estado;
                                estado = 2;
                                leitor.reset();
                            }
                            break;
                        case 5:
                            if (C == 'A' || C == 'B' || C == 'C' || C == 'D' || C == 'E' || C == 'F') {
                                lexema += C;
                                estado = 13;
                            } else
                                mostrarErro("lexema nao identificado [" + lexema + "]");
                            break;
                        case 6:
                            if (C == '=') {
                                lexema += C;
                                estado = 2;
                            } else {
                                mostrarErro("lexema nao identificado [" + lexema + "]");
                            }
                            break;
                        case 7:
                            if (C == '\'') {
                                lexema += C;
                                estado = 8;
                            } else if (C == '\n') {
                                mostrarErro("lexema nao identificado [" + lexema + "]");
                            } else if (C == EOF) {
                                mostrarErro("fim de arquivo nao esperado.");
                            } else {
                                lexema += C;
                            }
                            break;
                        case 8:
                            if (C != '\'') {
                                estado_previo = estado;
                                estado = 2;
                                leitor.reset();
                            } else {
                                lexema += C;
                                estado = 7;
                            }
                            break;
                        case 9:
                            if (C == '=') {
                                lexema += C;
                                estado = 2;
                            } else {
                                estado = 2;
                                leitor.reset();
                            }
                            break;
                        case 10:
                            if (C != '*') {
                                estado = 2;
                                leitor.reset();
                            } else {
                                lexema = "";
                                estado = 11;
                            }
                            break;
                        case 11:
                            if (C == '*') {
                                estado = 14;
                            } else if (C == EOF) {
                                mostrarErro("fim de arquivo nao esperado.");
                            }
                            break;
                        case 12:
                            if (Character.isDigit(C)) {
                                lexema += C;
                            } else if (C == EOF) {
                                mostrarErro("fim de arquivo nao esperado.");
                            } else {
                                estado_previo = estado;
                                estado = 2;
                                leitor.reset();
                            }
                            break;
                        case 13:
                            if (C == 'A' || C == 'B' || C == 'C' || C == 'D' || C == 'E' || C == 'F') {
                                lexema += C;
                                estado_previo = estado;
                                estado = 2;
                            } else
                                mostrarErro("lexema nao identificado [" + lexema + "]");
                            break;
                        case 14:
                            if (C == '/') {
                                estado = 0;
                            } else if (C == EOF) {
                                mostrarErro("fim de arquivo nao esperado.");
                            } else {
                                estado = 11;
                            }
                            break;
                    }
                }
                System.out.println("Caractere lido: " + C);
            }
        } catch (IOException e) {
            mostrarErro("fim de arquivo nao esperado.");
            e.printStackTrace();
        }
    }

    private void inicializarTabela() {
        caracteresPermitidos.add('_');
        caracteresPermitidos.add('.');
        caracteresPermitidos.add(',');
        caracteresPermitidos.add(';');
        caracteresPermitidos.add('&');
        caracteresPermitidos.add(':');
        caracteresPermitidos.add('(');
        caracteresPermitidos.add(')');
        caracteresPermitidos.add('{');
        caracteresPermitidos.add('}');
        caracteresPermitidos.add('[');
        caracteresPermitidos.add(']');
        caracteresPermitidos.add('+');
        caracteresPermitidos.add('-');
        caracteresPermitidos.add('"');
        caracteresPermitidos.add('*');
        caracteresPermitidos.add('\'');
        caracteresPermitidos.add('/');
        caracteresPermitidos.add('?');
        caracteresPermitidos.add('!');
        caracteresPermitidos.add('>');
        caracteresPermitidos.add('<');
        caracteresPermitidos.add('=');
        caracteresPermitidos.add('\n');
        caracteresPermitidos.add('\t');
        caracteresPermitidos.add(' ');
        caracteresPermitidos.add(EOF);
    }

    private boolean validarCaractere(char C) {
        return Character.isLetter(C) || Character.isDigit(C) || caracteresPermitidos.contains(C);
    }

    public void mostrarErro(String mensagem) {
        System.out.println(leitor.getLineNumber() + ":" + mensagem);
        System.exit(0);
    }
}

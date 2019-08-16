/*
 * Carlos Augusto Alves
 */
public class Main {
    private static AnalisadorLexico analisadorLexico;
    private static RegistroLexico registroLexico;

    private static void S() {
        D();

        while (!registroLexico.getToken().equals("main")) {
            D();
        }

        CT("main");
        C();
        while (registroLexico.getToken().equals("integer") || registroLexico.getToken().equals("const") ||
                registroLexico.getToken().equals("boolean") || registroLexico.getToken().equals("byte") ||
                registroLexico.getToken().equals("id") || registroLexico.getToken().equals("while") ||
                registroLexico.getToken().equals("if") || registroLexico.getToken().equals("readln") ||
                registroLexico.getToken().equals("write") || registroLexico.getToken().equals(";") ||
                registroLexico.getToken().equals("string")) {
            C();
        }
        CT("end");
    }

    private static void D() {
        if (registroLexico.getToken().equals("const")) {
            CT("const");
            CT("id");
            CT("=");

            if (registroLexico.getToken().equals("false"))
                CT("false");
            else if (registroLexico.getToken().equals("true"))
                CT("true");
            else
                CT("constantes");

            CT(";");
        } else {
            T();
            CT("id");

            if (registroLexico.getToken().equals("=")) {
                CT("=");

                if (registroLexico.getToken().equals("false"))
                    CT("false");
                else if (registroLexico.getToken().equals("true"))
                    CT("true");
                else
                    CT("constantes");
            }

            while (registroLexico.getToken().equals(",")) {
                CT(",");
                CT("id");

                if (registroLexico.getToken().equals("=")) {
                    CT("=");

                    if (registroLexico.getToken().equals("false"))
                        CT("false");
                    else if (registroLexico.getToken().equals("true"))
                        CT("true");
                    else
                        CT("constantes");
                }
            }

            CT(";");
        }
    }

    private static void T() {
        if (registroLexico.getToken().equals("integer"))
            CT("integer");
        else if (registroLexico.getToken().equals("boolean"))
            CT("boolean");
        else if (registroLexico.getToken().equals("byte"))
            CT("byte");
        else
            CT("string");
    }

    private static void C() {
        if (registroLexico.getToken().equals("id")) {
            CT("id");
            CT("=");
            exp();
            CT(";");
        } else if (registroLexico.getToken().equals("while")) {
            CT("while");
            A();
            W();
        } else if (registroLexico.getToken().equals("if")) {
            CT("if");
            A();
            CT("then");
            W();
            if (registroLexico.getToken().equals("else")) {
                CT("else");
                W();
            }
        } else if (registroLexico.getToken().equals("readln")) {
            CT("readln");
            CT("(");
            CT("id");
            CT(")");
            CT(";");
        } else if (registroLexico.getToken().equals("write")) {
            CT("write");
            CT("(");
            E();
            CT(")");
            CT(";");
        } else if (registroLexico.getToken().equals("writeln")) {
            CT("writeln");
            CT("(");
            E();
            CT(")");
            CT(";");
        } else {
            CT(";");
        }
    }

    private static void A() {
        CT("(");
        exp();
        CT(")");
    }

    private static void W() {
        if (registroLexico.getToken().equals("begin")) {
            CT("begin");
            while (!registroLexico.getToken().equals("end")) {
                C();
            }
            CT("end");
        } else {
            C();
        }
    }

    private static void E() {
        exp();
        while (registroLexico.getToken().equals(",")) {
            CT(",");
            exp();
        }
    }

    private static void exp() {
        expS();

        if (registroLexico.getToken().equals(">")) {
            CT(">");
            expS();
        } else if (registroLexico.getToken().equals("<")) {
            CT("<");
            expS();
        } else if (registroLexico.getToken().equals("==")) {
            CT("==");
            expS();
        } else if (registroLexico.getToken().equals(">=")) {
            CT(">=");
            expS();
        } else if (registroLexico.getToken().equals("<=")) {
            CT("<=");
            expS();
        } else if (registroLexico.getToken().equals("!=")) {
            CT("!=");
            expS();
        }
    }

    private static void expS() {
        if (registroLexico.getToken().equals("+"))
            CT("+");
        else if (registroLexico.getToken().equals("-"))
            CT("-");

        P();

        while (registroLexico.getToken().equals("+") || registroLexico.getToken().equals("-") || registroLexico.getToken().equals("or")) {
            if (registroLexico.getToken().equals("+"))
                CT("+");
            else if (registroLexico.getToken().equals("-"))
                CT("-");
            else
                CT("or");

            P();
        }
    }

    private static void P() {
        P1();

        while (registroLexico.getToken().equals("*") || registroLexico.getToken().equals("/") || registroLexico.getToken().equals("and")) {
            if (registroLexico.getToken().equals("*"))
                CT("*");
            else if (registroLexico.getToken().equals("/"))
                CT("/");
            else
                CT("and");

            P1();
        }
    }

    private static void P1() {
        if (registroLexico.getToken().equals("not"))
            CT("not");

        P2();
    }

    private static void P2() {
        if (registroLexico.getToken().equals("id"))
            CT("id");
        else if (registroLexico.getToken().equals("constantes"))
            CT("constantes");
        else if (registroLexico.getToken().equals("true"))
            CT("true");
        else if (registroLexico.getToken().equals("false"))
            CT("false");
        else if (registroLexico.getToken().equals("(")) {
            CT("(");
            exp();
            CT(")");
        }
    }

    private static void CT(String token_esp) {
        if (!registroLexico.getToken().equals(token_esp)) {
            analisadorLexico.mostrarErro("token não esperado [" + token_esp + "].");
        } else if (registroLexico.getToken().equals("EOF")) {
            analisadorLexico.mostrarErro("fim de arquivo não esperado.");
        } else
            analisadorLexico.verificar();
    }

    public static void main(String[] args) {
        /*try {
            if ((args[0] != null && args[1] != null) && (args[0].endsWith(".l") && args[1].endsWith(".asm"))) {*/
                TabelaSimbolos tabelaSimbolos = new TabelaSimbolos();
                tabelaSimbolos.construirTabelaInicial();

                registroLexico = new RegistroLexico();

                analisadorLexico = new AnalisadorLexico("C:\\Users\\carlos.alves\\Desktop\\TP1_TP2\\exemplo.l", registroLexico, tabelaSimbolos);
                analisadorLexico.verificar();

                S();
           /* } else {
                System.out.println("Favor informar o nome completo do programa fonte a ser compilado (extensão .L) " +
                        "e o nome completo do programa ASSEMBLY (extensão .ASM).");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Favor informar o nome completo do programa fonte a ser compilado (extensão .L) " +
                    "e o nome completo do programa ASSEMBLY (extensão .ASM).");
        }*/
    }
}

/*
 * Carlos Augusto Alves
 */
public class RegistroLexico {
    private String lexema;
    private String token;
    private String classe;
    private String tipo;

    public RegistroLexico() {
        this.lexema = "";
        this.token = "";
        this.classe = "";
        this.tipo = "";
    }

    public String getLexema() {
        return lexema;
    }

    public String getToken() {
        return token;
    }

    public String getClasse() {
        return classe;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

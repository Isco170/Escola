package domain;

public class Estudante {

    private int matricula;
    private String nome;
    private String apelido;
    private String telefone;
    private String endereco;
    private Turma turma;
    private String descricao;

    public Estudante() {

    }

    public Estudante(int matricula, String nome, String apelido, String telefone, String endereco) {
        this.matricula = matricula;
        this.nome = nome;
        this.apelido = apelido;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Estudante(String nome, String apelido, String telefone, String endereco) {
        
        this.nome = nome;
        this.apelido = apelido;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Estudante(String nome, String apelido, String telefone, String endereco, Turma turma) {
        
        this.nome = nome;
        this.apelido = apelido;
        this.telefone = telefone;
        this.endereco = endereco;
        this.turma = turma;
    }
    

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getDescricaoTurma() {
        return descricao;
    }

    public void setDescricaoTurma(String descricaoTurma) {
        this.descricao = descricaoTurma;
    }

}

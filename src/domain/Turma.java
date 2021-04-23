package domain;

public class Turma {
    private int identificacao;
    private String descricao;
    
    public Turma(String descricao){
        this.descricao = descricao;
    }
    
    public Turma(int identificacao, String descricao){
        this.identificacao = identificacao;
        this.descricao = descricao;
        
    }
    
    public Turma(){
        
    }

    public int getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
    
    
}

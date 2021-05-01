package dao;

import domain.Turma;
import factory.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TurmaDAO {
    ResultSet resultado;
    
    public void salvarTurma(Turma turma) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO turma ");
        sql.append("(descricao ) ");
        sql.append("VALUES (?)");
        
        Connection conexao = Conexao.conetar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        
        comando.setString(1, turma.getDescricao());
        comando.executeUpdate();
    }
    
    public void excluirTurma(Turma turma) throws SQLException{
        
        StringBuilder sql = new StringBuilder();
        sql.append("update turma set apagado = true ");
        sql.append("where identificacao = ? ");
        
        Connection conexao = Conexao.conetar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, turma.getIdentificacao());
        comando.executeUpdate();
    }
    
    public ObservableList<Turma> listarTurma() throws SQLException{
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM turma ");
        sql.append("where apagado = false ");
        
        Connection conexao = Conexao.conetar();
        
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        resultado = comando.executeQuery();
        
        ObservableList<Turma> listaTurma = FXCollections.observableArrayList();
        while(resultado.next()){
            Turma turma = new Turma();
            turma.setIdentificacao(resultado.getInt("identificacao"));
            turma.setDescricao(resultado.getString("descricao"));
            listaTurma.add(turma);
        }
        
        return listaTurma;
    }
    
    public void editarTurma(Turma turma) throws SQLException{
        
        StringBuilder sql = new StringBuilder();
        sql.append("update turma ");
        sql.append("set descricao = ? ");
        sql.append("where identificacao =? ");
        
        Connection conexao = Conexao.conetar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, turma.getDescricao());
        comando.setInt(2, turma.getIdentificacao());
        comando.executeUpdate();
    }
}

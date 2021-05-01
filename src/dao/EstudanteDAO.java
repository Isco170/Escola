package dao;

import domain.Estudante;
import domain.Turma;
import factory.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EstudanteDAO {

    ResultSet resultado;

    public void salvarEstudante(Estudante estudante) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into estudante ");
        sql.append("(nome, apelido, telefone, endereco, turma) ");
        sql.append("values (?, ?, ?, ?, ?) ");

        Connection conexao = Conexao.conetar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, estudante.getNome());
        comando.setString(2, estudante.getApelido());
        comando.setString(3, estudante.getTelefone());
        comando.setString(4, estudante.getEndereco());
        comando.setInt(5, estudante.getTurma().getIdentificacao());

        comando.executeUpdate();
    }

    public void excluirEstudante(Estudante estudante) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update estudante set apagado = true ");
        sql.append("where matricula = ? ");

        Connection conexao = Conexao.conetar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setInt(1, estudante.getMatricula());
        comando.executeUpdate();
    }

    public ObservableList<Estudante> listarEstudante() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from estudante estu ");
        sql.append("inner join turma tur on tur.identificacao = estu.turma ");
        sql.append("where estu.apagado = false and tur.apagado = false ");
        
        Connection conexao = Conexao.conetar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        resultado = comando.executeQuery();
        
        ObservableList<Estudante> listaEstudante = FXCollections.observableArrayList();
        while(resultado.next()){
            Estudante estu = new Estudante();
            Turma tur = new Turma();
             
            estu.setMatricula(resultado.getInt("estu.matricula"));
            estu.setNome(resultado.getString("estu.nome"));
            estu.setApelido(resultado.getString("estu.apelido"));
            estu.setTelefone(resultado.getString("estu.telefone"));
            estu.setEndereco(resultado.getString("estu.endereco"));
            estu.setDescricaoTurma(resultado.getString("tur.descricao"));
            
            System.out.println(estu.getDescricaoTurma());
            
            listaEstudante.add(estu);
        }
        
        return listaEstudante;

    }
    
    public void editarEstudante(Estudante estudante) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("update estudante ");
        sql.append("set nome = ?, apelido =?, telefone = ?, endereco = ? ");
        sql.append("where matricula = ? ");
        
        Connection conexao = Conexao.conetar();
        PreparedStatement comando = conexao.prepareStatement(sql.toString());
        comando.setString(1, estudante.getNome());
        comando.setString(2, estudante.getApelido());
        comando.setString(3, estudante.getTelefone());
        comando.setString(4, estudante.getEndereco());
        comando.executeQuery();
    }
}

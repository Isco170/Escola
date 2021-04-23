package escola;

import dao.TurmaDAO;
import domain.Turma;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class novoAluno extends Application {

    Stage janela;
    Scene cena;

    BorderPane border;
    VBox inputsEBotoes;
    GridPane gridPane;
    AnchorPane botoes;
    Button salvar, cancelar;
    
    TextField nome, apelido, telefone, endereco;
    ComboBox<String> turmas;

    public void initComponents() {
        border = new BorderPane();
        
        inputsEBotoes = new VBox();
        
        botoes = new AnchorPane();
        botoes.autosize();
        
        
        salvar = new Button("Salvar");
        cancelar = new Button("Cancelar");

        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        nome = new TextField();
        nome.setPromptText("Nome: ");
        nome.setFocusTraversable(false);
        nome.autosize();

        apelido = new TextField();
        apelido.setPromptText("Apelido: ");
        apelido.setFocusTraversable(false);
        apelido.autosize();

        telefone = new TextField();
        telefone.setPromptText("Telefone: ");
        telefone.setFocusTraversable(false);
        telefone.autosize();

        endereco = new TextField();
        endereco.setPromptText("Endereço: ");
        endereco.setFocusTraversable(false);
        endereco.autosize();

        turmas = new ComboBox();
        turmas.setPromptText("Turma");
        turmas.setId("turmas");
        
        try {
            preencherTurma();
        } catch (SQLException ex) {
            Logger.getLogger(novoAluno.class.getName()).log(Level.SEVERE, null, ex);
        }

        cena = new Scene(border, 400, 400);
    }

    public void initLayout() {
        GridPane.setConstraints(nome, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(apelido, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(telefone, 0, 3, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(endereco, 0, 4, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(turmas, 0, 5, 1, 1, HPos.CENTER, VPos.CENTER);

        gridPane.getChildren().addAll(nome, apelido, telefone, endereco, turmas);
        
        botoes.getChildren().addAll(salvar, cancelar);
        inputsEBotoes.getChildren().addAll(gridPane, botoes);

        border.setCenter(inputsEBotoes);

    }
    
    public void preencherTurma() throws SQLException{
            TurmaDAO turDAO = new TurmaDAO();
            ObservableList<Turma> listaTurma = (ObservableList<Turma>) turDAO.listarTurma();
            for(int i = 0; i < listaTurma.size(); i++){
                turmas.getItems().add(i, listaTurma.get(i).getDescricao());
                System.out.println(turmas.getItems().get(i));
            }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initComponents();
        initLayout();

        janela.setTitle("Novo Aluno");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.showAndWait();

    }

}

package escola;

import dao.EstudanteDAO;
import domain.Estudante;
import domain.Turma;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Principal extends Application {

    EstudanteDAO estuDAO;
    novoAluno novoAl;

    Stage janela;
    Scene cena;

    TabPane tabPane;
    Tab turmaTab, alunoTab;

    BorderPane border, turmaBorder, alunoBorder;
    HBox botoes;

    Button novaTurma, novoAluno, apagarTurma, apagarAluno;

    //Tabela de alunos
    static TableView<Estudante> tabelaEstudante;
    TableColumn<Estudante, String> nomeEstudante;
    TableColumn<Estudante, String> apelidoEstudante;
    TableColumn<Estudante, String> telefoneEstudante;
    TableColumn<Estudante, String> enderecoEstudante;
    TableColumn<Estudante, String> turmaEstudante;
    //fim da tabela de alunos
    
    //Tabela de turmas
    static TableView<Turma> tabelaTurma;
    TableColumn<Turma, String> descricaoTurma;
    //Fim da tabela de turmas
    

    public void initComponents() {
        border = new BorderPane();
        border.autosize();
        border.bottomProperty();
        border.setPadding(new Insets(10));

        tabPane = new TabPane();

        turmaTab = new Tab("Turmas");
        turmaTab.setClosable(false);

        alunoTab = new Tab("Alunos");
        alunoTab.setClosable(false);

        turmaBorder = new BorderPane();
        alunoBorder = new BorderPane();

        //Centro
        //Tabela
        nomeEstudante = new TableColumn<>("Nome");
        nomeEstudante.setMinWidth(180);
        nomeEstudante.setCellValueFactory(new PropertyValueFactory<>("nome"));

        apelidoEstudante = new TableColumn<>("Apelido");
        apelidoEstudante.setMinWidth(180);
        apelidoEstudante.setCellValueFactory(new PropertyValueFactory<>("apelido"));

        telefoneEstudante = new TableColumn<>("Telefone");
        telefoneEstudante.setMinWidth(180);
        telefoneEstudante.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        enderecoEstudante = new TableColumn<>("Endereço");
        enderecoEstudante.setMinWidth(180);
        enderecoEstudante.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        turmaEstudante = new TableColumn<>("Turma");
        turmaEstudante.setMinWidth(180);
        turmaEstudante.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabelaEstudante = new TableView();
//        tabelaEstudante.setPrefSize(1220, 400);
        tabelaEstudante.setPlaceholder(new Text("Sem estudantes"));
//        tabelaEstudante.setPadding(new Insets(10));
        try {
            tabelaEstudante.setItems(listarEstudantes());

        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaEstudante.getColumns().addAll(nomeEstudante, apelidoEstudante,
                telefoneEstudante, enderecoEstudante, turmaEstudante);

        apagarAluno = new Button("Remover aluno");
        apagarAluno.setVisible(false);
        apagarAluno.setId("apagarAluno");

        novoAluno = new Button("Novo aluno");
        novoAluno.setId("novoAluno");

        botoes = new HBox();
        botoes.autosize();
        botoes.setPadding(new Insets(8));
        botoes.setSpacing(8);

        //Fim tabela
        //Fim centro
        cena = new Scene(border, 1220, 650);
        cena.getStylesheets().add("escola/styles.css");
    }

    public void initLayout() {

        alunoBorder.setCenter(tabelaEstudante);
        botoes.getChildren().addAll(apagarAluno, novoAluno);
        alunoBorder.setBottom(botoes);
        alunoTab.setContent(alunoBorder);
        tabPane.getTabs().addAll(turmaTab, alunoTab);
        border.setCenter(tabPane);

        

    }

    public ObservableList<Estudante> listarEstudantes() throws SQLException {
        estuDAO = new EstudanteDAO();
        ObservableList<Estudante> estudantes = (ObservableList<Estudante>) estuDAO.listarEstudante();
        return estudantes;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initComponents();
        initLayout();

        tabelaEstudante.setOnMouseClicked(e -> {
            if ((!tabelaEstudante.getItems().isEmpty()) && (tabelaEstudante.getSelectionModel().getSelectedIndex() >= 0)) {
                apagarAluno.setVisible(true);
            }
        });

        apagarAluno.setOnAction(e -> {
            if (!tabelaEstudante.getItems().isEmpty()) {
                int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza "
                        + "que quer remover o estudante selecionado?", "Apagar "
                        + "despesa", JOptionPane.OK_OPTION);
                if (resposta == 0) {
                    try {
                        estuDAO.excluirEstudante(tabelaEstudante.getItems().get(tabelaEstudante.getSelectionModel().getSelectedIndex()));

                        tabelaEstudante.setItems(listarEstudantes());
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        novoAluno.setOnAction(e -> {
            novoAl = new novoAluno();
            try {
                novoAl.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        janela.setTitle("Escola");
        janela.setScene(cena);
//        janela.setResizable(false);
        janela.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}

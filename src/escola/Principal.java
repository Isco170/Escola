package escola;

import dao.EstudanteDAO;
import dao.TurmaDAO;
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

    TurmaDAO turDAO;
    novaTurma novaTur;

    Stage janela;
    Scene cena;

    TabPane tabPane;
    Tab turmaTab, alunoTab;

    BorderPane border, turmaBorder, alunoBorder;
    HBox botoes, botoes2;

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
    TableColumn<Turma, Integer> identificacaoTurma;
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

        identificacaoTurma = new TableColumn<>("Identificação");
        identificacaoTurma.setMinWidth(100);
        identificacaoTurma.setCellValueFactory(new PropertyValueFactory<>("identificacao"));

        descricaoTurma = new TableColumn<>("Descrição");
        descricaoTurma.setMinWidth(180);
        descricaoTurma.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabelaTurma = new TableView();
        tabelaTurma.setPlaceholder(new Text("Sem turmas"));
        try {
            tabelaTurma.setItems(listarTurmas());
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelaTurma.getColumns().addAll(identificacaoTurma, descricaoTurma);

        apagarAluno = new Button("Remover aluno");
        apagarAluno.setVisible(false);
        apagarAluno.setId("apagarAluno");

        apagarTurma = new Button("Remover turma");
        apagarTurma.setVisible(false);
        apagarTurma.setId("apagarAluno");

        novoAluno = new Button("Novo aluno");
        novoAluno.setId("novoAluno");

        novaTurma = new Button("Nova turma");
        novaTurma.setId("novoAluno");

        botoes = new HBox();
        botoes.autosize();
        botoes.setPadding(new Insets(8));
        botoes.setSpacing(8);

        botoes2 = new HBox();
        botoes2.autosize();
        botoes2.setPadding(new Insets(8));
        botoes2.setSpacing(8);

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

        turmaBorder.setCenter(tabelaTurma);
        botoes2.getChildren().addAll(apagarTurma, novaTurma);
        turmaBorder.setBottom(botoes2);

        turmaTab.setContent(turmaBorder);

        tabPane.getTabs().addAll(turmaTab, alunoTab);
        border.setCenter(tabPane);

    }

    public ObservableList<Estudante> listarEstudantes() throws SQLException {
        estuDAO = new EstudanteDAO();
        ObservableList<Estudante> estudantes = (ObservableList<Estudante>) estuDAO.listarEstudante();
        return estudantes;
    }

    public ObservableList<Turma> listarTurmas() throws SQLException {
        turDAO = new TurmaDAO();
        ObservableList<Turma> turmas = (ObservableList<Turma>) turDAO.listarTurma();
        return turmas;
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

        tabelaTurma.setOnMouseClicked(e -> {
            if ((!tabelaTurma.getItems().isEmpty()) && (tabelaTurma.getSelectionModel().getSelectedIndex() >= 0)) {
                apagarTurma.setVisible(true);
            }
        });

        apagarAluno.setOnAction(e -> {
            if (!tabelaEstudante.getItems().isEmpty()) {
                int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza "
                        + "que quer remover o estudante selecionado?", "Apagar "
                        + "estudante", JOptionPane.OK_OPTION);
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

        apagarTurma.setOnAction(e -> {
            if (!tabelaTurma.getItems().isEmpty()) {
                int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza "
                        + "que quer remover a turma selecionada?", "Apagar "
                        + "turma", JOptionPane.OK_OPTION);
                if (resposta == 0) {
                    try {
                        turDAO.excluirTurma(tabelaTurma.getItems().get(tabelaTurma.getSelectionModel().getSelectedIndex()));

                        tabelaTurma.setItems(listarTurmas());
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
        
        novaTurma.setOnAction(e -> {
            novaTur = new novaTurma();
            try {
                novaTur.start(new Stage());
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

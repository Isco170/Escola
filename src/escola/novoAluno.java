package escola;

import dao.EstudanteDAO;
import dao.TurmaDAO;
import domain.Estudante;
import domain.Turma;
import static escola.Principal.tabelaEstudante;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class novoAluno extends Application {

    Stage janela;
    Scene cena;
    
    Principal principal;

    BorderPane border;
    GridPane gridPane;

    Button salvar, cancelar;

    TextField nome, apelido, telefone, endereco;
    ComboBox<Turma> turmas;

    Notifications notificacaoBuilder;
    Node graphic = null;

    public void initComponents() {
        border = new BorderPane();
        border.autosize();
        border.setPadding(new Insets(10));

        salvar = new Button("Salvar");
        salvar.setAlignment(Pos.BOTTOM_RIGHT);
        salvar.setId("novoAluno");

        cancelar = new Button("Cancelar");
        cancelar.setAlignment(Pos.BASELINE_LEFT);
        cancelar.setId("apagarAluno");

        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.autosize();

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

        cena = new Scene(border, 300, 500);
        cena.getStylesheets().add("escola/styles.css");
    }

    public void initLayout() {
        GridPane.setConstraints(nome, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(apelido, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(telefone, 0, 3, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(endereco, 0, 4, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(turmas, 0, 5, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(cancelar, 0, 6, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(salvar, 0, 6, 1, 1, HPos.RIGHT, VPos.CENTER);

        gridPane.getChildren().addAll(nome, apelido, telefone, endereco, turmas,
                cancelar, salvar);

        border.setCenter(gridPane);

    }

    private void notificacao(Pos pos, Node graphic, String mesag) {
        notificacaoBuilder = Notifications.create()
                .text("Gestor Financeiro")
                .text(mesag)
                .graphic(graphic)
                .hideAfter(Duration.seconds(3))
                .position(pos)
                .onAction(e -> {
                    System.out.println("Notificador");
                });
    }

    public void preencherTurma() throws SQLException {
        TurmaDAO turDAO = new TurmaDAO();
        ObservableList<Turma> listaTurma = (ObservableList<Turma>) turDAO.listarTurma();
        for (int i = 0; i < listaTurma.size(); i++) {
            turmas.getItems().add(i, listaTurma.get(i));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initComponents();
        initLayout();

        cancelar.setOnAction(e -> {
            janela.close();
        });

        salvar.setOnAction(e -> {

            Turma turma = new Turma(turmas.getItems().get(turmas.getSelectionModel().getSelectedIndex()).getIdentificacao(), turmas.getItems().get(turmas.getSelectionModel().getSelectedIndex()).getDescricao());
            Estudante estudante = new Estudante(nome.getText(), apelido.getText(), telefone.getText(), endereco.getText(), turma);

            EstudanteDAO estuDAO = new EstudanteDAO();
            try {
                estuDAO.salvarEstudante(estudante);
                notificacao(Pos.TOP_CENTER, graphic, "Salvo com sucesso");
                notificacaoBuilder.showInformation();
                principal.tabelaEstudante.setItems(principal.listarEstudantes());
                
            } catch (SQLException ex) {
                notificacao(Pos.TOP_CENTER, graphic, "Falha ao salvar estudante");
                notificacaoBuilder.showError();
                Logger.getLogger(novoAluno.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        janela.setTitle("Novo Aluno");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.showAndWait();

    }

}

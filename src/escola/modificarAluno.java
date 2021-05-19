package escola;

import dao.EstudanteDAO;
import domain.Estudante;
import static escola.modificarTurma.principal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class modificarAluno extends Application {

    public modificarAluno(Estudante estudante) {
        this.estudante = estudante;
    }

    EstudanteDAO estDAO = new EstudanteDAO();
    Stage janela;
    Scene cena;

    Principal principal;

    BorderPane border;
    GridPane gridPane;

    Button salvar, cancelar;

    TextField nome, apelido, telefone, endereco;

    Notifications notificacaoBuilder;
    Node graphic = null;

    Estudante estudante;

    public void initComponents() {
        border = new BorderPane();
        border.autosize();
        border.setPadding(new Insets(10));

        salvar = new Button("Modificar");
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
        nome.setText(estudante.getNome());
        nome.setFocusTraversable(false);
        nome.autosize();

        apelido = new TextField();
        apelido.setPromptText("Apelido: ");
        apelido.setText(estudante.getApelido());
        apelido.setFocusTraversable(false);
        apelido.autosize();

        telefone = new TextField();
        telefone.setPromptText("Telefone: ");
        telefone.setText(estudante.getTelefone());
        telefone.setFocusTraversable(false);
        telefone.autosize();

        endereco = new TextField();
        endereco.setPromptText("Endereço: ");
        endereco.setText(estudante.getEndereco());
        endereco.setFocusTraversable(false);
        endereco.autosize();

        cena = new Scene(border, 300, 500);
        cena.getStylesheets().add("escola/styles.css");
    }

    public void initLayout() {
        GridPane.setConstraints(nome, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(apelido, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(telefone, 0, 3, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(endereco, 0, 4, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(cancelar, 0, 6, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(salvar, 0, 6, 1, 1, HPos.RIGHT, VPos.CENTER);

        gridPane.getChildren().addAll(nome, apelido, telefone, endereco,
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initComponents();
        initLayout();

        salvar.setOnAction(e -> {
            estudante.setNome(nome.getText());
            estudante.setApelido(apelido.getText());
            estudante.setEndereco(endereco.getText());
            estudante.setTelefone(telefone.getText());

            try {
                
                estDAO.editarEstudante(estudante);
                notificacao(Pos.TOP_CENTER, graphic, "Modficado com sucesso");
                notificacaoBuilder.showInformation();
                principal.tabelaEstudante.setItems(principal.listarEstudantes());
                cancelar.setText("Fechar");
            } catch (SQLException ex) {
                notificacao(Pos.TOP_CENTER, graphic, "Falha ao modificar estudante");
                notificacaoBuilder.showError();
                Logger.getLogger(modificarAluno.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cancelar.setOnAction(e -> {
            janela.close();
        });

        janela.setTitle("Novo Aluno");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.showAndWait();

    }
}

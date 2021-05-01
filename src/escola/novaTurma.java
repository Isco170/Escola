package escola;

import dao.TurmaDAO;
import domain.Turma;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class novaTurma extends Application {

    Stage janela;
    Scene cena;

    Principal principal;
    BorderPane border;
    VBox box;
    Button salvar, cancelar;
    HBox botoes;

    TextField descricao;

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
        
        botoes = new HBox();
        botoes.setAlignment(Pos.CENTER);
        botoes.setSpacing(10);
        

        descricao = new TextField();
        descricao.setPromptText("Descrição: ");
        descricao.setFocusTraversable(false);
        descricao.autosize();

        box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        cena = new Scene(border, 300, 300);
        cena.getStylesheets().add("escola/styles.css");
    }

    public void layoutComponents() {
        botoes.getChildren().addAll(salvar, cancelar);
        box.getChildren().addAll(descricao, botoes);
        border.setCenter(box);
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
        layoutComponents();

        cancelar.setOnAction(e -> {
            janela.close();
        });

        salvar.setOnAction(e -> {
            Turma turma = new Turma(descricao.getText());
            TurmaDAO turDAO = new TurmaDAO();
            try {
                turDAO.salvarTurma(turma);
                notificacao(Pos.TOP_CENTER, graphic, "Salvo com sucesso");
                notificacaoBuilder.showInformation();
                principal.tabelaTurma.refresh();
                janela.close();

            } catch (SQLException ex) {
                notificacao(Pos.TOP_CENTER, graphic, "Falha ao salvar turma");
                notificacaoBuilder.showError();
                Logger.getLogger(novoAluno.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        janela.setTitle("Nova Turma");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.showAndWait();
    }
}

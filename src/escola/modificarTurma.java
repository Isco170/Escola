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

public class modificarTurma extends Application{

    public modificarTurma(Turma turma) {
        this.turma = turma;
    }
    
    
    Stage janela;
    Scene cena;
    static Principal principal;
    BorderPane border;
    VBox box;
    Button salvar, cancelar;
    HBox botoes;

    TextField descricao;

    Notifications notificacaoBuilder;
    Node graphic = null;
    
    Turma turma;
    TurmaDAO turDAO = new TurmaDAO();
    
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
        
        botoes = new HBox();
        botoes.setAlignment(Pos.CENTER);
        botoes.setSpacing(10);
        

        descricao = new TextField();
        descricao.setText(turma.getDescricao());
        descricao.setAlignment(Pos.CENTER);
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

    public void start(Stage primaryStage) throws Exception {
        janela = primaryStage;

        initComponents();
        layoutComponents();

        salvar.setOnAction(e -> {
            turma.setDescricao(descricao.getText());
            try {
                
                turDAO.salvarTurma(turma);
                notificacao(Pos.TOP_CENTER, graphic, "Modficado com sucesso");
                notificacaoBuilder.showInformation();
                principal.tabelaTurma.setItems(principal.listarTurmas());
                cancelar.setText("Fechar");
            } catch (SQLException ex) {
                Logger.getLogger(modificarTurma.class.getName()).log(Level.SEVERE, null, ex);
                notificacao(Pos.TOP_CENTER, graphic, "Falha ao modificar turma");
                notificacaoBuilder.showError();
            }
            
        });
        cancelar.setOnAction(e -> {
            janela.close();
        });


        janela.setTitle("Modificar Turma");
        janela.setScene(cena);
        janela.setResizable(false);
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.showAndWait();
    }
}

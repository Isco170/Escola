package escola;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class novoAluno extends Application {

    Stage janela;
    Scene cena;

    BorderPane border;
    GridPane gridPane;
    TextField nome;
    ComboBox<String> turmas;

    public void initComponents() {
        border = new BorderPane();

        gridPane = new GridPane();

        nome = new TextField();
        nome.setPromptText("Nome: ");

        turmas = new ComboBox();

        cena = new Scene(border, 400, 400);
    }

    public void initLayout() {
        GridPane.setConstraints(nome, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER);
        gridPane.getChildren().addAll(nome);

        border.setCenter(gridPane);

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

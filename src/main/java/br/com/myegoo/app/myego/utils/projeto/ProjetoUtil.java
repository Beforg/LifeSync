package br.com.myegoo.app.myego.utils.projeto;

import br.com.myegoo.app.myego.interfaces.IPadraoDaData;
import br.com.myegoo.app.myego.model.treino.TabelaTreino;
import br.com.myegoo.app.myego.model.projetos.TabelaItensCard;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ProjetoUtil implements IPadraoDaData {

    public static void controleDeAtividades(Pane paneOpcoesProjeto,
                                            Pane paneInferiorProjetos,
                                            Pane paneOpcaoTreino,
                                            Pane  paneInferiorTreino,
                                            TableView<TabelaTreino> tabelaTreinos,
                                            boolean projeto,
                                            boolean treino) {
        paneOpcaoTreino.setVisible(treino);
        paneInferiorTreino.setVisible(treino);
        paneOpcoesProjeto.setVisible(projeto);
        paneInferiorProjetos.setVisible(projeto);
        tabelaTreinos.setVisible(treino);

    }
    public static void trocaTelaDeProjetos(GridPane gridPaneCriaItem,
                                           GridPane novoProjeto,
                                           GridPane novoCard,
                                           GridPane gridPaneMeusProjetos,
                                           GridPane gridPaneMuralProjetos,
                                           GridPane editarCard,
                                           GridPane editarProjeto,
                                           boolean projeto,
                                           boolean mural) {
        gridPaneCriaItem.setVisible(false);
        gridPaneMeusProjetos.setVisible(projeto);
        gridPaneMuralProjetos.setVisible(mural);
        editarCard.setVisible(false);
        novoProjeto.setVisible(false);
        novoCard.setVisible(false);
        editarProjeto.setVisible(false);
    }
    public static void telaEditarCard(GridPane gridPaneEditaCard,
                                      GridPane gridPaneMeusProjetos,
                                      GridPane gridPaneEditaProjeto,
                                      boolean projeto,
                                      boolean card) {
        gridPaneEditaCard.setVisible(card);
        gridPaneMeusProjetos.setVisible(false);
        gridPaneEditaProjeto.setVisible(projeto);
    }
    public static void novoProjetoEcardView(GridPane gridPaneCriaItem,
                                            GridPane novoProjeto,
                                            GridPane novoCard,
                                            GridPane gridPaneMural,
                                            GridPane gridPaneMeusProjetos,
                                            GridPane gridPaneEditaCard,
                                            GridPane gridPaneEditaProjeto,
                                            boolean projeto,
                                            boolean card,
                                            boolean item) {
        gridPaneCriaItem.setVisible(item);
        novoProjeto.setVisible(projeto);
        novoCard.setVisible(card);
        gridPaneEditaCard.setVisible(false);
        gridPaneMural.setVisible(false);
        gridPaneMeusProjetos.setVisible(false);
        gridPaneEditaProjeto.setVisible(false);
    }

    public static void criarCategoriaView(ChoiceBox<String> cbCategoriaProjeto,
                                          TextField tfCriarCategoriaProjeto,
                                          RadioButton rb) {
        if (rb.isSelected()) {
            cbCategoriaProjeto.setVisible(false);
            tfCriarCategoriaProjeto.setVisible(true);
        } else {
            cbCategoriaProjeto.setVisible(true);
            tfCriarCategoriaProjeto.setVisible(false);
        }

    }
    public static void carregaNomeDoCard(Label nomeDoCard,
                                         Label nomeDoCardItem,
                                         TableColumn<TabelaItensCard, String> tabela) {
        nomeDoCardItem.setText(nomeDoCard.getText());
        tabela.setText("Itens relacionados ao card " + nomeDoCard.getText());
    }

    public static void verificaItemConcluidoTabela(TableView<TabelaItensCard> tabelaTreino) {
        tabelaTreino.setRowFactory(tv -> new TableRow<TabelaItensCard>() {
            @Override
            protected void updateItem(TabelaItensCard item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else if (item.isConcluidoBoolean()) {

                    setStyle("-fx-background-color: #1b5c1b;");
                } else {

                    setStyle("");
                }
            }
        });
    }

    public static  void liberaBotaoCard(Button botaoCard, ChoiceBox<String> cbSelecionarProjeto) {
        cbSelecionarProjeto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (cbSelecionarProjeto.getValue() != null) {
                botaoCard.setDisable(false);
            } else {
                botaoCard.setDisable(true);
            }
        });
    }
    public static void liberaChoiceBox(ChoiceBox<String> status, Label nomeDoCard) {
        nomeDoCard.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                status.setDisable(false);
            } else {
                status.setDisable(true);
            }
        });
    }

}

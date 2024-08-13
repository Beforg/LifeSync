package br.com.myegoo.app.myego.utils;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class AppUtils {
    public static void mostraAdicionarHabito(
                                            Label labelDiaHabitoAdd,
                                             Label labelRepeteHabito,
                                             Label labelHabitoNomeAdd,
                                            RadioButton rbCriarNovoHabito,
                                            RadioButton rbRepete,
                                            Button btVincularHabito,
                                            Button btApagarHabito,
                                            DatePicker dpDiaHabito,
                                            DatePicker dpDiaHabitoRepeat,
                                            ChoiceBox<String> cbSelecionarHabito,
                                            boolean visivel) {
        labelDiaHabitoAdd.setVisible(visivel);
        labelRepeteHabito.setVisible(visivel);
        labelHabitoNomeAdd.setVisible(visivel);
        rbCriarNovoHabito.setVisible(visivel);
        rbRepete.setVisible(visivel);
        btVincularHabito.setVisible(visivel);
        btApagarHabito.setVisible(visivel);

        dpDiaHabito.setVisible(visivel);
        dpDiaHabitoRepeat.setVisible(visivel);
        cbSelecionarHabito.setVisible(visivel);
    }

    public static void listenerHabito(RadioButton rbCriarNovoHabito,
                                        RadioButton rbRepete,
                                        DatePicker dpDiaHabito,
                                        DatePicker dpDiaHabitoRepeat,
                                        ChoiceBox<String> cbSelecionarHabito,
                                        TextField tfCriarHabito) {
        if (rbCriarNovoHabito.isSelected()) {
            tfCriarHabito.setVisible(true);
            cbSelecionarHabito.setVisible(false);
        } else {
            tfCriarHabito.setVisible(false);
            cbSelecionarHabito.setVisible(true);
        }
        rbRepete.setOnAction(e -> {
            if (rbRepete.isSelected()) {
                dpDiaHabitoRepeat.setDisable(false);
            } else {
                dpDiaHabitoRepeat.setDisable(true);
            }
        });
    }
    public static void viewMenuProjetos(Pane paneProjetos, Pane paneCards, boolean visivelProjetos) {
        paneProjetos.setVisible(visivelProjetos);
        paneCards.setVisible(!visivelProjetos);
    }
    public static void line(Pane paneInferiorProjetos, Line line) {
        if (paneInferiorProjetos.isVisible()) {
            line.setVisible(false);
        } else {
            line.setVisible(true);
        }
    }
    public static void liberaBotaoCriarTarefaHome(Button btCriarTarefaHome,
                                                  TextField tfNomeTarefa,
                                                  ChoiceBox<String> cbCategoriaTarefa,
                                                  ChoiceBox<String> cbPrioridadeTarefa) {
//
//        tfNomeTarefa.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null && !newValue.isEmpty() && cbCategoriaTarefa.getValue() != null && cbPrioridadeTarefa.getValue() != null) {
//                btCriarTarefaHome.setDisable(false);
//            } else {
//                btCriarTarefaHome.setDisable(true);
//            }
//        });
//        verificaChoiceBox(btCriarTarefaHome, tfNomeTarefa, cbPrioridadeTarefa, cbCategoriaTarefa);
//        verificaChoiceBox(btCriarTarefaHome, tfNomeTarefa, cbCategoriaTarefa, cbPrioridadeTarefa);
    }

    public static void liberaBotaoCriarTreinoHome(TextField tfNomeTreino,
                                                  ChoiceBox<String> tipo,
                                                  Button btCriarTreinoHome) {
//        tfNomeTreino.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null && !newValue.isEmpty() && tipo.getValue() != null) {
//                btCriarTreinoHome.setDisable(false);
//            } else {
//                btCriarTreinoHome.setDisable(true);
//            }
//        });
//        tipo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null && !newValue.isEmpty() && tfNomeTreino.getText() != null && !tfNomeTreino.getText().isEmpty()) {
//                btCriarTreinoHome.setDisable(false);
//            } else {
//                btCriarTreinoHome.setDisable(true);
//            }
//        });
    }
    private static void verificaChoiceBox(Button btCriarTarefaHome, TextField tfNomeTarefa, ChoiceBox<String> cbCategoriaTarefa, ChoiceBox<String> cbPrioridadeTarefa) {
        cbPrioridadeTarefa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty() && tfNomeTarefa.getText() != null && !tfNomeTarefa.getText().isEmpty() && cbCategoriaTarefa.getValue() != null) {
                btCriarTarefaHome.setDisable(false);
            } else {
                btCriarTarefaHome.setDisable(true);
            }
        });
    }
    public static void listenerAddHabitoDiaHome(ChoiceBox<String> cbHabitosHome,
                                                Button btAddAoDia) {
        cbHabitosHome.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btAddAoDia.setDisable(false);
            } else {
                btAddAoDia.setDisable(true);
            }
        });
    }

}

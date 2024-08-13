package br.com.myegoo.app.myego.utils.habitos;

import br.com.myegoo.app.myego.model.Habitos;
import br.com.myegoo.app.myego.repository.HabitosRepository;
import br.com.myegoo.app.myego.utils.NotificacaoUtil;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ListenerHabitos {
    public static void listenerRadioButton(RadioButton rb,
                                           TextField textField,
                                           ChoiceBox<String> cbEscolheHabito) {
        rb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!rb.isVisible()) {
                textField.setVisible(false);
                cbEscolheHabito.setVisible(false);
            } else {
                if (newValue) {
                    textField.setVisible(true);
                    cbEscolheHabito.setVisible(false);
                } else {
                    textField.setVisible(false);
                    cbEscolheHabito.setVisible(true);
                }
            }
        });
    }
    public static void listenerInfosListaHabito(CheckBox checkBoxHabitos,
                                                HabitosRepository habitosRepository,
                                                ListView<String> listView,
                                                Button btRemoverHabito,
                                                Label pegaDataHabito,
                                                Label guardaNomeHabito) {

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDate data = LocalDate.parse(pegaDataHabito.getText());
                guardaNomeHabito.setText(newValue);
                Habitos habitoSelecionado = habitosRepository.findByNome(newValue,data);
                checkBoxHabitos.setSelected(habitoSelecionado.isConcluido());
                if (habitoSelecionado.isConcluido()) {
                    checkBoxHabitos.setText("Concluído");
                } else {
                    checkBoxHabitos.setText("Não Concluído");
                }

                checkBoxHabitos.setDisable(false);
                btRemoverHabito.setDisable(false);

            } else {
                checkBoxHabitos.setDisable(true);
                btRemoverHabito.setDisable(true);
                checkBoxHabitos.setText("");
            }
        });
    }
    public static void listenerRemoverHabito(ChoiceBox<String>  cbSelecionarHabito,
                                             Button btRemoverHabito,
                                             Button btAddHabito) {
        cbSelecionarHabito.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btRemoverHabito.setDisable(false);
                btAddHabito.setDisable(false);
            } else {
                btRemoverHabito.setDisable(true);
                btAddHabito.setDisable(true);
            }
        });
    }
    public static void listenerCheckHabito(CheckBox checkBoxHabitos,
                                           Label guardaNomeHabito,
                                           Label dataHabito,
                                           HabitosRepository habitosRepository) {
        checkBoxHabitos.selectedProperty().addListener((observable1, oldValue1, newValueHabito) -> {
            Habitos habitoSelecionado = habitosRepository.findByNome(guardaNomeHabito.getText(), LocalDate.parse(dataHabito.getText()));
            if (newValueHabito) {
                habitoSelecionado.setConcluido(true);
                NotificacaoUtil.mostrarNotificacao("Hábito", "Hábito "+habitoSelecionado.getNome() + " está concluído!", "SUCCESS");
                NotificacaoUtil.tocarSom("/audio/pomodoro.mp3");
                habitosRepository.save(habitoSelecionado);
            } else {
                habitoSelecionado.setConcluido(false);
                habitosRepository.save(habitoSelecionado);
            }
        });
    }
    public static void listenerHabitoHome(ListView<String> homeHabitosLista,
                                          Label homeHabitosNome,
                                          Label homeStatusHabito,
                                          Button btConcluidoHabitoDia,
                                          Button btRemoverHabitoDIa,
                                          HabitosRepository habitosRepository) {
        homeHabitosLista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Habitos habito = habitosRepository.findByNome(newValue, LocalDate.now());
                btConcluidoHabitoDia.setDisable(false);
                btRemoverHabitoDIa.setDisable(false);
                homeHabitosNome.setText(habito.getNome());
                homeStatusHabito.setText(habito.isConcluido() ? "Concluído" : "Não concluído");
            } else {
                btConcluidoHabitoDia.setDisable(true);
                btRemoverHabitoDIa.setDisable(true);
                homeHabitosNome.setText("Selecione um hábito!");
                homeStatusHabito.setText("");
            }
        });
        }
}

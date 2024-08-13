package br.com.myegoo.app.myego.utils;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ValidacaoDados {
    public static void validarDataInserida(DatePicker data, Label labelAvisos) {
        if (data.getValue() == null) {
            labelAvisos.setTextFill(Color.RED);
            labelAvisos.setText("Data não pode ser vazia");
            throw new IllegalArgumentException("Data não pode ser vazia!");
        }
        if (data.getValue().isBefore(data.getChronology().dateNow())) {
            labelAvisos.setTextFill(Color.RED);
            labelAvisos.setText("Data não pode ser menor que a data atual!");
            throw new IllegalArgumentException("Data não pode ser menor que a data atual");
        }
    }
    public static void validaCamposTreino(String nome, ChoiceBox<String> tipo, Label labelAvisos) {
        if (nome.isEmpty() || tipo.getValue() == null) {
            labelAvisos.setTextFill(Color.RED);
            labelAvisos.setText("Preencha os campos!");
            throw new IllegalArgumentException("Preencha os campos!");
        }
    }

    public static void validaRegistroEstudo(TextField tfNomeEstudo, DatePicker dpDataEstudo, Label labelAvisos) {
        if (tfNomeEstudo.getText().isEmpty() || dpDataEstudo.getValue() == null) {
            labelAvisos.setTextFill(Color.RED);
            labelAvisos.setText("Preencha os campos!");
            throw new IllegalArgumentException("Preencha os campos!");
        }
    }
    public static void validaDatas(DatePicker dpAtual, DatePicker dpLimite, Label labelAviso) {
        if (dpAtual.getValue().isAfter(dpLimite.getValue()) ) {
            labelAviso.setTextFill(Color.RED);
            labelAviso.setText("Data atual não pode ser maior que a data limite!");
            throw new IllegalArgumentException("Data atual não pode ser maior que a data limite!");
        } else if (dpLimite.getValue() == null) {
            labelAviso.setTextFill(Color.RED);
            labelAviso.setText("Data limite não pode ser vazia!");
            throw new IllegalArgumentException("Data limite não pode ser vazia!");
        }
    }
}

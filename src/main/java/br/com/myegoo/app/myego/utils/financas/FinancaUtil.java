package br.com.myegoo.app.myego.utils.financas;

import javafx.scene.control.*;

import java.time.LocalDate;

public class FinancaUtil {
    public static DatePicker datePicker = new DatePicker();
    public static void calendarioFinancas(DatePicker calendario,
                                          Button passar,
                                          Button voltar,
                                          Button hoje){
        if (datePicker.getValue() == null) {
            datePicker.setValue(LocalDate.now());
        }

        passar.setOnAction(e -> {
            datePicker.setValue(datePicker.getValue().plusMonths(1));
            calendario.setValue(datePicker.getValue());

        });
        voltar.setOnAction(e -> {
            datePicker.setValue(datePicker.getValue().minusMonths(1));
            calendario.setValue(datePicker.getValue());
        });
        hoje.setOnAction(e -> {
            datePicker.setValue(LocalDate.now());
            calendario.setValue(datePicker.getValue());
        });

    }
    public static void exibeRegistrarCusto(
            Label labelCategoria,
            ChoiceBox<String> cbCategoriaFinanca,
            RadioButton rbNovaCategoriaFinanca,
            Label labelDescricao,
            TextField tfDescricaoFinanca,
            Label labelValor,
            TextField tfValorGasto,
            Label labelData,
            DatePicker dpDataDoRegistro,
            CheckBox cbRepeteCusto,
            DatePicker dpRepeteCustoFinanca,
            Button btSalvarGasto,
            boolean visivel) {
        //noinspection DuplicatedCode
        labelCategoria.setVisible(visivel);
        cbCategoriaFinanca.setVisible(visivel);
        rbNovaCategoriaFinanca.setVisible(visivel);
        labelDescricao.setVisible(visivel);
        tfDescricaoFinanca.setVisible(visivel);
        labelValor.setVisible(visivel);
        btSalvarGasto.setVisible(visivel);

        tfValorGasto.setVisible(visivel);
        labelData.setVisible(visivel);
        dpDataDoRegistro.setVisible(visivel);
        cbRepeteCusto.setVisible(visivel);
        dpRepeteCustoFinanca.setVisible(visivel);

    }
}

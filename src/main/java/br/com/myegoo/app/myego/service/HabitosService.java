package br.com.myegoo.app.myego.service;

import br.com.myegoo.app.myego.model.Habitos;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.HabitosRepository;
import br.com.myegoo.app.myego.utils.NotificacaoUtil;
import br.com.myegoo.app.myego.utils.ValidacaoDados;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HabitosService {
    public static void criarHabito(Label labelAvisosHabitos,
                                   DatePicker datePicker,
                                   TextField textField,
                                   ChoiceBox<String> cbEscolha,
                                   CategoriaRepository categoriaRepository,
                                   HabitosRepository habitosRepository,
                                   RadioButton radioButton,
                                   RadioButton rbRepete,
                                   DatePicker dpDiaHabitoRepeat) {
        Habitos verificaSeExiste = habitosRepository.findByNome(cbEscolha.getValue(),datePicker.getValue());
        if (verificaSeExiste != null) {
            labelAvisosHabitos.setTextFill(Color.RED);
            labelAvisosHabitos.setText("Hábito já cadastrado para o dia " + datePicker.getValue());
            throw new RuntimeException("Hábito já cadastrado para o dia " + datePicker.getValue());
        }

        if (radioButton.isSelected()) {
            if (rbRepete.isSelected()) {
                criarHabitosEmSequencia(labelAvisosHabitos, datePicker, textField, categoriaRepository, habitosRepository, dpDiaHabitoRepeat,cbEscolha,radioButton);
                //////////////
            } else {
                Habitos habitos = new Habitos(textField.getText(),false,datePicker.getValue());
                try {
                    validacaoTextField(labelAvisosHabitos, textField, categoriaRepository);
                    salvarHabito(labelAvisosHabitos, habitosRepository, habitos);
                    labelAvisosHabitos.setText("Hábito " + textField.getText() + "para o dia " + datePicker.getValue() + " adicionado com sucesso");
                } catch (RuntimeException ex) {
                    labelAvisosHabitos.setTextFill(Color.RED);
                    labelAvisosHabitos.setText("Erro ao criar o hábito");
                    throw new RuntimeException(ex.getMessage());
                }
            }

        } else {
            if (rbRepete.isSelected()) {
                criarHabitosEmSequencia(labelAvisosHabitos, datePicker, textField, categoriaRepository, habitosRepository, dpDiaHabitoRepeat,cbEscolha,radioButton);
                //////////////
            } else {
                Habitos habitos = new Habitos(cbEscolha.getValue(),false,datePicker.getValue());
                try {
                    if (cbEscolha.getValue() == null || datePicker.getValue() == null) {
                        labelAvisosHabitos.setText("Preencha os campos!");
                        throw new RuntimeException("O campo de texto não pode estar vazio");
                    }
                    salvarHabito(labelAvisosHabitos, habitosRepository, habitos);
                    labelAvisosHabitos.setText("Hábito " + cbEscolha.getValue() + " no dia " + datePicker.getValue()+ " adicionado com sucesso");
                } catch (RuntimeException ex) {
                    labelAvisosHabitos.setTextFill(Color.RED);
                    labelAvisosHabitos.setText("Erro ao criar o hábito, verifique se os campos estão preenchidos.");
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }
    }

    private static void criarHabitosEmSequencia(Label labelAvisosHabitos, DatePicker datePicker, TextField textField, CategoriaRepository categoriaRepository, HabitosRepository habitosRepository, DatePicker dpDiaHabitoRepeat, ChoiceBox<String> cbNaoRepete, RadioButton radioButton) {
        String nome;
        Habitos habitos;
        if (textField.isVisible()) {
            validacaoTextField(labelAvisosHabitos, textField, categoriaRepository);
            nome = textField.getText();
        } else {
            nome = cbNaoRepete.getValue();
        }
        List<Habitos> verificaSeExiiste = habitosRepository.findByIntervaloDeData(cbNaoRepete.getValue(),datePicker.getValue(),dpDiaHabitoRepeat.getValue());
        if (!verificaSeExiiste.isEmpty()) {
            labelAvisosHabitos.setTextFill(Color.RED);
            labelAvisosHabitos.setText("Existem hábitos já cadastrados dentro do intervalo de datas.");
            throw new RuntimeException("Já existem hábitos cadastrados.");
        }
        LocalDate data = datePicker.getValue();
        ValidacaoDados.validaDatas(datePicker,dpDiaHabitoRepeat,labelAvisosHabitos);
        while (data.isBefore(dpDiaHabitoRepeat.getValue())) {


            if (!textField.isVisible()) {
                habitos = new Habitos(nome,false,data);
                try {
                    salvarHabito(labelAvisosHabitos, habitosRepository, habitos);
                } catch (RuntimeException ex) {
                    labelAvisosHabitos.setTextFill(Color.RED);
                    labelAvisosHabitos.setText("Erro ao criar o hábito");
                    throw new RuntimeException(ex.getMessage());
                }

            } else {
                habitos = new Habitos(nome,false,data); //
                try {
                    salvarHabito(labelAvisosHabitos, habitosRepository, habitos);
                } catch (RuntimeException ex) {
                    labelAvisosHabitos.setTextFill(Color.RED);
                    labelAvisosHabitos.setText("Erro ao criar o hábito");
                    throw new RuntimeException(ex.getMessage());
                }
            }
            data = data.plusDays(1);

            labelAvisosHabitos.setText("Hábito " + nome + "adicionado, de" + data + " até " +  dpDiaHabitoRepeat.getValue() + " com sucesso.");
            CarregaDadosService.carregarHabitos(categoriaRepository,cbNaoRepete);
        }
        radioButton.setSelected(false);
        textField.setText("");
    }

    private static void salvarHabito(Label labelAvisosHabitos, HabitosRepository habitosRepository, Habitos habitos) {
        habitosRepository.save(habitos);
        labelAvisosHabitos.setTextFill(Color.LIGHTGREEN);
    }


    private static void validacaoTextField(Label labelAvisosHabitos, TextField textField, CategoriaRepository categoriaRepository) {
        if (textField.getText().isEmpty()) {
            labelAvisosHabitos.setTextFill(Color.RED);
            labelAvisosHabitos.setText("O campo de texto não pode estar vazio");
            throw new RuntimeException("O campo de texto não pode estar vazio");
        }

        CategoriaService.salvarCategoria(categoriaRepository,textField,"Hábitos");

    }

    public static void removerHabito(Label labelAvisosHabitos,
                                     HabitosRepository habitosRepository,
                                     ListView<String> listaHabitos,
                                     Label guardaDataHabito) {
        LocalDate data;
        if (guardaDataHabito.getText().equals("Home")) {
            data = LocalDate.now();
        } else {
            data = LocalDate.parse(guardaDataHabito.getText());
        }

        try {
            habitosRepository.deleteByNome(listaHabitos.getSelectionModel().getSelectedItem(),data);
            labelAvisosHabitos.setTextFill(Color.LIGHTGREEN);
            labelAvisosHabitos.setText("Hábito removido com sucesso");
        } catch (RuntimeException ex) {
            labelAvisosHabitos.setTextFill(Color.RED);
            labelAvisosHabitos.setText("Erro ao remover o hábito");
            throw new RuntimeException(ex.getMessage());
        }
    }
    public static void concluirHabitoHome(String nomeHabito,
                                          HabitosRepository habitosRepository) {

        Habitos habito = habitosRepository.findByNome(nomeHabito,LocalDate.now());
        try {
            habito.setConcluido(true);
            habitosRepository.save(habito);
            NotificacaoUtil.tocarSom("/audio/pomodoro.mp3");
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}

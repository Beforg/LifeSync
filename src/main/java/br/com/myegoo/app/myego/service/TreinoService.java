package br.com.myegoo.app.myego.service;

import br.com.myegoo.app.myego.model.treino.TabelaTreino;
import br.com.myegoo.app.myego.model.treino.Treino;
import br.com.myegoo.app.myego.repository.TreinoRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import br.com.myegoo.app.myego.utils.TreinoUtil;
import br.com.myegoo.app.myego.utils.ValidacaoDados;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TreinoService {
    private static final TreinoUtil treinoUtil = new TreinoUtil();
    public static void criarTreino(Label labelAvisosTreino,
                                    TextField nomeTreino,
                                    DatePicker dataTreino,
                                    ListView<String> listaDeTreinos,
                                    ChoiceBox<String> cbTipoTreino,
                                    TextArea taDescricaoTreino,
                                    TextField tfNovoTipoTreino,
                                    TreinoRepository treinoRepository,
                                    RadioButton rbNovoTipoTreino,
                                    Button botaoVoltar,
                                    Label guardaNomeTreino,
                                   Pane paneHome) {

        ValidacaoDados.validaCamposTreino(nomeTreino.getText(),cbTipoTreino,labelAvisosTreino);
        String nomeDoTreino = nomeTreino.getText() + " | " + dataTreino.getValue().toString();
        if (treinoRepository.existsByNome(nomeDoTreino) && !botaoVoltar.isVisible()) {
            labelAvisosTreino.setTextFill(Color.RED);
            labelAvisosTreino.setText("Treino já existente!");
            throw new TratadorDeErros("Treino já existente!");
        }
        if (botaoVoltar.isVisible() && !paneHome.isVisible()) {
            if (treinoRepository.existsByNome(nomeDoTreino) && !nomeDoTreino.equals(guardaNomeTreino.getText())) {
                labelAvisosTreino.setTextFill(Color.RED);
                labelAvisosTreino.setText("Treino já existente!");
                throw new TratadorDeErros("Treino já existente!");
            }
           // ValidacaoDados.validarDataInserida(dataTreino, labelAvisosTreino);
            editarTreino(labelAvisosTreino,
                    listaDeTreinos,
                    treinoRepository,
                    nomeTreino,
                    dataTreino,
                    cbTipoTreino,
                    taDescricaoTreino,
                    tfNovoTipoTreino,
                    rbNovoTipoTreino,
                    guardaNomeTreino);
        } else {

            if (rbNovoTipoTreino.isSelected()) {
                ValidacaoDados.validarDataInserida(dataTreino, labelAvisosTreino);
                Treino treino = TreinoUtil.setarTreino(nomeDoTreino,
                        dataTreino.getValue(),
                        tfNovoTipoTreino.getText(),
                        taDescricaoTreino.getText(),
                        "Não registrado",
                        false);
                salvarTreino(labelAvisosTreino, treinoRepository, treino);
                tfNovoTipoTreino.setVisible(false);
                tfNovoTipoTreino.clear();
                rbNovoTipoTreino.setSelected(false);
                cbTipoTreino.setVisible(true);
            } else {
                ValidacaoDados.validarDataInserida(dataTreino, labelAvisosTreino);
                Treino treino = TreinoUtil.setarTreino(nomeDoTreino,
                        dataTreino.getValue(),
                        cbTipoTreino.getValue(),
                        taDescricaoTreino.getText(),
                        "Não registrado",
                        false);
                tfNovoTipoTreino.clear();
                rbNovoTipoTreino.setSelected(false);
                salvarTreino(labelAvisosTreino, treinoRepository, treino);
            }
        }
    }
    public static void editarTreino(Label labelAvisosTreino,
                                     ListView<String> listaTreinos,
                                     TreinoRepository treinoRepository,
                                     TextField nomeTreino,
                                     DatePicker dataTreino,
                                     ChoiceBox<String> cbTipoTreino,
                                     TextArea taDescricaoTreino,
                                     TextField tfNovoTipoTreino,
                                     RadioButton rbNovoTipoTreino,
                                     Label guardaNomeTreino) {
        if (rbNovoTipoTreino.isSelected()) {
            Treino treino = treinoRepository.findByNome(listaTreinos.getSelectionModel().getSelectedItem());
            String nomeDoTreino = nomeTreino.getText() + " | " + dataTreino.getValue().toString();
            treino.setNome(nomeDoTreino);
            treino.setData(dataTreino.getValue());
            treino.setTipo(tfNovoTipoTreino.getText());
            treino.setDescricao(taDescricaoTreino.getText());
            salvarTreino(labelAvisosTreino, treinoRepository, treino);
        } else {
            Treino treino = treinoRepository.findByNome(guardaNomeTreino.getText());
            String nomeDoTreino = nomeTreino.getText() + " | " + dataTreino.getValue().toString();
            treino.setNome(nomeDoTreino);
            treino.setData(dataTreino.getValue());
            treino.setTipo(cbTipoTreino.getValue());
            treino.setDescricao(taDescricaoTreino.getText());
            salvarTreino(labelAvisosTreino, treinoRepository, treino);
            guardaNomeTreino.setText("");
        }
    }

    private static void salvarTreino(Label labelAvisosTreino, TreinoRepository treinoRepository, Treino treino) {
        try {
            treinoRepository.save(treino);
            labelAvisosTreino.setTextFill(Color.LIGHTGREEN);
            labelAvisosTreino.setText("Treino salvo com sucesso!");
        } catch (Exception e) {
            labelAvisosTreino.setTextFill(Color.RED);
            labelAvisosTreino.setText("Erro ao salvar treino!");
            throw new TratadorDeErros("Erro ao salvar treino!");
        }
    }
    public static void apagarTreino(TableView<TabelaTreino> listaDeTreinos, TreinoRepository treinoRepository) {
        Mensagem mensagem = new Mensagem();
        int retorno = mensagem.retornoMessege("Atenção", "Deseja realmente apagar o treino?");
        if (retorno == 1) {
            treinoRepository.deleteByNome(listaDeTreinos.getSelectionModel().getSelectedItem().toString());
        }
    }
    public static void registrarTreino(Label avisosTreinos,
                                       Label labelDoTreino,
                                       TextField duracao,
                                       CheckBox verificacao,
                                       TreinoRepository treinoRepository) {
        Treino treino = treinoRepository.findByNome(labelDoTreino.getText());
        if (verificacao.isSelected()) {
            treino.setTempo("Não consta");
        } else {
            treino.setTempo(duracao.getText());
        }
        treino.setConcluido(true);
        try {
            treinoRepository.save(treino);
            avisosTreinos.setTextFill(Color.LIGHTGREEN);
            avisosTreinos.setText("Treino registrado com sucesso!");
        } catch (Exception e) {
            avisosTreinos.setTextFill(Color.RED);
            avisosTreinos.setText("Erro ao registrar treino!");
            throw new TratadorDeErros("Erro ao registrar treino!");
        }
    }
    public static void carregaProximosTreinos(Label treinoUm,
                                              Label treinoDois,
                                              Label treinoTres,
                                              Label treinoQuatro,
                                              Label nomeDataTreinoUm,
                                              Label nomeDataTreinoDois,
                                              Label nomeDataTreinoTres,
                                              Label nomeDataTreinoQuatro,
                                              TreinoRepository treinoRepository) {
        List<Treino> treinos = treinoRepository.findTop4ByDataAfterOrderByDataAsc(LocalDate.now());
        if (treinos.size() == 1) {
            treinoUm.setText(treinos.get(0).getNome() + " | " + treinos.get(0).getDescricao());
            nomeDataTreinoUm.setText(treinoUtil.formataDataLong(treinos.get(0).getData())+ " | " + treinos.get(0).getTipo());;
            treinoDois.setText("Não há treinos");
            treinoTres.setText("Não há treinos");
            treinoQuatro.setText("Não há treinos");
        } else if (treinos.size() == 2) {
            treinoUm.setText(treinos.get(0).getNome() + " | " + treinos.get(0).getDescricao());
            nomeDataTreinoUm.setText(treinoUtil.formataDataLong(treinos.get(0).getData())+ " | " + treinos.get(0).getTipo());;
            treinoDois.setText(treinos.get(1).getNome() + " | " + treinos.get(1).getDescricao());
            nomeDataTreinoDois.setText(treinoUtil.formataDataLong(treinos.get(1).getData()) + " | " + treinos.get(1).getTipo());
            treinoTres.setText("Não há treinos");
            treinoQuatro.setText("Não há treinos");
        } else if (treinos.size() == 3) {
            setTreinos(treinoUm, treinoDois, treinoTres, nomeDataTreinoUm, nomeDataTreinoDois, nomeDataTreinoTres, treinos);
            treinoQuatro.setText("Não há treinos");
        } else if (treinos.size() == 4) {
            setTreinos(treinoUm, treinoDois, treinoTres, nomeDataTreinoUm, nomeDataTreinoDois, nomeDataTreinoTres, treinos);
            treinoQuatro.setText(treinos.get(3).getNome() + " | " + treinos.get(3).getDescricao());
            nomeDataTreinoQuatro.setText(treinoUtil.formataDataLong(treinos.get(3).getData()) + " | " + treinos.get(3).getTipo());
        }
    }

    private static void setTreinos(Label treinoUm, Label treinoDois, Label treinoTres, Label nomeDataTreinoUm, Label nomeDataTreinoDois, Label nomeDataTreinoTres, List<Treino> treinos) {
        treinoUm.setText(treinos.get(0).getNome() + " | " + treinos.get(0).getDescricao());
        nomeDataTreinoUm.setText(treinoUtil.formataDataLong(treinos.get(0).getData())+ " | " + treinos.get(0).getTipo());;
        treinoDois.setText(treinos.get(1).getNome()+ " | " + treinos.get(1).getDescricao());
        nomeDataTreinoDois.setText(treinoUtil.formataDataLong(treinos.get(1).getData()) + " | " + treinos.get(1).getTipo());
        treinoTres.setText(treinos.get(2).getNome() + " | " + treinos.get(2).getDescricao());
        nomeDataTreinoTres.setText(treinoUtil.formataDataLong(treinos.get(2).getData())+ " | " + treinos.get(2).getTipo());
    }
}

package br.com.myegoo.app.myego.service.projeto;

import br.com.myegoo.app.myego.model.projetos.Projeto;
import br.com.myegoo.app.myego.model.projetos.ProjetoCard;
import br.com.myegoo.app.myego.model.projetos.TabelaItensCard;
import br.com.myegoo.app.myego.repository.ItemProjetoCardRepository;
import br.com.myegoo.app.myego.repository.ProjetoCardRepository;
import br.com.myegoo.app.myego.repository.ProjetoRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import br.com.myegoo.app.myego.utils.NotificacaoUtil;
import br.com.myegoo.app.myego.utils.projeto.ProjetoUtil;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjetoCardService {
    private static final ProjetoUtil projetoUtil = new ProjetoUtil();
    private static final String styleIndicadora =
            "-fx-background-color: linear-gradient(to bottom, #352f44, rgb(92, 84, 112));" +
                    "-fx-border-color: rgb(92, 84, 112);" +
            "    -fx-border-width: 1px;" +
            "    -fx-border-radius: 5px;" +
            "    -fx-background-radius: 5px;" +
            "    -fx-font-size: 15px;" +
            "    -fx-padding: 10px;" +
            "    -fx-text-fill: #ffffff;" +
            "-fx-max-height: 200px;" +
             "-fx-max-width: 300px;" +
                    "-fx-font-family: 'Yu Gothic UI Semibold';";
    private static final String styleIndicadoraHover = styleIndicadora + "-fx-background-color: rgba(150,156,71,0.75)";
    private static Map<Label, ProjetoCard> labelParaOCard = new HashMap<>();

    public static void criarCardEAdicionarAoVBox(TextField tfNomeCard,
                                                 TextArea taCriarCard,
                                                 ChoiceBox<String> cbEscolheProjetoCard,
                                                 DatePicker dpCriarCard,
                                                 Label labelAvisosProjetos,
                                                 ProjetoCardRepository projetoCardRepository,
                                                 ProjetoRepository projetoRepository,
                                                 VBox naoIniciados,
                                                 Label nomeDataCard,
                                                 Label descricaoCard,
                                                 ChoiceBox<String> cbStatusCard,
                                                 TableView<TabelaItensCard> tabelaitens,
                                                 ItemProjetoCardRepository itemProjetoCardRepository,
                                                 TableColumn<TabelaItensCard, String> colunaNome) {
        if (tfNomeCard.getText().isEmpty() || taCriarCard.getText().isEmpty() || dpCriarCard.getValue() == null || cbEscolheProjetoCard.getValue() == null) {
            labelAvisosProjetos.setTextFill(Color.RED);
            labelAvisosProjetos.setText("Preencha todos os campos!");
            throw new TratadorDeErros("Preencha todos os campos!");
        }
        try {
            if  (projetoCardRepository.findByNome(tfNomeCard.getText()) != null) {
                labelAvisosProjetos.setText("Já existe um card com esse nome!");
                throw new TratadorDeErros("Já existe um card com esse nome!");
            }
            ProjetoCard projetoCard = new ProjetoCard(tfNomeCard.getText(),
                    taCriarCard.getText(),
                    dpCriarCard.getValue(),
                    cbEscolheProjetoCard.getValue());
            projetoCard.setStatus("Não iniciado");
            Projeto projeto = projetoRepository.findByNome(cbEscolheProjetoCard.getValue());
            projetoCard.setProjeto(projeto);
            projetoCardRepository.save(projetoCard);
            labelAvisosProjetos.setTextFill(Color.LIGHTGREEN);
            labelAvisosProjetos.setText("Card criado com sucesso!");
            taCriarCard.setText("");
            tfNomeCard.setText("");
            dpCriarCard.setValue(null);


            mouseEvent(nomeDataCard, descricaoCard, projetoCard, cbStatusCard, tabelaitens, itemProjetoCardRepository,colunaNome);

        } catch (Exception e) {
            labelAvisosProjetos.setTextFill(Color.RED);
            labelAvisosProjetos.setText("Erro ao criar card!");
            throw new TratadorDeErros("Erro ao criar card!");
        }
    }
    public static void carregaDadosParaEdicaoDoCard(Label labelGuardaNomeCard,
                                                    TextField tfEditaCard,
                                                    DatePicker dpEditaCard,
                                                    TextArea taEditCard,
                                                    ChoiceBox<String> cbEditaCard,
                                                    ProjetoCardRepository projetoCardRepository) {
        ProjetoCard projetoCard = projetoCardRepository.findByNome(labelGuardaNomeCard.getText());
        tfEditaCard.setText(projetoCard.getNome());
        dpEditaCard.setValue(projetoCard.getData());
        taEditCard.setText(projetoCard.getDescricao());
        cbEditaCard.setValue(projetoCard.getProjeto().getNome());
    }
    public static void editarCard(TextField tfEditaCard,
                                  DatePicker dpEditaCard,
                                  TextArea taEditCard,
                                  ChoiceBox<String> cbEditaCard,
                                  Label labelGuardaNomeCard,
                                  ProjetoCardRepository projetoCardRepository,
                                  ProjetoRepository projetoRepository,
                                  Label labelDeAvisos,
                                  GridPane gridPaneEditaCard,
                                  GridPane meusProjetos) {

        if (tfEditaCard.getText().isEmpty() || dpEditaCard.getValue() == null || cbEditaCard.getValue() == null) {
            labelDeAvisos.setTextFill(Color.RED);
            labelDeAvisos.setText("Preencha todos os campos!");
            throw new TratadorDeErros("Preencha todos os campos!");
        }
        Projeto projetoSelecionado = projetoRepository.findByNome(cbEditaCard.getValue());
        try {
            ProjetoCard projetoCard = projetoCardRepository.findByNome(labelGuardaNomeCard.getText());
            projetoCard.setNome(tfEditaCard.getText());
            projetoCard.setData(dpEditaCard.getValue());
            projetoCard.setDescricao(taEditCard.getText());
            projetoCard.setProjeto(projetoSelecionado);
            projetoCardRepository.save(projetoCard);
            labelDeAvisos.setTextFill(Color.LIGHTGREEN);
            labelDeAvisos.setText("Card editado com sucesso!");
            tfEditaCard.setText("");
            dpEditaCard.setValue(null);
            taEditCard.setText("");
            cbEditaCard.setValue("");
            gridPaneEditaCard.setVisible(false);
            meusProjetos.setVisible(true);
        } catch (Exception e) {
            labelDeAvisos.setTextFill(Color.RED);
            labelDeAvisos.setText("Erro ao editar card!");
            throw new TratadorDeErros("Erro ao editar card!");
        }
    }
    public static void removerCard(Label guardaNomeCard,
                                   ProjetoCardRepository projetoCardRepository,
                                   Label labelAvisosProjetos) {
        Mensagem mensagem = new Mensagem();
        int retorno = mensagem.retornoMessege("Remover card","Deseja realmente remover o card?");
        if (retorno == 1) {
            try {
                ProjetoCard projetoCard = projetoCardRepository.findByNome(guardaNomeCard.getText());
                projetoCardRepository.deleteByNome(projetoCard.getNome());
                labelAvisosProjetos.setTextFill(Color.LIGHTGREEN);
                labelAvisosProjetos.setText("Card removido com sucesso!");
            } catch (Exception e) {
                labelAvisosProjetos.setTextFill(Color.RED);
                labelAvisosProjetos.setText("Erro ao remover card! Verifique se o car possui itens associados antes de remover!");
                throw new TratadorDeErros("Erro ao remover card!");
            }
        }
    }

    public static void carregaCardsNoMural(VBox naoIniciadas,
                                           VBox emAndamento,
                                           VBox concluidas,
                                           ProjetoCardRepository projetoCardRepository,
                                           String projeto,
                                           Label nomeDataCard,
                                           Label descricaoCard,
                                           ChoiceBox<String> cbStatusCard,
                                           TableView<TabelaItensCard> tabelaitens,
                                           ItemProjetoCardRepository itemProjetoCardRepository,
                                           Label naoIniciadasCount,Label emAndamentoCount,Label completoCount,
                                           Label diasRestantesProjetos,
                                           ProjetoRepository projetoRepository,
                                           TableColumn<TabelaItensCard, String> colunaNome,
                                           ProgressBar pgProjetos,
                                           Label labelProgressoProjetos){
        naoIniciadas.getChildren().clear();
        emAndamento.getChildren().clear();
        concluidas.getChildren().clear();
        int countNaoIniciadas = 0;
        int countEmAndamento = 0;
        int countConcluidas = 0;
        Projeto projetoEscolhido = projetoRepository.findByNome(projeto);
        int diasRestantes = Integer.parseInt(String.valueOf(projetoEscolhido.getData().getDayOfYear() - LocalDate.now().getDayOfYear()));
        if (diasRestantes < 0) {
            diasRestantesProjetos.setText("Projeto atrasado em " + Math.abs(diasRestantes) + " dias");
        } else {
            diasRestantesProjetos.setText("Dias restantes: " + diasRestantes);
        }
        List<ProjetoCard> cards = projetoCardRepository.findByProjeto(projeto);
        for (ProjetoCard projetoCard : cards) {
            Label label = mouseEvent(nomeDataCard, descricaoCard, projetoCard,cbStatusCard,tabelaitens,itemProjetoCardRepository,colunaNome);


            switch (projetoCard.getStatus()) {
                case "Não iniciado":
                    countNaoIniciadas++;
                    if (naoIniciadas.getChildren().stream().filter(node -> node instanceof Label).count() <11) {
                        naoIniciadas.getChildren().add(label);
                    }
                    break;
                case "Em andamento":
                    countEmAndamento++;
                    if (emAndamento.getChildren().stream().filter(node -> node instanceof Label).count() <11) {
                        emAndamento.getChildren().add(label);
                    }
                    break;
                case "Concluído":
                    countConcluidas++;
                    if (concluidas.getChildren().stream().filter(node -> node instanceof Label).count() <11) {
                        concluidas.getChildren().add(label);
                    }
                    break;
            }
            if (countNaoIniciadas > 11) {
                naoIniciadasCount.setText("Não iniciadas: " +countNaoIniciadas+ " cards" + "(" + (countNaoIniciadas - 11)+" oculto(s))");
            } else {
                naoIniciadasCount.setText("Não iniciadas: " +countNaoIniciadas + " cards");
            } if (countEmAndamento > 11) {
                emAndamentoCount.setText("Em andamento: " +countEmAndamento+ " cards" + "(" + (countEmAndamento - 11)+" oculto(s))");
            } else {
                emAndamentoCount.setText("Em andamento: " +countEmAndamento + " cards");
            } if (countConcluidas > 11) {
                completoCount.setText("Concluídas: " +countConcluidas+ " cards" + "(" + (countConcluidas - 11)+" oculto(s))");
            } else {
                completoCount.setText("Concluídas: " +countConcluidas + " cards");
            }
        }
        int totalDeCards = countNaoIniciadas + countEmAndamento + countConcluidas;
        double progresso = (double) countConcluidas /totalDeCards;
        pgProjetos.setProgress(progresso);
        labelProgressoProjetos.setText("Progresso: " + (int) (progresso * 100) + "%");
        if (progresso == 1.0) {
            labelProgressoProjetos.setTextFill(Color.LIGHTGREEN);
            NotificacaoUtil.mostrarNotificacao("Projeto", "Projeto " + projeto + " concluído com sucesso", "SUCCESS");
            NotificacaoUtil.tocarSom("/audio/done.mp3");
        } else{
            labelProgressoProjetos.setTextFill(Color.LIGHTGRAY);
        }

        ItemProjetoCardService.carregarItemNoCard(tabelaitens,itemProjetoCardRepository,nomeDataCard);
    }

    private static Label mouseEvent(Label nomeDataCard,
                                    Label descricaoCard,
                                    ProjetoCard projetoCard,
                                    ChoiceBox<String> cbStatusCard,
                                    TableView<TabelaItensCard> tabelaitens,
                                    ItemProjetoCardRepository itemProjetoCardRepository,
                                    TableColumn<TabelaItensCard, String> colunaNome) {
        Label label = new Label(projetoCard.getNome() +" | " + projetoUtil.formataData(projetoCard.getData()));
       // label.setPrefSize(235, 200);
//        label.setMaxHeight(200);
//        label.setMaxWidth(235);
        label.wrapTextProperty().setValue(true);
        label.setStyle(styleIndicadora);
        label.setOnMouseEntered(event -> label.setStyle(styleIndicadoraHover));
        label.setOnMouseExited(event -> label.setStyle(styleIndicadora));
        label.setOnMouseClicked(event -> {
            cbStatusCard.setDisable(false);
            ItemProjetoCardService.carregarItemNoCard(tabelaitens,itemProjetoCardRepository,nomeDataCard);
            System.out.println("Clicou no card");
            nomeDataCard.setText(projetoCard.getNome());
            colunaNome.setText("Itens do card " + projetoCard.getNome());
            descricaoCard.setText("Descrição: " + projetoCard.getDescricao());
            if (projetoCard.getStatus().equals("Não iniciado")) {
                cbStatusCard.setValue(projetoCard.getStatus());

            } else if (projetoCard.getStatus().equals("Em andamento")) {
                cbStatusCard.setValue(projetoCard.getStatus());

            } else {
                cbStatusCard.setValue(projetoCard.getStatus());

            }
        });
        labelParaOCard.put(label, projetoCard);
        return label;
    }

}






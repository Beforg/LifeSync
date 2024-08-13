package br.com.myegoo.app.myego.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


import java.net.URL;
import java.util.Objects;

public class NotificacaoUtil
{
    public static void mostrarNotificacao(String titulo, String mensagem, String tipo) {
        ImageView icone = null;
        if (tipo.equals("INFO")) {
            icone = new ImageView(new Image(Objects.requireNonNull(NotificacaoUtil.class.getResourceAsStream("/images/ico/info.png"))));
        } else if (tipo.equals("ERROR")) {
            icone = new ImageView(new Image(Objects.requireNonNull(NotificacaoUtil.class.getResourceAsStream("/images/ico/erro.png"))));
        } else if (tipo.equals("SUCCESS")) {
            icone = new ImageView(new Image(Objects.requireNonNull(NotificacaoUtil.class.getResourceAsStream("/images/ico/done.png"))));
        }

        if (icone != null) {
            icone.setFitHeight(35);
            icone.setFitWidth(35);
        }

        Notifications.create()
                .title(titulo)
                .text(mensagem)
                .graphic(icone)
                .hideAfter(Duration.seconds(5))
                .show();
    }
    public static void tocarSom(String caminhoDoSom) {
        URL resource = NotificacaoUtil.class.getResource(caminhoDoSom);
        if (resource == null) {
            throw new IllegalArgumentException("Arquivo de som não encontrado: " + caminhoDoSom);
        }
        Media media = new Media(resource.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}

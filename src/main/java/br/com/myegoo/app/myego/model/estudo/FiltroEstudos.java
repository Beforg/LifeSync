package br.com.myegoo.app.myego.model.estudo;

public enum FiltroEstudos {
    TODOS("Todos"),
    MATERIA("Matéria"),
    AREA("Área"),

    CONTEUDO("Conteúdo");

    private final String descricao;

    FiltroEstudos(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

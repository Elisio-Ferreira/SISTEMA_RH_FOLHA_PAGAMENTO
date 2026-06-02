package enums;

public enum TipoOcorrencia {
    PRESENTE("Presente"),
    FALTA("Falta"),
    FALTA_JUSTIFICADA("Falta Justificada"),
    HORA_EXTRA("Hora Extra"),
    ATESTADO("Atestado Médico"),
    FOLGA("Folga");

    private final String descricao;

    TipoOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
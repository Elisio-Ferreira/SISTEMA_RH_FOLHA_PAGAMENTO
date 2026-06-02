package enums;

public enum NivelAcesso {
    ADMIN("Administrador"),
    RH("Recursos Humanos"),
    GERENTE("Gerente"),
    FUNCIONARIO("Funcionário");

    private final String descricao;

    NivelAcesso(String descricao) {
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
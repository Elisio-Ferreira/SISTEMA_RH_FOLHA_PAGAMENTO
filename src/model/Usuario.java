package model;

import enums.NivelAcesso;

public class Usuario {
    private int id;
    private String login;
    private String senha;
    private NivelAcesso nivelAcesso;
    private Funcionario funcionario;
    private boolean ativo;

    public Usuario() {}

    public Usuario(int id, String login, String senha,
                   NivelAcesso nivelAcesso, Funcionario funcionario) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
        this.funcionario = funcionario;
        this.ativo = true;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public NivelAcesso getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public String toString() {
        return String.format("Usuario[id=%d, login=%s, nivel=%s, ativo=%b]",
                id, login, nivelAcesso, ativo);
    }
}
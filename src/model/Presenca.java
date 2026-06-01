package model;

import enums.TipoOcorrencia;
import java.time.LocalDate;

public class Presenca {
    private int id;
    private Funcionario funcionario;
    private LocalDate data;
    private TipoOcorrencia tipo;
    private double horasExtras;
    private String observacao;

    public Presenca() {}

    public Presenca(int id, Funcionario funcionario, LocalDate data,
                    TipoOcorrencia tipo, double horasExtras, String observacao) {
        this.id = id;
        this.funcionario = funcionario;
        this.data = data;
        this.tipo = tipo;
        this.horasExtras = horasExtras;
        this.observacao = observacao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public TipoOcorrencia getTipo() { return tipo; }
    public void setTipo(TipoOcorrencia tipo) { this.tipo = tipo; }

    public double getHorasExtras() { return horasExtras; }
    public void setHorasExtras(double horasExtras) { this.horasExtras = horasExtras; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    @Override
    public String toString() {
        return String.format("Presenca[id=%d, funcionario=%s, data=%s, tipo=%s, horasExtras=%.1f]",
                id, funcionario != null ? funcionario.getNome() : "N/A", data, tipo, horasExtras);
    }
}
package model;

public class Cargo {
    private int id;
    private String nome;
    private String descricao;
    private double salarioBase;
    private int cargaHorariaMensal;

    public Cargo() {}

    public Cargo(int id, String nome, String descricao, double salarioBase, int cargaHorariaMensal) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.salarioBase = salarioBase;
        this.cargaHorariaMensal = cargaHorariaMensal;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public int getCargaHorariaMensal() { return cargaHorariaMensal; }
    public void setCargaHorariaMensal(int cargaHorariaMensal) { this.cargaHorariaMensal = cargaHorariaMensal; }

    @Override
    public String toString() {
        return String.format("Cargo[id=%d, nome=%s, salarioBase=R$%.2f]", id, nome, salarioBase);
    }
}
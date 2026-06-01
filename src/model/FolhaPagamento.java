package model;

import model.interfaces.Folha;
import java.time.LocalDate;
import java.util.List;

public class FolhaPagamento implements Folha {
    private int id;
    private Funcionario funcionario;
    private int mes;
    private int ano;
    private double salarioBase;
    private int totalFaltas;
    private double totalHorasExtras;
    private double valorHoraExtra;
    private double descontoINSS;
    private double descontoIRRF;
    private double outrosDescontos;
    private double bonificacoes;
    private LocalDate dataEmissao;

    private static final double PERCENTUAL_INSS = 0.11;
    private static final double PERCENTUAL_IRRF = 0.075;
    private static final double VALOR_HORA_EXTRA_PADRAO = 1.5;

    public FolhaPagamento() {}

    public FolhaPagamento(int id, Funcionario funcionario, int mes, int ano) {
        this.id = id;
        this.funcionario = funcionario;
        this.mes = mes;
        this.ano = ano;
        this.salarioBase = funcionario.getCargo().getSalarioBase();
        this.dataEmissao = LocalDate.now();
        calcularValorHoraExtra();
    }

    private void calcularValorHoraExtra() {
        int cargaHoraria = funcionario.getCargo().getCargaHorariaMensal();
        double valorHoraNormal = salarioBase / cargaHoraria;
        this.valorHoraExtra = valorHoraNormal * VALOR_HORA_EXTRA_PADRAO;
    }

    @Override
    public double calcularSalarioBruto() {
        double descFaltas = (totalFaltas > 0)
                ? (salarioBase / 30.0) * totalFaltas : 0;
        double adicionalHorasExtras = totalHorasExtras * valorHoraExtra;
        return salarioBase - descFaltas + adicionalHorasExtras + bonificacoes;
    }

    @Override
    public double calcularDescontos() {
        double bruto = calcularSalarioBruto();
        descontoINSS = bruto * PERCENTUAL_INSS;
        descontoIRRF = bruto * PERCENTUAL_IRRF;
        return descontoINSS + descontoIRRF + outrosDescontos;
    }

    @Override
    public double calcularSalarioLiquido() {
        return calcularSalarioBruto() - calcularDescontos();
    }

    @Override
    public void gerarContracheque() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("              CONTRACHEQUE");
        System.out.println("=".repeat(55));
        System.out.printf("Funcionário : %s%n", funcionario.getNome());
        System.out.printf("CPF         : %s%n", funcionario.getCpf());
        System.out.printf("Cargo       : %s%n", funcionario.getCargo().getNome());
        System.out.printf("Competência : %02d/%d%n", mes, ano);
        System.out.printf("Emissão     : %s%n", dataEmissao);
        System.out.println("-".repeat(55));
        System.out.printf("Salário Base          : R$ %10.2f%n", salarioBase);
        System.out.printf("Faltas (%d dias)       : R$ %10.2f%n",
                totalFaltas, (salarioBase / 30.0) * totalFaltas);
        System.out.printf("Horas Extras (%.1fh)  : R$ %10.2f%n",
                totalHorasExtras, totalHorasExtras * valorHoraExtra);
        System.out.printf("Bonificações          : R$ %10.2f%n", bonificacoes);
        System.out.println("-".repeat(55));
        System.out.printf("SALÁRIO BRUTO         : R$ %10.2f%n", calcularSalarioBruto());
        System.out.println("-".repeat(55));
        System.out.printf("Desconto INSS (11%%)   : R$ %10.2f%n", descontoINSS);
        System.out.printf("Desconto IRRF (7.5%%)  : R$ %10.2f%n", descontoIRRF);
        System.out.printf("Outros Descontos      : R$ %10.2f%n", outrosDescontos);
        System.out.println("-".repeat(55));
        System.out.printf("SALÁRIO LÍQUIDO       : R$ %10.2f%n", calcularSalarioLiquido());
        System.out.println("=".repeat(55));
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public int getTotalFaltas() { return totalFaltas; }
    public void setTotalFaltas(int totalFaltas) { this.totalFaltas = totalFaltas; }

    public double getTotalHorasExtras() { return totalHorasExtras; }
    public void setTotalHorasExtras(double totalHorasExtras) { this.totalHorasExtras = totalHorasExtras; }

    public double getBonificacoes() { return bonificacoes; }
    public void setBonificacoes(double bonificacoes) { this.bonificacoes = bonificacoes; }

    public double getOutrosDescontos() { return outrosDescontos; }
    public void setOutrosDescontos(double outrosDescontos) { this.outrosDescontos = outrosDescontos; }

    public LocalDate getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }
}
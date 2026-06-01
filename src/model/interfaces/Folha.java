package model.interfaces;

public interface Folha {
    double calcularSalarioBruto();

    double calcularDescontos();

    double calcularSalarioLiquido();

    void gerarContracheque();
}

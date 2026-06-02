package controller;

import model.FolhaPagamento;
import model.Funcionario;
import service.FolhaPagamentoService;
import service.PresencaService;
import java.util.List;
import java.util.Scanner;

public class FolhaPagamentoController {
    private final FolhaPagamentoService service;
    private final Scanner scanner;
    private final FuncionarioController funcionarioController;

    public FolhaPagamentoController(Scanner scanner,
                                    FuncionarioController funcionarioController,
                                    PresencaService presencaService) {
        this.scanner = scanner;
        this.funcionarioController = funcionarioController;
        this.service = new FolhaPagamentoService(presencaService);
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n===== FOLHA DE PAGAMENTO =====");
            System.out.println("1. Gerar folha (funcionário)");
            System.out.println("2. Gerar folha completa (todos)");
            System.out.println("3. Emitir contracheque");
            System.out.println("4. Listar por período");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> gerarFolha();
                case 2 -> gerarFolhaCompleta();
                case 3 -> emitirContracheque();
                case 4 -> listarPorPeriodo();
            }
        } while (opcao != 0);
    }

    private void gerarFolha() {
        System.out.print("ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        Funcionario f = funcionarioController.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
        try {
            FolhaPagamento folha = service.gerar(f, mes, ano);
            System.out.printf("✔ Folha gerada. Líquido: R$ %.2f%n", folha.calcularSalarioLiquido());
        } catch (Exception e) {
            System.out.println("✖ Erro: " + e.getMessage());
        }
    }

    private void gerarFolhaCompleta() {
        System.out.print("Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        List<Funcionario> ativos = funcionarioController.listarAtivos();
        ativos.forEach(f -> {
            try {
                service.gerar(f, mes, ano);
                System.out.printf("✔ Folha gerada para %s%n", f.getNome());
            } catch (Exception e) {
                System.out.printf("✖ %s: %s%n", f.getNome(), e.getMessage());
            }
        });
    }

    private void emitirContracheque() {
        System.out.print("ID da folha: ");
        int id = Integer.parseInt(scanner.nextLine());
        service.buscarPorId(id).ifPresentOrElse(
                FolhaPagamento::gerarContracheque,
                () -> System.out.println("✖ Folha não encontrada."));
    }

    private void listarPorPeriodo() {
        System.out.print("Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        List<FolhaPagamento> lista = service.listarPorPeriodo(mes, ano);
        if (lista.isEmpty()) { System.out.println("Nenhuma folha encontrada."); return; }
        double totalBruto = 0, totalLiquido = 0;
        System.out.printf("%-5s %-25s %-10s %-12s %-12s%n",
                "ID", "Funcionário", "Faltas", "Bruto", "Líquido");
        System.out.println("-".repeat(70));
        for (FolhaPagamento f : lista) {
            System.out.printf("%-5d %-25s %-10d R$%-10.2f R$%-10.2f%n",
                    f.getId(), f.getFuncionario().getNome(),
                    f.getTotalFaltas(),
                    f.calcularSalarioBruto(),
                    f.calcularSalarioLiquido());
            totalBruto += f.calcularSalarioBruto();
            totalLiquido += f.calcularSalarioLiquido();
        }
        System.out.println("-".repeat(70));
        System.out.printf("TOTAL BRUTO: R$ %.2f | TOTAL LÍQUIDO: R$ %.2f%n", totalBruto, totalLiquido);
    }

    public FolhaPagamentoService getService() {
        return service;
    }
}
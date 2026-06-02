package controller;

import enums.TipoOcorrencia;
import model.Funcionario;
import model.Presenca;
import service.PresencaService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PresencaController {
    private final PresencaService service = new PresencaService();
    private final Scanner scanner;
    private final FuncionarioController funcionarioController;

    public PresencaController(Scanner scanner, FuncionarioController funcionarioController) {
        this.scanner = scanner;
        this.funcionarioController = funcionarioController;
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n===== CONTROLE DE PRESENÇA =====");
            System.out.println("1. Registrar ocorrência");
            System.out.println("2. Listar por funcionário");
            System.out.println("3. Listar por período");
            System.out.println("4. Resumo mensal");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> registrar();
                case 2 -> listarPorFuncionario();
                case 3 -> listarPorPeriodo();
                case 4 -> resumoMensal();
            }
        } while (opcao != 0);
    }

    private void registrar() {
        System.out.print("ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        Funcionario f = funcionarioController.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());
        System.out.println("Tipo: 1-PRESENTE  2-FALTA  3-FALTA_JUSTIFICADA  4-HORA_EXTRA  5-ATESTADO  6-FOLGA");
        System.out.print("Opção: ");
        int t = Integer.parseInt(scanner.nextLine());
        TipoOcorrencia tipo = TipoOcorrencia.values()[t - 1];
        System.out.print("Horas extras (0 se não houver): ");
        double he = Double.parseDouble(scanner.nextLine());
        System.out.print("Observação: ");
        String obs = scanner.nextLine();
        try {
            Presenca p = service.registrar(f, data, tipo, he, obs);
            System.out.println("✔ Registrado: " + p);
        } catch (Exception e) {
            System.out.println("✖ Erro: " + e.getMessage());
        }
    }

    private void listarPorFuncionario() {
        System.out.print("ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        List<Presenca> lista = service.listarPorFuncionario(id);
        if (lista.isEmpty()) System.out.println("Nenhum registro.");
        else lista.forEach(System.out::println);
    }

    private void listarPorPeriodo() {
        System.out.print("ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        service.listarPorPeriodo(id, mes, ano).forEach(System.out::println);
    }

    private void resumoMensal() {
        System.out.print("ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        System.out.printf("Faltas: %d | Horas Extras: %.1fh%n",
                service.contarFaltas(id, mes, ano),
                service.somarHorasExtras(id, mes, ano));
    }

    public PresencaService getService() {
        return service;
    }
}
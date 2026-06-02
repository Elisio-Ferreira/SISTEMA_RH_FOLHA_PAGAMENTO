package controller;

import model.Cargo;
import model.Funcionario;
import service.FuncionarioService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FuncionarioController {
    private final FuncionarioService service = new FuncionarioService();
    private final Scanner scanner;
    private final CargoController cargoController;

    public FuncionarioController(Scanner scanner, CargoController cargoController) {
        this.scanner = scanner;
        this.cargoController = cargoController;
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n===== FUNCIONÁRIOS =====");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por nome");
            System.out.println("4. Atualizar");
            System.out.println("5. Desativar");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listarTodos();
                case 3 -> buscarPorNome();
                case 4 -> atualizar();
                case 5 -> desativar();
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Funcionário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        LocalDate nascimento = LocalDate.parse(scanner.nextLine());
        System.out.print("Data de Admissão (AAAA-MM-DD): ");
        LocalDate admissao = LocalDate.parse(scanner.nextLine());

        cargoController.listarTodos();
        System.out.print("ID do Cargo: ");
        int cargoId = Integer.parseInt(scanner.nextLine());
        Cargo cargo = cargoController.buscarPorId(cargoId)
                .orElseThrow(() -> new IllegalArgumentException("Cargo não encontrado."));

        try {
            Funcionario f = service.cadastrar(nome, cpf, email, telefone, nascimento, admissao, cargo);
            System.out.println("✔ Funcionário cadastrado: " + f);
        } catch (Exception e) {
            System.out.println("✖ Erro: " + e.getMessage());
        }
    }

    private void listarTodos() {
        List<Funcionario> lista = service.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum funcionário cadastrado."); return; }
        System.out.println("\n--- Lista de Funcionários ---");
        lista.forEach(System.out::println);
    }

    private void buscarPorNome() {
        System.out.print("Nome para busca: ");
        String nome = scanner.nextLine();
        List<Funcionario> lista = service.buscarPorNome(nome);
        if (lista.isEmpty()) System.out.println("Nenhum encontrado.");
        else lista.forEach(System.out::println);
    }

    private void atualizar() {
        System.out.print("ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo email: ");
        String email = scanner.nextLine();
        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();
        cargoController.listarTodos();
        System.out.print("ID do novo cargo: ");
        int cargoId = Integer.parseInt(scanner.nextLine());
        Cargo cargo = cargoController.buscarPorId(cargoId)
                .orElseThrow(() -> new IllegalArgumentException("Cargo não encontrado."));
        try {
            Funcionario f = service.atualizar(id, nome, email, telefone, cargo);
            System.out.println("✔ Atualizado: " + f);
        } catch (Exception e) {
            System.out.println("✖ Erro: " + e.getMessage());
        }
    }

    private void desativar() {
        System.out.print("ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(service.desativar(id) ? "✔ Desativado." : "✖ Não encontrado.");
    }

    public Optional<Funcionario> buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    public List<Funcionario> listarAtivos() {
        return service.listarAtivos();
    }

    public List<Funcionario> getTodos() {
        return service.listarTodos();
    }
}
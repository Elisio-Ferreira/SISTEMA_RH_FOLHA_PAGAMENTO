package controller;

import model.Cargo;
import service.CargoService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CargoController {
    private final CargoService service = new CargoService();
    private final Scanner scanner;

    public CargoController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n===== CARGOS =====");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar todos");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listarTodos();
                case 3 -> atualizar();
                case 4 -> remover();
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Cargo ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Salário base: ");
        double salario = Double.parseDouble(scanner.nextLine());
        System.out.print("Carga horária mensal (horas): ");
        int carga = Integer.parseInt(scanner.nextLine());
        try {
            Cargo c = service.cadastrar(nome, descricao, salario, carga);
            System.out.println("✔ Cargo cadastrado: " + c);
        } catch (Exception e) {
            System.out.println("✖ Erro: " + e.getMessage());
        }
    }

    public void listarTodos() {
        List<Cargo> lista = service.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum cargo cadastrado."); return; }
        System.out.println("\n--- Cargos ---");
        lista.forEach(System.out::println);
    }

    private void atualizar() {
        listarTodos();
        System.out.print("ID do cargo: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nova descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Novo salário base: ");
        double salario = Double.parseDouble(scanner.nextLine());
        System.out.print("Nova carga horária: ");
        int carga = Integer.parseInt(scanner.nextLine());
        try {
            Cargo c = service.atualizar(id, nome, descricao, salario, carga);
            System.out.println("✔ Atualizado: " + c);
        } catch (Exception e) {
            System.out.println("✖ Erro: " + e.getMessage());
        }
    }

    private void remover() {
        listarTodos();
        System.out.print("ID do cargo: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(service.remover(id) ? "✔ Removido." : "✖ Não encontrado.");
    }

    public Optional<Cargo> buscarPorId(int id) {
        return service.buscarPorId(id);
    }
}
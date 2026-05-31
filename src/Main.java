import enums.NivelAcesso;
import model.Cargo;
import model.Funcionario;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Instanciar controllers
        CargoController cargoController = new CargoController(scanner);
        FuncionarioController funcionarioController =
                new FuncionarioController(scanner, cargoController);
        PresencaController presencaController =
                new PresencaController(scanner, funcionarioController);
        FolhaPagamentoController folhaController =
                new FolhaPagamentoController(scanner, funcionarioController,
                        presencaController.getService());
        UsuarioController usuarioController =
                new UsuarioController(scanner, funcionarioController);

        // Dados iniciais de exemplo
        carregarDadosIniciais(cargoController, funcionarioController, usuarioController);

        // LOGIN
        System.out.println("╔ ══════════════════════════════════ ╗");
        System.out.println("║   SISTEMA DE RH E FOLHA PAGAMENTO  ║");
        System.out.println("╚ ══════════════════════════════════ ╝");

        boolean logado = false;
        int tentativas = 0;
        while (!logado && tentativas < 3) {
            logado = usuarioController.fazerLogin();
            tentativas++;
        }

        if (!logado) {
            System.out.println("✖ Número máximo de tentativas atingido. Encerrando.");
            return;
        }

        // MENU PRINCIPAL
        int opcao;
        do {
            System.out.println("\n╔ ══════════  MENU PRINCIPAL ══════════ ╗");
            System.out.println("║  1. Cargos                            ║");
            System.out.println("║  2. Funcionários                      ║");
            System.out.println("║  3. Controle de Presença              ║");
            System.out.println("║  4. Folha de Pagamento                ║");
            System.out.println("║  5. Usuários (somente ADMIN/RH)       ║");
            System.out.println("║  6. Relatórios                        ║");
            System.out.println("║  0. Sair                              ║");
            System.out.println("╚ ════════════════════════════════════  ╝");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cargoController.menu();
                case 2 -> funcionarioController.menu();
                case 3 -> presencaController.menu();
                case 4 -> folhaController.menu();
                case 5 -> {
                    if (usuarioController.temPermissao(NivelAcesso.ADMIN, NivelAcesso.RH))
                        usuarioController.menu();
                    else
                        System.out.println("✖ Acesso negado.");
                }
                case 6 -> menuRelatorios(funcionarioController, folhaController,
                        presencaController.getService());
                case 0 -> {
                    usuarioController.logout();
                    System.out.println("Sistema encerrado. Até logo!");
                }
            }
        } while (opcao != 0);
    }

    private static void menuRelatorios(FuncionarioController funcCtrl,
                                       FolhaPagamentoController folhaCtrl,
                                       PresencaService presencaService) {
        int opcao;
        do {
            System.out.println("\n===== RELATÓRIOS =====");
            System.out.println("1. Todos os funcionários");
            System.out.println("2. Folha salarial do período");
            System.out.println("3. Funcionários mais faltosos");
            System.out.println("4. Resumo de horas extras");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> relatorioFuncionarios(funcCtrl);
                case 2 -> relatorioFolha(folhaCtrl);
                case 3 -> relatorioMaisFaltosos(funcCtrl, presencaService);
                case 4 -> relatorioHorasExtras(funcCtrl, presencaService);
            }
        } while (opcao != 0);
    }

    private static void relatorioFuncionarios(FuncionarioController ctrl) {
        List<Funcionario> lista = ctrl.getTodos();
        System.out.printf("%n%-5s %-25s %-15s %-20s %-8s%n",
                "ID", "Nome", "CPF", "Cargo", "Ativo");
        System.out.println("=".repeat(78));
        for (Funcionario f : lista)
            System.out.printf("%-5d %-25s %-15s %-20s %-8s%n",
                    f.getId(), f.getNome(), f.getCpf(),
                    f.getCargo().getNome(), f.isAtivo() ? "Sim" : "Não");
    }

    private static void relatorioFolha(FolhaPagamentoController ctrl) {
        System.out.print("Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        ctrl.menu(); // redireciona para listagem
    }

    private static void relatorioMaisFaltosos(FuncionarioController funcCtrl,
                                              PresencaService presencaService) {
        System.out.print("Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        System.out.printf("%n%-25s %-10s%n", "Funcionário", "Faltas");
        System.out.println("=".repeat(36));
        funcCtrl.getTodos().stream()
                .map(f -> new Object[]{f.getNome(),
                        presencaService.contarFaltas(f.getId(), mes, ano)})
                .sorted((a, b) -> Long.compare((Long) b[1], (Long) a[1]))
                .forEach(arr -> System.out.printf("%-25s %-10d%n", arr[0], arr[1]));
    }

    private static void relatorioHorasExtras(FuncionarioController funcCtrl,
                                             PresencaService presencaService) {
        System.out.print("Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        System.out.printf("%n%-25s %-12s%n", "Funcionário", "Horas Extras");
        System.out.println("=".repeat(38));
        funcCtrl.getTodos().forEach(f ->
                System.out.printf("%-25s %.1fh%n", f.getNome(),
                        presencaService.somarHorasExtras(f.getId(), mes, ano)));
    }

    private static void carregarDadosIniciais(CargoController cargoCtrl,
                                              FuncionarioController funcCtrl,
                                              UsuarioController usuarioCtrl) {
        CargoService cs = new CargoService();
        Cargo cargoAdmin;

        try {
            cargoAdmin = cs.cadastrar("Gerente de RH", "Gestão de RH", 8500.00, 176);
        } catch (Exception e) {
            cargoAdmin = cargoCtrl.buscarPorId(1).orElse(null);
        }

        try {
            cs.cadastrar("Analista", "Análise e desenvolvimento", 5000.00, 176);
        } catch (Exception ignored) {
        }

        if (cargoAdmin != null) {
            FuncionarioService fs = new FuncionarioService();
            try {
                Funcionario admin = fs.cadastrar(
                        "Administrador", "000.000.000-00", "admin@empresa.com",
                        "(00) 00000-0000", LocalDate.of(1990, 1, 1),
                        LocalDate.now(), cargoAdmin
                );
                usuarioCtrl.criarAdminPadrao(admin);
            } catch (Exception ignored) {
            }
        }

        System.out.println("[Sistema] Dados iniciais carregados.");
        System.out.println("[Sistema] Login padrão: admin | Senha: admin123");
    }

}
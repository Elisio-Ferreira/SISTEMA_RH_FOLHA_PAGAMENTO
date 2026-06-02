package controller;

import enums.NivelAcesso;
import model.Funcionario;
import model.Usuario;
import service.UsuarioService;
import java.util.Optional;
import java.util.Scanner;

public class UsuarioController {
    private final UsuarioService service = new UsuarioService();
    private final Scanner scanner;
    private final FuncionarioController funcionarioController;
    private Usuario usuarioLogado;

    public UsuarioController(Scanner scanner, FuncionarioController funcionarioController) {
        this.scanner = scanner;
        this.funcionarioController = funcionarioController;
    }

    public boolean fazerLogin() {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        Optional<Usuario> opt = service.autenticar(login, senha);
        if (opt.isPresent()) {
            usuarioLogado = opt.get();
            System.out.println("✔ Bem-vindo, " + usuarioLogado.getFuncionario().getNome()
                    + " [" + usuarioLogado.getNivelAcesso() + "]");
            return true;
        }
        System.out.println("✖ Login ou senha inválidos.");
        return false;
    }

    public void logout() {
        usuarioLogado = null;
        System.out.println("✔ Logout realizado.");
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }

    public boolean temPermissao(NivelAcesso... niveis) {
        if (usuarioLogado == null) return false;
        for (NivelAcesso n : niveis)
            if (usuarioLogado.getNivelAcesso() == n) return true;
        return false;
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n===== USUÁRIOS =====");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Listar todos");
            System.out.println("3. Alterar senha");
            System.out.println("4. Desativar usuário");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listarTodos();
                case 3 -> alterarSenha();
                case 4 -> desativar();
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.println("Nível: 1-ADMIN  2-RH  3-GERENTE  4-FUNCIONARIO");
        System.out.print("Opção: ");
        NivelAcesso nivel = NivelAcesso.values()[Integer.parseInt(scanner.nextLine()) - 1];
        System.out.print("ID do funcionário vinculado: ");
        int funcId = Integer.parseInt(scanner.nextLine());
        Funcionario f = funcionarioController.buscarPorId(funcId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
        try {
            Usuario u = service.cadastrar(login, senha, nivel, f);
            System.out.println("✔ Usuário criado: " + u);
        } catch (Exception e) {
            System.out.println("✖ Erro: " + e.getMessage());
        }
    }

    private void listarTodos() {
        service.listarTodos().forEach(System.out::println);
    }

    private void alterarSenha() {
        System.out.print("ID do usuário: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nova senha: ");
        String senha = scanner.nextLine();
        System.out.println(service.alterarSenha(id, senha) ? "✔ Senha alterada." : "✖ Usuário não encontrado.");
    }

    private void desativar() {
        System.out.print("ID do usuário: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(service.desativar(id) ? "✔ Desativado." : "✖ Não encontrado.");
    }

    public void criarAdminPadrao(Funcionario funcionario) {
        try {
            service.cadastrar("admin", "admin123", NivelAcesso.ADMIN, funcionario);
        } catch (Exception ignored) {}
    }

    public Usuario getUsuarioLogado() { return usuarioLogado; }
}
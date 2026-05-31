package service;

import enums.NivelAcesso;
import model.Funcionario;
import model.Usuario;
import repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private final UsuarioRepository repository = new UsuarioRepository();

    public Usuario cadastrar(String login, String senha, NivelAcesso nivel, Funcionario funcionario) {
        if (login == null || login.isBlank())
            throw new IllegalArgumentException("Login é obrigatório.");
        if (senha == null || senha.length() < 4)
            throw new IllegalArgumentException("Senha deve ter ao menos 4 caracteres.");
        if (repository.buscarPorLogin(login).isPresent())
            throw new IllegalArgumentException("Login já em uso.");

        Usuario u = new Usuario(0, login, senha, nivel, funcionario);
        return repository.salvar(u);
    }

    public Optional<Usuario> autenticar(String login, String senha) {
        return repository.autenticar(login, senha);
    }

    public boolean alterarSenha(int id, String novaSenha) {
        Optional<Usuario> opt = repository.buscarPorId(id);
        opt.ifPresent(u -> {
            u.setSenha(novaSenha);
            repository.salvar(u);
        });
        return opt.isPresent();
    }

    public boolean desativar(int id) {
        Optional<Usuario> opt = repository.buscarPorId(id);
        opt.ifPresent(u -> {
            u.setAtivo(false);
            repository.salvar(u);
        });
        return opt.isPresent();
    }

    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }

    public boolean remover(int id) {
        return repository.deletar(id);
    }
}
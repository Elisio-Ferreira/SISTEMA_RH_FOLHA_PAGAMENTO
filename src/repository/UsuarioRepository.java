package repository;

import model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository {
    private final List<Usuario> usuarios = new ArrayList<>();
    private int proximoId = 1;

    public Usuario salvar(Usuario u) {
        if (u.getId() == 0) {
            u.setId(proximoId++);
            usuarios.add(u);
        } else {
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getId() == u.getId()) {
                    usuarios.set(i, u);
                    return u;
                }
            }
        }
        return u;
    }

    public Optional<Usuario> buscarPorId(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst();
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        return usuarios.stream().filter(u -> u.getLogin().equals(login)).findFirst();
    }

    public Optional<Usuario> autenticar(String login, String senha) {
        return usuarios.stream()
                .filter(u -> u.getLogin().equals(login)
                        && u.getSenha().equals(senha)
                        && u.isAtivo())
                .findFirst();
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public boolean deletar(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }
}
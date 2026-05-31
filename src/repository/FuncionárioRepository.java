package repository;

import model.Funcionario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FuncionarioRepository {
    private final List<Funcionario> funcionarios = new ArrayList<>();
    private int proximoId = 1;

    public Funcionario salvar(Funcionario f) {
        if (f.getId() == 0) {
            f.setId(proximoId++);
            funcionarios.add(f);
        } else {
            for (int i = 0; i < funcionarios.size(); i++) {
                if (funcionarios.get(i).getId() == f.getId()) {
                    funcionarios.set(i, f);
                    return f;
                }
            }
        }
        return f;
    }

    public Optional<Funcionario> buscarPorId(int id) {
        return funcionarios.stream().filter(f -> f.getId() == id).findFirst();
    }

    public Optional<Funcionario> buscarPorCpf(String cpf) {
        return funcionarios.stream().filter(f -> f.getCpf().equals(cpf)).findFirst();
    }

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios);
    }

    public List<Funcionario> listarAtivos() {
        return funcionarios.stream().filter(Funcionario::isAtivo).collect(Collectors.toList());
    }

    public boolean deletar(int id) {
        return funcionarios.removeIf(f -> f.getId() == id);
    }

    public List<Funcionario> buscarPorNome(String nome) {
        return funcionarios.stream()
                .filter(f -> f.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
}
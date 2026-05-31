package service;

import model.Cargo;
import model.Funcionario;
import repository.FuncionarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FuncionarioService {
    private final FuncionarioRepository repository = new FuncionarioRepository();

    public Funcionario cadastrar(String nome, String cpf, String email, String telefone,
                                 LocalDate dataNascimento, LocalDate dataAdmissao, Cargo cargo) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório.");
        if (cpf == null || cpf.isBlank())
            throw new IllegalArgumentException("CPF é obrigatório.");
        if (repository.buscarPorCpf(cpf).isPresent())
            throw new IllegalArgumentException("CPF já cadastrado.");
        if (cargo == null)
            throw new IllegalArgumentException("Cargo é obrigatório.");

        Funcionario f = new Funcionario(0, nome, cpf, email, telefone,
                dataNascimento, dataAdmissao, cargo);
        return repository.salvar(f);
    }

    public Funcionario atualizar(int id, String nome, String email,
                                 String telefone, Cargo cargo) {
        Funcionario f = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
        f.setNome(nome);
        f.setEmail(email);
        f.setTelefone(telefone);
        f.setCargo(cargo);
        return repository.salvar(f);
    }

    public boolean desativar(int id) {
        Optional<Funcionario> opt = repository.buscarPorId(id);
        opt.ifPresent(f -> {
            f.setAtivo(false);
            repository.salvar(f);
        });
        return opt.isPresent();
    }

    public boolean remover(int id) {
        return repository.deletar(id);
    }

    public Optional<Funcionario> buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public List<Funcionario> listarTodos() {
        return repository.listarTodos();
    }

    public List<Funcionario> listarAtivos() {
        return repository.listarAtivos();
    }

    public List<Funcionario> buscarPorNome(String nome) {
        return repository.buscarPorNome(nome);
    }
}
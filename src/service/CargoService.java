package service;

import model.Cargo;
import repository.CargoRepository;
import java.util.List;
import java.util.Optional;

public class CargoService {
    private final CargoRepository repository = new CargoRepository();

    public Cargo cadastrar(String nome, String descricao, double salarioBase, int cargaHoraria) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do cargo é obrigatório.");
        if (salarioBase <= 0)
            throw new IllegalArgumentException("Salário base deve ser positivo.");
        if (repository.buscarPorNome(nome).isPresent())
            throw new IllegalArgumentException("Já existe um cargo com esse nome.");

        Cargo cargo = new Cargo(0, nome, descricao, salarioBase, cargaHoraria);
        return repository.salvar(cargo);
    }

    public Cargo atualizar(int id, String nome, String descricao, double salarioBase, int cargaHoraria) {
        Cargo cargo = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cargo não encontrado."));
        cargo.setNome(nome);
        cargo.setDescricao(descricao);
        cargo.setSalarioBase(salarioBase);
        cargo.setCargaHorariaMensal(cargaHoraria);
        return repository.salvar(cargo);
    }

    public boolean remover(int id) {
        return repository.deletar(id);
    }

    public Optional<Cargo> buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public List<Cargo> listarTodos() {
        return repository.listarTodos();
    }
}
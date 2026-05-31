package repository;

import model.Cargo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CargoRepository {
    private final List<Cargo> cargos = new ArrayList<>();
    private int proximoId = 1;

    public Cargo salvar(Cargo cargo) {
        if (cargo.getId() == 0) {
            cargo.setId(proximoId++);
            cargos.add(cargo);
        } else {
            for (int i = 0; i < cargos.size(); i++) {
                if (cargos.get(i).getId() == cargo.getId()) {
                    cargos.set(i, cargo);
                    return cargo;
                }
            }
        }
        return cargo;
    }

    public Optional<Cargo> buscarPorId(int id) {
        return cargos.stream().filter(c -> c.getId() == id).findFirst();
    }

    public List<Cargo> listarTodos() {
        return new ArrayList<>(cargos);
    }

    public boolean deletar(int id) {
        return cargos.removeIf(c -> c.getId() == id);
    }

    public Optional<Cargo> buscarPorNome(String nome) {
        return cargos.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }
}

package repository;

import model.FolhaPagamento;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FolhaPagamentoRepository {
    private final List<FolhaPagamento> folhas = new ArrayList<>();
    private int proximoId = 1;

    public FolhaPagamento salvar(FolhaPagamento f) {
        if (f.getId() == 0) {
            f.setId(proximoId++);
            folhas.add(f);
        } else {
            for (int i = 0; i < folhas.size(); i++) {
                if (folhas.get(i).getId() == f.getId()) {
                    folhas.set(i, f);
                    return f;
                }
            }
        }
        return f;
    }

    public Optional<FolhaPagamento> buscarPorId(int id) {
        return folhas.stream().filter(f -> f.getId() == id).findFirst();
    }

    public List<FolhaPagamento> listarTodos() {
        return new ArrayList<>(folhas);
    }

    public List<FolhaPagamento> buscarPorPeriodo(int mes, int ano) {
        return folhas.stream()
                .filter(f -> f.getMes() == mes && f.getAno() == ano)
                .collect(Collectors.toList());
    }

    public Optional<FolhaPagamento> buscarPorFuncionarioPeriodo(int funcId, int mes, int ano) {
        return folhas.stream()
                .filter(f -> f.getFuncionario().getId() == funcId
                        && f.getMes() == mes && f.getAno() == ano)
                .findFirst();
    }

    public boolean deletar(int id) {
        return folhas.removeIf(f -> f.getId() == id);
    }
}

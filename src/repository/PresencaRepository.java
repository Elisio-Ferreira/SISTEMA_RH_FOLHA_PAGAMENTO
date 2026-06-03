package repository;

import enums.TipoOcorrencia;
import model.Presenca;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PresencaRepository {
    private final List<Presenca> presencas = new ArrayList<>();
    private int proximoId = 1;

    public Presenca salvar(Presenca p) {
        if (p.getId() == 0) {
            p.setId(proximoId++);
            presencas.add(p);
        } else {
            for (int i = 0; i < presencas.size(); i++) {
                if (presencas.get(i).getId() == p.getId()) {
                    presencas.set(i, p);
                    return p;
                }
            }
        }
        return p;
    }

    public Optional<Presenca> buscarPorId(int id) {
        return presencas.stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<Presenca> listarTodos() {
        return new ArrayList<>(presencas);
    }

    public List<Presenca> buscarPorFuncionario(int funcionarioId) {
        return presencas.stream()
                .filter(p -> p.getFuncionario().getId() == funcionarioId)
                .collect(Collectors.toList());
    }

    public List<Presenca> buscarPorPeriodo(int funcionarioId, int mes, int ano) {
        return presencas.stream()
                .filter(p -> p.getFuncionario().getId() == funcionarioId
                        && p.getData().getMonthValue() == mes
                        && p.getData().getYear() == ano)
                .collect(Collectors.toList());
    }

    public long contarFaltasPorFuncionario(int funcionarioId, int mes, int ano) {
        return presencas.stream()
                .filter(p -> p.getFuncionario().getId() == funcionarioId
                        && p.getData().getMonthValue() == mes
                        && p.getData().getYear() == ano
                        && p.getTipo() == TipoOcorrencia.FALTA)
                .count();
    }

    public double somarHorasExtrasPorFuncionario(int funcionarioId, int mes, int ano) {
        return presencas.stream()
                .filter(p -> p.getFuncionario().getId() == funcionarioId
                        && p.getData().getMonthValue() == mes
                        && p.getData().getYear() == ano)
                .mapToDouble(Presenca::getHorasExtras)
                .sum();
    }

    public boolean deletar(int id) {
        return presencas.removeIf(p -> p.getId() == id);
    }
}
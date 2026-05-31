package service;

import enums.TipoOcorrencia;
import model.Funcionario;
import model.Presenca;
import repository.PresencaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PresencaService {
    private final PresencaRepository repository = new PresencaRepository();

    public Presenca registrar(Funcionario funcionario, LocalDate data,
                              TipoOcorrencia tipo, double horasExtras, String obs) {
        if (funcionario == null)
            throw new IllegalArgumentException("Funcionário inválido.");
        if (data == null)
            throw new IllegalArgumentException("Data inválida.");
        Presenca p = new Presenca(0, funcionario, data, tipo, horasExtras, obs);
        return repository.salvar(p);
    }

    public Presenca atualizar(int id, TipoOcorrencia tipo, double horasExtras, String obs) {
        Presenca p = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro não encontrado."));
        p.setTipo(tipo);
        p.setHorasExtras(horasExtras);
        p.setObservacao(obs);
        return repository.salvar(p);
    }

    public boolean remover(int id) {
        return repository.deletar(id);
    }

    public List<Presenca> listarPorFuncionario(int funcionarioId) {
        return repository.buscarPorFuncionario(funcionarioId);
    }

    public List<Presenca> listarPorPeriodo(int funcionarioId, int mes, int ano) {
        return repository.buscarPorPeriodo(funcionarioId, mes, ano);
    }

    public long contarFaltas(int funcionarioId, int mes, int ano) {
        return repository.contarFaltasPorFuncionario(funcionarioId, mes, ano);
    }

    public double somarHorasExtras(int funcionarioId, int mes, int ano) {
        return repository.somarHorasExtrasPorFuncionario(funcionarioId, mes, ano);
    }

    public List<Presenca> listarTodos() {
        return repository.listarTodos();
    }
}
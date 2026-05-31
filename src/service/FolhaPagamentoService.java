package service;

import model.FolhaPagamento;
import model.Funcionario;
import repository.FolhaPagamentoRepository;
import java.util.List;
import java.util.Optional;

public class FolhaPagamentoService {
    private final FolhaPagamentoRepository repository = new FolhaPagamentoRepository();
    private final PresencaService presencaService;

    public FolhaPagamentoService(PresencaService presencaService) {
        this.presencaService = presencaService;
    }

    public FolhaPagamento gerar(Funcionario funcionario, int mes, int ano) {
        if (repository.buscarPorFuncionarioPeriodo(funcionario.getId(), mes, ano).isPresent())
            throw new IllegalArgumentException("Folha já gerada para este período.");

        long faltas = presencaService.contarFaltas(funcionario.getId(), mes, ano);
        double horasExtras = presencaService.somarHorasExtras(funcionario.getId(), mes, ano);

        FolhaPagamento folha = new FolhaPagamento(0, funcionario, mes, ano);
        folha.setTotalFaltas((int) faltas);
        folha.setTotalHorasExtras(horasExtras);
        return repository.salvar(folha);
    }

    public Optional<FolhaPagamento> buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public List<FolhaPagamento> listarPorPeriodo(int mes, int ano) {
        return repository.buscarPorPeriodo(mes, ano);
    }

    public List<FolhaPagamento> listarTodos() {
        return repository.listarTodos();
    }

    public boolean remover(int id) {
        return repository.deletar(id);
    }
}
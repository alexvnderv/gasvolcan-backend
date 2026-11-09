package cl.volcan.gasvolcan.service;

import cl.volcan.gasvolcan.model.Cliente;
import cl.volcan.gasvolcan.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        cliente.setId(null);
        return repository.save(cliente);
    }

    public Optional<Cliente> actualizar(Long id, Cliente cliente) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        cliente.setId(id);
        return Optional.of(repository.save(cliente));
    }

    public boolean eliminar(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}

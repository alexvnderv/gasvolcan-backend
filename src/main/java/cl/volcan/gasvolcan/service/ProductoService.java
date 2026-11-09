package cl.volcan.gasvolcan.service;

import cl.volcan.gasvolcan.model.Producto;
import cl.volcan.gasvolcan.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Producto guardar(Producto producto) {
        producto.setId(null);
        return repository.save(producto);
    }

    public Optional<Producto> actualizar(Long id, Producto producto) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        producto.setId(id);
        return Optional.of(repository.save(producto));
    }

    public boolean eliminar(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}

package cl.volcan.gasvolcan.service;

import cl.volcan.gasvolcan.model.Venta;
import cl.volcan.gasvolcan.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaRepository repository;

    public VentaService(VentaRepository repository) {
        this.repository = repository;
    }

    public List<Venta> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<Venta> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Venta guardar(Venta venta) {
        venta.setId(null);
        return repository.save(venta);
    }

    public Optional<Venta> actualizar(Long id, Venta venta) {
        return repository.findById(id).map(existente -> {
            venta.setId(id);
            venta.setCodigo(existente.getCodigo());
            venta.setFecha(existente.getFecha());
            if (venta.getEstado() == null) {
                venta.setEstado(existente.getEstado());
            }
            return repository.save(venta);
        });
    }

    public boolean eliminar(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}

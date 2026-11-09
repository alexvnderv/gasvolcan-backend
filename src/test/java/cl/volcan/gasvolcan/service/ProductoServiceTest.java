package cl.volcan.gasvolcan.service;

import cl.volcan.gasvolcan.model.Producto;
import cl.volcan.gasvolcan.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoService service;

    @Test
    void obtenerTodos_devuelveLosRegistros() {
        when(repository.findAll()).thenReturn(List.of(new Producto(), new Producto()));

        assertEquals(2, service.obtenerTodos().size());
    }

    @Test
    void buscarPorId_inexistente_devuelveVacio() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(service.buscarPorId(99L).isEmpty());
    }

    @Test
    void guardar_guardaYDevuelveElRegistro() {
        Producto nuevo = new Producto();
        when(repository.save(nuevo)).thenReturn(nuevo);

        assertSame(nuevo, service.guardar(nuevo));
    }

    @Test
    void actualizar_existente_asignaElIdYGuarda() {
        Producto datos = new Producto();
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(datos)).thenReturn(datos);

        Optional<Producto> resultado = service.actualizar(1L, datos);

        assertTrue(resultado.isPresent());
        assertEquals(1L, datos.getId());
    }

    @Test
    void actualizar_inexistente_noGuarda() {
        when(repository.existsById(5L)).thenReturn(false);

        assertTrue(service.actualizar(5L, new Producto()).isEmpty());
        verify(repository, never()).save(any());
    }

    @Test
    void eliminar_existente_borraYDevuelveTrue() {
        when(repository.existsById(1L)).thenReturn(true);

        assertTrue(service.eliminar(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void eliminar_inexistente_devuelveFalse() {
        when(repository.existsById(7L)).thenReturn(false);

        assertFalse(service.eliminar(7L));
        verify(repository, never()).deleteById(any());
    }
}

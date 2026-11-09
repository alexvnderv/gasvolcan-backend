package cl.volcan.gasvolcan.service;

import cl.volcan.gasvolcan.model.Venta;
import cl.volcan.gasvolcan.repository.VentaRepository;
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
class VentaServiceTest {

    @Mock
    private VentaRepository repository;

    @InjectMocks
    private VentaService service;

    @Test
    void obtenerTodos_devuelveLosRegistros() {
        when(repository.findAll()).thenReturn(List.of(new Venta(), new Venta()));

        assertEquals(2, service.obtenerTodos().size());
    }

    @Test
    void buscarPorId_inexistente_devuelveVacio() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(service.buscarPorId(99L).isEmpty());
    }

    @Test
    void guardar_guardaYDevuelveElRegistro() {
        Venta nuevo = new Venta();
        when(repository.save(nuevo)).thenReturn(nuevo);

        assertSame(nuevo, service.guardar(nuevo));
    }

    @Test
    void actualizar_existente_conservaCodigoFechaYEstado() {
        Venta original = new Venta();
        original.setId(1L);
        original.setCodigo("VOLCAN-1234");
        original.setEstado("En camino");
        original.setFecha(java.time.LocalDateTime.of(2026, 11, 9, 10, 0));
        Venta datos = new Venta();
        when(repository.findById(1L)).thenReturn(Optional.of(original));
        when(repository.save(datos)).thenReturn(datos);

        Optional<Venta> resultado = service.actualizar(1L, datos);

        assertTrue(resultado.isPresent());
        assertEquals(1L, datos.getId());
        assertEquals("VOLCAN-1234", datos.getCodigo());
        assertEquals("En camino", datos.getEstado());
        assertEquals(original.getFecha(), datos.getFecha());
    }

    @Test
    void actualizar_inexistente_noGuarda() {
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertTrue(service.actualizar(5L, new Venta()).isEmpty());
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

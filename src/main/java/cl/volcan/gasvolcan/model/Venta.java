package cl.volcan.gasvolcan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String codigo;

    @NotNull(message = "El id del cliente es obligatorio")
    @Column(nullable = false)
    private Long clienteId;

    @NotNull(message = "El id del producto es obligatorio")
    @Column(nullable = false)
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a 0")
    @Column(nullable = false)
    private Integer total;

    @NotBlank(message = "El método de pago es obligatorio")
    @Column(nullable = false, length = 30)
    private String metodoPago;

    @Column(length = 30)
    private String estado;

    private LocalDateTime fecha;

    @PrePersist
    void antesDeGuardar() {
        if (fecha == null) fecha = LocalDateTime.now();
        if (estado == null) estado = "Recibido";
        if (codigo == null) codigo = "VOLCAN-" + (1000 + (int) (Math.random() * 9000));
    }
}

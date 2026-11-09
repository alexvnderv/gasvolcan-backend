-- Gas El Volcán - esquema inicial (MySQL 8)
-- Las validaciones replican las de las entidades y las del front React.
-- ventas guarda cliente_id y producto_id como números simples (sin claves foráneas),
-- igual que la entidad Venta (sin relaciones JPA).

CREATE TABLE productos (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(100) NOT NULL,
    categoria   VARCHAR(30)  NOT NULL,                 -- 'Cilindro' o 'Accesorio'
    capacidad   VARCHAR(20),                           -- '5 kg', '11 kg', 'No aplica'
    precio      INT          NOT NULL,                 -- CLP entero
    stock       INT          NOT NULL,
    descripcion VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT ck_productos_precio CHECK (precio > 0),
    CONSTRAINT ck_productos_stock  CHECK (stock >= 0)
);

CREATE TABLE clientes (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(60)  NOT NULL,
    apellido     VARCHAR(60)  NOT NULL,
    correo       VARCHAR(100) NOT NULL,
    telefono     VARCHAR(20),
    comuna       VARCHAR(40)  NOT NULL,
    direccion    VARCHAR(120) NOT NULL,
    tipo_cliente VARCHAR(20),                          -- Hogar, Comercio...
    PRIMARY KEY (id),
    UNIQUE KEY uk_clientes_correo (correo)
);

CREATE TABLE ventas (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    codigo      VARCHAR(20),                           -- VOLCAN-XXXX
    cliente_id  BIGINT      NOT NULL,
    producto_id BIGINT      NOT NULL,
    cantidad    INT         NOT NULL,
    total       INT         NOT NULL,
    metodo_pago VARCHAR(30) NOT NULL,
    estado      VARCHAR(30),
    fecha       DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT ck_ventas_cantidad CHECK (cantidad > 0),
    CONSTRAINT ck_ventas_total    CHECK (total > 0)
);

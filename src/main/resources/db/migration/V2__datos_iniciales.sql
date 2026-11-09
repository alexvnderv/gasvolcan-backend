-- Gas El Volcán - datos iniciales (tomados del catálogo del front React)

INSERT INTO productos (id, nombre, categoria, capacidad, precio, stock, descripcion) VALUES
 (1, 'Cilindro 5 kg',           'Cilindro',  '5 kg',       9990, 40, 'Ideal para cocinas pequeñas, camping o uso ocasional.'),
 (2, 'Cilindro 11 kg',          'Cilindro',  '11 kg',     15990, 60, 'El formato más usado en hogares de la zona.'),
 (3, 'Cilindro 15 kg',          'Cilindro',  '15 kg',     19990, 35, 'Pensado para negocios y consumo familiar alto.'),
 (4, 'Regulador de gas',        'Accesorio', 'No aplica',  6490, 25, 'Compatible con cilindros de 5, 11 y 15 kg.'),
 (5, 'Manguera de gas (1,5 m)', 'Accesorio', 'No aplica',  4990, 30, 'Manguera certificada para conexión segura.');

INSERT INTO clientes (id, nombre, apellido, correo, telefono, comuna, direccion, tipo_cliente) VALUES
 (1, 'Juan',  'Pérez',    'juan@gmail.com',    '+56912345678', 'Chillán',    'Av. Libertad 450', 'Hogar'),
 (2, 'María', 'González', 'maria@outlook.com', '+56987654321', 'San Carlos', 'Av. Ecuador 120',   'Comercio');

INSERT INTO ventas (id, codigo, cliente_id, producto_id, cantidad, total, metodo_pago, estado, fecha) VALUES
 (1, 'VOLCAN-1001', 1, 2, 2, 31980, 'Efectivo', 'Entregado', NOW()),
 (2, 'VOLCAN-1002', 2, 3, 1, 19990, 'Webpay',   'En camino', NOW());

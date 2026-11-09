# Gas El Volcán – Backend (gasvolcan) (Semana 1)

Base del backend con **Spring Boot + MySQL**: 3 entidades independientes (`Producto`, `Cliente`, `Venta`), repositorios, servicios y controladores REST con CRUD completo.

## 1. Spring Initializr (https://start.spring.io)

| Campo | Valor |
|---|---|
| Project | Maven |
| Language | Java |
| Spring Boot | 3.5.x (la estable que aparezca) |
| Group | `cl.volcan` |
| Artifact / Name | `gasvolcan` |
| Packaging | Jar |
| Java | 17 |

**Dependencies a agregar:**
1. **Spring Web**: controladores REST
2. **Spring Data JPA**: entidades y repositorios
3. **MySQL Driver**: conexión a MySQL
4. **Lombok**: menos código repetido (getters, setters, constructores)
5. **Validation**: validaciones (`@NotBlank`, `@Positive`, `@Email`)
6. **Spring Boot DevTools**: reinicio automático al guardar

## 2. Base de datos MySQL
1. Instala y abre MySQL (o XAMPP/Workbench).
2. En `src/main/resources/application.properties` cambia `spring.datasource.password=TU_PASSWORD` por tu contraseña (el usuario por defecto es `root`).
3. La base `gas_el_volcan` se crea sola al iniciar (`createDatabaseIfNotExist=true`).
4. Las tablas y los datos iniciales los crea **Flyway** con `db/migration/V1__crear_tablas.sql` y `V2__datos_iniciales.sql`. Hibernate solo valida (`ddl-auto=validate`).

## 3. Ejecutar
- **IntelliJ / VS Code / Eclipse (STS):** abre la carpeta como proyecto Maven y ejecuta `GasvolcanApplication`.
- **Con Maven instalado:** `mvn spring-boot:run`

La API queda en `http://localhost:8080`.

## 4. Estructura
```
src/main/java/cl/volcan/gasvolcan/
├── model/        Producto, Cliente, Venta   (sin relaciones JPA)
├── repository/   interfaces que extienden JpaRepository
├── service/      @Service con el CRUD básico
├── controller/   @RestController + @RequestMapping
└── exception/    errores 400/409 claros
```

## 5. Endpoints (iguales para `/api/v1/productos`, `/api/v1/clientes` y `/api/v1/ventas`)
| Método | URL | Acción | Respuesta |
|---|---|---|---|
| GET | `/api/v1/productos` | Obtener todos | 200 |
| GET | `/api/v1/productos/{id}` | Buscar por ID | 200 / 404 |
| POST | `/api/v1/productos` | Crear | 201 / 400 |
| PUT | `/api/v1/productos/{id}` | Actualizar | 200 / 404 |
| DELETE | `/api/v1/productos/{id}` | Eliminar | 204 / 404 |

Prueba rápida: abre `requests.http` (VS Code con la extensión *REST Client*, o IntelliJ) o usa Postman.

## 6. Notas
- `Venta` guarda `clienteId` y `productoId` como números simples (sin `@ManyToOne`), como piden las instrucciones.
- `@CrossOrigin` permite que más adelante el front React (`http://localhost:5173`) consuma esta API.

## 7. Datos de ejemplo
Al iniciar por primera vez Flyway ejecuta `V2__datos_iniciales.sql` y carga 5 productos, 2 clientes y 2 ventas. Así `GET /api/v1/productos` ya devuelve datos.

## 8. Pruebas unitarias
`mvn test` (o clic derecho en `src/test` → *Run Tests* en IntelliJ). Son 21 pruebas de los servicios con el repositorio simulado (Mockito), por lo que **no necesitan MySQL**.

## 9. Lista de verificación – Semana 1 (revisión del lunes 09-11)
- [ ] Proyecto Spring Boot generado y abre sin errores
- [ ] Base de datos MySQL creada y credenciales configuradas
- [ ] 3 entidades independientes: Producto, Cliente, Venta (sin `@OneToMany`, `@ManyToOne` ni `@ManyToMany`)
- [ ] 3 repositorios que extienden `JpaRepository` (sin métodos personalizados)
- [ ] 3 servicios `@Service` con CRUD: todos, buscar por ID, guardar, actualizar, eliminar
- [ ] 3 controladores `@RestController` + `@RequestMapping` con los 5 endpoints
- [ ] Probado en `requests.http` o Postman: los 15 requests responden bien
- [ ] Tablas visibles en MySQL Workbench con datos

**Para la demostración:** inicia la app, abre `http://localhost:8080/api/v1/productos`, ejecuta un POST, un PUT y un DELETE, y muestra la tabla en MySQL.

## 10. Detalle de comportamiento
- `POST` ignora cualquier `id` enviado y siempre inserta un registro nuevo.
- `PUT` reemplaza el registro completo. En `Venta` se conservan el `codigo` y la `fecha` originales.
- `Venta` genera sola el `codigo` (`VOLCAN-XXXX`), la `fecha` y el `estado` ("Recibido") si no se envían.
- Un correo de cliente repetido responde `409 Conflict`; datos inválidos responden `400` con el detalle por campo.

## 11. Base de datos (Flyway)
Las migraciones están en `src/main/resources/db/migration/`:
- `V1__crear_tablas.sql`: tablas `productos`, `clientes` y `ventas`.
- `V2__datos_iniciales.sql`: datos de ejemplo.

`ventas` no tiene claves foráneas: `cliente_id` y `producto_id` son números simples, igual que la entidad `Venta`.

> Si cambias una migración ya ejecutada, Flyway falla. En ese caso ejecuta `DROP DATABASE gas_el_volcan;` en MySQL y vuelve a iniciar la app.

### Modelo relacional
![Modelo relacional](database/modelo_relacional.png)

```
CLIENTES  (PK id, nombre, apellido, correo UK, telefono, comuna, direccion, tipo_cliente)
PRODUCTOS (PK id, nombre, categoria, capacidad, precio, stock, descripcion)
VENTAS    (PK id, codigo, cliente_id, producto_id, cantidad, total, metodo_pago, estado, fecha)

Relaciones lógicas:  CLIENTES 1 ──── N VENTAS    (ventas.cliente_id  → clientes.id)
                     PRODUCTOS 1 ─── N VENTAS    (ventas.producto_id → productos.id)
```

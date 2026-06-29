# 🍔 Food Store JPA — Trabajo Práctico Integrador

Este proyecto corresponde al desarrollo del TPI de Programación III, implementado con JPA/Hibernate y una interfaz de consola.

---

## 🧰 Tecnologías utilizadas

- Java 21
- JPA / Hibernate 6
- H2 Database (modo archivo)
- Lombok
- Gradle 8

---

## 📁 Estructura del proyecto


src/main/java/com/tp/jpa/

├── model/ # Entidades JPA
│ ├── Base.java

│ ├── Calculable.java

│ ├── Categoria.java

│ ├── Producto.java

│ ├── Usuario.java

│ ├── Pedido.java

│ ├── DetallePedido.java
│ └── enums/
│ ├── Rol.java
│ ├── EstadoPedido.java
│ └── FormaPago.java
│
├── util/
│ └── JPAUtil.java
│
├── repository/
│ ├── BaseRepository.java
│ ├── ProductoRepository.java
│ ├── CategoriaRepository.java
│ ├── UsuarioRepository.java
│ └── PedidoRepository.java
│
└── Main.java


---

## ⚙️ Funcionalidades implementadas

### 📦 CRUD completo
- Categorías (alta, baja lógica, modificación, listado)
- Productos (alta, baja lógica, modificación, listado)
- Usuarios (alta, baja lógica, modificación, listado)
- Pedidos (alta, baja lógica, cambio de estado, listado)

---

### 🔍 Reportes implementados

- Productos por categoría
- Pedidos por usuario
- Pedidos por estado
- Total facturado (pedidos entregados)

---

### 🧠 Reglas de negocio aplicadas

- Baja lógica mediante campo `eliminado`
- Cálculo automático de total en pedidos
- Relación Usuario → Pedidos (unidireccional)
- Relación Categoría → Productos (unidireccional)
- Uso de enums para estado, rol y forma de pago
- Validación de integridad con JPA/Hibernate

---

## 🗄️ Base de datos

- Motor: H2
- Modo: archivo persistente
- Ubicación: `./data/jpa_db.mv.db`
- Creación automática al ejecutar el sistema

---

## ▶️ Cómo ejecutar el proyecto

### Con Gradle

```bash
./gradlew run
Alternativa
./gradlew jar
java -jar build/libs/foodstore-jpa-0.0.1-SNAPSHOT.jar
🧪 Datos de prueba

No hay carga inicial automática.

Orden recomendado:

Categorías
Productos
Usuarios
Pedidos
🎥 Video demostrativo

👉 [Colocar aquí link a YouTube o Google Drive (público)]

Duración: 10 a 15 minutos
Requisito obligatorio: inicio con cámara encendida

📄 Documento PDF

👉 [Colocar aquí link al PDF o archivo en el repositorio]

📌 Notas importantes
Se utiliza baja lógica (no eliminación física).
Las relaciones se manejan con JPA/Hibernate.
El sistema está diseñado con patrón Repository.
Toda la lógica de persistencia está centralizada en BaseRepository.
## Entrega

- **Video demostrativo:** [link aquí]
- **Informe PDF:** [link aquí]

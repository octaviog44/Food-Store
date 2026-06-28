package com.tp.jpa;

import com.tp.jpa.model.enums.EstadoPedido;
import com.tp.jpa.model.*;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.model.enums.Rol;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.PedidoRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.repository.UsuarioRepository;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clase principal: menú de consola del sistema Food Store.
 * Orden de uso natural: Categorías -> Productos -> Usuarios -> Pedidos.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();
    private static final ProductoRepository productoRepo = new ProductoRepository();
    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final PedidoRepository pedidoRepo = new PedidoRepository();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== FOOD STORE - MENÚ PRINCIPAL =====");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": menuCategorias(); break;
                case "2": menuProductos(); break;
                case "3": menuUsuarios(); break;
                case "4": menuPedidos(); break;
                case "5": menuReportes(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
        JPAUtil.close();
        System.out.println("Aplicación finalizada.");
    }

    // ── Submenús ─────────────────────────────────────────────────

    private static void menuCategorias() {

    boolean volver = false;

    while (!volver) {

        System.out.println("\n===== GESTIÓN DE CATEGORÍAS =====");
        System.out.println("1. Alta");
        System.out.println("2. Modificar");
        System.out.println("3. Baja lógica");
        System.out.println("4. Listar");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        String op = sc.nextLine().trim();

        switch (op) {

            case "1":
                altaCategoria();
                break;

            case "2":
                modificarCategoria();
                break;

            case "3":
                bajaCategoria();
                break;

            case "4":
                listarCategorias();
                break;

            case "0":
                volver = true;
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }
}

private static void altaCategoria() {

    System.out.println("\n===== ALTA DE CATEGORÍA =====");

    System.out.print("Nombre: ");
    String nombre = sc.nextLine().trim();

    if (nombre.isBlank()) {
        System.out.println("El nombre no puede estar vacío.");
        return;
    }

    System.out.print("Descripción: ");
    String descripcion = sc.nextLine().trim();

    Categoria categoria = Categoria.builder()
            .nombre(nombre)
            .descripcion(descripcion)
            .build();

    try {
        categoriaRepo.guardar(categoria);
        System.out.println("Categoría creada correctamente con ID: " + categoria.getId());
    } catch (Exception e) {
        System.out.println("Error al guardar la categoría: " + e.getMessage());
    }
}

private static void modificarCategoria() {

    System.out.println("\n===== MODIFICAR CATEGORÍA =====");

    System.out.print("Ingrese el ID de la categoría: ");

    Long id;

    try {
        id = Long.parseLong(sc.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("ID inválido.");
        return;
    }

    Optional<Categoria> categoriaOpt = categoriaRepo.buscarPorId(id);

    if (categoriaOpt.isEmpty()) {
        System.out.println("No existe una categoría con ese ID.");
        return;
    }

    Categoria categoria = categoriaOpt.get();

    System.out.println("Nombre actual: " + categoria.getNombre());
    System.out.print("Nuevo nombre: ");
    categoria.setNombre(sc.nextLine().trim());

    System.out.println("Descripción actual: " + categoria.getDescripcion());
    System.out.print("Nueva descripción: ");
    categoria.setDescripcion(sc.nextLine().trim());

    try {
        categoriaRepo.guardar(categoria);
        System.out.println("Categoría modificada correctamente.");
    } catch (Exception e) {
        System.out.println("Error al modificar la categoría: " + e.getMessage());
    }
}

private static void bajaCategoria() {

    System.out.println("\n===== BAJA DE CATEGORÍA =====");

    System.out.print("Ingrese el ID de la categoría: ");

    Long id;

    try {
        id = Long.parseLong(sc.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("ID inválido.");
        return;
    }

    try {
        boolean eliminada = categoriaRepo.eliminarLogico(id);

        if (eliminada) {
            System.out.println("Categoría eliminada correctamente.");
        } else {
            System.out.println("No existe una categoría con ese ID.");
        }

    } catch (Exception e) {
        System.out.println("Error al eliminar la categoría: " + e.getMessage());
    }
}

private static void listarCategorias() {

    System.out.println("\n===== LISTADO DE CATEGORÍAS =====");

    List<Categoria> categorias = categoriaRepo.listarActivos();

    if (categorias.isEmpty()) {
        System.out.println("No hay categorías registradas.");
        return;
    }

    for (Categoria categoria : categorias) {
        System.out.println("--------------------------------");
        System.out.println("ID: " + categoria.getId());
        System.out.println("Nombre: " + categoria.getNombre());
        System.out.println("Descripción: " + categoria.getDescripcion());
    }

    System.out.println("--------------------------------");
}

private static void menuProductos() {

    boolean volver = false;

    while (!volver) {

        System.out.println("\n===== GESTIÓN DE PRODUCTOS =====");
        System.out.println("1. Alta");
        System.out.println("2. Modificar");
        System.out.println("3. Baja lógica");
        System.out.println("4. Listar");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                altaProducto();
                break;

            case "2":
                modificarProducto();
                break;

            case "3":
                bajaProducto();
                break;

            case "4":
                listarProductos();
                break;

            case "0":
                volver = true;
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }
}

private static void altaProducto() {

    System.out.println("\n===== ALTA DE PRODUCTO =====");

    System.out.print("Nombre: ");
    String nombre = sc.nextLine();

    System.out.print("Precio: ");
    Double precio = Double.parseDouble(sc.nextLine());

    System.out.print("Descripción: ");
    String descripcion = sc.nextLine();

    System.out.print("Stock: ");
    Integer stock = Integer.parseInt(sc.nextLine());

    System.out.print("URL de la imagen: ");
    String imagen = sc.nextLine();

    // Mostrar categorías disponibles
    List<Categoria> categorias = categoriaRepo.listarActivos();

    if (categorias.isEmpty()) {
        System.out.println("No existen categorías. Debe crear una primero.");
        return;
    }

    System.out.println("\nCategorías disponibles:");

    for (Categoria categoria : categorias) {
        System.out.println(categoria.getId() + " - " + categoria.getNombre());
    }

    System.out.print("Ingrese el ID de la categoría: ");
    Long idCategoria = Long.parseLong(sc.nextLine());

    Optional<Categoria> categoriaOptional = categoriaRepo.buscarPorId(idCategoria);

    if (categoriaOptional.isEmpty()) {
        System.out.println("La categoría no existe.");
        return;
    }

    Categoria categoria = categoriaOptional.get();

    Producto producto = Producto.builder()
            .nombre(nombre)
            .precio(precio)
            .descripcion(descripcion)
            .stock(stock)
            .imagen(imagen)
            .disponible(true)
            .build();

    categoria.addProducto(producto);

    categoriaRepo.guardar(categoria);

    System.out.println("Producto agregado correctamente.");
}

private static void modificarProducto() {

    System.out.println("\n===== MODIFICAR PRODUCTO =====");

    System.out.print("Ingrese el ID del producto: ");

    Long id;

    try {
        id = Long.parseLong(sc.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("ID inválido.");
        return;
    }

    Optional<Producto> productoOptional = productoRepo.buscarPorId(id);

    if (productoOptional.isEmpty()) {
        System.out.println("No existe un producto con ese ID.");
        return;
    }

    Producto producto = productoOptional.get();

    System.out.println("Deje el campo vacío para no modificarlo.");

    System.out.print("Nombre (" + producto.getNombre() + "): ");
    String nombre = sc.nextLine();
    if (!nombre.isBlank()) {
        producto.setNombre(nombre);
    }

    System.out.print("Precio (" + producto.getPrecio() + "): ");
    String precio = sc.nextLine();
    if (!precio.isBlank()) {
        producto.setPrecio(Double.parseDouble(precio));
    }

    System.out.print("Descripción (" + producto.getDescripcion() + "): ");
    String descripcion = sc.nextLine();
    if (!descripcion.isBlank()) {
        producto.setDescripcion(descripcion);
    }

    System.out.print("Stock (" + producto.getStock() + "): ");
    String stock = sc.nextLine();
    if (!stock.isBlank()) {
        producto.setStock(Integer.parseInt(stock));
    }

    System.out.print("Imagen (" + producto.getImagen() + "): ");
    String imagen = sc.nextLine();
    if (!imagen.isBlank()) {
        producto.setImagen(imagen);
    }

    System.out.print("Disponible (true/false) (" + producto.getDisponible() + "): ");
    String disponible = sc.nextLine();
    if (!disponible.isBlank()) {
        producto.setDisponible(Boolean.parseBoolean(disponible));
    }

    productoRepo.guardar(producto);

    System.out.println("Producto modificado correctamente.");
}

private static void bajaProducto() {

    System.out.println("\n===== BAJA DE PRODUCTO =====");

    System.out.print("Ingrese el ID del producto: ");

    Long id;

    try {
        id = Long.parseLong(sc.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("ID inválido.");
        return;
    }

    try {
        boolean eliminado = productoRepo.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("No existe un producto con ese ID.");
        }

    } catch (Exception e) {
        System.out.println("Error al eliminar el producto: " + e.getMessage());
    }
}

private static void listarProductos() {

    System.out.println("\n===== LISTADO DE PRODUCTOS =====");

    List<Producto> productos = productoRepo.listarActivos();

    if (productos.isEmpty()) {
        System.out.println("No hay productos registrados.");
        return;
    }

    for (Producto producto : productos) {

        System.out.println("--------------------------------");
        System.out.println("ID: " + producto.getId());
        System.out.println("Nombre: " + producto.getNombre());
        System.out.println("Precio: $" + producto.getPrecio());
        System.out.println("Descripción: " + producto.getDescripcion());
        System.out.println("Stock: " + producto.getStock());
        System.out.println("Imagen: " + producto.getImagen());
        System.out.println("Disponible: " + (producto.getDisponible() ? "Sí" : "No"));
    }

    System.out.println("--------------------------------");
}


    private static void menuUsuarios() {
    boolean volver = false;

    while (!volver) {
        System.out.println("\n===== GESTIÓN DE USUARIOS =====");
        System.out.println("1. Alta");
        System.out.println("2. Modificar");
        System.out.println("3. Baja lógica");
        System.out.println("4. Listar");
        System.out.println("5. Buscar por mail");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                altaUsuario();
                break;
            case "2":
                modificarUsuario();
                break;
            case "3":
                bajaUsuario();
                break;
            case "4":
                listarUsuarios();
                break;
            case "5":
                buscarUsuarioPorMail();
                break;
            case "0":
                volver = true;
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
}

    private static void altaUsuario() {
    System.out.println("\n===== ALTA DE USUARIO =====");

    System.out.print("Nombre: ");
    String nombre = sc.nextLine();

    System.out.print("Apellido: ");
    String apellido = sc.nextLine();

    System.out.print("Mail: ");
    String mail = sc.nextLine();

    System.out.print("Celular: ");
    String celular = sc.nextLine();

    System.out.print("Contraseña: ");
    String contraseña = sc.nextLine();

    System.out.println("\nRoles disponibles:");
    System.out.println("1 - ADMIN");
    System.out.println("2 - USUARIO");
    System.out.print("Seleccione un rol: ");

    String opcionRol = sc.nextLine();

    Rol rol;

    switch (opcionRol) {
        case "1":
            rol = Rol.ADMIN;
            break;
        case "2":
            rol = Rol.USUARIO;
            break;
        default:
            System.out.println("Rol inválido. Se asignará USUARIO.");
            rol = Rol.USUARIO;
    }

    Usuario usuario = Usuario.builder()
            .nombre(nombre)
            .apellido(apellido)
            .mail(mail)
            .celular(celular)
            .contraseña(contraseña)
            .rol(rol)
            .build();

    try {
        usuarioRepo.guardar(usuario);
        System.out.println("Usuario creado correctamente.");
    } catch (Exception e) {
        System.out.println("Error al guardar el usuario: " + e.getMessage());
    }
}

private static void modificarUsuario() {
    System.out.println("\n===== MODIFICAR USUARIO =====");

    listarUsuarios();

    System.out.print("\nIngrese el ID del usuario a modificar: ");
    Long id = Long.parseLong(sc.nextLine());

    Optional<Usuario> usuarioOptional = usuarioRepo.buscarPorId(id);

    if (usuarioOptional.isEmpty()) {
        System.out.println("Usuario no encontrado.");
        return;
    }

    Usuario usuario = usuarioOptional.get();

    System.out.print("Nombre (" + usuario.getNombre() + "): ");
    String nombre = sc.nextLine();
    if (!nombre.isBlank()) {
        usuario.setNombre(nombre);
    }

    System.out.print("Apellido (" + usuario.getApellido() + "): ");
    String apellido = sc.nextLine();
    if (!apellido.isBlank()) {
        usuario.setApellido(apellido);
    }

    System.out.print("Mail (" + usuario.getMail() + "): ");
    String mail = sc.nextLine();
    if (!mail.isBlank()) {
        usuario.setMail(mail);
    }

    System.out.print("Celular (" + usuario.getCelular() + "): ");
    String celular = sc.nextLine();
    if (!celular.isBlank()) {
        usuario.setCelular(celular);
    }

    System.out.print("Contraseña (Enter para mantener): ");
    String contraseña = sc.nextLine();
    if (!contraseña.isBlank()) {
        usuario.setContraseña(contraseña);
    }

    System.out.println("\nRol actual: " + usuario.getRol());
    System.out.println("1 - ADMIN");
    System.out.println("2 - USUARIO");
    System.out.print("Nuevo rol (Enter para mantener): ");
    String opcionRol = sc.nextLine();

    if (!opcionRol.isBlank()) {
        switch (opcionRol) {
            case "1":
                usuario.setRol(Rol.ADMIN);
                break;
            case "2":
                usuario.setRol(Rol.USUARIO);
                break;
            default:
                System.out.println("Rol inválido. Se mantiene el actual.");
        }
    }

    try {
        usuarioRepo.guardar(usuario);
        System.out.println("Usuario modificado correctamente.");
    } catch (Exception e) {
        System.out.println("Error al modificar el usuario: " + e.getMessage());
    }
}

private static void bajaUsuario() {
    System.out.println("\n===== BAJA DE USUARIO =====");

    listarUsuarios();

    System.out.print("\nIngrese el ID del usuario a eliminar: ");
    Long id = Long.parseLong(sc.nextLine());

    try {
        boolean eliminado = usuarioRepo.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Usuario dado de baja correctamente.");
        } else {
            System.out.println("No se encontró el usuario con ese ID.");
        }

    } catch (Exception e) {
        System.out.println("Error al dar de baja el usuario: " + e.getMessage());
    }
}

private static void listarUsuarios() {
    System.out.println("\n===== LISTADO DE USUARIOS =====");

    List<Usuario> usuarios = usuarioRepo.listarActivos();

    if (usuarios.isEmpty()) {
        System.out.println("No hay usuarios registrados.");
        return;
    }

    for (Usuario u : usuarios) {
        System.out.println("--------------------------------");
        System.out.println("ID: " + u.getId());
        System.out.println("Nombre: " + u.getNombre());
        System.out.println("Apellido: " + u.getApellido());
        System.out.println("Mail: " + u.getMail());
        System.out.println("Celular: " + u.getCelular());
        System.out.println("Rol: " + u.getRol());
        System.out.println("--------------------------------");
    }
}

private static void buscarUsuarioPorMail() {
    System.out.println("\n===== BUSCAR USUARIO POR MAIL =====");

    System.out.print("Ingrese el mail: ");
    String mail = sc.nextLine();

    Optional<Usuario> usuarioOpt = usuarioRepo.buscarPorMail(mail);

    if (usuarioOpt.isPresent()) {
        System.out.println(usuarioOpt.get());
    } else {
        System.out.println("No se encontró ningún usuario con ese mail.");
    }
}

    private static void menuPedidos() {
    boolean volver = false;

    while (!volver) {
        System.out.println("\n===== GESTIÓN DE PEDIDOS =====");
        System.out.println("1. Alta");
        System.out.println("2. Cambiar estado");
        System.out.println("3. Baja lógica");
        System.out.println("4. Listar");
        System.out.println("5. Por usuario");
        System.out.println("6. Por estado");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        String op = sc.nextLine();

        switch (op) {
            case "1":
                altaPedido();
                break;
            case "2":
                cambiarEstadoPedido();
                break;
            case "3":
                bajaPedido();
                break;
            case "4":
                listarPedidos();
                break;
            case "5":
                pedidosPorUsuario();
                break;
            case "6":
                pedidosPorEstado();
                break;
            case "0":
                volver = true;
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
}

private static void altaPedido() {
    System.out.println("\n===== ALTA DE PEDIDO =====");

    // 1. Seleccionar usuario
    listarUsuarios();

    System.out.print("\nIngrese ID del usuario: ");
    Long idUsuario = Long.parseLong(sc.nextLine());

    Optional<Usuario> usuarioOpt = usuarioRepo.buscarPorId(idUsuario);

    if (usuarioOpt.isEmpty()) {
        System.out.println("Usuario no encontrado.");
        return;
    }

    Usuario usuario = usuarioOpt.get();

    Pedido pedido = new Pedido();
    pedido.setUsuario(usuario);

    boolean seguir = true;

    while (seguir) {
        listarProductos();

        System.out.print("\nIngrese ID del producto (0 para finalizar): ");
        Long idProducto = Long.parseLong(sc.nextLine());

        if (idProducto == 0) {
            seguir = false;
            break;
        }

        Optional<Producto> productoOpt = productoRepo.buscarPorId(idProducto);

        if (productoOpt.isEmpty()) {
            System.out.println("Producto no encontrado.");
            continue;
        }

        Producto producto = productoOpt.get();

        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(sc.nextLine());

        pedido.addDetallePedido(cantidad, producto);
    }

    System.out.println("\nForma de pago:");
    System.out.println("1 - EFECTIVO");
    System.out.println("2 - TARJETA");
    System.out.println("3 - TRANSFERENCIA");

    String pago = sc.nextLine();

    switch (pago) {
        case "1":
            pedido.setFormaPago(FormaPago.EFECTIVO);
            break;
        case "2":
            pedido.setFormaPago(FormaPago.TARJETA);
            break;
        case "3":
            pedido.setFormaPago(FormaPago.TRANSFERENCIA);
            break;
        default:
            System.out.println("Forma de pago inválida. Se asigna EFECTIVO.");
            pedido.setFormaPago(FormaPago.EFECTIVO);
    }

    pedido.calcularTotal();

    usuario.addPedido(pedido);

    try {
        usuarioRepo.guardar(usuario);
        System.out.println("Pedido creado correctamente. Total: " + pedido.getTotal());
    } catch (Exception e) {
        System.out.println("Error al guardar pedido: " + e.getMessage());
    }
}

private static void cambiarEstadoPedido() {
    System.out.println("\n===== CAMBIAR ESTADO DE PEDIDO =====");

    listarPedidos();

    System.out.print("\nIngrese ID del pedido: ");
    Long idPedido = Long.parseLong(sc.nextLine());

    Optional<Pedido> pedidoOpt = pedidoRepo.buscarPorId(idPedido);

    if (pedidoOpt.isEmpty()) {
        System.out.println("Pedido no encontrado.");
        return;
    }

    Pedido pedido = pedidoOpt.get();

    System.out.println("\nEstado actual: " + pedido.getEstado());

    System.out.println("\nNuevo estado:");
    System.out.println("1 - PENDIENTE");
    System.out.println("2 - EN_PREPARACION");
    System.out.println("3 - ENTREGADO");

    String op = sc.nextLine();

    switch (op) {
        case "1":
            pedido.setEstado(EstadoPedido.PENDIENTE);
            break;
        case "2":
            pedido.setEstado(EstadoPedido.EN_PREPARACION);
            break;
        case "3":
            pedido.setEstado(EstadoPedido.ENTREGADO);
            break;
        default:
            System.out.println("Estado inválido. No se realizaron cambios.");
            return;
    }

    try {
        pedidoRepo.guardar(pedido);
        System.out.println("Estado actualizado correctamente.");
    } catch (Exception e) {
        System.out.println("Error al actualizar estado: " + e.getMessage());
    }
}

private static void bajaPedido() {
    System.out.println("\n===== BAJA DE PEDIDO =====");

    listarPedidos();

    System.out.print("\nIngrese ID del pedido a eliminar: ");
    Long id = Long.parseLong(sc.nextLine());

    boolean eliminado = pedidoRepo.eliminarLogico(id);

    if (eliminado) {
        System.out.println("Pedido eliminado lógicamente correctamente.");
    } else {
        System.out.println("No se encontró el pedido.");
    }
}

private static void listarPedidos() {
    System.out.println("\n===== LISTADO DE PEDIDOS =====");

    List<Pedido> pedidos = pedidoRepo.listarActivos();

    if (pedidos.isEmpty()) {
        System.out.println("No hay pedidos registrados.");
        return;
    }

    for (Pedido p : pedidos) {
        System.out.println("--------------------------------");
        System.out.println("ID Pedido: " + p.getId());
        System.out.println("Fecha: " + p.getFecha());
        System.out.println("Estado: " + p.getEstado());
        System.out.println("Forma de pago: " + p.getFormaPago());
        System.out.println("Total: " + p.getTotal());

        System.out.println("\nUsuario:");
        System.out.println(p.getUsuario().getNombre() + " " + p.getUsuario().getApellido());
        System.out.println("Mail: " + p.getUsuario().getMail());

        System.out.println("\nDetalles:");

        for (DetallePedido d : p.getDetalles()) {
            System.out.println("- Producto: " + d.getProducto().getNombre());
            System.out.println("  Cantidad: " + d.getCantidad());
            System.out.println("  Subtotal: " + d.getSubtotal());
        }

        System.out.println("--------------------------------");
    }
}

private static void pedidosPorUsuario() {
    System.out.println("\n===== PEDIDOS POR USUARIO =====");

    listarUsuarios();

    System.out.print("\nIngrese ID del usuario: ");
    Long idUsuario = Long.parseLong(sc.nextLine());

    Optional<Usuario> usuarioOpt = usuarioRepo.buscarPorId(idUsuario);

    if (usuarioOpt.isEmpty()) {
        System.out.println("Usuario no encontrado.");
        return;
    }

    Usuario usuario = usuarioOpt.get();

    List<Pedido> pedidos = usuarioRepo.buscarPedidosPorUsuario(idUsuario);

    if (pedidos.isEmpty()) {
        System.out.println("El usuario no tiene pedidos.");
        return;
    }

    System.out.println("\nPedidos de: " +
            usuario.getNombre() + " " + usuario.getApellido());

    for (Pedido p : pedidos) {
        System.out.println("--------------------------------");
        System.out.println("ID Pedido: " + p.getId());
        System.out.println("Fecha: " + p.getFecha());
        System.out.println("Estado: " + p.getEstado());
        System.out.println("Forma de pago: " + p.getFormaPago());
        System.out.println("Total: " + p.getTotal());

        System.out.println("\nDetalles:");

        for (DetallePedido d : p.getDetalles()) {
            System.out.println("- Producto: " + d.getProducto().getNombre());
            System.out.println("  Cantidad: " + d.getCantidad());
            System.out.println("  Subtotal: " + d.getSubtotal());
        }
    }
}

private static void pedidosPorEstado() {
    System.out.println("\n===== PEDIDOS POR ESTADO =====");

    System.out.println("Estados disponibles:");
    for (EstadoPedido e : EstadoPedido.values()) {
        System.out.println("- " + e);
    }

    System.out.print("\nIngrese estado: ");
    String estadoInput = sc.nextLine().trim().toUpperCase();

    try {
        EstadoPedido estado = EstadoPedido.valueOf(estadoInput);

        List<Pedido> pedidos = pedidoRepo.buscarPorEstado(estado);

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos con ese estado.");
            return;
        }

        System.out.println("\n===== RESULTADO =====");
        for (Pedido p : pedidos) {
            System.out.println("--------------------------------");
            System.out.println("ID: " + p.getId());
            System.out.println("Fecha: " + p.getFecha());
            System.out.println("Estado: " + p.getEstado());
            System.out.println("Total: $" + p.getTotal());
            System.out.println("Forma de pago: " + p.getFormaPago());
        }

    } catch (IllegalArgumentException e) {
        System.out.println("Estado inválido.");
    }
}


    private static void menuReportes() {
    boolean volver = false;

    while (!volver) {
        System.out.println("\n===== REPORTES =====");
        System.out.println("1. Productos por categoría");
        System.out.println("2. Pedidos por usuario");
        System.out.println("3. Pedidos por estado");
        System.out.println("4. Total facturado");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        String op = sc.nextLine();

        switch (op) {
            case "1":
                reporteProductosPorCategoria();
                break;
            case "2":
                reportePedidosPorUsuario();
                break;
            case "3":
                reportePedidosPorEstado();
                break;
            case "4":
                reporteTotalFacturado();
                break;
            case "0":
                volver = true;
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
}

private static void reporteProductosPorCategoria() {}
private static void reportePedidosPorUsuario() {}
private static void reportePedidosPorEstado() {}
private static void reporteTotalFacturado() {}
}

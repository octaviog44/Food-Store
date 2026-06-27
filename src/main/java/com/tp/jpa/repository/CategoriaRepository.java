package com.tp.jpa.repository;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repositorio de Categoria. Además del CRUD heredado implementa la consulta
 * de productos activos pertenecientes a una categoría.
 *
 * Nota de diseño: como la relación es unidireccional y Categoria es la dueña
 * de la colección Set<Producto>, la navegación se hace desde Categoria hacia
 * sus productos (p. ej. JPQL con JOIN sobre c.productos).
 */
public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }

    /**
     * Retorna los productos activos que pertenecen a la categoría indicada.
     */
   public List<Producto> buscarProductosPorCategoria(Long categoriaId) {
    EntityManager em = emf.createEntityManager();
    try {
        String jpql = """
                SELECT p
                FROM Categoria c
                JOIN c.productos p
                WHERE c.id = :id
                AND p.eliminado = false
                """;

        return em.createQuery(jpql, Producto.class)
                .setParameter("id", categoriaId)
                .getResultList();
    } finally {
        em.close();
    }
}
}

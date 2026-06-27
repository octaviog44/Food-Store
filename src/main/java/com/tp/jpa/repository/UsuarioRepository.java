package com.tp.jpa.repository;

import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;


/**
 * Repositorio de Usuario. Además del CRUD heredado implementa la consulta
 * de pedidos activos pertenecientes a un usuario.
 *
 * Nota de diseño: como la relación es unidireccional y Usuario es la dueña
 * de la colección Set<Pedido>, la navegación se hace desde Usuario hacia
 * sus pedidos (p. ej. JPQL con JOIN sobre u.pedidos).
 */

public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public Optional<Usuario> buscarPorMail(String mail) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = """
                    SELECT u
                    FROM Usuario u
                    WHERE u.mail = :mail
                      AND u.eliminado = false
                    """;

            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("mail", mail);

            List<Usuario> resultados = query.getResultList();

            if (resultados.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(resultados.get(0));

        } finally {
            em.close();
        }
    }

    public List<Pedido> buscarPedidosPorUsuario(Long idUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = """
                    SELECT p
                    FROM Usuario u
                    JOIN u.pedidos p
                    WHERE u.id = :id
                      AND p.eliminado = false
                    """;

            return em.createQuery(jpql, Pedido.class)
                    .setParameter("id", idUsuario)
                    .getResultList();

        } finally {
            em.close();
        }
    }
}
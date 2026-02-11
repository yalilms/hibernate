package org.yalil.tierramedia.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.yalil.tierramedia.model.Raza;
import org.yalil.tierramedia.util.HibernateUtil;

import java.util.List;

public class RazaDAO {
    
    // CREATE - Guardar raza
    public void guardar(Raza raza) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(raza);
            transaction.commit();
            System.out.println("Raza guardada exitosamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // READ - Obtener raza por ID
    public Raza obtenerPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Raza.class, id);
        }
    }
    
    // READ - Obtener todas las razas
    public List<Raza> obtenerTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Raza", Raza.class).list();
        }
    }
    
    // UPDATE - Actualizar raza
    public void actualizar(Raza raza) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(raza);
            transaction.commit();
            System.out.println("Raza actualizada exitosamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // DELETE - Eliminar raza
    public void eliminar(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Raza raza = session.get(Raza.class, id);
            if (raza != null) {
                session.delete(raza);
                System.out.println("Raza eliminada exitosamente");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Buscar razas por reino
    public List<Raza> buscarPorReino(String reino) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Raza WHERE reinoOrigen = :reino", Raza.class)
                    .setParameter("reino", reino)
                    .list();
        }
    }

    public Raza obtenerConPersonajes(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Raza r LEFT JOIN FETCH r.personajes WHERE r.id = :id",
                            Raza.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }
}
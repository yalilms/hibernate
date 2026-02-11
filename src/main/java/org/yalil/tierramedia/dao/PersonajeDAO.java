package org.yalil.tierramedia.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.yalil.tierramedia.model.Personaje;
import org.yalil.tierramedia.util.HibernateUtil;

import java.util.List;

public class PersonajeDAO {
    
    // CREATE - Guardar personaje
    public void guardar(Personaje personaje) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(personaje);
            transaction.commit();
            System.out.println("Personaje guardado exitosamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // READ - Obtener personaje por ID
    public Personaje obtenerPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Personaje p LEFT JOIN FETCH p.raza WHERE p.id = :id",
                            Personaje.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }
    
    // READ - Obtener todos los personajes
    public List<Personaje> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Personaje p LEFT JOIN FETCH p.raza", Personaje.class).list();
        }
    }
    
    // UPDATE - Actualizar personaje
    public void actualizar(Personaje personaje) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(personaje);
            transaction.commit();
            System.out.println("Personaje actualizado exitosamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // DELETE - Eliminar personaje
    public void eliminar(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Personaje personaje = session.get(Personaje.class, id);
            if (personaje != null) {
                session.delete(personaje);
                System.out.println("Personaje eliminado exitosamente");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Buscar personajes por raza
    public List<Personaje> buscarPorRaza(Long razaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Personaje p LEFT JOIN FETCH p.raza WHERE p.raza.id = :razaId", Personaje.class)
                    .setParameter("razaId", razaId)
                    .list();
        }
    }
    
    // Buscar personajes por rango de nivel de poder
    public List<Personaje> buscarPorRangoNivelPoder(Double min, Double max) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Personaje WHERE nivelPoder BETWEEN :min AND :max", 
                            Personaje.class)
                    .setParameter("min", min)
                    .setParameter("max", max)
                    .list();
        }
    }
    
    // Buscar personajes por arma
    public List<Personaje> buscarPorArma(String arma) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Personaje WHERE armaPrincipal = :arma", Personaje.class)
                    .setParameter("arma", arma)
                    .list();
        }
    }
    
    // Obtener los N personajes más poderosos
    public List<Personaje> obtenerMasPoderosos(int limite) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Personaje p LEFT JOIN FETCH p.raza ORDER BY p.nivelPoder DESC",
                            Personaje.class)
                    .setMaxResults(limite)
                    .list();
        }
    }
    
    // Obtener personaje con su raza (eager loading)
    public Personaje obtenerConRaza(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            /*
                LEFT JOIN: trae personaje aunque no tenga raza asociada
                FETCH: palabra de Hibernate para traer todo en una consulta
                :id es para parametrizar el id
                setParameter es para decir cual es el id parametrizado
             */
            return session.createQuery(
                            "FROM Personaje p LEFT JOIN FETCH p.raza WHERE p.id = :id",
                            Personaje.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }
} 
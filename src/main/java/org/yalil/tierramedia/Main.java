package org.yalil.tierramedia;

import org.yalil.tierramedia.dao.RazaDAO;
import org.yalil.tierramedia.dao.PersonajeDAO;
import org.yalil.tierramedia.model.Raza;
import org.yalil.tierramedia.model.Personaje;
import org.yalil.tierramedia.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        RazaDAO razaDAO = new RazaDAO();
        PersonajeDAO personajeDAO = new PersonajeDAO();

        System.out.println("=== SISTEMA DE GESTIÓN DE LA TIERRA MEDIA ===\n");

        // 1. CREATE - Insertar razas primero
        System.out.println("1. Insertando razas...");
        Raza hobbit = new Raza("Hobbit", "Sigilo y resistencia", "La Comarca", 100);
        Raza elfo = new Raza("Elfo", "Inmortalidad y visión aguda", "Rivendel", 999999);
        Raza humano = new Raza("Humano", "Adaptabilidad y coraje", "Gondor", 80);
        Raza enano = new Raza("Enano", "Fuerza y artesanía", "Erebor", 250);

        razaDAO.guardar(hobbit);
        razaDAO.guardar(elfo);
        razaDAO.guardar(humano);
        razaDAO.guardar(enano);
        System.out.println();

        // 2. CREATE - Insertar personajes asociados a razas
        System.out.println("2. Insertando personajes con relación a razas...");
        
        Personaje frodo = new Personaje("Frodo Bolsón", 50, "Dardo", 45.0, 
                LocalDate.of(2968, 9, 22), hobbit);
        Personaje sam = new Personaje("Sam Gamyi", 38, "Dardo", 40.0, 
                LocalDate.of(2980, 4, 6), hobbit);
        Personaje legolas = new Personaje("Legolas", 2931, "Arco élfico", 80.0, 
                LocalDate.of(87, 1, 1), elfo);
        Personaje galadriel = new Personaje("Galadriel", 7000, "Nenya", 95.0, 
                LocalDate.of(1, 1, 1), elfo);
        Personaje aragorn = new Personaje("Aragorn", 87, "Andúril", 85.0, 
                LocalDate.of(2931, 3, 1), humano);
        Personaje boromir = new Personaje("Boromir", 41, "Espada", 70.0, 
                LocalDate.of(2978, 1, 1), humano);
        Personaje gimli = new Personaje("Gimli", 139, "Hacha de batalla", 75.0, 
                LocalDate.of(2879, 1, 1), enano);
        Personaje thorin = new Personaje("Thorin Escudo de Roble", 195, "Orcrist", 82.0, 
                LocalDate.of(2746, 1, 1), enano);

        personajeDAO.guardar(frodo);
        personajeDAO.guardar(sam);
        personajeDAO.guardar(legolas);
        personajeDAO.guardar(galadriel);
        personajeDAO.guardar(aragorn);
        personajeDAO.guardar(boromir);
        personajeDAO.guardar(gimli);
        personajeDAO.guardar(thorin);
        System.out.println();

        // 3. READ - Listar todos los personajes
        System.out.println("3. Listando todos los personajes:");
        List<Personaje> personajes = personajeDAO.obtenerTodos();
        for (Personaje p : personajes) {
            System.out.println(p);
        }
        System.out.println();

        // 4. READ - Obtener personaje con su raza (JOIN FETCH)
        System.out.println("4. Obteniendo personaje con ID 1 incluyendo su raza:");
        Personaje personaje = personajeDAO.obtenerConRaza(1L);
        if (personaje != null) {
            System.out.println(personaje);
            System.out.println("Raza completa: " + personaje.getRaza());
        }
        System.out.println();

        // 5. UPDATE - Actualizar personaje
        System.out.println("5. Actualizando personaje...");
        if (personaje != null) {
            personaje.setNivelPoder(50.0); 
            personajeDAO.actualizar(personaje);
            System.out.println("Personaje después de actualizar: " + personajeDAO.obtenerPorId(1L));
        }
        System.out.println();

        // 6. Búsqueda por raza
        System.out.println("6. Buscando personajes de la raza Hobbit:");
        List<Personaje> personajesHobbit = personajeDAO.buscarPorRaza(1L);  // 1L = ID de Hobbit
        for (Personaje p : personajesHobbit) {
            System.out.println(p);
        }
        System.out.println();

        // 7. Listar todas las razas
        System.out.println("7. Listando todas las razas:");
        List<Raza> razas = razaDAO.obtenerTodas();
        for (Raza r : razas) {
            System.out.println(r);
        }
        System.out.println();

        // 8. Buscar razas por reino
        System.out.println("8. Buscando razas de Gondor:");
        List<Raza> razasGondor = razaDAO.buscarPorReino("Gondor");
        for (Raza r : razasGondor) {
            System.out.println(r);
        }
        System.out.println();

        // 9. Obtener los 5 personajes más poderosos
        System.out.println("9. Top 5 personajes más poderosos:");
        List<Personaje> masPoderosos = personajeDAO.obtenerMasPoderosos(5);
        for (Personaje p : masPoderosos) {
            System.out.println(p);
        }
        System.out.println();

        // 10. DELETE - Eliminar personaje
        System.out.println("10. Eliminando personaje con ID 2 (Sam)...");
        personajeDAO.eliminar(2L);

        System.out.println("\nPersonajes restantes:");
        List<Personaje> personajesRestantes = personajeDAO.obtenerTodos();
        for (Personaje p : personajesRestantes) {
            System.out.println(p);
        }
        System.out.println();

        // Cerrar SessionFactory
        HibernateUtil.shutdown();
        System.out.println("\n=== FIN DEL SISTEMA ===");
    }
}
package com.libralia.proyecto.principal;

import com.libralia.proyecto.modelo.Libro;
import com.libralia.proyecto.modelo.Usuario;

import java.util.ArrayList;
import java.util.Collections;

public class Principal {

    public static void main(String[] args) {
        Libro misLibros = new Libro("Deja de ser tú", "Joe Dispenza");
        misLibros.setGenero("Autoayuda");
        misLibros.setAnioDePublicacion(2012);
        misLibros.setReseniaPersonal("Deja de ser tú es un enfoque científico sobre el poder de la mente para transformar nuestra vida. Joe Dispenza desmitifica conceptos espirituales y los explica de manera clara, mostrando que el cambio real es posible si dejamos de ser nuestra versión limitada. Un libro revelador y transformador.");
        misLibros.setCalificacion(9.5);

        System.out.println(misLibros.mostrarInfoGeneral());

        misLibros.editarResenia("Prueba reseña editada");
        System.out.println(misLibros.mostrarInfoGeneral());

        misLibros.calificar(misLibros.getCalificacion());
        System.out.println("Valoración Personal: " + misLibros.getCalificacion());
        System.out.println("///////////////////////////////");

        Usuario miPerfil = new Usuario();
        miPerfil.setNombreUsuario("Natalia");
        miPerfil.setDescripcion("Lectora activa");

        System.out.println(miPerfil.toString());

        Libro libro1 = new Libro("El placebo eres tú", "Joe Dispenza");
        libro1.setGenero("Neurobiología");
        Libro libro2 = new Libro("Deja de ser tú", "Joe Dispenza");
        libro2.setGenero("Neurobiología");
        Libro libro3 = new Libro("Descubre tu destino", "Robin Sharma");
        libro3.setGenero("Autoayuda");
        Libro libro4 = new Libro("Resetea tu mente", "Mario Alonso Puig");
        libro4.setGenero("Neurobiología");
        Libro libro5 = new Libro("El camino del despertar", "Mario Alonso Puig");
        libro5.setGenero("Autoayuda");
        Libro libro6 = new Libro("Sigo siendo yo", "Jojo Moyes");
        libro6.setGenero("Romance");
        Libro libro7 = new Libro("Después de ti", "Jojo Moyes");
        libro7.setGenero("Romance");

        miPerfil.agregarLibroLeido(libro1);
        miPerfil.agregarLibroLeido(libro2);
        miPerfil.agregarLibroLeido(libro3);
        miPerfil.agregarLibroLeido(libro4);
        miPerfil.agregarLibroLeido(libro5);
        miPerfil.agregarLibroLeido(libro6);
        miPerfil.agregarLibroLeido(libro7);

        System.out.println("Lista de libros leídos por " + miPerfil.getNombreUsuario() + ":");
        for (Libro libro : miPerfil.getListaLibrosLeidos()) {
            System.out.println(("- ") + libro.getTituloConAutor()) ;
        }
        System.out.println("///////////////////////////////");

        System.out.println("Libros ordenados por Género:");

//        Collections.sort(miPerfil.getListaLibrosLeidos(), OrdenDeListaDeLibros.porTituloYAutor());
//        Collections.sort(miPerfil.getListaLibrosLeidos(), OrdenDeListaDeLibros.porAutorYTitulo());
//        Collections.sort(miPerfil.getListaLibrosLeidos(), OrdenDeListaDeLibros.porAnioYAutor());
//        Collections.sort(miPerfil.getListaLibrosLeidos(), OrdenDeListaDeLibros.porCalificacionTituloYAutor());

        Collections.sort(miPerfil.getListaLibrosLeidos(), OrdenDeListaDeLibros.porGeneroYAutor()); // criterio a probar
        for (Libro libro : miPerfil.getListaLibrosLeidos()) {
            System.out.println(libro.getGenero() + ":" + libro.getTituloConAutor() + ".");
            
        }
    }
}


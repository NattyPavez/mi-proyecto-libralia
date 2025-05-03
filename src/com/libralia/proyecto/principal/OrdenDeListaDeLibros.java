package com.libralia.proyecto.principal;

import com.libralia.proyecto.modelo.Libro;

import java.util.Collections;
import java.util.Comparator;

public class OrdenDeListaDeLibros {

    public static Comparator<Libro> porTitulo() {
        return Comparator.comparing(Libro::getTitulo, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Libro> porAutor() {
        return Comparator.comparing(Libro::getAutor, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Libro> porGenero() {
        return Comparator.comparing(Libro::getGenero, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Libro> porAnio() {
        return Comparator.comparingInt(Libro::getAnioDePublicacion);
    }

    public static Comparator<Libro> porCalificacion() {
        return Comparator.comparingDouble(Libro::getCalificacion).reversed(); // mayor a menor
    }

    //ordenadores compuestos aplicados

    public static Comparator<Libro> porTituloYAutor() {
        return porTitulo().thenComparing(porAutor());
    }
    public static Comparator<Libro> porAutorYTitulo() {
        return porAutor().thenComparing(porTitulo());
    }
    public static Comparator<Libro> porGeneroYAutor() {
        return porGenero().thenComparing(porTitulo().thenComparing(porAutor()));
    }
    public static Comparator<Libro> porAnioYAutor() {
        return porAnio().thenComparing(porTitulo().thenComparing(porAutor()));
    }
    public static Comparator<Libro> porCalificacionTituloYAutor() {
        return porCalificacion().thenComparing(porTitulo().thenComparing(porAutor()));
    }

}

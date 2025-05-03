package com.libralia.proyecto.modelo;

import java.util.ArrayList;

public class Usuario {
    private String nombreUsuario;
    private String descripcion;
    private String listaLecturaActual;
    private String listaDeDeseos;
    private String enlaces;
    private ArrayList<Libro> listaLibrosLeidos = new ArrayList<>();

    public String mostrarInfoUsuario(){
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Sobre mi: " + descripcion);
        System.out.println("Libros leídos: " + listaLibrosLeidos.size()); // mejor mostrar el número
        System.out.println("Lectura actual: " + listaLecturaActual);
        System.out.println("Lista por leer: " + listaDeDeseos);
        System.out.println("Redes: " + enlaces);
        return "";
    }

    @Override
    public String toString() {
        return "Perfil \n" + this.getNombreUsuario() + "\n" + this.getDescripcion();
    }

    public void agregarLibroLeido(Libro libro){
        listaLibrosLeidos.add(libro);
    }

    public ArrayList<Libro> getListaLibrosLeidos() {
        return listaLibrosLeidos;
    }

    // Getters y setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEnlaces() {
        return enlaces;
    }

    public void setEnlaces(String enlaces) {
        this.enlaces = enlaces;
    }

    public String getListaDeDeseos() {
        return listaDeDeseos;
    }

    public void setListaDeDeseos(String listaDeDeseos) {
        this.listaDeDeseos = listaDeDeseos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getListaLecturaActual() {
        return listaLecturaActual;
    }

    public void setListaLecturaActual(String listaLecturaActual) {
        this.listaLecturaActual = listaLecturaActual;
    }

}

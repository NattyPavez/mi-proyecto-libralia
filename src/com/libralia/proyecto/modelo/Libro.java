package com.libralia.proyecto.modelo;

public class Libro {
    private String titulo;
    private String autor;
    private String genero;
    private int anioDePublicacion;
    private String reseniaPersonal;
    private double calificacion;

    public Libro(String titulo, String autor) {
        this.setTitulo(titulo);
        this.autor = autor;
    }

    public String getTituloConAutor(){
        return titulo + " (" + autor + ").";
    }

    public void calificar(double calificacionPersonal){
        if (calificacionPersonal >= 0 && calificacionPersonal <= 5){
            this.calificacion = calificacionPersonal;
        }else {
            System.out.println("Tu valoración debe estar entre 0 y 5 estrellas");
        }

    }


    public String editarResenia(String nuevaResenia){
        this.reseniaPersonal = nuevaResenia;
        return nuevaResenia;
    }

    public String mostrarInfoGeneral(){
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Género: " + genero);
        System.out.println("Año: " + anioDePublicacion);
        System.out.println("Reseña Personal: " + reseniaPersonal);

        return "";
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnioDePublicacion() {
        return anioDePublicacion;
    }

    public void setAnioDePublicacion(int anioDePublicacion) {
        this.anioDePublicacion = anioDePublicacion;
    }

    public String getReseniaPersonal() {
        return reseniaPersonal;
    }

    public void setReseniaPersonal(String reseniaPersonal) {
        this.reseniaPersonal = reseniaPersonal;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }


}

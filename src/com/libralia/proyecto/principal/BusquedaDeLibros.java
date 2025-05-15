package com.libralia.proyecto.principal;

import com.google.gson.Gson;
import com.libralia.proyecto.exepciones.CampoInvalidoException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BusquedaDeLibros {
    public static void main(String[] args) throws IOException, InterruptedException {

        //busqueda del usuario
        Scanner teclado = new Scanner(System.in);
        System.out.println("Buscar por título o autor");
        String busqueda = teclado.nextLine();

        // armar Url
        // verificar que el usuario no deje en blanco
        if (busqueda.trim().isEmpty()) {
            System.out.println("Escribe un nombre válido");
            return;
        }
        String busquedaFormateada = URLEncoder.encode(busqueda.trim(), StandardCharsets.UTF_8);
        ;//remplaza los espacios intermedios por "+",.trim : elimina espacio al comienzo y final.
        String direccion = "https://www.googleapis.com/books/v1/volumes?q=" + busquedaFormateada;

        //requerimiento a  HTTP
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        //deserializamos Json con Gson

        Gson gson = new Gson();
        ResultadoGoogle resultado = gson.fromJson(response.body(), ResultadoGoogle.class);// el resultado de deserializar se guarda en la clase ResultadoGoogle

        // info de libros buscados y encontrados comprobando que existe la informacion o es nula

        if (resultado.items == null || resultado.items.isEmpty()) { //si la busqueda es null o esta vacia
            System.out.println("No se encontraron resultados para: " + busqueda);
        } else {
            for (LibroGoogleDto libro : resultado.items) {//si no recorre la lista con un for
                if (libro.volumeInfo == null){//valida que vollumeInfo no sea "null"
                    System.out.println("Este libro no contiene información detallada");
                    System.out.println("-----------------------------");
                    continue;
                }
                var info = libro.volumeInfo; //estrae la info

                //validando el titulo
                try {
                    validarCampo(info.title, "Título");
                    validarCampo(info.publishedDate, "Fecha de publicación");
                    validarCampo(String.valueOf(info.authors), "Autor");

                    System.out.println("Título: " + info.title);
                    System.out.println("Publicación: " + info.publishedDate);
                    System.out.println("Autor: " +  String.join(", ",info.authors));
                } catch (CampoInvalidoException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("Descripción: " + (info.description != null ? info.description : "Sin descripción"));
                System.out.println("Género: " + (info.categories != null ? String.join(", ", info.categories) : "No especificado"));
                System.out.println("Portada: " + (info.imageLinks != null ? info.imageLinks.thumbnail : "No disponible"));
                System.out.println("---------------------------------------------");
            }
        }
    }

    // metodo auxiliar para validar campos
    public static void validarCampo(String campo, String nombreCampo) {
        if (campo == null || campo.equals("N/A")) {
            throw new CampoInvalidoException(nombreCampo + " Inválido o no disponible.");
        }
    }
    public static void validarCampoLista(java.util.List<String> lista, String nombreCampo){
        if (lista == null || lista.isEmpty()){
            throw new CampoInvalidoException(nombreCampo + " Inválido o no disponible");
        }
    }
}
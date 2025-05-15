package com.libralia.proyecto.principal;

import com.google.gson.Gson;
import com.libralia.proyecto.exepciones.CampoInvalidoException;
import com.libralia.proyecto.modelo.Libro;
import com.libralia.proyecto.modelo.Usuario;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws IOException, InterruptedException {

        Usuario usuario = crearUsuarioDesdeConsola(); //el usuario construye su perfil
        System.out.println("Bienvenido/a " + usuario.getNombreUsuario() + " a tu biblioteca social Libralia ");

        Scanner teclado = new Scanner(System.in);

        while (true) {
            System.out.println("\n ¿Qué deseas hacer ahora?");
            System.out.println("""
                    1 - Ver tu perfil.
                    2 - Buscar un libro.
                    0 - salir
                    """);
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1" -> usuario.mostrarInfoUsuario();
                case "2" -> buscarLibro();
                case "0" -> {
                    System.out.println("Fin de capítulo. Nos vemos en la próxima página.");
                    teclado.close();
                    return;
                }
                default -> System.out.println("Opción Invalida.");
            }
        }
    }

    // Metodo para construir el perfil del usuario
    public static Usuario crearUsuarioDesdeConsola() {
        Scanner teclado = new Scanner(System.in);
        Usuario user = new Usuario();

        System.out.println("//////////////////////////////////////////////");
        System.out.println("Bienvenido/a a tu biblioteca social Libralia ");
        System.out.println("---------------------------------------------");
        System.out.println("¿Cuál será tu nombre en el Multiverso Libralia?");
        user.setNombreUsuario(teclado.nextLine());

        System.out.println("¿Cuál es tu edad?");

        try {
            user.setEdad((Integer.parseInt(teclado.nextLine())));
        } catch (NumberFormatException e) {
            user.setEdad(0);
            System.out.println("Edad inválida o no registrada");
        }
        if (user.getEdad() < 14) {
            System.out.println("¡Wow! Un lector joven, bienvenido a Libralia.");
        }

        System.out.println("Cuentanos brevemente algo de tí: ");
        user.setDescripcion(teclado.nextLine());

        System.out.println("¿Qué estás leyendo actualmente?");
        user.setListaLecturaActual(teclado.nextLine());

        System.out.println("---------------------------------------");
        System.out.println("Agrega libros a tu biblioteca de leídos");
        while (true) {
            System.out.println("Título del libro: ");
            String titulo = teclado.nextLine();
            System.out.println("Autor: ");
            String autor = teclado.nextLine();

            Libro nuevoLibro = new Libro(titulo, autor);
            user.agregarLibroLeido(nuevoLibro);

            System.out.println("¿Agregarás otro? (si/no): ");
            String respuesta = teclado.nextLine().toLowerCase();
            if (!respuesta.equals("si")) break; // si la repuesta es diferente de si, pasamos a lo que viene
        }
        System.out.println("Crea tu lista de deseos (agrega todos los que quieras separados por comas)");
        user.setListaDeDeseos(teclado.nextLine());

        System.out.println("¿Quieres compartir algún enlace o red social?");
        user.setEnlaces(teclado.nextLine());

        System.out.println("¡Felicidades! Tu perfil ha sido creado");
        return user;
    }
    // prueba metodo para buscar libros con API
    public static void buscarLibro() throws IOException, InterruptedException {
        Scanner teclado = new Scanner(System.in);
        System.out.println("//////////////////////////////////////////////////////////////////");
        System.out.println("PRUEBA BUSQUEDA CON API");
        System.out.println("------------------------");
        System.out.println("Buscar libro por título o por autor: ");
        String busqueda = teclado.nextLine();

        // armar Url
        // verificar que el usuario no deje en blanco
        if (busqueda.trim().isEmpty()) {
            System.out.println("Escribe un nombre válido");
            return;
        }
        String busquedaFormateada = URLEncoder.encode(busqueda.trim(), StandardCharsets.UTF_8);
        //remplaza los espacios intermedios por "+",.trim : elimina espacio al comienzo y final.
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


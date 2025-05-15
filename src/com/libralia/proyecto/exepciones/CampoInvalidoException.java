package com.libralia.proyecto.exepciones;

public class CampoInvalidoException extends RuntimeException{
    public CampoInvalidoException(String mensaje){
        super(mensaje);
    }

}

package com.pjff.springcloud.msvc.cursos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
//import jakarta.validation.constraints.NotEmpty;

//Paso 1 Vid 14
//Anotamos la clase entity
@Entity
// Lo mapeamos a la tabla
@Table(name = "cursos")
public class Curso {

    /*--------------------------  ATRIBUTOS ------------------------------------------ */
    // Le mandamos su id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Vid 28, agregamos la validación.
    @NotEmpty
    private String nombre;

    /*--------------------------  GETTERS AND SETTERS --------------------------------- */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
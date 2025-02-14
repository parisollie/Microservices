package org.pjff.springcloud.msvc.usuarios.models.entity;

//import javax.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.*;

/*
//Es lo mismo que arriba 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
 */
//V-10,paso 3 
@Entity
// Paso 4, nombre de la tabla a la cual estará asociado.
@Table(name = "usuarios")
public class Usuario {

    /*-------------------------------Paso 5, ponemos  ATRIBUTOS ---------------------------------------- */
    // Le ponemos la llave primaria con el auto-incremental con el @GeneratedValue
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // la llave primaria
    private Long id;

    /*
     * V-28, agregamos las validacin @NotBlank, esta extension debe estar en el pom
     * 
     * @NotEmpty es solo para tipos String que sean de referencia como Long, Integer
     * Date o cualquier otro objeto usamos @Not Null
     */
    @NotBlank
    private String nombre;

    // Agregamos validaciones.
    @NotEmpty
    @Email
    // Column sirve para que el email sea unico en la tabla email.
    @Column(unique = true)
    private String email;

    private String password;

    /*-------------------------- Paso 6, GETTERS AND SETTERS --------------------------------- */
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.pjff.springcloud.msvc.cursos.models.entity;

import java.util.ArrayList;
import java.util.List;

import com.pjff.springcloud.msvc.cursos.models.Usuario;

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

    /*
     * VID 32
     * 1 curso puede tener muchos usuarios,cascade all ,eliminamos en cascada
     * que no queden huerfanos, todos los ids nulos que los eliminen
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // Vid 33, lo enlazamos a la llave foranea
    @JoinColumn(name = "curso_id")
    // Es del tipo list
    private List<CursoUsuario> cursoUsuarios;

    // Vid 33, este atributo es un campo y solo sirve para poblar.
    @Transient
    private List<Usuario> usuarios;

    // Generamos un constructor
    public Curso() {
        // Una instancia de cursoSuarios
        cursoUsuarios = new ArrayList<>();
        // Vid 33
        usuarios = new ArrayList<>();
    }

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

    // Vid 32

    public void addCursoUsuario(CursoUsuario cursoUsuario) {
        // Agregamos de uno
        cursoUsuarios.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario) {
        // elimina por el objeto y compara uno por uno
        cursoUsuarios.remove(cursoUsuario);
    }

    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }

    // Vid 33
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
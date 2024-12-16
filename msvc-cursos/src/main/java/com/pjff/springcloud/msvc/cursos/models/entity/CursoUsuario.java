package com.pjff.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;

//Vid 32 
@Entity
// se encarga de registar los ids de los usuarios en particular
@Table(name = "cursos_usuarios")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tendra una llave única
    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;

    /* _________________GETTERS AND SETTERS ________________________________ */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Vid 32
    @Override
    public boolean equals(Object obj) {
        // si los objetos son iguales, comparando por instancias.
        if (this == obj) {
            return true;
        }
        // revisar que el objeto sea del tipo CursoUsuario
        if (!(obj instanceof CursoUsuario)) {
            return false;
        }
        // Hacemos un cast de objeto y lo convertifmos a CursoUsuario
        CursoUsuario o = (CursoUsuario) obj;
        /*
         * Validamos si es distinto de nulo y si son iguales al otro objeto
         * que pasamos por referencia
         */
        return this.usuarioId != null && this.usuarioId.equals(o.usuarioId);
    }
}

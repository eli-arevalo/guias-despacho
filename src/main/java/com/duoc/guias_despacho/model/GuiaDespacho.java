package com.duoc.guias_despacho.model;
// cuando la app arranque hibernate va a crear una tabla guias_despacho con las columnas id, numero_guia, transportista, destino, fecha, nombre_archivo y ruta_s3
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "guias_despacho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuiaDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroGuia;
    private String transportista;
    private String destino;
    private String fecha;
    private String nombreArchivo;
    private String rutaS3;

}

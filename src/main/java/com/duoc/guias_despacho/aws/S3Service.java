package com.duoc.guias_despacho.aws;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import java.nio.charset.StandardCharsets;

// esta clase es el encargado de comunicarse con AWS s3 para subir archivos al bucket
@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void subirTexto(String nombreArchivo, String contenido) {

        PutObjectRequest request = PutObjectRequest.builder()//construimos la solicitud
                .bucket(bucket)//dice a qué bucket se va a subir el archivo
                .key(nombreArchivo)//con el nombre del archivo
                .build();

        s3Client.putObject(request, RequestBody.fromString(contenido));//y enviamo el contenido 
    }

    public String obtenerTexto(String rutaS3) {// este método recibira una ruta dentro de s3 y devolverá el texto que contiene
        
        GetObjectRequest request = GetObjectRequest.builder()//aqui creamos la solicitud para obtener el objeto
                .bucket(bucket)//en que bucket debe buscar
                .key(rutaS3)//y la ruta dentro del bucket
                .build();

        return new String(s3Client.getObjectAsBytes(request).asByteArray(), StandardCharsets.UTF_8);// y acá obtenemos el contenido del objeto y lo convertimos a string para devolverlo
    }

    public void eliminarArchivo(String rutaS3){

        DeleteObjectRequest request = DeleteObjectRequest.builder()
            .bucket(bucket)
            .key(rutaS3)
            .build();

        s3Client.deleteObject(request);
    }
}

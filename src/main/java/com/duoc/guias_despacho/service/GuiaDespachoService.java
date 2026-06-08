package com.duoc.guias_despacho.service;

import org.springframework.stereotype.Service;
import com.duoc.guias_despacho.model.GuiaDespacho;
import com.duoc.guias_despacho.repository.GuiaDespachoRepository;
import com.duoc.guias_despacho.aws.S3Service;
import java.util.List;

@Service
public class GuiaDespachoService {

    private final GuiaDespachoRepository repository;
    private final S3Service s3Service;

    public GuiaDespachoService(GuiaDespachoRepository repository, S3Service s3Service) {
        this.repository = repository;
        this.s3Service = s3Service;
    }

    public GuiaDespacho guardar(GuiaDespacho guiaDespacho) {
        
        String nombreArchivo = "guia_" + guiaDespacho.getNumeroGuia() + ".txt";

        String rutaS3 = guiaDespacho.getFecha() + "/" 
                        + guiaDespacho.getTransportista() + "/" 
                        + nombreArchivo;
        
        String contenido = "Número de Guía: " + guiaDespacho.getNumeroGuia() + "\n" +
                           "Transportista: " + guiaDespacho.getTransportista() + "\n" +
                           "Destino: " + guiaDespacho.getDestino() + "\n" +
                           "Fecha: " + guiaDespacho.getFecha();
        s3Service.subirTexto(rutaS3, contenido);

        guiaDespacho.setNombreArchivo(nombreArchivo);
        guiaDespacho.setRutaS3(rutaS3);

        return repository.save(guiaDespacho);
    }

    public List<GuiaDespacho> listarTodo() {
        return repository.findAll();
    }

    public GuiaDespacho buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<GuiaDespacho> buscarPorTransportista(String transportista) {
        return repository.findByTransportista(transportista);
    }

    public List<GuiaDespacho> buscarPorFecha(String fecha) {
        return repository.findByFecha(fecha);
    }

    public GuiaDespacho actualizar(Long id, GuiaDespacho nuevaGuiaDespacho){

        GuiaDespacho guia = repository.findById(id).orElseThrow();

        guia.setNumeroGuia(nuevaGuiaDespacho.getNumeroGuia());
        guia.setTransportista(nuevaGuiaDespacho.getTransportista());
        guia.setDestino(nuevaGuiaDespacho.getDestino());
        guia.setFecha(nuevaGuiaDespacho.getFecha());

        return repository.save(guia);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

}

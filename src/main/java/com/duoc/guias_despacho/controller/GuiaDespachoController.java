package com.duoc.guias_despacho.controller;

import com.duoc.guias_despacho.service.GuiaDespachoService;
import com.duoc.guias_despacho.aws.S3Service;
import com.duoc.guias_despacho.model.GuiaDespacho;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guias")
public class GuiaDespachoController {

    private final GuiaDespachoService service;

    //para verificar si la conexion funciona:
    private final S3Service s3Service;

    public GuiaDespachoController(GuiaDespachoService service, S3Service s3Service) {
        this.service = service;
        this.s3Service = s3Service;
    }

    @PostMapping("/test-s3")
    public String probarS3() {
        s3Service.subirTexto("prueba.txt", "¡Hola AWS S3 desde Spring :D!");
        return "Archivo subido a S3 exitosamente.";
    }

    @PostMapping
    public GuiaDespacho crearGuia(@RequestBody GuiaDespacho guiaDespacho) {
        return service.guardar(guiaDespacho);
    }      

    @GetMapping
    public List<GuiaDespacho> listarGuias() {
        return service.listarTodo();
    }

    @GetMapping("/descargar/{id}") //hacemos un endpoint de descarga
    public String descargarGuia(@PathVariable Long id, @RequestParam String token) {
        
        if(!token.equals("admin321")){//hacemos una validacion ultra simple para simular el control de acceso
            return "Token inválido. Acceso denegado >:c";
        }
        GuiaDespacho guia = service.buscarPorId(id);

        return s3Service.obtenerTexto(guia.getRutaS3());

    }

    @GetMapping("/transportista/{transportista}")
    public List<GuiaDespacho> buscarPorTransportista(@PathVariable String transportista) {
        return service.buscarPorTransportista(transportista);
    }

    @GetMapping("/fecha/{fecha}")
    public List<GuiaDespacho> buscarPorFecha(@PathVariable String fecha) {
        return service.buscarPorFecha(fecha);
    }

    @PutMapping("/{id}")
    public GuiaDespacho actualizarGuia(@PathVariable Long id, @RequestBody GuiaDespacho guiaDespacho) {
        return service.actualizar(id, guiaDespacho);
    }

    @DeleteMapping("/{id}")
    public void eliminarGuia(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/test")
    public String test() {
        return "Deploy automático funcionando >:O";
    }
}

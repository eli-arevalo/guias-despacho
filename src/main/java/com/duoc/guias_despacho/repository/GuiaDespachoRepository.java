package com.duoc.guias_despacho.repository;

import com.duoc.guias_despacho.model.GuiaDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GuiaDespachoRepository extends JpaRepository<GuiaDespacho, Long> {

    List<GuiaDespacho> findByTransportista(String transportista);

    List<GuiaDespacho> findByFecha(String fecha);
}

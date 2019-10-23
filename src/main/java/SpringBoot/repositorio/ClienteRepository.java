package SpringBoot.repositorio;


import SpringBoot.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by vacax on 20/09/16.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {



    List<Cliente> findAll();

    Cliente findByCedula(String cedula);


}

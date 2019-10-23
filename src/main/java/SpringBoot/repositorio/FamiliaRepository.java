package SpringBoot.repositorio;


import SpringBoot.entidades.Familia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by vacax on 20/09/16.
 */
public interface FamiliaRepository extends JpaRepository<Familia, Long> {



    List<Familia> findAll();
    Familia findById(int id);


}

package project.Shockbyte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.Shockbyte.DTO.NodeDTO;
import project.Shockbyte.model.Node;

import java.util.List;

@Repository
public interface NodeRepository  extends JpaRepository<Node, Long> {

   // @Query(value = "DELETE FROM node WHERE extract(minute from system_uptime) <> 0 AND extract(second from system_uptime) <> 0", nativeQuery = true)
   // void deleteTempData();

    @Query(value = "SELECT * FROM node where extract(second from system_uptime) = 0 AND extract(minute from system_uptime) = 0 ORDER BY id DESC LIMIT 24", nativeQuery = true)
    List<Node> listOfNodes24Hours();
}

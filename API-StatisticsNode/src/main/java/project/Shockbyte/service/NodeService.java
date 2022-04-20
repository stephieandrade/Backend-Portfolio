package project.Shockbyte.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.Shockbyte.DTO.NodeDTO;
import project.Shockbyte.exceptions.BadRequestException;
import project.Shockbyte.model.Node;
import project.Shockbyte.repository.NodeRepository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NodeService implements IService<NodeDTO, Long>{

    @Autowired(required = true)
    private final NodeRepository nodeRepository;

    Logger log = LoggerFactory.getLogger(NodeService.class);

    @Autowired
    private ObjectMapper mapper;

    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public NodeDTO findById(Long id) throws BadRequestException {
        Optional<Node> foundNode = nodeRepository.findById(id);
        if(foundNode.isEmpty() || foundNode.get().getId() == null){
            throw new BadRequestException("Node not found");
        }
        else {
            return mapper.convertValue(foundNode.get(), NodeDTO.class);
        }

    }

    //List All
    @Scheduled(cron="0 1 1 * * *") //call every 24 hours
    @Override
    public List<NodeDTO> findAll() {
        List<NodeDTO> nodeDTOS = new ArrayList<>();
        List<Node> listOfNodes24Hours = nodeRepository.listOfNodes24Hours();
        for(Node node : listOfNodes24Hours){
            NodeDTO nodeDTO = mapper.convertValue(node, NodeDTO.class);
            nodeDTOS.add(nodeDTO);
        }
        log.info("Nodes: {}", nodeDTOS);
        return nodeDTOS;
    }

    //Update CRUD
    @Override
    public NodeDTO update(NodeDTO node, Long id) throws BadRequestException {
        Node newNode = mapper.convertValue(this.findById(id), Node.class);
        if(node != null){
            newNode.setSystemUptime(LocalDateTime.now());
            newNode.setAllocatedDisk(node.getAllocatedDisk());
            newNode.setTotalDisk(node.getTotalDisk());
            newNode.setTotalRAM(node.getTotalRAM());
            newNode.setAllocatedRAM(node.getAllocatedRAM());
        }
        return mapper.convertValue(nodeRepository.save(newNode), NodeDTO.class);
    }

    //Delete CRUD
    @Override
    public void delete(Long id) {
        nodeRepository.deleteById(id);
    }

    //Create CRUD
    @Scheduled(cron = "*/30 * * * * ?") //executes every 30 sec
    @Override
    public void save(){
        Node newNode = new Node();
        File file = new File("c:");
        long totalSpace = file.getTotalSpace(); //total disk space in bytes.
        long usableSpace = file.getUsableSpace(); ///unallocated / free disk space in bytes.
        long freeSpace = file.getFreeSpace();
        newNode.setSystemUptime(LocalDateTime.now());
        newNode.setAllocatedDisk(totalSpace);
        newNode.setTotalDisk(freeSpace);
        newNode.setTotalRAM(Runtime.getRuntime().totalMemory());
        newNode.setAllocatedRAM(Runtime.getRuntime().freeMemory());
        mapper.convertValue(nodeRepository.save(newNode), NodeDTO.class);
    }
    /*// @Scheduled(cron = "0 0 12 * * ?") // executes at 12 pm every day
    @Scheduled(cron = "0 0 * * * ?") //every hour
    public void purgeData(){
        nodeRepository.deleteTempData();
        log.info("Data deleted");
    } */


}

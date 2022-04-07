package project.Shockbyte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.Shockbyte.DTO.NodeDTO;
import project.Shockbyte.exceptions.BadRequestException;
import project.Shockbyte.model.Node;
import project.Shockbyte.service.IService;

@CrossOrigin
@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private IService service;

    @PostMapping
    public void save(){
        service.save();
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findbyID(@PathVariable Long id) throws BadRequestException{
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody NodeDTO node, @PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(service.update(node, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id ) throws BadRequestException {
    if (service.findById(id) != null){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
    }
    else {
            throw new BadRequestException("Node not found with the ID to be deleted");
    }}

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> processErrorBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}

package com.example.thursday.controller;

import com.example.thursday.dto.ActorDTO;
import com.example.thursday.dto.ActorMapping;
import com.example.thursday.model.Actor;
import com.example.thursday.service.ActorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class ActorController {

  private final ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping("/actors")
  public ResponseEntity<List<ActorDTO>> getAllActors() {
    return ResponseEntity.ok(ActorMapping.toActorDTOList(actorService.getAllActors()));
  }

  @GetMapping("/actors/{id}")
  public ResponseEntity<ActorDTO> getActorById(@PathVariable long id) {
    if (!actorService.existsActorById(id)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(ActorMapping.toActorDTO(actorService.getActorById(id).get()));
  }

  @PostMapping("/actors")
  public ResponseEntity<ActorDTO> saveActor(@Valid @RequestBody ActorDTO actorDTO) {
    actorService.saveActor(ActorMapping.toActor(actorDTO));
    return ResponseEntity.ok(actorDTO);
  }

  @PutMapping("/actor/{id}")
  public ResponseEntity<ActorDTO> updateActor(@Valid @RequestBody ActorDTO actorDTO, @PathVariable int id) {
    if (!actorService.existsActorById(id)) {
      actorService.saveActor(ActorMapping.toActor(actorDTO));
      return ResponseEntity.created(
                      ServletUriComponentsBuilder.fromCurrentRequest()
                              .replacePath("/actors/{id}")
                              .buildAndExpand(id)
                              .toUri())
              .body(actorDTO);
    }

    Actor actorFromDB = actorService.getActorById(id).get();

    ActorMapping.updateActorFromDTO(actorDTO, actorFromDB);

    return ResponseEntity.ok(ActorMapping.toActorDTO(actorService.saveActor(actorFromDB)));
  }

  @DeleteMapping("/actor/{id}")
  public ResponseEntity<ActorDTO> deleteActor(@PathVariable long id) {
    if (!actorService.existsActorById(id)) {
      return ResponseEntity.notFound().build();
    }
    actorService.deleteActorById(id);

    return ResponseEntity.noContent().build();
  }

}

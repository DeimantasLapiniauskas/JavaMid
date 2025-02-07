package com.example.thursday.service;

import com.example.thursday.model.Actor;
import com.example.thursday.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
  private final ActorRepository actorRepository;

  @Autowired
  public ActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public List<Actor> getAllActors() {
    return actorRepository.findAll();
  }

  public Optional<Actor> getActorById(long id) {
    return actorRepository.findById(id);
  }

  public Actor saveActor(Actor actor) {
    return actorRepository.save(actor);
  }

  public boolean existsActorById(long id) {
    return actorRepository.existsById(id);
  }

  public void deleteActorById(long id) {
    actorRepository.deleteById(id);
  }


}

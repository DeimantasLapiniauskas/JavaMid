package com.example.thursday.dto;

import com.example.thursday.model.Actor;

import java.util.List;

public class ActorMapping {
  public static List<ActorDTO> toActorDTOList(List<Actor> actors) {
    return actors.stream()
            .map(actor -> new ActorDTO(
                            actor.getYour(),
                            actor.getLiking()
                    )
            )
            .toList();
  }

  public static ActorDTO toActorDTO(Actor actor) {
    return new ActorDTO(actor.getYour(), actor.getLiking());
  }

  public static Actor toActor(ActorDTO actorDTO) {
    Actor actor = new Actor();
    updateActorFromDTO(actorDTO, actor);

    return actor;
  }

  public static void updateActorFromDTO(ActorDTO actorDTO, Actor actor) {
    actor.setLiking(actor.getLiking());
    actor.setYour(actor.getYour());
  }

}

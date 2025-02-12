package com.example.missedtuesday.controller;


import com.example.missedtuesday.dto.MovieDTO;
import com.example.missedtuesday.dto.MovieMapping;
import com.example.missedtuesday.model.Movie;
import com.example.missedtuesday.repository.MovieRepository;
import com.example.missedtuesday.security.SecurityConfig;
import com.example.missedtuesday.service.MovieService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = MovieController.class)
@Import(SecurityConfig.class) // needed to check security
public class MovieControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MovieService movieService;

  @MockitoBean
  private MovieRepository movieRepository;

  //Happy test (aka how things are supposed to work)
  @Test
  @WithMockUser
  void getMovies_whenFindAll_thenReturnAllAnd200AsUser() throws Exception {
// given
    List<Movie> movies = List.of(
            new Movie(
                    "title1",
                    "director1"
            ), new Movie(
                    "title2",
                    "director2"
            )
    );

    given(movieService.findAllMovies()).willReturn(movies);

    // when
    mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("length()")
                    .value(2))
            .andExpect(jsonPath("[0].title")
                    .value("title1"))
            .andExpect(jsonPath("[0].director")
                    .value("director1"))
            .andExpect(jsonPath("[1].title")
                    .value("title2"))
            .andExpect(jsonPath("[1].director")
                    .value("director2"));
    Mockito.verify(movieService, times(1)).findAllMovies();
  }


  @Test
  @WithMockUser
  void getMovies_whenNoMovies_thenReturnAllAnd200AsUser() throws Exception {
// given
    List<Movie> movies = List.of();

    given(movieService.findAllMovies()).willReturn(movies);

    // when
    mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());

    Mockito.verify(movieService, times(1)).findAllMovies();
  }


  // Unhappy test (aka edgecase)
  @Test
  void getMovies_whenFindAllUnauthenticated_thenRespond401() throws Exception {
    List<Movie> movies = List.of(
            new Movie(
                    "title1",
                    "director1"
            ), new Movie(
                    "title2",
                    "director2"
            )
    );
    given(movieService.findAllMovies()).willReturn(movies);

    mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());

    Mockito.verify(movieService, times(0)).findAllMovies();
  }


  @Test
  @WithMockUser
  void getMovieById_whenFindOneMovieAuthenticated_thenReturn200AsUser() throws Exception {
    long id = 6L;
    Movie movie = new Movie(
            "Title",
            "director"
    );
    given(movieService.getMovieById(id)).willReturn(Optional.of(movie));
    given(movieService.existsMovieByID(id)).willReturn(true);

    mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value("Title"))
            .andExpect(jsonPath("director").value("director"));

    Mockito.verify(movieService, times(1)).getMovieById(id);
  }

  @Test
  @WithMockUser
  void getMovieById_whenFindOneMovieAuthenticated_thenReturn404AsUser() throws Exception {
    long id = 6L;

    given(movieService.existsMovieByID(id)).willReturn(false);

    mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + id))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());

    Mockito.verify(movieService, times(1)).existsMovieByID(id);
  }

  @Test
  void getMovieById_whenFindOneUnauthenticated_thenRespond401() throws Exception {
    long id = 6L;
    Movie movie = new Movie(
            "Title",
            "director"
    );
    given(movieService.getMovieById(id)).willReturn(Optional.of(movie));
    given(movieService.existsMovieByID(id)).willReturn(true);

    mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + id))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());

    Mockito.verify(movieService, times(0)).findAllMovies();
  }


  @Test
  @WithMockUser(roles = {"USER", "ADMIN"})
  void addMovie_whenAddMovieAdmin_thenRespond201() throws Exception {

    Movie movie = new Movie(
            "newTitle",
            "Newdirector"
    );

    given(movieService.existsMovieByTitle(movie.getTitle())).willReturn(false);
    given(movieService.existsMovieByDirector(movie.getDirector())).willReturn(false);
//    given(movieService.saveMovie().willReturn(movie); // doesn't work - can't put in "novies" into saveMovie() due to it changing forms on the way
    when(movieService.saveMovie(Mockito.any(Movie.class))).thenReturn(movie);
    ObjectMapper objectMapper = new ObjectMapper();


    mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                    .content(objectMapper.writeValueAsString(MovieMapping.toMovieDTO(movie)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("title").value("newTitle"))
            .andExpect(jsonPath("director").value("Newdirector"));

    Mockito.verify(movieService, times(1))
            .saveMovie(Mockito.any(Movie.class));

  }

  @Test
  @WithMockUser(roles = {"USER", "ADMIN"})
  void addMovie_whenAddMovieAdminWithBadMovie_thenRespond400() throws Exception {

    Movie movie = new Movie();

    given(movieService.existsMovieByTitle(movie.getTitle())).willReturn(false);
    given(movieService.existsMovieByDirector(movie.getDirector())).willReturn(false);
//    given(movieService.saveMovie().willReturn(movie); // doesn't work - can't put in "novies" into saveMovie() due to it changing forms on the way
    when(movieService.saveMovie(Mockito.any(Movie.class))).thenReturn(movie);
    ObjectMapper objectMapper = new ObjectMapper();


    mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                    .content(objectMapper.writeValueAsString(MovieMapping.toMovieDTO(movie)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("{\"director\":\"must not be null\",\"title\":\"Title has to be between 2 and 100 characters in length and be composed of only letters.\"}"));

  }

  @Test
  @WithMockUser
  void addMovie_whenAddMovieUser_thenRespond403() throws Exception {

    Movie movie = new Movie();

    ObjectMapper objectMapper = new ObjectMapper();

    mockMvc.perform(MockMvcRequestBuilders.post("/movies")
            )
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
  }


  @Test
  void addMovie_whenAddMovieUnauthenticated_thenRespond401() throws Exception {

    Movie movie = new Movie();

    ObjectMapper objectMapper = new ObjectMapper();

    mockMvc.perform(MockMvcRequestBuilders.post("/movies")
            )
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());
  }


  @Test
  @WithMockUser(roles = {"USER", "ADMIN"})
  void putMovie_whenPutMovieAdmin_thenRespond200() throws Exception {
    long id = 6;
    Movie movie = new Movie(
            "newTitle",
            "Newdirector"
    );

    given(movieService.existsMovieByTitleAndNotId(movie.getTitle(), id)).willReturn(false);
    given(movieService.existsMovieByDirectorAndNotId(movie.getDirector(), id)).willReturn(false);
    given(movieService.getMovieById(id)).willReturn(Optional.of(movie));
    given(movieService.existsMovieByID(id)).willReturn(true);
    when(movieService.saveMovie(Mockito.any(Movie.class))).thenReturn(movie);
    ObjectMapper objectMapper = new ObjectMapper();


    mockMvc.perform(MockMvcRequestBuilders.put("/movies/" + id)
                    .content(objectMapper.writeValueAsString(MovieMapping.toMovieDTO(movie)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value("newTitle"))
            .andExpect(jsonPath("director").value("Newdirector"));

    Mockito.verify(movieService, times(1)).saveMovie(Mockito.any(Movie.class));
  }


  @Test
  @WithMockUser
  void putMovie_whenPutMovieUser_thenRespond403() throws Exception {
    long id = 6;
    Movie movie = new Movie(
            "newTitle",
            "Newdirector"
    );

    given(movieService.existsMovieByTitleAndNotId(movie.getTitle(), id)).willReturn(false);
    given(movieService.existsMovieByDirectorAndNotId(movie.getDirector(), id)).willReturn(false);
    given(movieService.getMovieById(id)).willReturn(Optional.of(movie));
    given(movieService.existsMovieByID(id)).willReturn(true);
    when(movieService.saveMovie(Mockito.any(Movie.class))).thenReturn(movie);
    ObjectMapper objectMapper = new ObjectMapper();


    mockMvc.perform(MockMvcRequestBuilders.put("/movies/" + id)
                    .content(objectMapper.writeValueAsString(MovieMapping.toMovieDTO(movie)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    Mockito.verify(movieService, times(0)).saveMovie(Mockito.any(Movie.class));
  }

  @Test
  void putMovie_whenPutMovieUnauthorized_thenRespond401() throws Exception {
    long id = 6;
    Movie movie = new Movie(
            "newTitle",
            "Newdirector"
    );

    given(movieService.existsMovieByTitleAndNotId(movie.getTitle(), id)).willReturn(false);
    given(movieService.existsMovieByDirectorAndNotId(movie.getDirector(), id)).willReturn(false);
    given(movieService.getMovieById(id)).willReturn(Optional.of(movie));
    given(movieService.existsMovieByID(id)).willReturn(true);
    when(movieService.saveMovie(Mockito.any(Movie.class))).thenReturn(movie);
    ObjectMapper objectMapper = new ObjectMapper();


    mockMvc.perform(MockMvcRequestBuilders.put("/movies/" + id)
                    .content(objectMapper.writeValueAsString(MovieMapping.toMovieDTO(movie)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());
    Mockito.verify(movieService, times(0)).saveMovie(Mockito.any(Movie.class));
  }


  @Test
  @WithMockUser(roles = {"USER", "ADMIN"})
  void deleteMovie_whenDeleteMovieAdminWhenNoMovie_thenRespond204() throws Exception {
    long id = 6;

    given(movieService.existsMovieByID(id)).willReturn(true);
    ObjectMapper objectMapper = new ObjectMapper();


    mockMvc.perform(MockMvcRequestBuilders.delete("/movies/" + id))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());

    Mockito.verify(movieService, times(1)).deleteMovieById(id);
  }

  @Test
  @WithMockUser
  void deleteMovie_whenDeleteMovieUser_thenRespond204() throws Exception {
    long id = 6;

    given(movieService.existsMovieByID(id)).willReturn(true);
    ObjectMapper objectMapper = new ObjectMapper();


    mockMvc.perform(MockMvcRequestBuilders.delete("/movies/" + id))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());

    Mockito.verify(movieService, times(0)).deleteMovieById(id);
  } // hi


  @Test
  void deleteMovie_whenDeleteMovieUnauthorized_thenRespond204() throws Exception {
    long id = 6;

    given(movieService.existsMovieByID(id)).willReturn(true);
    ObjectMapper objectMapper = new ObjectMapper();


    mockMvc.perform(MockMvcRequestBuilders.delete("/movies/" + id))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());

    Mockito.verify(movieService, times(0)).deleteMovieById(id);
  }
}

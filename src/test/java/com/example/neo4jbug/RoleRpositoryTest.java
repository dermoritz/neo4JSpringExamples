package com.example.neo4jbug;

import static java.util.stream.StreamSupport.*;
import static org.junit.Assert.*;

import java.util.Optional;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRpositoryTest {

    @Autowired
    private RoleRepository repository;

    @Test
    public void testCrud(){
        //create
        Actor actor = new Actor();
        actor.setId("muller");

        Movie movie = new Movie();
        movie.setId("movie title");

        Role role = new Role();
        role.setActor(actor);
        role.setMovie(movie);
        role.setTitle("roleTitle");

        Role save = repository.save(role);
        assertNotNull(save.getRelationshipId());
        //read
        Iterable<Role> all = repository.findAll();

        assertThat(stream(all.spliterator(), false).count(), Is.is(1L));

        //update
        Role savedAndFoundRole = all.iterator().next();

        Movie movie1 = new Movie();
        movie1.setId("2nd title");

        savedAndFoundRole.setMovie(movie1);

        Role updated = repository.save(savedAndFoundRole);

        Optional<Role> byId = repository.findById(updated.getRelationshipId());
        assertThat(byId.get().getMovie(), Is.is(movie1));

        repository.deleteAll();
        assertThat(stream(repository.findAll().spliterator(), false).count(), Is.is(0L));

    }


}

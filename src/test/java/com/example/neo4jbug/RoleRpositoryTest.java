package com.example.neo4jbug;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRpositoryTest {

    @Autowired
    private RoleRepository repository;

    @Test
    @Transactional
    //@Rollback(false)
    public void testCrud(){
        //create
        Actor actor = new Actor();
        actor.setId("actor");

        Movie movie = new Movie();
        movie.setId("1st movie");

        Role role = new Role();
        role.setActor(actor);
        role.setMovie(movie);
        role.setTitle("roleTitle");

        Role save = repository.save(role);
        Long id = save.getRelationshipId();
        assertNotNull(id);
        //read
        Optional<Role> foundRoleOptional = repository.findById(id);
        assertTrue(foundRoleOptional.isPresent());
        Role savedAndFoundRole = foundRoleOptional.get();
        assertThat(savedAndFoundRole.getMovie(), is(movie));

        //update
        Movie movie1 = new Movie();
        movie1.setId("2nd movie");

        savedAndFoundRole.setMovie(movie1);

        Role updated = repository.save(savedAndFoundRole);

        Iterable<Role> all = repository.findAll();
        assertThat(StreamSupport.stream(all.spliterator(), false).count(), is(1L));

        //Optional<Role> byId = repository.findById(updated.getRelationshipId()); //this will work update creats new entity
        Optional<Role> byId = repository.findById(id);
        assertThat(byId.get().getMovie(), is(movie1));

    }


}

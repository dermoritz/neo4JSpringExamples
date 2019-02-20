package com.example.neo4jbug;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import lombok.Data;

@Data
@RelationshipEntity(type = "PLAYED_IN")
public class Role {
    @Id
    @GeneratedValue
    private Long relationshipId;

    private String title;

    @StartNode
    private Actor actor;

    @EndNode
    private Movie movie;
}

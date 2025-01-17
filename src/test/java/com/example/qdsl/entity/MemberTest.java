package com.example.qdsl.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() {
        //Given
        Team teamA = new Team("teamA");
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        member1.changeTeam(teamA);
        member2.changeTeam(teamA);

        //When
        em.persist(teamA);
        em.flush();
        em.clear();;

        //Then
        Team findTeam = em.createQuery("SELECT t FROM Team t WHERE t.id = :team_id", Team.class)
                .setParameter("team_id", 1L).getSingleResult();

        Assertions.assertThat(findTeam.getMembers().size()).isEqualTo(2);
    }
}
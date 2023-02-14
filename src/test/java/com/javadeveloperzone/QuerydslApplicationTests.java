package com.javadeveloperzone;

import com.javadeveloperzone.entity.Hello;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.entity.QHello;
import com.javadeveloperzone.entity.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

//@ExtendWith(SpringExtension.class) //Junit5
//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = {"spring.config.location=classpath:application-test.yml"})
//@Transactional
//@ActiveProfiles("test")
public class QuerydslApplicationTests {
//
//    @Test
//    void contextLoads() {
//        Hello hello = new Hello();
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
//            em.createQuery("select h from Hello as h", Hello.class);
//            tx.commit();
//
//            JPAQueryFactory query = new JPAQueryFactory(em);
//            QHello qHello = new QHello("h");
//
//            Hello result = query.selectFrom(qHello).fetchOne();
//            assertThat(result).isEqualTo(hello);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        em.close();
//        emf.close();
//
//    }
}

package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        // tx
        book.setName("asdsadsa");

        // 변경감지
        // tx commit
    }
}
package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // code

        try {
            Member member = new Member();
            member.setUsername("C");

            em.persist(member);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        // 1. Insertion
        /*
        try {
            Member member = new Member();

            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        */

        // 2. find (Select
        /*
        try {
            Member findMember = em.find(Member.class, 1L);

            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        */

        // 3. remove
        /*
        try {
            Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);

            tx.commit();

        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        */

        // 4. update
        /*
        try {
            Member findMember = em.find(Member.class, 2L);

            findMember.setName("HelloJPA");

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        */

        // 5. JPQL
        /*
        try {
            // 테이블 대상 쿼리가 아닌 객체 대상 쿼리 작성
            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();

            for (Member member : result) {
                System.out.println(member.getName());
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        */

        // application 종료
        emf.close();

    }
}

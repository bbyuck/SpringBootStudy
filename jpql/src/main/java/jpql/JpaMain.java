package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // code

        try {
            Team team = new Team();
            team.setName("team A");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

//            select절 서브쿼리를 하이버네이트가 지원한다. jpa 표준 스펙에선 지원하지 않
//            String query = "select (select avg(m1.age) from Member m1) as avgAge from Member m"...;

//            from절 서브쿼리는 안된다.
//            String query = "select mm.age, mm.username from (select m.age, m.username from Member m) as mm ..."
            em.createQuery(query);


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        // application 종료
        emf.close();

    }
}

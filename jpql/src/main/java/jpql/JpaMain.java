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

            String query = "select m from Member m left outer join m.team t on t.name = 'teamA'";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();
            /*
             * 1. inner join
             */

//            String query = "select m from Member m inner join m.team t";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();

            /*
             * 2. outer join
             */

//            String query = "select m from Member m left outer join m.team t";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();

            /*
             * 3. theta join
             */
//            String query = "select m from Member m, Team t where m.username = t.name";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();

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

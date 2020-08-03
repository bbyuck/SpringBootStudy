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
            member.setMemberType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            /*
             * case - end 문법
             */

//            String query = "select case when m.age <= 10 then '학생요금' when m.age >= 60 then '경로요금' else '일반요금' end from Member m";
//
//            List<String> resultList = em.createQuery(query, String.class).getResultList();
//
//            for(String s : resultList) {
//                System.out.println("s = " + s);
//            }

            /*
             * coalesce 문법
             */
            Member member1 = new Member();
            member1.setUsername("관리자");
            member1.setAge(10);
            member1.setTeam(team);
            member1.setMemberType(MemberType.ADMIN);
            em.persist(member1);

            String query = "select coalesce(m.username, '이름 없는 회원') from Member m";

            List<String> coalResult = em.createQuery(query, String.class).getResultList();

            for(String s : coalResult) {
                System.out.println("s : " + s);
            }


            /*
             * nullif 문법
             */

            String query1 = "select nullif(m.team.name, 'team A') as username from Member m";
            List<String> nullifRes = em.createQuery(query1, String.class).getResultList();

            for (String s : nullifRes) {
                System.out.println("s = " + s) ;
            }

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

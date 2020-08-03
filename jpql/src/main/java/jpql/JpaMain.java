package jpql;

import javax.persistence.*;
import java.util.Collection;
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
            member.setUsername("member2");
            member.setAge(10);
            member.setTeam(team);
            member.setMemberType(MemberType.ADMIN);
            em.persist(member);



            Member member1 = new Member();
            member1.setUsername("관리자");
            member1.setAge(10);
            member1.setTeam(team);
            member1.setMemberType(MemberType.ADMIN);
            em.persist(member1);


            em.flush();
            em.clear();

            /*
             * 경로 표현식
             * 단일 값 연관 경로
             */
//            String query = "select m.team from Member m";

            /*
             * 컬렉션 값 연관 경로
             */

//            String query = "select t.members from Team t";
//            List<Collection> result = em.createQuery(query, Collection.class).getResultList();


            /*
             * 컬렉션 값 연관 경로 -> from절에서 명시적 join을 통해 alias를 얻어 컬렉션에 저장된 엔티티 필드 탐색
             */

            String query = "select m.username from Team t join t.members m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
                System.out.println("result : " + s);
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

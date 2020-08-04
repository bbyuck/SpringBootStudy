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
            Team team1 = new Team();
            Team team2 = new Team();
            Team team3 = new Team();

            team1.setName("팀A");
            team2.setName("팀B");
            team3.setName("팀C");

            em.persist(team1);
            em.persist(team2);
            em.persist(team3);

            Member member1 = new Member();
            Member member2 = new Member();
            Member member3 = new Member();
            Member member4 = new Member();

            member1.setUsername("회원1");
            member2.setUsername("회원2");
            member3.setUsername("회원3");
            member4.setUsername("회원4");

            member1.setTeam(team1);
            member2.setTeam(team1);
            member3.setTeam(team2);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);

            em.flush();
            em.clear();

            /*
             * many to one 페치 조인 --> 실무에서 많이 쓰인다. 여기서 m.team은 프록시가 아닌 실제 엔티티 데이터가 영속성 컨텍스트에 담겨 저장됨
             */

            /*
            String query = "select m from Member m join fetch m.team";
            List<Member> resultList = em.createQuery(query, Member.class).getResultList();

            for(Member m : resultList) {
                System.out.println("member = " + m.getUsername() + ", " + m.getTeam().getName());

                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차 캐시)
                // 회원3, 팀B(SQL)
            }
            */

            /*
             * 컬렉션 페치 조인 --> one to many -> 데이터가 증가할 수 있으므로 일대다 연관관계 매칭에서는 페치 조인시 distinct로 데이터 뻥튀기 막음
             */

//            String query = "select distinct t from Team t join fetch t.members";
//            List<Team> resultList = em.createQuery(query, Team.class).getResultList();
//
//            for (Team t : resultList) {
//                // System.out.println("team = " + t.getName() + " | " + t.getMembers().size());
//                System.out.println("team = " + t + "\t" + t.getMembers());
//                // One To Many 관계에서는 중복 데이터가 발생할 수 있음 -> 영속성 컨텍스트에서는 하나의 인스턴스로 관리하긴 하지만 참조를 두 번 하게됨.
//            }

            /*
             * 일반 조인과 페치조인의 차이점
             */

            String query = "select t from Team t join t.members m";
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();

            for(Team t1 : resultList) {
                System.out.println("Team = " + t1.getName() + "| members = " + t1.getMembers().size());
                for(Member m : t1.getMembers()) {
                    System.out.println("-> member = Member{id=" + m.getId() + ", " + "username='" + m.getUsername() + "', age=" + m.getAge());
                }
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

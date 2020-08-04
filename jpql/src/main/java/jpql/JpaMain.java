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
            member4.setTeam(team3);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);

            Book book1 = new Book();
            Book book2 = new Book();

            book1.setAuthor("김영한");
            book1.setName("JPA");
            book1.setIsbn("12312321");
            book1.setPrice(10000);
            book1.setStockQuantity(10);

            book2.setAuthor("강혁");
            book2.setName("EGO");
            book2.setIsbn("12142321");
            book2.setPrice(50000);
            book2.setStockQuantity(10);

            em.persist(book1);
            em.persist(book2);

            Movie movie1 = new Movie();
            Movie movie2 = new Movie();
            Movie movie3 = new Movie();

            movie1.setActor("송강호");
            movie1.setDirector("봉준호");
            movie1.setName("살인의 추억");
            movie1.setPrice(11000);
            movie1.setStockQuantity(10);

            movie2.setActor("배두나");
            movie2.setDirector("봉준호");
            movie2.setName("괴물");
            movie2.setPrice(15000);
            movie2.setStockQuantity(10);

            movie3.setActor("송강호");
            movie3.setDirector("봉준호");
            movie3.setName("설국열차");
            movie3.setPrice(20000);
            movie3.setStockQuantity(10);

            em.persist(movie1);
            em.persist(movie2);
            em.persist(movie3);

            em.flush();
            em.clear();


            /*
             * jpql에서 데이터에 접근하는 것이 아닌 엔티티를 직접 사용하면 해당 엔티티의 기본 키값을 사용하는 sql쿼리를 내보냄
             */


            /*
            String query = "select count(m) from Member m";
//            String query = "select count(m) from Member m"; // Member 엔티티의 기본 키값인 Member.id를 사용
            Integer singleResult = em.createQuery(query, Integer.class).getSingleResult();
            System.out.println(singleResult);
             */

            /*
             * 엔티티를 jpql 파라미터로 전달해도 기본 키값을 이용한 sql을 보내준다.
             */

//            String query = "select m from Member m where m = :member";
//            Member findMember = em.createQuery(query, Member.class)
//                    .setParameter("member", member1)
//                    .getSingleResult();

            /*
             * 위와 아래가 동일한 SQL 쿼리를 내보냄
             */
//            String query = "select m from Member m where m.id =:memberId";
//            Member findMember = em.createQuery(query, Member.class)
//                    .setParameter("memberId", member1.getId())
//                    .getSingleResult();
//            System.out.println(findMember);



            /*
             * 엔티티 직접사용 foreign key 값
             */

//            String query = "select m from Member m where m.team.id = :teamId";
//            List<Member> findMember = em.createQuery(query, Member.class)
//                    .setParameter("teamId", team1.getId())
//                    .getResultList();
//
//            for (Member m : findMember) {
//                System.out.println("Member = " + m);
//            }

            /*
             * 위 아래가 동일한 SQL 내보냄
             */
            
            String query = "select m from Member m where m.team = :team";
            List<Member> findMember = em.createQuery(query, Member.class)
                    .setParameter("team", team1)
                    .getResultList();

            for (Member m : findMember) {
                System.out.println("Member = " + m);
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

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
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            /*
             * 여러 값 조회
             */

            // 3. 단순 값을 DTO로 바로 조회 -> 제일 중요 -> new 명령어로

            List<MemberDTO> res = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = res.get(0);
            System.out.println("username = " + memberDTO.getUsername());
            System.out.println("age = " + memberDTO.getAge());

            // 2. generic
            List<Object[]> resultList1 = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();
            Object[] result1 = resultList1.get(0);
            System.out.println("username = " + result1[0]);
            System.out.println("age = " + result1[1]);
            // 1. query type으로 조회

            List resultList = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            Object o = resultList.get(0);
            Object[] result = (Object[]) o;

            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);

            /*
             * imbedded type projection
             * 한계점 : 소속 entity를 명시해주어야 한다.
             */

            List<Address> list = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            /*
             * join이 발생하는 entity 프로젝션은 join을 명시적으로 사용해야 쿼리 튜닝이 수월해진다
             */
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
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

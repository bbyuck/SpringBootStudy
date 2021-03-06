package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member")
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Embedded
    private Address address;

    @OneToMany (mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}

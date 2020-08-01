package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> catrgories = new ArrayList<>();

    /*
     * 비즈니스 로직
     */


    /*
     * stock양 증가
     */
    public void addStockQuantity(Integer quantity) {
        this.stockQuantity += quantity;
    }

    /*
     * stock양 감소
     */
    public void removeStockQuantity(Integer quantity) {
        Integer restStock = this.stockQuantity - quantity;

        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;
    }

}

package study.springboot.basictest.cafe.beverage;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "price"})
@Getter
@Entity
public class Beverage {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private int price;

    @Builder
    public Beverage(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void update(BeverageUpdateRequestDto updateRequestDto) {
        this.name = updateRequestDto.getName();
        this.price = updateRequestDto.getPrice();
    }
}

package study.springboot.basictest.cafe.beverage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BeverageResponseDto {

    private Long id;
    private String name;
    private int price;

    @Builder
    public BeverageResponseDto(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

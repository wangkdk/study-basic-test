package study.springboot.basictest.cafe.beverage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class BeverageUpdateRequestDto {

    @NotEmpty
    private String name;
    private int price;

    @Builder
    public BeverageUpdateRequestDto(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

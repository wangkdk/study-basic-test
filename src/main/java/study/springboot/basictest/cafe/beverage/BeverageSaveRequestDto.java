package study.springboot.basictest.cafe.beverage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class BeverageSaveRequestDto {

    @NotEmpty
    private String name;
    private int price;

    @Builder
    public BeverageSaveRequestDto(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

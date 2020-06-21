package study.springboot.basictest.cafe.beverage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class BeverageServiceTest {

    @Mock BeverageRepository beverageRepository;

    @DisplayName("음료 등록 성공 테스트")
    @Test
    void beverageSave() {
        // given
        BeverageService beverageService = new BeverageService(beverageRepository);

        BeverageSaveRequestDto saveRequestDto = BeverageSaveRequestDto.builder()
                .name("아메리카노")
                .price(1000)
                .build();

        Beverage beverage = Beverage.builder()
                .name("아메리카노")
                .price(1000)
                .build();

        // mocking
        given(beverageRepository.existsByName(any())).willReturn(false);
        given(beverageService.saveBeverage(saveRequestDto)).willReturn(beverage);

        // when
        Beverage savedBeverage = beverageService.saveBeverage(saveRequestDto);

        // then
        assertEquals(saveRequestDto.getName(), savedBeverage.getName());
    }

}
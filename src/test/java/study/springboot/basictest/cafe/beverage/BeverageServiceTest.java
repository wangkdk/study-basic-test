package study.springboot.basictest.cafe.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BeverageServiceTest {

    @Mock BeverageRepository beverageRepository;

    /**
     * - 외부 API 를 테스트 해야 하는 경우
     * - 아직 interface 만 존재하고 실제 구현 코드가 없는 상태에서 테스트를 진행하고 싶은 경우
     * - service logic 에만 집중하고 싶은 경우
     */
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
package study.springboot.basictest.cafe.beverage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BeverageService {

    private final BeverageRepository beverageRepository;

    @Transactional
    public Beverage saveBeverage(BeverageSaveRequestDto saveRequestDto) {

        if (beverageRepository.existsByName(saveRequestDto.getName())) {
           throw new IllegalArgumentException("이미 존재하는 음료 이름 입니다 : " + saveRequestDto.getName());
        }

        Beverage beverage = Beverage.builder()
                .name(saveRequestDto.getName())
                .price(saveRequestDto.getPrice())
                .build();

        return beverageRepository.save(beverage);
    }
}

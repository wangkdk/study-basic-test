package study.springboot.basictest.cafe.beverage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BeverageService {

    private final BeverageRepository beverageRepository;

    // TODO exception 처리가 필요 하다.
    @Transactional
    public Beverage saveBeverage(BeverageSaveRequestDto saveRequestDto) {
        if (beverageRepository.existsByName(saveRequestDto.getName())) {
           throw new IllegalArgumentException("이미 존재 하는 음료 이름 입니다 : " + saveRequestDto.getName());
        }

        Beverage beverage = Beverage.builder()
                .name(saveRequestDto.getName())
                .price(saveRequestDto.getPrice())
                .build();

        return beverageRepository.save(beverage);
    }

    @Transactional
    public Beverage updateBeverage(Long id, BeverageUpdateRequestDto updateRequestDto) {
        Beverage beverage = beverageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 음료 아이디 입니다 : " + id));
        beverage.update(updateRequestDto);
        return beverage;
    }
}

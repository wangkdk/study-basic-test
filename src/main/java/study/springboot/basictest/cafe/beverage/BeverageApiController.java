package study.springboot.basictest.cafe.beverage;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RequestMapping("/beverage")
@RestController
public class BeverageApiController {

    private final BeverageService beverageService;
    private final BeverageRepository beverageRepository;

    @GetMapping
    public ResponseEntity<List<BeverageResponseDto>> getBeverages() {
        List<Beverage> all = beverageRepository.findAll();

        List<BeverageResponseDto> beverages = all.stream()
                .map(b -> new BeverageResponseDto(b.getId(), b.getName(), b.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(beverages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeverageResponseDto> getBeverage(@PathVariable Long id) {
        Optional<Beverage> byId = beverageRepository.findById(id);
        if(byId.isEmpty()) return ResponseEntity.notFound().build();
        Beverage beverage = byId.get();

        return ResponseEntity.ok().body(
                BeverageResponseDto.builder()
                        .id(beverage.getId())
                        .name(beverage.getName())
                        .price(beverage.getPrice())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<Long> saveBeverage(@RequestBody @Valid BeverageSaveRequestDto saveRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(beverageService.saveBeverage(saveRequestDto).getId());
    }

}

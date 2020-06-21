package study.springboot.basictest.cafe.beverage;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/beverage")
@RestController
public class BeverageApiController {

    private final BeverageService beverageService;

    @PostMapping
    public ResponseEntity<Long> saveBeverage(@RequestBody @Valid BeverageSaveRequestDto saveRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(beverageService.saveBeverage(saveRequestDto).getId());
    }

}

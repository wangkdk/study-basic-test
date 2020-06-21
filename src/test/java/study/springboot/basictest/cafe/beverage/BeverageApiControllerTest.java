package study.springboot.basictest.cafe.beverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BeverageApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BeverageRepository beverageRepository;

    @BeforeEach
    void setUp() {
        Beverage beverage = Beverage.builder()
                .name("아메리카노")
                .price(2000)
                .build();
        beverageRepository.save(beverage);
    }

    @Test
    @DisplayName("음료 저장 테스트")
    void saveBeverage() throws Exception {
        // given
        BeverageSaveRequestDto beverage = BeverageSaveRequestDto.builder()
                .name("카페라뗴")
                .price(3000)
                .build();

        // when & then
        this.mockMvc.perform(post("/beverage")
                .content(objectMapper.writeValueAsString(beverage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("음료 저장 테스트 - 잘못된 inpupt")
    void saveBeverage_wrong_input() throws Exception {
        // given
        BeverageSaveRequestDto beverage = BeverageSaveRequestDto.builder()
                .price(2000)
                .build();

        // when & then
        this.mockMvc.perform(post("/beverage")
                .content(objectMapper.writeValueAsString(beverage))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
}
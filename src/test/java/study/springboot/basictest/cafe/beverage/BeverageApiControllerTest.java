package study.springboot.basictest.cafe.beverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeverageApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BeverageRepository beverageRepository;

    @Test
    @DisplayName("모든 음료 조회하기")
    void getBeverages() throws Exception {
        IntStream.range(0, 30)
                .forEach(i -> {
                    generateBeverage("음료"+i, 1000+i);
                });

        MvcResult result = mockMvc.perform(get("/beverage"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("음료0"))
                .andExpect(jsonPath("[29].price").value(1029))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(contentAsString);
        assertEquals(30, jsonArray.length());
    }

    @Test
    @DisplayName("음료 조회 테스트")
    void getBeverage() throws Exception {
        Beverage savedBeverage = generateBeverage("돌체라떼", 5000);

        mockMvc.perform(get("/beverage/{id}", savedBeverage.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(savedBeverage.getId()))
                .andExpect(jsonPath("name").value(savedBeverage.getName()))
                .andExpect(jsonPath("price").value(savedBeverage.getPrice()))
                ;
    }

    @Test
    @DisplayName("음료 조회 테스트 - 존재하지 않는 음료")
    void getBeverage_notFound() throws Exception {
        mockMvc.perform(get("/beverage/{id}", 99999))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
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

    private Beverage generateBeverage(String name, int price) {
        return beverageRepository.save(Beverage.builder()
                .name(name)
                .price(price)
                .build()
        );
    }
}
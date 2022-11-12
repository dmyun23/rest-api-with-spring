package me.whiteship.restapiwithspring.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest  // 웹과 관련된 빈들이 모두 등록됨
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;  // Mock으로 만들어져 있는 Moking이 되어 있는 dispatcherServlet 상대로,
                      // 가짜 요청을 만들어 dispatcherServlet으로 보내고 응답을 확인할 수 있는 테스트를 만들 수 있다.

    @MockBean
    EventRepository eventRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent() throws Exception {

        Event event = Event.builder()
                        .name("Spring")
                        .description("REST API Development With Spring")
                        .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23 ,14, 21))
                        .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 13, 21))
                        .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
                        .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14,21))
                        .basePrice(100)
                        .maxPrice(200)
                        .limitOfEnrollment(100)
                        .location("강남역 D2 스타텁 팩토리")
                        .build();

        event.setId(10);
        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }
}
package me.whiteship.restapiwithspring.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest  // 웹과 관련된 빈들이 모두 등록됨
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;  // Mock으로 만들어져 있는 Moking이 되어 있는 dispatcherServlet 상대로,
                      // 가짜 요청을 만들어 dispatcherServlet으로 보내고 응답을 확인할 수 있는 테스트를 만들 수 있다.

    @Test
    public void createEvent() throws Exception {
        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isCreated());
    }
}

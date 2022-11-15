package me.whiteship.restapiwithspring.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.whiteship.restapiwithspring.common.RestDocsConfiguration;
import me.whiteship.restapiwithspring.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.util.regex.Matcher;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest  // 웹과 관련된 빈들이 모두 등록됨
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;  // Mock으로 만들어져 있는 Moking이 되어 있는 dispatcherServlet 상대로,
                      // 가짜 요청을 만들어 dispatcherServlet으로 보내고 응답을 확인할 수 있는 테스트를 만들 수 있다.

//    @MockBean
//    EventRepository eventRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @TestDescription("정상적으로 이벤트를 생성하는 테스트")
    public void createEvent() throws Exception {

        EventDto eventDto = EventDto.builder()
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

//        event.setId(10);
//        Mockito .when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andDo(document("create-event",
                            links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-events").description("link to query events"),
                                linkWithRel("update-event").description("link to update an existing event")
                            ),
                           requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                           ),
                           requestFields(
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of evnent"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of evnent"),
                                fieldWithPath("beginEventDateTime").description("date time of begin of new evnent"),
                                fieldWithPath("endEventDateTime").description("date time of end of new evnent"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("base price of new event"),
                                fieldWithPath("maxPrice").description("max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrolment")
                           ),
                            responseHeaders(
                                    headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
                            ),
//                            relaxedResponseFields(  // relaxed prefix를 사용하여
                            responseFields(  // relaxed prefix를 사용하여
                                    fieldWithPath("id").description("identifier of new event"),
                                    fieldWithPath("name").description("Name of new event"),
                                    fieldWithPath("description").description("description of new event"),
                                    fieldWithPath("beginEnrollmentDateTime").description("date time of begin of evnent"),
                                    fieldWithPath("closeEnrollmentDateTime").description("date time of close of evnent"),
                                    fieldWithPath("beginEventDateTime").description("date time of begin of new evnent"),
                                    fieldWithPath("endEventDateTime").description("date time of end of new evnent"),
                                    fieldWithPath("location").description("location of new event"),
                                    fieldWithPath("basePrice").description("base price of new event"),
                                    fieldWithPath("maxPrice").description("max price of new event"),
                                    fieldWithPath("limitOfEnrollment").description("limit of enrolment"),
                                    fieldWithPath("free").description("it tells is this event is free or not"),
                                    fieldWithPath("offline").description("it tells is this event is offline meeting or not"),
                                    fieldWithPath("eventStatus").description("event status"),
                                    fieldWithPath("_links.self.href").description("link to href"),
                                    fieldWithPath("_links.query-events.href").description("link to query event list"),
                                    fieldWithPath("_links.update-event.href").description("link to update existing event")
                            )
                        ))
        ;

    }
    @Test
    @TestDescription("입력 받을 수 없는 값을 사용하는 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request() throws Exception {

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
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.DRAFT)
                .build();

//        event.setId(10);
//        Mockito .when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("입력 값이 비어 있는 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest())
       ;
    }

    @Test
    @TestDescription("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development With Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 26 ,14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
                .endEventDateTime(LocalDateTime.of(2018, 11, 23, 14,21))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        this.mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(eventDto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andExpect(jsonPath("$[0].objectName").exists())
//                      .andExpect(jsonPath("$[0].field").exists())
                        .andExpect(jsonPath("$[0].defaultMessage").exists())
                        .andExpect(jsonPath("$[0].code").exists())
//                      .andExpect(jsonPath("$[0].rejectValue").exists())
        ;
    }

}

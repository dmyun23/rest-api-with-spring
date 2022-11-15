package me.whiteship.restapiwithspring.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder().build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        // Given
        String name = "Event";
        String description = "Spring";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    private Object[] paramsForTestFree(){  // TypeSafe하게 리팩토링
        return new Object[]{
                new Object[]{0,0,true},
                new Object[]{100,0,false},
                new Object[]{0,100, false},
                new Object[]{100,100,false}
        };
    }
    @Test
    @Parameters(method = "paramsForTestFree")
//    @Parameters({
//            "0,0,true",
//            "100,0,false",
//            "0,100,false"
//    })
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        // TDD를 리팩토링 해보자
        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(isFree);

//        // Given
//        Event event = Event.builder()
//                .basePrice(0)
//                .maxPrice(0)
//                .build();
//
//        // When
//        event.update();
//
//        // Then
//        assertThat(event.isFree()).isTrue();
//
//        // Given
//        event = Event.builder()
//                .basePrice(100)
//                .maxPrice(0)
//                .build();
//
//        // When
//        event.update();
//
//        // Then
//        assertThat(event.isFree()).isFalse();
//
//
//        // Given
//        event = Event.builder()
//                .basePrice(0)
//                .maxPrice(100)
//                .build();
//
//        // When
//        event.update();
//
//        // Then
//        assertThat(event.isFree()).isFalse();
    }

    @Test
    @Parameters(method = "parametersForTestOffline")
    public void testOffline(String location, boolean offline){
        // TDD를 리팩토링 해보자
        // Given
        //Given
        Event event = Event.builder()
                .location(location)
                .build();

        // when
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(offline);
//
//        //Given
//        Event event = Event.builder()
//                .location("강남역 네이버 D2 스타럽 팩토리")
//                .build();
//
//        // when
//        event.update();
//
//        // Then
//        assertThat(event.isOffline()).isTrue();
//
//        //Given
//        event = Event.builder()
//                .build();
//
//        // when
//        event.update();
//
//        // Then
//        assertThat(event.isOffline()).isFalse();

    }

    private Object[] parametersForTestOffline(){
        return new Object[]{
                new Object[]{"강남", true},
                new Object[]{null, false},
                new Object[]{"", false},
        };
    }
}
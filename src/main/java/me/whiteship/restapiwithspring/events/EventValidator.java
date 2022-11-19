package me.whiteship.restapiwithspring.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) {
        if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0 ){
//            errors.rejectValue("basePrice", "wrongValue", "BasePrice is Wrong");
//            errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is Wrong");

            errors.reject("wrongPrices", "Values for Prices are wrong"); // global 에러
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
            endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
            endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {

                errors.rejectValue("endEventDateTime","wrongValue","eventEventDateTime is wrong");  // 필드 에러
        }

        // TODO BeginEventDateTime
        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
        if( beginEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
            beginEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {

                errors.rejectValue("beginEventDateTime", "wrongValue", "beginEventDateTime is wrong");
        }
    }
}

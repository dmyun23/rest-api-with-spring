package me.whiteship.restapiwithspring.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import me.whiteship.restapiwithspring.events.index.IndexController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorsResource extends RepresentationModel {

    @JsonUnwrapped
    private Errors content;

    public ErrorsResource(Errors content, Link... links) {
        this.content = content;
    //        List<Link> linkList =  new ArrayList();
    //        for(int i=0; i< links.length; i++){
    //            linkList.add(links[i]);
    //        }
    //        super(linkList);

        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}

package org.larserik.sam.storebrand.web;

import org.larserik.sam.common.dto.Vedtak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController

public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);


    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/varsling/person/{fnr}", method = RequestMethod.GET)
    public String varsling(@PathVariable String fnr){
        Vedtak vedtak =restTemplate.getForObject("http://localhost:8091/person/" + fnr, Vedtak.class);

        LOGGER.info("Storebrand er varlset om at "
                + vedtak.fnr  + " mottar " + vedtak.netto + " fra "
                + vedtak.fom.toString());
        return "OK";
    }
}

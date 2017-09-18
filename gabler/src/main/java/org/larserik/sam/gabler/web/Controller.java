package org.larserik.sam.gabler.web;

import org.larserik.sam.common.dto.SamMelding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController

public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @RequestMapping(value = "/varsling", method = RequestMethod.POST)
    public String nyttVedtak(@RequestBody SamMelding vedtak){
        LOGGER.info("TPordning " + vedtak.tpOrdning + " hos Gabler er varlset om at "
                + vedtak.vedtak.fnr  + " mottar " + vedtak.vedtak.netto + " fra "
                + vedtak.vedtak.fom.toString());
        return "OK";
    }
}

package org.larserik.sam.tpreg.tpordninger;


import org.larserik.sam.common.dto.TpForholdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class Forhold {

    private static final Logger LOGGER = LoggerFactory.getLogger(Forhold.class);
    private Map<String, int[]> db;

    public Forhold(){
        db = new HashMap<>();
        int[] forhold = {2050, 3010};
        db.put("123",forhold);
        forhold = new int[] {2050};
        db.put("321", forhold);
    }

    @RequestMapping("/person/{fnr}")
    public TpForholdResponse fib(@PathVariable String fnr){

        TpForholdResponse res = new TpForholdResponse();
        res.tpForhold = db.get(fnr);
        LOGGER.info("Bedt om å finne tp-forhold for " + fnr + ". Fant " + Arrays.toString(res.tpForhold));
        return res;
    }
}

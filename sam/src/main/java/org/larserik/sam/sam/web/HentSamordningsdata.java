package org.larserik.sam.sam.web;

import org.larserik.sam.common.dto.Vedtak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HentSamordningsdata {


    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/person/{fnr}", method = RequestMethod.GET)
    public Vedtak hentSamordnginsData(@PathVariable String fnr){
        return restTemplate.getForObject("http://localhost:8090/person/" + fnr, Vedtak.class);
    }
}

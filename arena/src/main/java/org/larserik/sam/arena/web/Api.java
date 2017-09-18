package org.larserik.sam.arena.web;

import org.larserik.sam.arena.producer.Sender;
import org.larserik.sam.common.dto.Vedtak;
import org.larserik.sam.common.dto.VedtakHendelse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class Api {

    @Autowired
    Sender sender;

    List<Vedtak> db = new ArrayList<>();

    @RequestMapping(value = "/person/{fnr}", method = RequestMethod.POST)
    public int nyttVedtak(@PathVariable String fnr, @RequestBody Vedtak vedtak){
        db.add(vedtak);
        vedtak.vedtakId = db.indexOf(vedtak);
        vedtak.fnr = fnr;
        vedtak.fom = LocalDate.now();

        VedtakHendelse h = new VedtakHendelse();
        h.vedtakid = vedtak.vedtakId;
        sender.send(h);

        return vedtak.vedtakId;
    }

    @RequestMapping(value = "/person/{fnr}", method = RequestMethod.GET)
    public Vedtak nyesteVedtak(@PathVariable String fnr){
        return db.stream().filter(v->v.fnr.equals(fnr)).max(Comparator.comparing(v->v.fom)).orElse(null);
    }

    @RequestMapping("/vedtak/{id}")
    public Vedtak hentVedtak(@PathVariable int id){
        return db.get(id);
    }


}

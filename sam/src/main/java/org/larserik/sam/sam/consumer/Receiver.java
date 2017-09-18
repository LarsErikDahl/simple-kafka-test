package org.larserik.sam.sam.consumer;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import org.larserik.sam.common.dto.SamMelding;
import org.larserik.sam.common.dto.TpForholdResponse;
import org.larserik.sam.common.dto.Vedtak;
import org.larserik.sam.common.dto.VedtakHendelse;
import org.larserik.sam.sam.producer.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;


public class Receiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  public CountDownLatch getLatch() {
    return latch;
  }


  @Autowired
  RestTemplate restTemplate;

  @Autowired
  Sender sender;


  @KafkaListener(topics = "sam.varsle.aap")
  public void receive(VedtakHendelse hendlse) {
    LOGGER.info("SAM bedt om å samordne vedtak='{}'", hendlse.vedtakid);

    Vedtak vedtak =restTemplate.getForObject("http://localhost:8090/vedtak/" + hendlse.vedtakid, Vedtak.class);

    Arrays.stream(restTemplate.getForObject("http://localhost:8093/person/" + vedtak.fnr, TpForholdResponse.class).tpForhold)
            .forEach(i -> sender.send(i, vedtak));

    latch.countDown();



  }
}

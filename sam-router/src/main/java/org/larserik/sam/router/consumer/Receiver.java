package org.larserik.sam.router.consumer;

import java.util.concurrent.CountDownLatch;

import org.larserik.sam.common.dto.SamMelding;
import org.larserik.sam.common.dto.Vedtak;
import org.larserik.sam.router.producer.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


public class Receiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  public CountDownLatch getLatch() {
    return latch;
  }

  @Autowired
  Sender sender;

  @Autowired
  RestTemplate restTemplate;


  @KafkaListener(topics = "sam.varsle.router.inn")
  public void receive(SamMelding melding) {
    sender.sendTilOrdning(melding);
    latch.countDown();
  }

  @KafkaListener(topics = {"sam.varsle.ordning.2050", "sam.varsle.ordning.2060"} )
  public void routeToGabler(SamMelding melding) {
    sender.sendTilLeverandor("gabler", melding);
    latch.countDown();
  }

  @KafkaListener(topics ="sam.varsle.ordning.3010" )
  public void routeToStorebrand(SamMelding melding) {
    sender.sendTilLeverandor("storebrand", melding);
    latch.countDown();
  }

  @KafkaListener(topics ="sam.varsle.leverandor.gabler" )
  public void sendToGabler(SamMelding melding) {
    LOGGER.info("Sending to gabler");
    restTemplate.postForObject("http://localhost:8094/varsling", melding, String.class);
    latch.countDown();
  }

  @KafkaListener(topics ="sam.varsle.leverandor.storebrand" )
  public void sendToStorebrand(SamMelding melding) {
    LOGGER.info("Sending to storebrand");
    restTemplate.getForObject("http://localhost:8095/varsling/person/" + melding.vedtak.fnr, String.class);

    latch.countDown();
  }


}

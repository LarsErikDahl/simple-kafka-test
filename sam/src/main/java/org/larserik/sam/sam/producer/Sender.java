package org.larserik.sam.sam.producer;

import org.larserik.sam.common.dto.SamMelding;
import org.larserik.sam.common.dto.Vedtak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;


public class Sender {

  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

  @Autowired
  private KafkaTemplate<String, SamMelding> kafkaTemplate;

  public void send(SamMelding melding) {
    kafkaTemplate.send("sam.varsle.router.inn", melding);
  }

  public void send(int tpnummer, Vedtak vedtak) {
    SamMelding melding = new SamMelding();
    melding.tpOrdning =tpnummer;
    melding.vedtak = vedtak;
    send(melding);
    LOGGER.info("SAM skal varlse tpnummer " + tpnummer);
  }
}

package org.larserik.sam.router.producer;

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

  public void sendTilOrdning(SamMelding melding) {
    kafkaTemplate.send("sam.varsle.ordning." + melding.tpOrdning, melding);
  }

  public void sendTilLeverandor(String leverandor, SamMelding melding) {
    kafkaTemplate.send("sam.varsle.leverandor." + leverandor, melding);
  }

}

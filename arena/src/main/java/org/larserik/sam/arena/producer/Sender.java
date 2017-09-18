package org.larserik.sam.arena.producer;

import org.larserik.sam.common.dto.VedtakHendelse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;


public class Sender {

  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

  @Autowired
  private KafkaTemplate<String, VedtakHendelse> kafkaTemplate;

  public void send(VedtakHendelse hendelse) {
    LOGGER.info("varlser vedtak='{}'", hendelse.vedtakid);
    kafkaTemplate.send("sam.varsle.aap", hendelse);
  }
}

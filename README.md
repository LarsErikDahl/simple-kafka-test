# simple-kafka-test

Forsøk på en enkel prototype for å lære litt om kafka. Forutsetter en lokal kafka på port 9092.
Meningen er at det skal postes meldinger til arenatjenesten, for eksempel `curl -H "Content-Type: application/json" -X POST -d '{"netto":2345}' http://localhost:8090/person/123`

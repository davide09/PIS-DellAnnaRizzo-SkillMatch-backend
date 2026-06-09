
package com.skillmatch.contract_service.model;
public enum ContractStatus {
    PENDING,     // proposta inviata al professionista, in attesa di risposta
    ACCEPTED,    // proposta accettata
    REJECTED,    // proposta rifiutata
    ACTIVE,      // contratto effettivo in corso
    COMPLETED,    // contratto concluso
    PAID // pagamento (creazione fattura)
}
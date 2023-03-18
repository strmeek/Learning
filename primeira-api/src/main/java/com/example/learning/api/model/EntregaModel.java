package com.example.learning.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.example.learning.domain.model.StatusEntrega;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaModel {
	/*Output altamente customizável e flexivel
	 * não interfere na minha classe principal Entrega
	 * e impede de acontecer bugs indesejados
	 */

	private Long id;
	private ClienteResumoModel cliente;
	private DestinatarioModel destinatario;
	private BigDecimal taxa;
	private StatusEntrega status;
	private OffsetDateTime dataPedido;
	private OffsetDateTime dataFinalizacao;
	
}

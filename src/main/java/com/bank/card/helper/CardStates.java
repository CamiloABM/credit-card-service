package com.bank.card.helper;

public enum CardStates {

	INACTIVE("Inactiva"), ACTIVE("Activa"), BLOCKED("Bloqueada");

	private final String descripcion;

	CardStates(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

}

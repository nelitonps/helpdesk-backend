package com.npsouza.helpdesk.domain.enums;

public enum Status {
	
	ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");
	
	private Integer codigo;
	private String descricao;
	
	private Status(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	//statico não precisa de uma instancia de perfil para ser chamado em outro código
	public static Status toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Status p : Status.values()) {
			if(cod.equals(p.getCodigo())) {
				return p;
			}
		}
		
		throw new IllegalArgumentException("Status inválido");
	}

}

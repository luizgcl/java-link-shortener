package br.com.luizgcl.linkshortener.exceptions;

import lombok.Getter;

@Getter
public class LinkAlreadyExistsException extends RuntimeException {

    private final String code;

    public LinkAlreadyExistsException() {
        super("O link solicitado já existe");
        this.code = "LINK_ALREADY_EXISTS";
    }
}

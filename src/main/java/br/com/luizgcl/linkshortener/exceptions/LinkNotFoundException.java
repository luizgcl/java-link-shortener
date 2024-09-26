package br.com.luizgcl.linkshortener.exceptions;

import lombok.Getter;

@Getter
public class LinkNotFoundException extends RuntimeException {

    String code;

    public LinkNotFoundException() {
        super("O link solicitado não foi encontrado.");
        this.code = "LINK_NOT_FOUND";
    }
}

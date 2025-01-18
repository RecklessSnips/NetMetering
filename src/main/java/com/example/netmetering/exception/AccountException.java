package com.example.netmetering.exception;

public class AccountException extends RuntimeException{

    static final long serialVersionUID = -1736651799396492657L;

    public AccountException(){

    }

    public AccountException(String msg){
        super(msg);
    }

}

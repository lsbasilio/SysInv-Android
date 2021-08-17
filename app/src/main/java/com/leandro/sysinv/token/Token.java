package com.leandro.sysinv.token;

import java.util.Random;

public class Token {

    public Token() {
    }

    public String geraToken() {

        Random randomico = new Random();
        int numeroRandomico = randomico.nextInt( 9999 - 1000 ) + 1000;
        return String.valueOf( numeroRandomico );

    }

}

package com.leandro.sysinv.importacao;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class Arquivos {

    public static final String nomePasta = "Inventario";
    public static String pathInventario;
    //public static final String pathInventario = Environment.getExternalStorageDirectory() +"/" + nomePasta;

    public Arquivos() {
    }

    public static Boolean pathInventarioExiste(String empresa) throws IOException {

        File diretorio = new File(pathInventario + "/" + empresa);
        return diretorio.isDirectory();

    }

    public static Boolean fileInventarioExiste(String empresa, String nomeArquivo) throws IOException {

        File diretorio = new File(pathInventario + "/" + empresa + "/" + nomeArquivo);
        return diretorio.isFile();
    }

}

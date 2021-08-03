package com.leandro.sysinv.model.entities;

import com.leandro.sysinv.model.entities.enums.BensStatus;

import java.util.Date;

public class Bens {

    private Integer numero_bem;
    private CentroDeCusto ccusto_atual;
    private BensStatus status;
    private Date data_inv;
    private Integer conta;
    private  Date data;
    private String observacao;
    private Local local_atual;
    private String usuario;
    private String descricao;
    private String marca;
    private String modelo;
    private String numero_serie;
    private String situacao;
    private Integer numero_bemant;
    private CentroDeCusto ccusto_ant;
    private Local local_ant;
    private String descricao_ant;
    private String marca_ant;
    private String modelo_ant;
    private String numero_serieant;
    private String situacao_ant;

    public Bens() {
        this.numero_bem = 0;
        this.ccusto_atual = null;
        this.status = BensStatus.NAO_ENCONTRADO;
        //this.data_inv = data_inv;
        this.conta = 0;
        //this.data = data;
        this.observacao = "";
        this.local_atual = null;
        this.usuario = "";
        this.descricao = "";
        this.marca = "";
        this.modelo = "";
        this.numero_serie = "";
        this.situacao = "";
    }

    public Bens(Integer numero_bem, CentroDeCusto ccusto_atual, BensStatus status, Date data_inv, Integer conta, Date data, String observacao, Local local_atual, String usuario, String descricao, String marca, String modelo, String numero_serie, String situacao, Integer numero_bemant, CentroDeCusto ccusto_ant, Local local_ant, String descricao_ant, String marca_ant, String modelo_ant, String numero_serieant, String situacao_ant) {
        this.numero_bem = numero_bem;
        this.ccusto_atual = ccusto_atual;
        this.status = status;
        this.data_inv = data_inv;
        this.conta = conta;
        this.data = data;
        this.observacao = observacao;
        this.local_atual = local_atual;
        this.usuario = usuario;
        this.descricao = descricao;
        this.marca = marca;
        this.modelo = modelo;
        this.numero_serie = numero_serie;
        this.situacao = situacao;
        this.numero_bemant = numero_bemant;
        this.ccusto_ant = ccusto_ant;
        this.local_ant = local_ant;
        this.descricao_ant = descricao_ant;
        this.marca_ant = marca_ant;
        this.modelo_ant = modelo_ant;
        this.numero_serieant = numero_serieant;
        this.situacao_ant = situacao_ant;
    }

    public Integer getNumero_bem() {
        return numero_bem;
    }

    public void setNumero_bem(Integer numero_bem) {
        this.numero_bem = numero_bem;
    }

    public CentroDeCusto getCcusto_atual() {
        return ccusto_atual;
    }

    public void setCcusto_atual(CentroDeCusto ccusto_atual) {
        this.ccusto_atual = ccusto_atual;
    }

    public BensStatus getStatus() {
        return status;
    }

    public void setStatus(BensStatus status) {
        this.status = status;
    }

    public Date getData_inv() {
        return data_inv;
    }

    public void setData_inv(Date data_inv) {
        this.data_inv = data_inv;
    }

    public Integer getConta() {
        return conta;
    }

    public void setConta(Integer conta) {
        this.conta = conta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Local getLocal_atual() {
        return local_atual;
    }

    public void setLocal_atual(Local local_atual) {
        this.local_atual = local_atual;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(String numero_serie) {
        this.numero_serie = numero_serie;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getNumero_bemant() {
        return numero_bemant;
    }

    public void setNumero_bemant(Integer numero_bemant) {
        this.numero_bemant = numero_bemant;
    }

    public CentroDeCusto getCcusto_ant() {
        return ccusto_ant;
    }

    public void setCcusto_ant(CentroDeCusto ccusto_ant) {
        this.ccusto_ant = ccusto_ant;
    }

    public Local getLocal_ant() {
        return local_ant;
    }

    public void setLocal_ant(Local local_ant) {
        this.local_ant = local_ant;
    }

    public String getDescricao_ant() {
        return descricao_ant;
    }

    public void setDescricao_ant(String descricao_ant) {
        this.descricao_ant = descricao_ant;
    }

    public String getMarca_ant() {
        return marca_ant;
    }

    public void setMarca_ant(String marca_ant) {
        this.marca_ant = marca_ant;
    }

    public String getModelo_ant() {
        return modelo_ant;
    }

    public void setModelo_ant(String modelo_ant) {
        this.modelo_ant = modelo_ant;
    }

    public String getNumero_serieant() {
        return numero_serieant;
    }

    public void setNumero_serieant(String numero_serieant) {
        this.numero_serieant = numero_serieant;
    }

    public String getSituacao_ant() {
        return situacao_ant;
    }

    public void setSituacao_ant(String situacao_ant) {
        this.situacao_ant = situacao_ant;
    }

    public int getStatusNumerico() {
        if (this.status == null)
            return 0;
        else
            return this.status.ordinal();
    }

    public BensStatus getStatusCodigo(int Codigo) {

       BensStatus statusTemp = null;

        switch (Codigo) {
            case 0:
                statusTemp = BensStatus.NAO_ENCONTRADO;
                break;
            case 1:
                statusTemp = BensStatus.PENDENTE;
                break;
            case 2:
                statusTemp = BensStatus.INVENTARIADO;
                break;
            case 3:
                statusTemp = BensStatus.NUMERO_TROCADO;
                break;
            case 4:
                statusTemp = BensStatus.NOVO;
                break;
        }

        return statusTemp;

    }

    @Override
    public String toString() {
        return "Bens{" +
                "numero_bem=" + numero_bem +
                ", ccusto_atual=" + ccusto_atual +
                ", status=" + status +
                ", data_inv=" + data_inv +
                ", conta=" + conta +
                ", data=" + data +
                ", observacao='" + observacao + '\'' +
                ", local_atual=" + local_atual +
                ", usuario='" + usuario + '\'' +
                ", descricao='" + descricao + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", numero_serie='" + numero_serie + '\'' +
                ", situacao='" + situacao + '\'' +
                ", numero_bemant=" + numero_bemant +
                ", ccusto_ant=" + ccusto_ant +
                ", local_ant=" + local_ant +
                ", descricao_ant='" + descricao_ant + '\'' +
                ", marca_ant='" + marca_ant + '\'' +
                ", modelo_ant='" + modelo_ant + '\'' +
                ", numero_serieant='" + numero_serieant + '\'' +
                ", situacao_ant='" + situacao_ant + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bens that = (Bens) o;
        return numero_bem == that.numero_bem;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numero_bem == null) ? 0 : numero_bem.hashCode());
        return result;
    }
}

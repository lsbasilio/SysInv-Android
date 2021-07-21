package com.leandro.sysinv.model.entities;

import com.leandro.sysinv.model.entities.enums.CcustoStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class CentroDeCusto implements Serializable {

    private Integer ccusto_id;
    private String descricao;
    private CcustoStatus status;
    private Date data_inicio;
    private Date data_fim;
    private Integer pendentes;
    private Integer inventariados;
    private Integer novos;

    public CentroDeCusto() {

    }

    public CentroDeCusto(int ccusto_id, String descricao, CcustoStatus status, Date data_inicio, Date data_fim, int pendentes, int inventariados, int novos) {
        this.ccusto_id = ccusto_id;
        this.descricao = descricao;
        this.status = status;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.pendentes = pendentes;
        this.inventariados = inventariados;
        this.novos = novos;
    }

    public int getCcusto_id() {
        return ccusto_id;
    }

    public void setCcusto_id(int ccusto_id) {
        this.ccusto_id = ccusto_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CcustoStatus getStatus() {
        return status;
    }

    public void setStatus(CcustoStatus status) {
        this.status = status;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Date getData_fim() {
        return data_fim;
    }

    public void setData_fim(Date data_fim) {
        this.data_fim = data_fim;
    }

    public int getPendentes() {
        return pendentes;
    }

    public void setPendentes(int pendentes) {
        this.pendentes = pendentes;
    }

    public int getInventariados() {
        return inventariados;
    }

    public void setInventariados(int inventariados) {
        this.inventariados = inventariados;
    }

    public int getNovos() {
        return novos;
    }

    public void setNovos(int novos) {
        this.novos = novos;
    }


    public int getStatusNumerico() {
        return status.ordinal() + 1;
    }

    public CcustoStatus getStatusCodigo(int Codigo) {

        CcustoStatus statusTemp = null;

        switch (Codigo) {
            case 1:
                statusTemp = CcustoStatus.NAO_INICIALIZADO;
                break;
            case 2:
                statusTemp = CcustoStatus.EM_ANDAMENTO;
                break;
            case 3:
                statusTemp = CcustoStatus.FINALIZADO;
                break;
            case 4:
                statusTemp = CcustoStatus.ATIVO;
                break;
        }

        return statusTemp;

    };

    @Override
    public String toString() {
        return "CentroDeCusto{" +
                "ccusto_id=" + ccusto_id +
                ", descricao='" + descricao + '\'' +
                ", status=" + status +
                ", data_inicio=" + data_inicio +
                ", data_fim=" + data_fim +
                ", pendentes=" + pendentes +
                ", inventariados=" + inventariados +
                ", novos=" + novos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CentroDeCusto that = (CentroDeCusto) o;
        return ccusto_id == that.ccusto_id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ccusto_id == null) ? 0 : ccusto_id.hashCode());
        return result;
//        return Objects.hash(ccusto_id);
    }
}

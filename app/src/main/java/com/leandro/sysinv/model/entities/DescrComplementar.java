package com.leandro.sysinv.model.entities;

public class DescrComplementar {

    private String descricao_id;
    private String descricao;

    public DescrComplementar() {

    }

    public DescrComplementar(String descricao_id, String descricao) {
        this.descricao_id = descricao_id;
        this.descricao = descricao;
    }

    public String getDescricao_id() {
        return descricao_id;
    }

    public void setDescricao_id(String descricao_id) {
        this.descricao_id = descricao_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "DescrComplementar{" +
                "descricao_id='" + descricao_id + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescrComplementar that = (DescrComplementar) o;
        return descricao_id == that.descricao_id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descricao_id == null) ? 0 : descricao_id.hashCode());
        return result;
    }

}

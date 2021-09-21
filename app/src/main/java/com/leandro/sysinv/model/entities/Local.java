package com.leandro.sysinv.model.entities;

public class Local {

    private Integer local_id;
    private String descricao;
    private Integer totalBens;

    public Local() {

    }

    public Local(Integer local_id, String descricao) {
        this.local_id = local_id;
        this.descricao = descricao;
    }

    public Integer getLocal_id() {
        return local_id;
    }

    public void setLocal_id(Integer local_id) {
        this.local_id = local_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTotalBens() {
        return totalBens;
    }

    public void setTotalBens(Integer totalBens) {
        this.totalBens = totalBens;
    }

    @Override
    public String toString() {
        return "Local{" +
                "local_id=" + local_id +
                ", descricao='" + descricao + '\'' +
                ", totalBens=" + totalBens +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local that = (Local) o;
        return local_id == that.local_id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((local_id == null) ? 0 : local_id.hashCode());
        return result;
    }

}

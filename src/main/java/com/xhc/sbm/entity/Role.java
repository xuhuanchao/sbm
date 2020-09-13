package com.xhc.sbm.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private Integer id;

    private String rolename;

    private String desc;

    private Byte deleted;

    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", rolename=").append(rolename);
        sb.append(", desc=").append(desc);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
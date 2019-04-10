package com.wanxian.jpa.entity.listener;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

public class UserListener {
    /**
     * 执行之前
     * @param source
     */
    @PrePersist
    public void prePersist(Object source) {
        System.out.println("prePersist" + source);
    }

    /**
     * 执行之后
     * @param source
     */
    @PostPersist
    public void postPersist(Object source) {
        System.out.println("postPersist" + source);
    }
}

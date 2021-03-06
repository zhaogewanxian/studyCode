package com.wanxian.jpa.entity;

import com.wanxian.jpa.entity.listener.UserListener;

import javax.persistence.*;

/**
 * 用户实体
 */
@Entity
@Table(name = "t_user")
@EntityListeners(value = {UserListener.class})
public class User {
    @Id //id
    @GeneratedValue //默认为自动增长
    private Long id;
    private String name;
    @OneToOne(mappedBy = "user")
    private IdCard idCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }
}

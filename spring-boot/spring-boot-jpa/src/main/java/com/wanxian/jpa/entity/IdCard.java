package com.wanxian.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_idCard")
public class IdCard {
    @Id //id
    @GeneratedValue //默认为自动增长
    private long id;
    private String number;

    @OneToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

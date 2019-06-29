package com.ral.manages.entity.account;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Component
@Scope("prototype")
public class AccountEntity {

    private String id;
    @NotNull(message = "用户账号为空")
    private String account;//账号
    @NotNull(message = "密码为空")
    @Length(min = 6,max = 9,message = "密码长度为6-9位")
    @Pattern(regexp = "[a-zA-Z]*", message = "密码不合法")
    private String pass;//密码
    private int source;//来源
    private int tellphone;//电话
    private String lrrq;//创建日期
    private int cancellation;//注销标志

    public String getId() {
        return id;
    }
    public String getAccount() {
        return account;
    }
    public String getPass() {
        return pass;
    }
    public int getSource() {
        return source;
    }
    public int getTellphone() {
        return tellphone;
    }
    public String getLrrq() {
        return lrrq;
    }
    public int getCancellation() {
        return cancellation;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setSource(int source) {
        this.source = source;
    }
    public void setTellphone(int tellphone) {
        this.tellphone = tellphone;
    }
    public void setLrrq(String lrrq) {
        this.lrrq = lrrq;
    }
    public void setCancellation(int cancellation) {
        this.cancellation = cancellation;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", pass='" + pass + '\'' +
                ", source=" + source +
                ", tellphone=" + tellphone +
                ", lrrq='" + lrrq + '\'' +
                ", cancellation=" + cancellation +
                '}';
    }
}

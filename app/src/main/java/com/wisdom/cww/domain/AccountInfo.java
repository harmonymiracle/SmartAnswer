package com.wisdom.cww.domain;

import java.io.Serializable;

/**
 * Created by Mr.Wang on 2017/12/9.
 */

public class AccountInfo implements Serializable{
    public String getAccountId() {
        return AccountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    String AccountId;
    String accountName;
    String accountPwd;

}

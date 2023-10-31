package com.example.springboot.pojo.manager;

/**
 * 功能：返回登录信息
 * 作者：单汝轩
 * 日期：2023/10/20 22:20
 */
public class LoginResponse {
    private String role;
    private String managerName;
    private String message;

    private int storeId;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
// getters and setters

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.komsoft.shopspringmvc.model;

import com.komsoft.shopspringmvc.util.Encoding;

public class UserModel {
    protected final String login;
    protected String encryptedPassword;
    protected final String fullName;
    protected final String region;
    protected final String gender;
    protected final String comment;

    public UserModel(String login, String password, String fullName, String region, String gender, String comment) {
        this.login = login;
        this.encryptedPassword = UserModel.encryptPassword(password);
        this.fullName = fullName;
        this.region = region;
        this.gender = gender;
        this.comment = comment;
    }

    public UserModel(String login, String fullName, String region, String gender, String comment) {
        this.login = login;
        this.encryptedPassword = null;
        this.fullName = fullName;
        this.region = region;
        this.gender = gender;
        this.comment = comment;
    }

    public String getLogin() {
        return login;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRegion() {
        return region;
    }

    public String getGender() {
        return gender;
    }

    public String getComment() {
        return comment;
    }

    public static String encryptPassword(String password) {
//        return Encoding.md5EncryptionWithSalt(password);
        return Encoding.bCryptEncryption(password);
    }

}

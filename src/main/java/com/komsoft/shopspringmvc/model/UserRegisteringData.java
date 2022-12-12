package com.komsoft.shopspringmvc.model;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserRegisteringData extends UserModel {

    private String savedPassword;

    public UserRegisteringData(String login, String password, String fullName, String region, String gender, String comment) {
        super(login, password, fullName, region, gender, comment);
    }

    public UserRegisteringData(String login, String fullName, String region, String gender, String comment) {
        super(login, fullName, region, gender, comment);
    }
//    Це збочення, але поки так. Щоб не видавати дійсний пароль та була можливість порівняти
//    два хешованих пароля, сюди зберігаємо пароль з бази. Таким чином дійсний пароль назовні
//    не вийде, і не можна буде записати відкритий пароль в базу, бо видаватись буде encryptedPassword
//    Тобто якщо ми прочитали користувача з бази, то отримати його зашифрований пароль можна лише
//    після виклику метода isPasswordCorrect(String candidatePassword), якщо candidatePassword буде вірним
//    Після успішного виклику метода збережений пароль з'явиться в encryptedPassword,
//    інакше getEncryptedPassword() буде повертати null


    public void setSavedPassword(String savedPassword) {
        this.savedPassword = savedPassword;
    }

//  Compare savedPassword with encryptedPassword. Call only after isPasswordCorrect(String candidatePassword)
//    Can use to check if user has  all fields correct
    public boolean isPasswordCorrect() {
        return (this.getEncryptedPassword() != null && this.getEncryptedPassword().equals(savedPassword));
    }

//  Compare savedPassword with candidate using encryption rules
    public boolean isPasswordCorrect(String candidatePassword) {
        boolean isEquals = false;
//      isEquals = encryptPassword(candidatePassword).equals(savedPassword);    //      for MD5 encryption
        try {
            isEquals = BCrypt.checkpw(candidatePassword, this.savedPassword);       //      for BCrypt
            if (isEquals) {
                super.encryptedPassword = savedPassword;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return isEquals;
    }

}

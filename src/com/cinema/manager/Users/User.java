package com.cinema.manager.Users;

import com.cinema.manager.Users.Authentication.LoginAuthentication;
import com.cinema.manager.Users.UserController.UserController;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {

    Connection conectar;
    public User(Connection conectar){
        this.conectar = conectar;
    }

    private String idUser;
    private int accessLevel;
    private String user;
    private String password;

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser){
        this.idUser = idUser;
    }

    public int getAccessLevel(){
        return accessLevel;
    }
    public void setAccessLevel(int accessLevel){
        this.accessLevel = accessLevel;
    }

    public String getUser(){
        return user;
    }
    public void setUser(String user){
        this.user = user;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }


    public void accessAccount() throws SQLException {
        String nomeUser = JOptionPane.showInputDialog(null, "Digite seu nome de usúario: ");
        String passwordUser = JOptionPane.showInputDialog(null, "Digite sua senha: ");

        LoginAuthentication authentication = new LoginAuthentication(conectar);
        Boolean res = authentication.LoginAuthentication(conectar, nomeUser, passwordUser);

        if(res){
            UserController controller = new UserController(conectar);
            controller.optionsForLoggedInUser(nomeUser, passwordUser, authentication.levelUserGet(nomeUser));
        }else{
            JOptionPane.showMessageDialog(null, "USUARIO OU SENHA INCORRETOS!");
        }

    }

    public void deleteAccount(String user, String password){
        String userReplace = "'"+user+"'";
        String passwordReplace = "'"+password+"'";
        String sqlDelete = "DELETE FROM user WHERE user = ? AND password = ?";

        try{
            PreparedStatement p = conectar.prepareStatement(sqlDelete);
            p.setString(1, userReplace);
            p.setString(2, passwordReplace);
            p.executeUpdate();
            JOptionPane.showMessageDialog(null,"USUARIO DELETADO COM SUCESSO");
        }catch (SQLException e){
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

}

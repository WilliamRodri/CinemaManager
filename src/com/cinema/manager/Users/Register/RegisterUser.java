package com.cinema.manager.Users.Register;

import com.cinema.manager.Users.Authentication.LoginAuthentication;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class RegisterUser {
    Connection conectar;
    public RegisterUser(Connection conectar){
        this.conectar = conectar;
    }

    private boolean registerStatus = false;

    public String generatorUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        return uuidAsString;
    }

    public Boolean registerUser(Connection conectar, String user, String password, int accessLevel){
        LoginAuthentication authentication = new LoginAuthentication(conectar);
        Boolean res = authentication.registerAuthentication(conectar, user);

        if(res){
            JOptionPane.showMessageDialog(null, "USUARIO JÁ EXISTE!");
        }else{
            String sqlInsert = "INSERT INTO users(idUsers, acessLevel, user, password) VALUES(?,?,?,?)";
            try{
                PreparedStatement p = conectar.prepareStatement(sqlInsert);
                p.setString(1, generatorUUID());
                p.setInt(2, accessLevel);
                p.setString(3, user);
                p.setString(4, password);
                p.executeUpdate();
                registerStatus = true;
            }catch (SQLException e){
                System.out.printf("ERROR: %s", e.getMessage());
                registerStatus = false;
            }
        }
        return registerStatus;
    }

}

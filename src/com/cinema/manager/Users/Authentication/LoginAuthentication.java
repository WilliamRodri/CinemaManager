package com.cinema.manager.Users.Authentication;

import com.cinema.manager.movies.Movies;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class LoginAuthentication {

    Connection conectar;
    public LoginAuthentication(Connection conectar){
        this.conectar = conectar;
    }

    private boolean authentication = false;
    private boolean authenticationRegister = false;
    private int levelUserGet = 0;

    public Boolean LoginAuthentication(Connection conectar, String user, String password){

        String userReplace = "'"+user+"'";
        String passwordReplace = "'"+password+"'";

        String sqlQuery = "SELECT * FROM users WHERE user = "+userReplace+" AND password = "+passwordReplace;
        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stm.executeQuery(sqlQuery);
        ){
            if(rs.next()){
                authentication = true;

            }else{
                authentication = false;
            }
        }catch (SQLException e){
            System.out.printf("ERROR: %s", e.getMessage());
        }
        return authentication;
    }

    public int levelUserGet(String user){
        List<LoginAuthentication> levelGet = new LinkedList<>();
        String userReplace = "'"+user+"'";

        String sqlQuery = "SELECT acessLevel FROM users WHERE user = "+userReplace;
        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(sqlQuery);
        ){
            if(rs.next()){
                int level = rs.getInt(1);
                levelUserGet = level;
            }else{
                levelUserGet = 0;
            }
        }catch (SQLException e){
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return levelUserGet;
    }

    public Boolean registerAuthentication(Connection conectar, String user){

        String userReplace = "'"+user+"'";

        String sqlQuery = "SELECT * FROM users WHERE user = "+userReplace;
        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(sqlQuery);
        ){
            if(rs.next()){
                authenticationRegister = true;
                levelUserGet(user);
            }else{
                authenticationRegister = false;
            }
        }catch (SQLException e){
            System.out.printf("ERROR: %s", e.getMessage());
        }
        return authenticationRegister;
    }


}

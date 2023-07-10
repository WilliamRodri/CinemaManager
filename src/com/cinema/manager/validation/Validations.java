package com.cinema.manager.validation;

import javax.swing.*;
import java.sql.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

public class Validations {

    private final Connection conectar;

    private boolean validationStatus = false;
    private boolean checkSessionStatus = false;
    private boolean checkRoomExistStatus = false;
    private boolean checkRoomLinkedMovieAndSessionStatus = false;
    private boolean checkIfItExistsStatus = false;
    private boolean checkIfRoomIsAvailableStatus = false;
    private boolean checkIfSessionTimeIsCompatibleStatus = false;
    private boolean checkIfSessionCanBeDeletedStatus = false;

    public Validations(Connection conectar) {
        this.conectar = conectar;
    }

    public Boolean validacaoAdd (Connection conectar, String movie){
        String a = "SELECT * FROM movie";
        String b = " WHERE titleMovie = ";

        String SQL = a+b+movie;

        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stm.executeQuery(SQL)){
            validationStatus = rs.next();
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }
        return validationStatus;
    }

    public Boolean checkSession (Connection conectar, int idDeOperacao){
        String SQL = "SELECT * FROM sessions WHERE idDeOperacaoMovie = "+idDeOperacao;

        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(SQL)){
            checkSessionStatus = rs.next();
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }
        return checkSessionStatus;
    }

    public Boolean checkRoomExist (Connection conectar, String nomeRoom){
        String nameRoomReplace =  "'" +nomeRoom+ "'";
        String sqlVERIFY = "SELECT nomeRoom FROM rooms WHERE roomName = "+nameRoomReplace;
        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stm.executeQuery(sqlVERIFY);
        ){
            if(rs.next()){
                checkRoomExistStatus = true;
            }else{
                checkRoomExistStatus = false;
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }
        return checkRoomExistStatus;
    }

    public Boolean checkRoomLinkedMovieAndSession(Connection conectar, int idDeOperacao){
        String SQLverify = "SELECT linkedRoom, idDeOperacaoRoom FROM sessions WHERE idDeOperacaoRoom = "+idDeOperacao;

        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stm.executeQuery(SQLverify);
        ){
            if(rs.next()){
                checkRoomLinkedMovieAndSessionStatus = true;
            }else{
                checkRoomLinkedMovieAndSessionStatus = false;
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }

        return checkRoomLinkedMovieAndSessionStatus;
    }

    public Boolean checkIfItExists(Connection conectar, String table, int idDeOperacao){
        String SQLverify = "SELECT * FROM "+table+" WHERE idDeOperacao = "+idDeOperacao;

        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(SQLverify);
        ){
            if(rs.next()){
                checkIfItExistsStatus = true;
            }else{
                checkIfItExistsStatus = false;
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }
        return checkIfItExistsStatus;
    }

    public Boolean checkIfRoomIsAvailable(Connection conectar,String sala, String date, String startTime){

        String salaReplace = "'" + sala+ "'";
        String dateReplace = "'" +date+ "'";
        String startTimeReplace = "'" +startTime+ "'";
        String sqlQUERY = "SELECT * FROM sessions WHERE linkedRoom = "+salaReplace+" AND dateSession = "+dateReplace+" AND startTime = "+startTimeReplace;

        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stm.executeQuery(sqlQUERY)
        )
        {
            if(rs.next()){
                checkIfRoomIsAvailableStatus = true;
            }else{
                checkIfRoomIsAvailableStatus = false;
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }

        return checkIfRoomIsAvailableStatus;
    }

    public Boolean checkIfSessionTimeIsCompatible(Connection conectar, String salaReservar, String startTime){
        String salaReplace = "'"+salaReservar+"'";
        String sqlVerify = "SELECT endTime FROM sessions WHERE linkedRoom = "+salaReplace;

        try{
            Validations validations = new Validations(conectar);
            PreparedStatement p = conectar.prepareStatement(sqlVerify);
            ResultSet a = p.executeQuery();

            //Converter o startTime em int
            String dadoStartTime = startTime.replace("-", "");
            int newStartTimeInt = Integer.parseInt(dadoStartTime);

            a.next();
            String foundType = a.getString(1);
            String s = foundType.replace("-", "");
            int endTime = Integer.parseInt(s);

            if(newStartTimeInt >= endTime + 20000){
                checkIfSessionTimeIsCompatibleStatus = true;
            }else{
                checkIfSessionTimeIsCompatibleStatus = false;
            }

        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }

        return checkIfSessionTimeIsCompatibleStatus;
    }

    public Boolean checkIfSessionCanBeDeleted(Connection conectar, int idDeOperacao){
        String sqlQuery = "SELECT dateSession FROM sessions WHERE idDeOperacao = "+idDeOperacao;
        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(sqlQuery);
        ){
            if(rs.next()){
                String data = rs.getString(1);
                LocalDate dataFixa = LocalDate.parse(data);

                LocalDate dataAtual = LocalDate.now();
                LocalDate dataFixaMenos10Dias = dataFixa.minusDays(10);

                boolean resultado = dataFixaMenos10Dias.isAfter(dataAtual);

                if(resultado){
                    checkIfSessionCanBeDeletedStatus = true;
                }else{
                    checkIfSessionCanBeDeletedStatus = false;
                }

            }else{
                JOptionPane.showMessageDialog(null, "SESSÃO AINDA SEM HORÁRIO!");
                checkIfSessionCanBeDeletedStatus = false;
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }
        return checkIfSessionCanBeDeletedStatus;
    }

}

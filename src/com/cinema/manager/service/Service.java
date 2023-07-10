package com.cinema.manager.service;

import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Service {
    private final Connection conectar;

    private int idRoomGetService;
    private String duration;

    public Service(Connection conectar) {
        this.conectar = conectar;
    }

    public int getIdDeOperacao(String table, String name, String linkedRoom){
        String linkedRoomReplace = "'"+linkedRoom+"'";
        String sqlQuery = "SELECT idDeOperacao FROM "+table+" WHERE "+name+" = "+linkedRoomReplace;
        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(sqlQuery);
        ){
            if(rs.next()){
                int id = rs.getInt(1);
                idRoomGetService = id;
            }else{
                idRoomGetService = 0;
            }
        }catch (SQLException e) {
            System.err.printf("ERROR: %s", e.getMessage());
        }
        return idRoomGetService;
    }

    public String getEndTimeService(String startTime, String durationMovie){
        String startTimeReplace = startTime.replaceAll("-","");
        int startTimeValue = Integer.parseInt(startTimeReplace);

        String duratioMovieReplace = durationMovie.replaceAll("h","");
        String formatted = String.format(duratioMovieReplace+"00");

        int durationMovieValue = Integer.parseInt(formatted);

        int calculo = startTimeValue + durationMovieValue;
        String convertToStr = Integer.toString(calculo);
        String formartEndTime = convertToStr.substring(0,2)+"-"+convertToStr.substring(2,4)+"-"+convertToStr.substring(4,6);

        return formartEndTime;
    }

    public String getdurationMovieService(String linkedMovie){
        String linkedMovieReplac = "'"+linkedMovie+"'";
        String sqlSELECT = "SELECT durationMovie FROM movies WHERE titleMovie = "+linkedMovieReplac;
        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(sqlSELECT);
        )
        {
            if(rs.next()){
                String duration = rs.getString(1);
                return duration;
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }
        return duration = "NÃO ENCONTRADA";
    }
}

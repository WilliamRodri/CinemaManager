package com.cinema.manager.sessions;

import com.cinema.manager.DAO.Conexao;
import com.cinema.manager.service.Service;
import com.cinema.manager.validation.Validations;
import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sessions {
    Connection conectar;

    public Sessions(Connection conectar){
        this.conectar = conectar;
    }

    private String idSession;
    private int idDeOperacao;
    private String date;
    private int startTime;
    private int endTime;

    private String startTimeStr;
    private String endTimeStr;

    private int ticketValue;
    private String typeOfAnimation;
    private String typeOfAudio;
    private String linkedRoom;
    private int idDeOperacaoRoom;
    private String linkedMovie;
    private int idDeOperacaoMovie;

    public String generatorUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        return uuidAsString;
    }

    public int generatorId(){
        Random r = new Random();
        int number = r.nextInt(4000);
        return number;
    }

    public void visualizarInfo(){
        System.out.println("                                   ");
        System.out.println("ID DA SESSÃO: " + idSession);
        System.out.println("ID DE OPERAÇÃO: " + idDeOperacao);
        System.out.println("DATA DA SESSÃO: " + date);
        System.out.println("SESSÃO COMEÇA: " + startTime);
        System.out.println("SESSÃO TERMINA: " + endTime);
        System.out.println("CUSTO DO INGRESSO: R$ " + ticketValue);
        System.out.println("TIPO DE ANIMAÇÃO: " + typeOfAnimation);
        System.out.println("TIPO DE AUDIO: " + typeOfAudio);
        System.out.println("SALA LINKADA: " + linkedRoom);
        System.out.println("FILME LINKADA: " + linkedMovie);
        System.out.println("                                   ");
    }

    /* Getters and Setters of Sessions! */
    public String getIdSession(){
        return idSession;
    }
    public void setIdSession(String idSession){
        this.idSession = idSession;
    }

    public int getIdDeOperacao(){
        return idDeOperacao;
    }
    public void setIdDeOperacao(int idDeOperacao){
        this.idDeOperacao = idDeOperacao;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public int getStartTime(){
        return startTime;
    }
    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public int getEndTime(){
        return endTime;
    }
    public void setEndTime(int endTime){
        this.endTime = endTime;
    }

    public String getStartTimeStr(){
        return startTimeStr;
    }
    public void setStartTimeStr(String startTimeStr){
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr(){
        return endTimeStr;
    }
    public void setEndTimeStr(String endTimeStr){
        this.endTimeStr = endTimeStr;
    }

    public int getTicketValue(){
        return ticketValue;
    }
    public void setTicketValue(int ticketValue){
        this.ticketValue = ticketValue;
    }

    public String getTypeOfAnimation(){
        return typeOfAnimation;
    }
    public void setTypeOfAnimation(String typeOfAnimation){
        this.typeOfAnimation = typeOfAnimation;
    }

    public String getTypeOfAudio(){
        return typeOfAudio;
    }
    public void setTypeOfAudio(String typeOfAudio){
        this.typeOfAudio = typeOfAudio;
    }

    public String getLinkedRoom(){
        return linkedRoom;
    }
    public void setLinkedRoom(String linkedRoom){
        this.linkedRoom = linkedRoom;
    }

    public int getIdDeOperacaoRoom() { return idDeOperacaoRoom; }
    public void setIdDeOperacaoRoom(int idDeOperacaoRoom){
        this.idDeOperacaoRoom = idDeOperacaoRoom;
    }

    public String getLinkedMovie(){
        return linkedMovie;
    }
    public void setLinkedMovie(String linkedMovie){
        this.linkedMovie = linkedMovie;
    }

    public int getIdDeOperacaoMovie(){
        return idDeOperacaoMovie;
    }
    public void setIdDeOperacaoMovie(int idDeOperacaoMovie){
        this.idDeOperacaoMovie = idDeOperacaoMovie;
    }

    public List<Sessions> searchSessios(){
        List<Sessions> listObjects = new LinkedList<>();
        String sqlSearch = "SELECT * FROM sessions";

        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stm.executeQuery(sqlSearch);
        ){
            while (rs.next()){
                Sessions sessionsAtivas = new Sessions(conectar);
                sessionsAtivas.setIdSession(rs.getString("idSessions"));
                sessionsAtivas.setIdDeOperacao(rs.getInt("idDeOperacao"));
                sessionsAtivas.setDate(rs.getString("dateSession"));
                sessionsAtivas.setStartTime(rs.getInt("startTime"));
                sessionsAtivas.setEndTime(rs.getInt("endTime"));
                sessionsAtivas.setTicketValue(rs.getInt("ticketValue"));
                sessionsAtivas.setTypeOfAnimation(rs.getString("typeOfAnimation"));
                sessionsAtivas.setTypeOfAudio(rs.getString("typeOfAudio"));
                sessionsAtivas.setLinkedRoom(rs.getString("linkedRoom"));
                sessionsAtivas.setIdDeOperacaoRoom(rs.getInt("idDeOperacaoRoom"));
                sessionsAtivas.setLinkedMovie(rs.getString("linkedMovie"));
                sessionsAtivas.setIdDeOperacaoMovie(rs.getInt("idDeOperacaoMovie"));
                listObjects.add(sessionsAtivas);
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }
        return listObjects;
    }

    public void addSession(String date, String startTime, int ticketValue, String typeOfAnimation, String typeOfAudio, String linkedRoom, String linkedMovie){
        Validations validations = new Validations(conectar);
        Service service = new Service(conectar);
        Boolean res = validations.checkIfRoomIsAvailable(conectar,linkedRoom, date, startTime);

        if(res){
            JOptionPane.showMessageDialog(null, "SALA JA COM UMA SESSÃO RESERVADA!");
        }else{
            Boolean result = validations.checkIfSessionTimeIsCompatible(conectar, linkedRoom, startTime);
            if(result != true){
                try {
                    String sqlInsert = "INSERT INTO sessions (idSessions, idDeOperacao, dateSession, startTime, endTime, ticketValue, typeOfAnimation, typeOfAudio, linkedRoom, idDeOperacaoRoom, linkedMovie, idDeOperacaoMovie) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
                    PreparedStatement p = conectar.prepareStatement(sqlInsert);
                    p.setString(1, generatorUUID());
                    p.setInt(2, generatorId());
                    p.setString(3, date);
                    p.setString(4, startTime);
                    p.setString(5, service.getEndTimeService(startTime, service.getdurationMovieService(linkedMovie)));
                    p.setInt(6, ticketValue);
                    p.setString(7, typeOfAnimation);
                    p.setString(8, typeOfAudio);
                    p.setString(9, linkedRoom);
                    p.setInt(10, service.getIdDeOperacao("rooms", "roomName", linkedRoom));
                    p.setString(11, linkedMovie);
                    p.setInt(12, service.getIdDeOperacao("movies", "titleMovie", linkedMovie));
                    p.executeUpdate();
                    JOptionPane.showMessageDialog(null, "SESSÃO CRIADA COM SUCESSO!");
                }catch (SQLException e){
                    System.err.printf("ERROR: %s", e.getMessage());
                }
            }else{
                JOptionPane.showMessageDialog(null, "EXISTE UMA DIFERENÇA DE 2H PARA CADA SESSÃO! ESCOLHA OUTRO HORARIO.");
            }
        }

    }

    public void deleteSession(int idDeOperacaoSession){
        Validations validations = new Validations(conectar);
        Boolean res = validations.checkIfItExists(conectar, "sessions", idDeOperacaoSession);

        if (res){
            Boolean val = validations.checkIfSessionCanBeDeleted(conectar, idDeOperacaoSession);
            if(val){
                String sqlDelete = "DELETE FROM sessions WHERE idDeOperacao = ?";
                try{
                    PreparedStatement p = conectar.prepareStatement(sqlDelete);
                    p.setInt(1, idDeOperacaoSession);
                    p.executeUpdate();
                    JOptionPane.showMessageDialog(null, "SESSÃO DELETADA COM SUCESSO!");
                }catch (SQLException e){
                    System.out.printf("ERROR: %s", e.getMessage());
                }
            }else{
                JOptionPane.showMessageDialog(null, "A SESSÃO SO PODE SER DELETADA FALTANDO 10 DIAS!");
            }
        }else {
            JOptionPane.showMessageDialog(null, "A SESSÃO QUE VOCÊ COLOCOU NÃO EXISTE!");
        }

    }

    public void alterSession(int idDeOperacaoSession){
        List<Sessions> dadosSessionsAlter = new LinkedList<>();
        String querySQL = "UPDATE sessions SET dateSession = ?, startTime = ?, endTime = ?, ticketValue = ?, typeOfAnimation = ?, typeOfAudio = ? WHERE idDeOperacao = ?";
        String querySQLSearchName = "SELECT dateSession, startTime, endTime, ticketValue, typeOfAnimation, typeOfAudio FROM sessions WHERE idDeOperacao = "+idDeOperacaoSession;

        try(Statement stm = conectar.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(querySQLSearchName)){
            while(rs.next()){
                Sessions sessionsSearch = new Sessions(conectar);
                sessionsSearch.setDate(rs.getString("dateSession"));
                sessionsSearch.setStartTimeStr(rs.getString("startTime"));
                sessionsSearch.setEndTimeStr(rs.getString("endTime"));
                sessionsSearch.setTicketValue(rs.getInt("ticketValue"));
                sessionsSearch.setTypeOfAnimation(rs.getString("typeOfAnimation"));
                sessionsSearch.setTypeOfAudio(rs.getString("typeOfAudio"));
                dadosSessionsAlter.add(sessionsSearch);
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }finally {
            if (dadosSessionsAlter == null) {
                System.err.println("ERROR NO ID DE OPERAÇÃO TENTE NOVAMENTE!");
            }
        }
        String dataSession = null;
        String startTimeStr = null;
        String endTimeStr = null;
        int ticketValue = 0;
        String typeOfAnimation = null;
        String typeOfAudio = null;


        for(Sessions s : dadosSessionsAlter){
            dataSession = JOptionPane.showInputDialog("Data da Sessão", s.getDate());
            startTimeStr = JOptionPane.showInputDialog("Sessão começa ás ", s.getStartTimeStr());
            endTimeStr = JOptionPane.showInputDialog("Sessão termina as ", s.getEndTimeStr());
            ticketValue = Integer.parseInt(JOptionPane.showInputDialog("O valor da sessão é R$", s.getTicketValue()));
            typeOfAnimation = JOptionPane.showInputDialog("Tipo de Animação: ", s.getTypeOfAnimation());
            typeOfAudio = JOptionPane.showInputDialog("Tipo de Audio: ", s.getTypeOfAudio());
        }

        try{
            PreparedStatement p = conectar.prepareStatement(querySQL);
            p.setString(1, dataSession);
            p.setString(2, startTimeStr);
            p.setString(3, endTimeStr);
            p.setDouble(4, ticketValue);
            p.setString(5, typeOfAnimation);
            p.setString(6, typeOfAudio);
            p.setInt(7, idDeOperacaoSession);
            p.executeUpdate();
            System.out.println("SESSÃO ALTERADA COM SUCESSO!");
            JOptionPane.showMessageDialog(null, "SESSÃO ALTERADA COM SUCESSO");
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE,null, e);
        }
    }

}

package com.cinema.manager.rooms;

import com.cinema.manager.validation.Validations;

import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Rooms {
    Connection conectar;

    public Rooms(Connection conectar){
        this.conectar = conectar;
    }

    private String idRoom;
    private int idDeOperacao;
    private String roomName;
    private int qtdAssentos;

    /* Getters and Setters of objects. */
    public String getIdRoom(){
        return idRoom;
    }
    public void setIdRoom(String idRoom){
        this.idRoom = idRoom;
    }

    public int getIdDeOperacao(){
        return idDeOperacao;
    }
    public void setIdDeOperacao(int idDeOperacao){
        this.idDeOperacao = idDeOperacao;
    }

    public String getRoomName(){
        return roomName;
    }
    public void setRoomName(String roomName){
        this.roomName = roomName;
    }

    public int getQtdAssentos(){
        return qtdAssentos;
    }
    public void setQtdAssentos(int qtdAssentos){
        this.qtdAssentos = qtdAssentos;
    }

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
        System.out.println("ID DA SALA: " + idRoom);
        System.out.println("ID DE OPERAÇÃO: " + idDeOperacao);
        System.out.println("NOME DA SALA: " + roomName);
        System.out.println("QUANTIDADE DE ASSENTOS: " + qtdAssentos);
        System.out.println("                                   ");
    }

    /* functions for choosing movie! */

    public List<Rooms> searchRooms(){
        List<Rooms> listObjects = new LinkedList<>();
        try(Statement stm = conectar.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery("SELECT * FROM rooms ORDER BY roomName")){
            while (rs.next()){
                Rooms roomsPrepare = new Rooms(conectar);
                roomsPrepare.setIdRoom(rs.getString("idRooms"));
                roomsPrepare.setIdDeOperacao(rs.getInt("idDeOperacao"));
                roomsPrepare.setRoomName(rs.getString("roomName"));
                roomsPrepare.setQtdAssentos(rs.getInt("qtdAssentos"));
                listObjects.add(roomsPrepare);
            }
        }catch(SQLException e){
            System.err.println("ERROR: " + e.getMessage());
        }
        return listObjects;
    }

    public void addRoom(String roomName, int qtdAssentos){
        Validations validations = new Validations(conectar);
        Boolean res = validations.checkRoomExist(conectar, roomName);

        if(res == false){
            String SQL = "INSERT INTO rooms (idRooms, idDeOperacao, roomName, qtdAssentos) VALUES(?,?,?,?)";
            try{
                PreparedStatement p = conectar.prepareStatement(SQL);
                p.setString(1, generatorUUID());
                p.setInt(2, generatorId());
                p.setString(3, roomName);
                p.setInt(4, qtdAssentos);
                p.executeUpdate();
                JOptionPane.showMessageDialog(null, "SALA CRIADA COM SUCESSO!");
            }catch (SQLException e){
                System.err.printf("ERROR: %s", e.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(null, "JA EXISTE UMA SALA COM O MESMO NOME! Tente novamente.");
        }
    }

    public void deleteRoom(int idDeOperacao){
        Validations validations = new Validations(conectar);
        Boolean val = validations.checkIfItExists(conectar,"rooms", idDeOperacao);
        if(val){
            Boolean res = validations.checkRoomLinkedMovieAndSession(conectar, idDeOperacao);

            if(res == false){
                try{
                    String sqlDelete = "DELETE FROM rooms WHERE idDeOperacao = ?";
                    PreparedStatement p = conectar.prepareStatement(sqlDelete);
                    p.setInt(1, idDeOperacao);
                    p.executeUpdate();
                    JOptionPane.showMessageDialog(null, "A SALA FOI DELETADA COM SUCESSO!");
                }catch (SQLException e){
                    System.err.printf("ERROR: %s", e.getMessage());
                }
            }else{
                JOptionPane.showMessageDialog(null, "A SALA NÃO PODE SER DELETADA PORQUE EXISTE UMA SESSÃO VINCULADA!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "SALA NÃO EXISTE");
        }
    }

    public void alterRoom(String roomName,int qtsAssentos, int idDeOperacao){
        Validations validations = new Validations(conectar);
        Boolean val = validations.checkIfItExists(conectar, "rooms", idDeOperacao);

        if(val){
            try{
                String sqlAlter = "UPDATE rooms SET roomName = ?, qtdAssentos = ? WHERE idDeOperacao = ?";
                PreparedStatement p = conectar.prepareStatement(sqlAlter);
                p.setString(1, roomName);
                p.setInt(2, qtsAssentos);
                p.setInt(3, idDeOperacao);
                p.executeUpdate();
                JOptionPane.showMessageDialog(null, "SALA ALTERADA COM SUCESSO!");
            }catch (SQLException e){
                System.err.printf("ERROR: %s", e.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(null, "A SALA QUE VOCÊ ESTÁ TENTANDO ALTERAR NÃO EXISTE!");
        }

    }
}

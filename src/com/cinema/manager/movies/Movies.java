package com.cinema.manager.movies;

import com.cinema.manager.DAO.Conexao;
import com.cinema.manager.validation.Validations;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Movies {
    Connection conectar;

    public Movies (Connection conectar) { this.conectar = conectar;}

    Validations validations = new Validations(conectar);

    private String idMovie;
    private int idDeOperacao;
    private String title;
    private String description;
    private String duration;

    /* Get and Set of id Movie */
    public String getIdMovie(){
        return idMovie;
    }
    public void setIdMovie(String idMovie){
        this.idMovie = idMovie;
    }

    /* Get and Set of id de operação */
    public int getIdOperacao(){
        return idDeOperacao;
    }
    public void setIdOperacao(int idDeOperacao){ this.idDeOperacao = idDeOperacao; }

    /* Get and Set of title */
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    /* Get and Set of description */
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    /* Get and Set of duration */
    public String getDuration(){
        return duration;
    }
    public void setDuration(String duration){
        this.duration = duration;
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
        System.out.println("ID DO FILME: " + idMovie);
        System.out.println("ID DE OPERAÇÃO: " + idDeOperacao);
        System.out.println("NOME DO FILME: " + title);
        System.out.println("DESCRIÇÃO DO FILME: " + description);
        System.out.println("DURAÇÃO DO FILME: " + duration);
        System.out.println("                                   ");
    }

    /* functions for choosing movie! */
    public List<Movies> searchMovies() {
        List<Movies> listObjects = new LinkedList<>();
        try(Statement stm = conectar.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery("SELECT * FROM movies")){
            while(rs.next()) {
                Movies moviesEncontrado = new Movies(conectar);
                moviesEncontrado.setIdMovie(rs.getString("idMovies"));
                moviesEncontrado.setIdOperacao(rs.getInt("idDeOperacao"));
                moviesEncontrado.setTitle(rs.getString("titleMovie"));
                moviesEncontrado.setDescription(rs.getString("descriptionMovie"));
                moviesEncontrado.setDuration(rs.getString("durationMovie"));
                listObjects.add(moviesEncontrado);
            }
        }catch(SQLException e){
            System.err.println("ERROR: " + e.getMessage());
        }
        return listObjects;
    }

    public void addMovie(String title, String description, String duration){

        String replaceTitle = "'" +title+ "'";
        Boolean res = validations.validacaoAdd(conectar, replaceTitle);

        if(res){
            JOptionPane.showMessageDialog(null, "JA EXISTE UM FILME COM O MESMO NOME!, TENTE NOVAMENTE");
        }else{
            String querySQL = "INSERT INTO movies (idMovies, idDeOperacao, titleMovie, descriptionMovie, durationMovie) VALUES (?,?,?,?,?)";
            try{
                PreparedStatement p = conectar.prepareStatement(querySQL);
                p.setString(1, generatorUUID());
                p.setInt(2, generatorId());
                p.setString(3, title);
                p.setString(4, description);
                p.setString(5, duration);
                p.executeUpdate();
                System.out.printf("O FILME %s FOI CADASTADO COM SUCESSO!", title);
            }catch (SQLException e){
                System.err.printf("ERROR: %s", e.getMessage());
                if(conectar != null){
                    try{
                        System.out.println("O processo está sendo refeito");
                        conectar.rollback();
                    }catch (SQLException excep){
                        System.err.println(excep);
                    }
                }
            }
        }
    }

    public void deleteMovie(int idDeOperacao) {
        Validations validations = new Validations(conectar);
        Boolean val = validations.checkIfItExists(conectar, "movies", idDeOperacao);
        if(val){
            String querySQLSearchName = "SELECT titleMovie FROM movies WHERE idDeOperacao =" + idDeOperacao;
            String nameMovie = null;

            Boolean res = validations.checkSession(conectar, idDeOperacao);

            if(!res){
                try(Statement stm = conectar.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = stm.executeQuery(querySQLSearchName)){
                    while(rs.next()){
                        Movies titleEncontrado = new Movies(conectar);
                        titleEncontrado.setTitle(rs.getString("titleMovie"));
                        nameMovie = titleEncontrado.title;
                    }
                }catch (SQLException e){
                    System.err.printf("ERROR: %s", e.getMessage());
                }finally {
                    if(nameMovie == null){
                        System.err.println("ERROR NO ID DE OPERAÇÃO TENTE NOVAMENTE!");
                    }
                }

                String querySQL = "DELETE FROM movies WHERE idDeOperacao = ?";
                try{
                    PreparedStatement p = conectar.prepareStatement(querySQL);
                    p.setInt(1, idDeOperacao);
                    p.executeUpdate();
                    System.out.printf("FILME %S FOI APAGADO COM SUCESSO.", nameMovie);
                }catch (SQLException e){
                    System.out.printf("ERROR: %s", e.getMessage());
                    if(conectar != null){
                        try{
                            System.out.println("O processo está sendo refeito");
                            conectar.rollback();
                        }catch (SQLException excep){
                            System.out.printf("ERROR NO SISTEMA DE CONEXÃO: %s",excep);
                        }
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null, "FILME NÃO PODE SER DELETADO POR CONTEM UMA SESSÃO VINCULADA");
            }
        }else{
            JOptionPane.showMessageDialog(null, "FILME NÃO EXISTE");
        }
    }

    public void alterMovie(int idDeOperacao){
        List<Movies> dadosMovieAlter = new LinkedList<>();
        String querySQL = "UPDATE movies SET titleMovie = ?, descriptionMovie = ?, durationMovie = ? WHERE idDeOperacao = ?";
        String querySQLSearchName = "SELECT titleMovie, descriptionMovie, durationMovie FROM movies WHERE idDeOperacao =" + idDeOperacao;

        try(Statement stm = conectar.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stm.executeQuery(querySQLSearchName)){
            while(rs.next()){
                Movies movieSearch = new Movies(conectar);
                movieSearch.setTitle(rs.getString("titleMovie"));
                movieSearch.setDescription(rs.getString("descriptionMovie"));
                movieSearch.setDuration(rs.getString("durationMovie"));
                dadosMovieAlter.add(movieSearch);
            }
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
        }finally {
            if (dadosMovieAlter == null) {
                System.err.println("ERROR NO ID DE OPERAÇÃO TENTE NOVAMENTE!");
            }
        }
        String titleAlter = null;
        String descriptionAlter = null;
        String durationAlter = null;


        for(Movies m : dadosMovieAlter){
            titleAlter = JOptionPane.showInputDialog("Titulo do Filme", m.getTitle());
            descriptionAlter = JOptionPane.showInputDialog("Descrição do Filme", m.getDescription());
            durationAlter = JOptionPane.showInputDialog("Duração do filme", m.getDuration());
        }

        try{
            PreparedStatement p = conectar.prepareStatement(querySQL);
            p.setString(1, titleAlter);
            p.setString(2, descriptionAlter);
            p.setString(3, durationAlter);
            p.setInt(4, idDeOperacao);
            p.executeUpdate();
            System.out.println("FILME ALTERADO COM SUCESSO!");
            JOptionPane.showMessageDialog(null, "FILME ALTERADO COM SUCESSO");
        }catch (SQLException e){
            System.err.printf("ERROR: %s", e.getMessage());
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE,null, e);
        }
    }
}

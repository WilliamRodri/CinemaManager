package com.cinema.manager.Users.UserController;

import com.cinema.manager.Users.Register.RegisterUser;
import com.cinema.manager.Users.User;
import com.cinema.manager.movies.Movies;
import com.cinema.manager.rooms.Rooms;
import com.cinema.manager.sessions.Sessions;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class UserController {
    Connection conectar;
    public UserController(Connection conectar){
        this.conectar = conectar;
    }

    public void optionsForLoggedInUser(String user, String password, int level) throws SQLException {
        Scanner entrada = new Scanner(System.in);
        Movies movies = new Movies(conectar);
        Rooms rooms = new Rooms(conectar);
        Sessions sessions = new Sessions(conectar);
        User userController = new User(conectar);
        RegisterUser userRegister = new RegisterUser(conectar);
        switch (level){
            case 1:
                if(userController.getAccessLevel() == 1){
                    break;
                }
                System.out.println("**********************************");
                System.out.println("*********ESCOLHA UMA OPÇÃO********");
                System.out.println("1 - Visualizar filmes cadastrados");
                System.out.println("2 - Visualizar salas cadastrados");
                System.out.println("3 - Visualizar sessões cadastrados");
                System.out.println("4- Excluir meu usuario");
                System.out.println("5- Sair");
                int resposta = entrada.nextInt();
                switch (resposta){
                    case 1:
                        for(Movies m : movies.searchMovies()){
                            m.visualizarInfo();
                        }
                        break;
                    case 2:
                        for(Rooms r : rooms.searchRooms()){
                            r.visualizarInfo();
                        }
                        break;
                    case 3:
                        for(Sessions s : sessions.searchSessios()){
                            s.visualizarInfo();
                        }
                        break;
                    case 4:
                        userController.deleteAccount(user, password);
                        break;
                    case 5:
                        System.out.println("OBRIGADO!");
                        conectar.close();
                        break;
                    default:
                        break;
                }
            case 2:
                if(userController.getAccessLevel() == 2){
                    break;
                }
                System.out.println("**********************************");
                System.out.println("*********ESCOLHA UMA OPÇÃO********");
                System.out.println("1 - Visualizar filmes cadastrados");
                System.out.println("2 - Visualizar salas cadastrados");
                System.out.println("3 - Visualizar sessões cadastrados");
                System.out.println("4 - Adicionar filme");
                System.out.println("5 - Excluir meu usuario");
                System.out.println("6 - Sair");
                int resposta_level2 = entrada.nextInt();
                switch (resposta_level2){
                    case 1:
                        for(Movies m : movies.searchMovies()){
                            m.visualizarInfo();
                        }
                        break;
                    case 2:
                        for(Rooms r : rooms.searchRooms()){
                            r.visualizarInfo();
                        }
                        break;
                    case 3:
                        for(Sessions s : sessions.searchSessios()){
                            s.visualizarInfo();
                        }
                        break;
                    case 4:
                        String titleMovie = JOptionPane.showInputDialog(null, "");
                        String description = JOptionPane.showInputDialog(null, "");
                        String duration = JOptionPane.showInputDialog("Duração nesse formato: 0h00", "");
                        movies.addMovie(titleMovie, description, duration);
                        break;
                    case 5:
                        userController.deleteAccount(user, password);
                        break;
                    case 6:
                        System.out.println("OBRIGADO!");
                        conectar.close();
                        break;
                    default:
                        break;
                }
            case 3:
                if(userController.getAccessLevel() == 3){
                    break;
                }
                System.out.println("**********************************");
                System.out.println("*********ESCOLHA UMA OPÇÃO********");
                System.out.println("1 - Visualizar filmes cadastrados");
                System.out.println("2 - Visualizar salas cadastrados");
                System.out.println("3 - Visualizar sessões cadastrados");
                System.out.println("4 - Opções para filme");
                System.out.println("5 - Opções para salas");
                System.out.println("6 - Opções para sessões");
                System.out.println("7 - Excluir meu usuario");
                System.out.println("8 - Adicionar um novo usuario");
                System.out.println("9 - Sair");
                int resposta_level3 = entrada.nextInt();
                switch (resposta_level3){
                    case 1:
                        for(Movies m : movies.searchMovies()){
                            m.visualizarInfo();
                        }
                        break;
                    case 2:
                        for(Rooms r : rooms.searchRooms()){
                            r.visualizarInfo();
                        }
                        break;
                    case 3:
                        for(Sessions s : sessions.searchSessios()){
                            s.visualizarInfo();
                        }
                        break;
                    case 4:
                        System.out.println("**********************************");
                        System.out.println("*********ESCOLHA UMA OPÇÃO********");
                        System.out.println("1 - ADICIONAR FILME");
                        System.out.println("2 - MODIFICAR FILME");
                        System.out.println("3 - DELETAR FILME");
                        int resposta_opcao = entrada.nextInt();
                        if(resposta_opcao == 1){
                            String titleMovie = JOptionPane.showInputDialog(null, "");
                            String description = JOptionPane.showInputDialog(null, "");
                            String duration = JOptionPane.showInputDialog("Duração nesse formato: 0h00", "");
                            movies.addMovie(titleMovie, description, duration);
                            break;
                        }else if(resposta_opcao == 2){
                            int idDeOperacao = Integer.parseInt(JOptionPane.showInputDialog(null, "DIGITE O ID DE OPERAÇÕES"));
                            movies.alterMovie(idDeOperacao);
                            break;
                        } else if (resposta_opcao == 3) {
                            int idDeOperacao = Integer.parseInt(JOptionPane.showInputDialog(null, "DIGITE O ID DE OPERAÇÕES"));
                            movies.deleteMovie(idDeOperacao);
                            break;
                        }else {
                            break;
                        }
                    case 5:
                        System.out.println("**********************************");
                        System.out.println("*********ESCOLHA UMA OPÇÃO********");
                        System.out.println("1 - ADICIONAR SALA");
                        System.out.println("2 - MODIFICAR SALA");
                        System.out.println("3 - DELETAR SALA");
                        int resposta_opcao2 = entrada.nextInt();
                        if(resposta_opcao2 == 1){
                            String titleMovie = JOptionPane.showInputDialog(null, "");
                            String description = JOptionPane.showInputDialog(null, "");
                            String duration = JOptionPane.showInputDialog("Duração nesse formato: 0h00", "");
                            movies.addMovie(titleMovie, description, duration);
                            break;
                        }else if(resposta_opcao2 == 2){
                            String roomName = JOptionPane.showInputDialog(null, "DIGITE UM NOVO NOME PAR A SALA");
                            int qtdAssentos = Integer.parseInt(JOptionPane.showInputDialog(null, "DIGITE A NOVA QUANTIDADE DE ASSENTOS DA SALA"));
                            int idDeOperacao = Integer.parseInt(JOptionPane.showInputDialog(null, "DIGITE O ID DE OPERAÇÕES DA SALA QUE DESEJA ALTERAR"));
                            rooms.alterRoom(roomName, qtdAssentos, idDeOperacao);
                            break;
                        } else if (resposta_opcao2 == 3) {
                            int idDeOperacao = Integer.parseInt(JOptionPane.showInputDialog(null, "DIGITE O ID DE OPERAÇÕES"));
                            rooms.deleteRoom(idDeOperacao);
                            break;
                        }else {
                            break;
                        }
                    case 6:
                        System.out.println("**********************************");
                        System.out.println("*********ESCOLHA UMA OPÇÃO********");
                        System.out.println("1 - CRIAR SESSÃO");
                        System.out.println("2 - MODIFICAR SESSÃO");
                        System.out.println("3 - DELETAR SESSÃO");
                        int resposta_opcao3 = entrada.nextInt();
                        if(resposta_opcao3 == 1){
                            String date = JOptionPane.showInputDialog(null, "DIA DA SESSÃO: ");
                            String startTime = JOptionPane.showInputDialog("FORMATO: 18-00-00", "HORARIO DE INICIO DA SESSÃO: ");
                            int ticket = Integer.parseInt(JOptionPane.showInputDialog(null, "VALOR DO INGRESSO: "));
                            String animation = JOptionPane.showInputDialog(null, "TIPO DE ANIMAÇÃO");
                            String audio = JOptionPane.showInputDialog(null, "TIPO DE AUDIO");
                            String linkedRoom = JOptionPane.showInputDialog(null, "EM QUAL SALA SERÁ ESSA SESSÃO");
                            String linkedMovie = JOptionPane.showInputDialog(null, "QUAL FILME SERÁ PASSADO NESSA SESSÃO");
                            sessions.addSession(date, startTime, ticket, animation, audio, linkedRoom, linkedMovie);
                            break;
                        }else if(resposta_opcao3 == 2){
                            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "DIGITE O ID DE OPERAÇÃO DA SESSÃO QUE DESEJA ALTERAR!"));
                            sessions.alterSession(id);
                            break;
                        } else if (resposta_opcao3 == 3) {
                            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "DIGITE O ID DE OPERAÇÃO DA SESSÃO QUE DESEJA DELETAR!"));
                            sessions.deleteSession(id);
                            break;
                        }else {
                            break;
                        }
                    case 7:
                        userController.deleteAccount(user, password);
                        break;
                    case 8:
                        String userNewUser = JOptionPane.showInputDialog(null, "Digite o username: ");
                        String passNewUser = JOptionPane.showInputDialog(null, "Digite o password: ");
                        int levelNewUser = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o level do usuario"));
                        userRegister.registerUser(conectar, userNewUser, passNewUser, levelNewUser);
                    case 9:
                        System.out.println("OBRIGADO!");
                        conectar.close();
                        break;

                    default:
                        break;
                }
        }
    }
}

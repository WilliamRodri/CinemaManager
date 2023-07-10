-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: cinemamanager
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `idMovies` varchar(750) NOT NULL,
  `idDeOperacao` int NOT NULL,
  `titleMovie` varchar(100) NOT NULL,
  `descriptionMovie` varchar(2000) NOT NULL,
  `durationMovie` varchar(10) NOT NULL,
  PRIMARY KEY (`idMovies`),
  UNIQUE KEY `idMovies_UNIQUE` (`idMovies`),
  UNIQUE KEY `idDeOperacao_UNIQUE` (`idDeOperacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES ('183b8722-c0dc-4d3b-9027-e1aba69014b1',6256,'The Flash','Mundos colidem em The Flash quando Barry usa seus superpoderes para viajar no tempo e mudar os eventos do passado. Mas quando tenta salvar sua família e acaba, sem querer, alterando o futuro, Barry fica preso em uma realidade na qual o General Zod está de volta, ameaçando colocar o mundo em risco, e não há super-heróis a quem recorrer. A não ser que que Barry consiga persuadir um Batman muito diferente a sair da aposentadoria e resgatar um kryptoniano preso… mesmo que não seja exatamente quem Batman está procurando. Para salvar o mundo em que está e retornar ao futuro que conhece, a única esperança de Barry é usar seus superpoderes para salvar sua vida. Mas, se afinal, precisar desistir dela, será seu sacrifício suficiente para reconfigurar o universo?','2h24'),('486e1e0d-8ffa-48ac-a79c-680df227993d',6357,'Indiana Jones e a Relíquia do Destino','Indiana Jones e o Chamado do Destino passa em 1969, onde o já conhecido arqueólogo e aventureiro americano vivido por Harrison Ford vive no cenário da Corrida Espacial. Jones está preocupado com o fato de o governo dos Estados Unidos ter recrutado ex-nazistas para ajudar a vencer a União Soviética na competição para chegar ao espaço.','2h22'),('4f483e0d-7b24-4d78-b38c-efbdcde605ba',3843,'Sobrenatural: A Porta Vermelha ','Para colocar seus demônios para trás de uma vez por todas, Josh e um agora jovem universitário Dalton precisar ir o mais fundo que já estiveram no Além, encarando de frente o passado sombrio de sua família e encontrando novos protagonistas dos mais profundos medos que se escondem atrás da porta vermelha. ','1h47'),('53d44cbe-d013-41e1-8aad-57012b04758b',3710,'Barbie','No mundo mágico das Barbies, \"Barbieland\", uma das bonecas começa a perceber que não se encaixa como as outras. Depois de ser expulsa, ela parte para uma aventura no \"mundo real\", onde descobre que a beleza está no interior de cada um.','1h54'),('944578d8-0649-40fc-beba-f6be7fedfae8',1048,'Um Dia Cinco Estrelas ','Pedro Paulo passa dia e noite cuidando do seu Mozão, um Opala dos anos 70. Para ajudar a família a sair do sufoco e realizar o sonho da sua mãe de viajar para Buenos Aires, ele começa a trabalhar como motorista de aplicativo. Durante a nova jornada, ele passa por situações inusitadas e divertidas que mudam a vida de Pedro Paulo, da sua família e dos seus passageiros. ','1h34');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `idRooms` varchar(750) NOT NULL,
  `idDeOperacao` int DEFAULT NULL,
  `roomName` varchar(40) NOT NULL,
  `qtdAssentos` int NOT NULL,
  PRIMARY KEY (`idRooms`),
  UNIQUE KEY `idRooms_UNIQUE` (`idRooms`),
  UNIQUE KEY `idDeOperacao_UNIQUE` (`idDeOperacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES ('0b55af0a-1cb1-11ee-be56-0242ac120002',1001,'SALA 1',50),('bb17ed12-e7ed-4593-8881-28d053185e1e',1002,'SALA 4',250);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `idSessions` varchar(750) NOT NULL,
  `idDeOperacao` int DEFAULT NULL,
  `dateSession` varchar(20) DEFAULT NULL,
  `startTime` varchar(20) DEFAULT NULL,
  `endTime` varchar(20) DEFAULT NULL,
  `ticketValue` double DEFAULT NULL,
  `typeOfAnimation` varchar(20) DEFAULT NULL,
  `typeOfAudio` varchar(20) DEFAULT NULL,
  `linkedRoom` varchar(45) DEFAULT NULL,
  `idDeOperacaoRoom` int DEFAULT NULL,
  `linkedMovie` varchar(45) DEFAULT NULL,
  `idDeOperacaoMovie` int DEFAULT NULL,
  PRIMARY KEY (`idSessions`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES ('1124633f-7c4e-47ca-a1d3-4602767ae900',1142,'2023-07-15','20-35-00','21-77-00',30,'3D','DUBLADO','SALA 4',1002,'Sobrenatural: A Porta Vermelha ',3843),('1a4f0d9c-f463-4e4d-830e-f8d9b4ef3c26',2421,'2023-07-10','18-35-00','20-57-00',30,'3D','DUBLADO','SALA 2',0,'Indiana Jones e a Relíquia do Destino',6357);
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `idUsers` varchar(150) NOT NULL,
  `acessLevel` int NOT NULL,
  `user` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`idUsers`),
  UNIQUE KEY `idUsers_UNIQUE` (`idUsers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('9d40ca53-27d0-4cb6-9c3e-da980db2b62c',1,'user1','user1'),('c1fd14af-8b98-4cad-bd55-6704d2df8806',3,'user3','user3'),('e18b17ed-6a72-4477-9c65-522832faffcc',2,'user2','user2');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-08 13:37:27

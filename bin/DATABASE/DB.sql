-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ekrutdatabase
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `machines`
--

DROP TABLE IF EXISTS `machines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machines` (
  `machineid` varchar(20) NOT NULL,
  `machinelocation` enum('tel_aviv','haifa','none') DEFAULT NULL,
  PRIMARY KEY (`machineid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machines`
--

LOCK TABLES `machines` WRITE;
/*!40000 ALTER TABLE `machines` DISABLE KEYS */;
INSERT INTO `machines` VALUES ('HA01','haifa'),('TA01','tel_aviv');
/*!40000 ALTER TABLE `machines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderid` varchar(10) NOT NULL,
  `price` varchar(45) DEFAULT NULL,
  `products` varchar(500) NOT NULL,
  `machineid` varchar(10) DEFAULT NULL,
  `orderdate` datetime DEFAULT NULL,
  `estimateddeliverydate` varchar(45) DEFAULT NULL,
  `confirmationdate` varchar(45) DEFAULT NULL,
  `orderstatus` enum('APPROVED','NOT_APPROVED','AWAITING_APPROVAL','CANCELED') DEFAULT NULL,
  `customerid` varchar(45) NOT NULL,
  `supplymethod` enum('DELIVERY','MACHINE_PICKUP','INSTANT_PICKUP') DEFAULT NULL,
  `paidwith` enum('CREDIT_CARD','DELAYED_PAYMENT') NOT NULL,
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `productid` varchar(10) NOT NULL,
  `name` varchar(80) NOT NULL,
  `price` float NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `type` enum('DRINK','SNACK') DEFAULT NULL,
  PRIMARY KEY (`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('P001','BambaNougat',6,'bamba nougatte 60g','SNACK'),('P002','BambaOriginal',4,'bamba 80g','SNACK'),('P003','BluDay',3,'blu day blueberries','DRINK'),('P004','BluMojito',3,'blu mojito','DRINK'),('P005','BluOriginal',3,'blu','DRINK'),('P006','CocaColaCan',5,'coca-cola','DRINK'),('P007','CocaColaCanZero',5,'coca-cola zero','DRINK'),('P008','CocaColaGlass',9,'coca-cola glass bottle','DRINK'),('P009','FitnessCinnamon',8,'fitness cinnamon taste','SNACK'),('P010','FitnessSalted',7,'fitness salted taste','SNACK'),('P011','MilkaHazlenutsChocolate',9,'milka chocolate with hazelnuts','SNACK'),('P012','MilkaOreo',9,'milka chocolate with oreo','SNACK'),('P013','MonsterPink',11,'monster Passion Fruit, Orange, and Guava','DRINK'),('P014','MonsterRegular',10,'monster ','DRINK'),('P015','MonsterZeroSugar',9,'monster zero sugar','DRINK'),('P016','PepsiMax',5,'pepsi max','DRINK'),('P017','PringlesGrill',12,'pringles grill taste','SNACK'),('P018','PringlesOriginal',10,'pringles natural taste','SNACK'),('P019','PringlesSourCreamAndOnion',12,'pringles sour cream and onion taste','SNACK'),('P020','SchweppesOriginal',7,'schweppes mint taste','DRINK'),('P021','Snickers',4,'snickers','SNACK'),('P022','SnickersPeanutButter',6,'snickers creamy-peanut butter','SNACK'),('P023','TapuGrill',3,'tapuchips crunch grill taste','SNACK'),('P024','TapuMexican',3,'tapuchips crunch mexican taste','SNACK'),('P025','TapuNatural',3,'tapuchips natural taste','SNACK'),('P026','TapuNatural',3,'tapuchips stix natural taste','SNACK'),('P027','TapuSaltAndPepper',4,'tapuchips salt and pepper','SNACK'),('P028','TapuSourCreamAndOnion',5,'tapuchips sour cream & onion taste','SNACK'),('P029','TapuWavyPaprica',6,'tapuchips wavy paprika taste','SNACK'),('P030','Toblerone',14,'Toblerone chocolate','SNACK'),('P031','TonyMilkChocolate',999,'tonys chocolonely milk chocolate','SNACK'),('P032','TonyMilkHazelnutChocolate',999,'tonys chocolonely milk cholate with hazelnut','SNACK');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productsinmachines`
--

DROP TABLE IF EXISTS `productsinmachines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productsinmachines` (
  `machineid` varchar(80) NOT NULL,
  `productid` varchar(100) NOT NULL,
  `discount` float DEFAULT '0',
  `amount` int NOT NULL DEFAULT '0',
  `criticalamount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`machineid`,`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productsinmachines`
--

LOCK TABLES `productsinmachines` WRITE;
/*!40000 ALTER TABLE `productsinmachines` DISABLE KEYS */;
INSERT INTO `productsinmachines` VALUES ('HA01','P002',0.2,150,15),('HA01','P005',0.15,130,10),('TA01','P001',0,0,0),('TA01','P002',0,0,0),('TA01','P003',0,0,0),('TA01','P004',0,0,0),('TA01','P005',0,0,0),('TA01','P006',0,0,0),('TA01','P007',0,0,0),('TA01','P008',0,0,0),('TA01','P009',0,0,0),('TA01','P010',0,0,0),('TA01','P011',0,0,0),('TA01','P012',0,0,0),('TA01','P013',0,0,0),('TA01','P014',0,0,0),('TA01','P015',0,0,0),('TA01','P016',0,0,0),('TA01','P017',0,0,0),('TA01','P018',0,0,0),('TA01','P019',0,0,0),('TA01','P020',0,0,0),('TA01','P021',0,0,0),('TA01','P022',0,0,0),('TA01','P023',0,0,0),('TA01','P024',0,0,0),('TA01','P025',0,0,0),('TA01','P026',0,0,0),('TA01','P027',0,0,0),('TA01','P028',0,0,0),('TA01','P029',0,0,0),('TA01','P030',0,0,0),('TA01','P031',0,0,0),('TA01','P032',0,0,0);
/*!40000 ALTER TABLE `productsinmachines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `id` varchar(45) NOT NULL,
  `phonenumber` varchar(45) DEFAULT NULL,
  `emailaddress` varchar(45) DEFAULT NULL,
  `isloggedin` tinyint(1) NOT NULL DEFAULT '0',
  `userstatus` enum('approved','not approved','frozen') DEFAULT 'not approved',
  `department` enum('member','ceo','customer_service') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('ravid','123','ravid','goldin','222222222','0548889990','dsad@dsad.com',0,'approved','ceo'),('lior','123','lior','jigalo','316109115','0528081434','audiblemaple@gmail.com',0,'approved','member'),('avi','123','avi','baguette','333333333','0252324412','sdad@ds.com',0,'approved','customer_service');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `productid` varchar(10) NOT NULL,
  `amount` int DEFAULT NULL,
  PRIMARY KEY (`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-22 14:03:09

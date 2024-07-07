-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: pharmacy
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--
CREATE DATABASE elipharma;
USE elipharma;

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customerName` varchar(100) NOT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Kwame Owusu','+233 54 123 4567'),(2,'Ama Serwaa','+233 24 987 6543'),(3,'Kofi Mensah','+233 55 901 2345'),(4,'Akua Agyeman','+233 27 765 4321'),(5,'Yaw Boakye','+233 50 111 2222'),(6,'Mr Asare','02366541367'),(7,'Mr Kwadwo Amoah ','0243877126'),(8,'Kofi Menu','0245679924'),(9,'Kwame Owusu','0245565716'),(10,'Kofi Annan','0236687971'),(11,'Solomon Duncan','0235571652'),(12,'Kwame Opoku','023557661'),(13,'Kwame','02312983113'),(14,'Yaw ','02355187543');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drug_supplier`
--

DROP TABLE IF EXISTS `drug_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug_supplier` (
  `drug_suppID` int NOT NULL AUTO_INCREMENT,
  `supplierID` int DEFAULT NULL,
  `drugID` int DEFAULT NULL,
  PRIMARY KEY (`drug_suppID`),
  KEY `supplierID` (`supplierID`),
  KEY `drugID` (`drugID`),
  CONSTRAINT `drug_supplier_ibfk_1` FOREIGN KEY (`supplierID`) REFERENCES `suppliers` (`supplierID`),
  CONSTRAINT `drug_supplier_ibfk_2` FOREIGN KEY (`drugID`) REFERENCES `drugs` (`drugID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drug_supplier`
--

LOCK TABLES `drug_supplier` WRITE;
/*!40000 ALTER TABLE `drug_supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `drug_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drugs`
--

DROP TABLE IF EXISTS `drugs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drugs` (
  `drugID` int NOT NULL AUTO_INCREMENT,
  `drugName` varchar(100) NOT NULL,
  `unitPrice` double NOT NULL,
  `numOfUnits` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `supplier` varchar(50) DEFAULT NULL,
  `available` enum('yes','no') DEFAULT 'no',
  `Deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`drugID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drugs`
--

LOCK TABLES `drugs` WRITE;
/*!40000 ALTER TABLE `drugs` DISABLE KEYS */;
INSERT INTO `drugs` VALUES (21,'Ibuprofen',10.99,100,'Pain reliever','Pfizer','yes',0),(22,'Acetaminophen',5.99,200,'Pain reliever','Johnson & Johnson','yes',0),(23,'Amoxicillin',7.99,50,'Antibiotic','GlaxoSmithKline','yes',0),(24,'Lisinopril',12.99,100,'Blood pressure medication','AstraZeneca','no',1),(25,'Omeprazole',8.99,120,'Antacid','AstraZeneca','no',1),(26,'Paracetamol',20,300,'Pain Killer','ECL','no',1),(27,'Paracetamol',10,0,'Pain Killer','Enapharm','yes',0),(28,'Malar 2',10,32,'Malaria drug','ECL','no',1),(29,'Paracetamol',2.8,400,'headache','Eli','no',1);
/*!40000 ALTER TABLE `drugs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `purchase_id` int NOT NULL AUTO_INCREMENT,
  `drugID` int DEFAULT NULL,
  `price_sold` double DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `customerID` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`purchase_id`),
  KEY `drugID` (`drugID`),
  KEY `fk_customerID` (`customerID`),
  CONSTRAINT `fk_customerID` FOREIGN KEY (`customerID`) REFERENCES `customers` (`id`),
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`drugID`) REFERENCES `drugs` (`drugID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,21,10.99,'2024-06-24 12:08:42',3,1),(2,22,5.99,'2024-06-24 12:08:42',1,1),(4,27,10,'2024-06-24 19:02:37',4,1),(5,27,10,'2024-06-24 19:02:37',4,1),(6,24,12.99,'2024-06-24 19:04:36',2,1),(7,24,12.99,'2024-06-24 19:04:36',4,1),(9,23,7.99,'2024-06-27 22:39:07',2,1),(11,27,10,'2024-06-30 19:29:12',10,1),(12,27,10,'2024-06-30 19:41:35',11,1),(13,27,10,'2024-06-30 19:44:18',12,1),(14,27,10,'2024-06-30 19:44:18',2,1),(15,24,12.99,'2024-07-05 15:00:50',13,1),(16,24,12.99,'2024-07-05 15:01:16',14,1);
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `supplierID` int NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(100) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `contactInfo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`supplierID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (4,'ECL','Accra','0233872765'),(5,'Enapharm','Legon','0234981389'),(6,'ECL','Accra','0234265765'),(7,'Eli','Legon','08');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-07 16:12:08

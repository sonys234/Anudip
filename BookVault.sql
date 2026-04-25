-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: bookvault
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `Book_id` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(150) NOT NULL,
  `Author` varchar(100) NOT NULL,
  `Description` varchar(300) NOT NULL,
  `Genre` varchar(50) DEFAULT NULL,
  `Price` double NOT NULL,
  `Quantity` int DEFAULT '0',
  `Rating` double DEFAULT '0',
  PRIMARY KEY (`Book_id`),
  CONSTRAINT `books_chk_1` CHECK (((`Rating` >= 0.0) and (`Rating` <= 5.0)))
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (2,'Harry Potter and the Prisoner of Azkaban','J.K. Rowling','Harry learns about Sirius Black, an escaped prisoner who may be connected to his past','Fantasy',550,18,4.9),(3,'Think and Grow Rich','Napoleon Hill','Focuses on mindset, goal-setting, and persistence as keys to success.','Self-help',250,12,4.5),(4,'Harry Potter and the Deathly Hallows','J.K. Rowling','Harry, Ron, and Hermione set out to destroy Horcruxes and defeat Voldemort.','Fantasy',750,12,4.9),(5,'Wings of Fire','A.P.J. Abdul Kalam','The inspiring autobiography of India’s former President, tracing his journey from humble beginnings to a renowned scientist.','Biography',399,20,4.8),(6,'The Guide','R.K. Narayan','A classic novel about a tour guide whose life takes unexpected turns.','Fiction',320,10,4.5),(7,'Godaan','Munshi Premchand','A powerful story depicting rural life, poverty, and social injustice in India.','Fiction',299,12,4.7),(8,'Madhushala','Harivansh Rai Bachchan','A famous collection of Hindi poetry using wine as a metaphor for life and philosophy.','Poetry',200,15,4.8),(9,'Mrityunjay','Shivaji Sawant','A retelling of Karna’s life from the Mahabharata, exploring his struggles and greatness.','Mythological Fiction',400,10,4.8),(10,'Gunahon Ka Devta','Gunahon Ka Devta','A tragic love story exploring emotions, sacrifice, and societal norms.','Romance',280,12,4.7),(11,'The Catcher in the Rye','J.D. Salinger','A coming-of-age story about teenage alienation and identity.','Coming-of-age',340,11,4.2),(12,'Suraj Ka Satvan Ghoda','Dharamvir Bharati','A unique narrative exploring multiple perspectives on love and life.','Experimental Fiction',260,8,4.3),(13,'Harry Potter and the Philosopher\'s Stone','J.K. Rowling','A young boy discovers he is a wizard and begins his journey at Hogwarts School of Witchcraft and Wizardry.','Fantasy',499,25,4.9),(14,'Harry Potter and the Chamber of Secrets','J.K. Rowling','Harry returns to Hogwarts and uncovers a dark secret hidden within the school.','Fantasy',300,20,4.8),(15,'Harry Potter and the Goblet of Fire','J.K. Rowling','Harry is mysteriously entered into a dangerous magical tournament.','Fantasy',650,15,4.8),(16,'Harry Potter and the Order of the Phoenix','J.K. Rowling','Harry faces growing darkness as he forms a secret group to fight against evil forces.','Fantasy',699,12,4.7),(17,'Harry Potter and the Half-Blood Prince','J.K. Rowling','Harry discovers secrets about Voldemort’s past and a mysterious Half-Blood Prince.','Fantasy',720,10,4.8),(18,'The Adventures of Sherlock Holmes','Arthur Conan Doyle','A collection of detective stories featuring Sherlock Holmes solving mysterious and complex cases.','Mystery ',299,18,4.8),(19,'Dune','Frank Herbert','A science fiction epic set on the desert planet Arrakis.','Sci-Fi',599,20,4.9),(20,'Clean Architecture','Robert C. Martin','A Handbook of Agile Software Craftsmanship.','Technology',850.5,15,4.8);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `Cart_id` int NOT NULL AUTO_INCREMENT,
  `User_id` int DEFAULT NULL,
  `Book_id` int DEFAULT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`Cart_id`),
  KEY `User_id` (`User_id`),
  KEY `Book_id` (`Book_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`User_id`) REFERENCES `users` (`User_id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`Book_id`) REFERENCES `books` (`Book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (13,4,7,2);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `Item_id` int NOT NULL AUTO_INCREMENT,
  `Order_id` int NOT NULL,
  `Book_id` int NOT NULL,
  `Quantity` int NOT NULL,
  `Price` double NOT NULL,
  PRIMARY KEY (`Item_id`),
  KEY `Order_id` (`Order_id`),
  KEY `Book_id` (`Book_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`Order_id`) REFERENCES `orders` (`Order_id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`Book_id`) REFERENCES `books` (`Book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (2,5,18,1,299);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `Order_id` int NOT NULL AUTO_INCREMENT,
  `User_id` int DEFAULT NULL,
  `Total_bill` double DEFAULT NULL,
  `Order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Order_id`),
  KEY `User_id` (`User_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`User_id`) REFERENCES `users` (`User_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,998,'2026-04-12 17:11:02'),(2,1,1500,'2026-04-17 08:45:43'),(5,4,299,'2026-04-17 09:57:00');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `Payment_id` int NOT NULL AUTO_INCREMENT,
  `Order_id` int DEFAULT NULL,
  `Payment_method` varchar(20) DEFAULT NULL,
  `Transaction_id` varchar(50) DEFAULT NULL,
  `Status` varchar(20) DEFAULT 'Success',
  PRIMARY KEY (`Payment_id`),
  KEY `Order_id` (`Order_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`Order_id`) REFERENCES `orders` (`Order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,'UPI','TXN-95C7C40B','SUCCESS'),(2,2,'Credit Card','TXN-5FB96B65','SUCCESS'),(5,5,'Credit Card','TXN-B69C9878','SUCCESS');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `User_id` int NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(50) NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Contact` varchar(10) NOT NULL,
  `Password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`User_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Trupti','Pati','truptipatil@gmail.com','9876543210','trupti'),(2,'Alice','Carter','alicecarter@gmail.com','9675432190','alice'),(4,'Rahul','Sharma','rahul.s@email.com','7654321098','rahuls');
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

-- Dump completed on 2026-04-17 15:34:09

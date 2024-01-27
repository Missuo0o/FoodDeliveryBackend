-- MySQL dump 10.13  Distrib 8.2.0, for Linux (aarch64)
--
-- Host: localhost    Database: missuo
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `address_book`
--

DROP TABLE IF EXISTS `address_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address_book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `consignee` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `sex` varchar(1) COLLATE utf8mb3_bin NOT NULL,
  `phone` varchar(10) COLLATE utf8mb3_bin NOT NULL,
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `zip_code` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `is_default` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_book`
--

LOCK TABLES `address_book` WRITE;
/*!40000 ALTER TABLE `address_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `address_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` tinyint unsigned NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `sort` tinyint unsigned NOT NULL DEFAULT '0',
  `status` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (11,1,'Beverage',7,1,'2022-06-09 22:09:18','2022-06-09 22:09:18',1,1),(12,1,'Food',2,1,'2022-06-09 22:09:32','2024-01-24 19:04:13',1,1),(13,2,'Combo',1,1,'2022-06-09 22:11:38','2022-06-10 11:04:40',1,1),(16,1,'Fish',3,1,'2022-06-09 22:15:37','2022-08-31 14:27:25',1,1),(17,1,'Bullfrog',4,1,'2022-06-09 22:16:14','2022-08-31 14:39:44',1,1),(18,1,'Vegetable',5,1,'2022-06-09 22:17:42','2022-06-09 22:17:42',1,1),(21,1,'Soup',6,1,'2022-06-10 10:51:47','2022-06-10 10:51:47',1,1),(23,1,'test',0,0,'2024-01-25 17:10:37','2024-01-25 17:10:37',1,1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `category_id` bigint NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `image` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `status` tinyint unsigned NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (46,'Beverage1',11,6.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png','',1,'2022-06-09 22:40:47','2022-06-09 22:40:47',1,1),(47,'Beverage2',11,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png','It still tastes like my childhood',1,'2022-06-10 09:18:49','2022-06-10 09:18:49',1,1),(48,'Beer',11,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png','',1,'2022-06-10 09:22:54','2022-06-10 09:22:54',1,1),(49,'Rice',12,2.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png','Selected Rice',1,'2022-06-10 09:30:17','2022-06-10 09:30:17',1,1),(50,'Steamed bun',12,1.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png','High-quality flour',1,'2022-06-10 09:34:28','2022-06-10 09:34:28',1,1),(51,'Pickled Fish',20,56.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4a9cefba-6a74-467e-9fde-6e687ea725d7.png','Ingredients: soup, grass carp, pickled cabbage',1,'2022-06-10 09:40:51','2022-06-10 09:40:51',1,1),(52,'Pickled catfish',20,66.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/5260ff39-986c-4a97-8850-2ec8c7583efc.png','Ingredients: pickled cabbage, catfish',1,'2022-06-10 09:46:02','2022-06-10 09:46:02',1,1),(53,'Sichuan Style Poached Grass Carp',20,38.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a6953d5a-4c18-4b30-9319-4926ee77261f.png','Ingredients: grass carp, soup',1,'2022-06-10 09:48:37','2022-06-10 09:48:37',1,1),(54,'Stir fried rapeseed',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/3613d38e-5614-41c2-90ed-ff175bf50716.png','Raw material: small rapeseed',1,'2022-06-10 09:51:46','2022-06-10 09:51:46',1,1),(55,'Baby Cabbage with Garlic',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4879ed66-3860-4b28-ba14-306ac025fdec.png','Ingredients: garlic, baby cabbage',1,'2022-06-10 09:53:37','2022-06-10 09:53:37',1,1),(56,'Stir fried broccoli',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/e9ec4ba4-4b22-4fc8-9be0-4946e6aeb937.png','Ingredients: Broccoli',1,'2022-06-10 09:55:44','2022-06-10 09:55:44',1,1),(57,'Stir fried cabbage',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/22f59feb-0d44-430e-a6cd-6a49f27453ca.png','Ingredients: cabbage',1,'2022-06-10 09:58:35','2022-06-10 09:58:35',1,1),(58,'Steamed sea bass',18,98.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png','Ingredients: sea bass',1,'2022-06-10 10:12:28','2022-06-10 10:12:28',1,1),(59,'Pig Knuckle',18,138.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png','Ingredients: Pork knuckle stick',1,'2022-06-10 10:24:03','2022-06-10 10:24:03',1,1),(60,'Pork with pickled vegetables',18,58.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png','Ingredients: pork, pickles',1,'2022-06-10 10:26:03','2022-06-10 10:26:03',1,1),(61,'Fish head with chopped pepper',18,66.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png','Ingredients：Fish, chopped pepper',1,'2022-06-10 10:28:54','2022-06-10 10:28:54',1,1),(62,'Golden Soup Sauerkraut Bullfrog',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png','Ingredients: fresh bullfrog, sauerkraut',1,'2022-06-10 10:33:05','2022-06-10 10:33:05',1,1),(63,'Hot pot bullfrog',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png','Ingredients: fresh bullfrog, lotus root, green bamboo shoots',1,'2022-06-10 10:35:40','2022-06-10 10:35:40',1,1),(64,'Gluttonous bullfrog',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7a55b845-1f2b-41fa-9486-76d187ee9ee1.png','Ingredients: fresh bullfrog, loofah, soybean sprouts',1,'2022-06-10 10:37:52','2022-06-10 10:37:52',1,1),(65,'2 pounds of grass carp',16,68.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png','Ingredients: grass carp, soybean sprouts, lotus root',1,'2022-06-10 10:41:08','2022-06-10 10:41:08',1,1),(66,'2 pounds of catfish',16,72.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/8cfcc576-4b66-4a09-ac68-ad5b273c2590.png','Ingredients: catfish, soybean sprouts, lotus root',1,'2022-06-10 10:43:56','2022-06-10 10:43:56',1,1),(68,'Egg soup',21,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c09a0ee8-9d19-428d-81b9-746221824113.png','Ingredients: eggs, seaweed',1,'2022-06-10 10:54:25','2022-06-10 10:54:25',1,1),(69,'Oyster Mushroom and Tofu Soup',21,6.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/16d0a3d6-2253-4cfc-9b49-bf7bd9eb2ad2.png','Ingredients: tofu, mushrooms',1,'2022-06-10 10:55:02','2022-06-10 10:55:02',1,1);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish_flavor`
--

DROP TABLE IF EXISTS `dish_flavor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish_flavor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dish_id` bigint NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish_flavor`
--

LOCK TABLES `dish_flavor` WRITE;
/*!40000 ALTER TABLE `dish_flavor` DISABLE KEYS */;
INSERT INTO `dish_flavor` VALUES (40,10,'Sweetness','[\"No sugar\",\"Less sugar\",\"Half sugar\",\"More sugar\",\"Full sugar\"]'),(41,7,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\",\"No spicy\"]'),(42,7,'Temperature','[\"Hot drink\",\"Room temperature\",\"No iced\",\"Less iced\",\"More iced\"]'),(45,6,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\",\"No spicy\"]'),(46,6,'Spiciness','[\"Not spicy\",\"Slightly spicy\",\"Medium spicy\",\"Heavy spicy\"]'),(47,5,'Spiciness','[\"Not spicy\",\"Slightly spicy\",\"Medium spicy\",\"Heavy spicy\"]'),(48,5,'Sweetness','[\"No sugar\",\"Less sugar\",\"Half sugar\",\"More sugar\",\"Full sugar\"]'),(49,2,'Sweetness','[\"No sugar\",\"Less sugar\",\"Half sugar\",\"More sugar\",\"Full sugar\"]'),(50,4,'Sweetness','[\"No sugar\",\"Less sugar\",\"Half sugar\",\"More sugar\",\"Full sugar\"]'),(51,3,'Sweetness','[\"No sugar\",\"Less sugar\",\"Half sugar\",\"More sugar\",\"Full sugar\"]'),(52,3,'Sweetness','[\"No sugar\",\"Less sugar\",\"Half sugar\",\"More sugar\",\"Full sugar\"]'),(86,52,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\",\"No spicy\"]'),(87,52,'Spiciness','[\"Not spicy\",\"Slightly spicy\",\"Medium spicy\",\"Heavy spicy\"]'),(88,51,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\",\"No spicy\"]'),(89,51,'Spiciness','[\"Not spicy\",\"Slightly spicy\",\"Medium spicy\",\"Heavy spicy\"]'),(92,53,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\",\"No spicy\"]'),(93,53,'Spiciness','[\"Not spicy\",\"Slightly spicy\",\"Medium spicy\",\"Heavy spicy\"]'),(94,54,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\"]'),(95,56,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\",\"No spicy\"]'),(96,57,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\",\"No spicy\"]'),(97,60,'Diet','[\"No scallions\",\"No garlic\",\"No cilantro\",\"No spicy\"]'),(101,66,'Spiciness','[\"Not spicy\",\"Slightly spicy\",\"Medium spicy\",\"Heavy spicy\"]'),(102,67,'Spiciness','[\"Not spicy\",\"Slightly spicy\",\"Medium spicy\",\"Heavy spicy\"]'),(103,65,'Spiciness','[\"Not spicy\",\"Slightly spicy\",\"Medium spicy\",\"Heavy spicy\"]');
/*!40000 ALTER TABLE `dish_flavor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(12) COLLATE utf8mb3_bin NOT NULL,
  `username` varchar(20) COLLATE utf8mb3_bin NOT NULL,
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `phone` varchar(10) COLLATE utf8mb3_bin NOT NULL,
  `sex` tinyint unsigned NOT NULL,
  `id_number` varchar(9) COLLATE utf8mb3_bin NOT NULL,
  `status` tinyint unsigned NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'admin','admin','$1$ShunZhan$lrbh.EeHLA9Yyw.jsShZS0','9299950764',1,'1111',1,'2022-02-15 15:51:20','2022-02-17 09:16:20',1,1),(22,'missuo','123','$1$ShunZhan$lrbh.EeHLA9Yyw.jsShZS0','111',1,'111',1,'2024-01-24 22:47:28','2024-01-26 00:47:11',1,1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `dish_id` bigint DEFAULT NULL,
  `setmeal_id` bigint DEFAULT NULL,
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `number` int unsigned NOT NULL DEFAULT '1',
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `status` tinyint unsigned NOT NULL DEFAULT '1',
  `user_id` bigint NOT NULL,
  `address_book_id` bigint NOT NULL,
  `order_time` datetime NOT NULL,
  `checkout_time` datetime DEFAULT NULL,
  `pay_method` tinyint unsigned NOT NULL DEFAULT '1',
  `pay_status` tinyint unsigned NOT NULL DEFAULT '0',
  `amount` decimal(10,2) NOT NULL,
  `remark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `phone` varchar(10) COLLATE utf8mb3_bin NOT NULL,
  `address` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `user_name` varchar(32) COLLATE utf8mb3_bin NOT NULL,
  `consignee` varchar(32) COLLATE utf8mb3_bin NOT NULL,
  `cancel_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `rejection_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `cancel_time` datetime DEFAULT NULL,
  `estimated_delivery_time` datetime DEFAULT NULL,
  `delivery_status` tinyint unsigned NOT NULL DEFAULT '1',
  `delivery_time` datetime DEFAULT NULL,
  `pack_amount` tinyint unsigned NOT NULL,
  `tableware_number` tinyint unsigned NOT NULL,
  `tableware_status` tinyint unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setmeal`
--

DROP TABLE IF EXISTS `setmeal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setmeal` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` tinyint unsigned NOT NULL DEFAULT '1',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setmeal`
--

LOCK TABLES `setmeal` WRITE;
/*!40000 ALTER TABLE `setmeal` DISABLE KEYS */;
/*!40000 ALTER TABLE `setmeal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setmeal_dish`
--

DROP TABLE IF EXISTS `setmeal_dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setmeal_dish` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `setmeal_id` bigint NOT NULL,
  `dish_id` bigint DEFAULT NULL,
  `name` varchar(32) COLLATE utf8mb3_bin NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `copies` int unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setmeal_dish`
--

LOCK TABLES `setmeal_dish` WRITE;
/*!40000 ALTER TABLE `setmeal_dish` DISABLE KEYS */;
/*!40000 ALTER TABLE `setmeal_dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8mb3_bin NOT NULL,
  `image` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `user_id` bigint NOT NULL,
  `dish_id` bigint DEFAULT NULL,
  `setmeal_id` bigint DEFAULT NULL,
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `number` int unsigned NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_cart`
--

LOCK TABLES `shopping_cart` WRITE;
/*!40000 ALTER TABLE `shopping_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `shopping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `openid` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `name` varchar(12) COLLATE utf8mb3_bin NOT NULL,
  `phone` varchar(10) COLLATE utf8mb3_bin NOT NULL,
  `sex` tinyint unsigned NOT NULL,
  `id_number` varchar(18) COLLATE utf8mb3_bin NOT NULL,
  `avatar` varchar(500) COLLATE utf8mb3_bin NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-27 20:00:49

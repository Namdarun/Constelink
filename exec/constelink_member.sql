-- MariaDB dump 10.19  Distrib 10.11.2-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: constelink_member
-- ------------------------------------------------------
-- Server version	10.11.2-MariaDB-1:10.11.2+maria~ubu2204

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donation` (
  `donation_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `fundraising_id` bigint(20) NOT NULL,
  `donation_price` int(11) NOT NULL DEFAULT 0,
  `donation_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `donation_transaction_hash` varchar(255) DEFAULT NULL,
  `hospital_name` varchar(20) NOT NULL,
  `beneficiary_disease` varchar(50) NOT NULL,
  `fundraising_title` varchar(50) NOT NULL,
  `fundraising_thumbnail` varchar(300) DEFAULT NULL,
  `beneficiary_name` varchar(10) DEFAULT NULL,
  `beneficiary_id` bigint(20) NOT NULL,
  PRIMARY KEY (`donation_id`),
  KEY `FK_member_TO_donation_1` (`member_id`),
  CONSTRAINT `FK_member_TO_donation_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES
(2,1,1,100,'2023-03-24 15:49:30','string','삼성서울병원','다발성 경화증','string','string','윤동근',1),
(3,1,1,300,'2023-03-24 15:49:41','string','삼성서울병원','다발성 경화증','string','string','윤동근',1),
(4,1,2,500,'2023-03-24 15:49:54','string','삼성서울병원','다발성 경화증','string','string','윤동근',1),
(5,1,3,500,'2023-03-24 15:50:23','string','삼성아산병원','당근 폭식증','string','string','당근윤',2),
(6,1,3,100,'2023-03-24 15:50:29','string','삼성아산병원','당근 폭식증','string','string','당근윤',2),
(7,1,4,300,'2023-03-24 15:50:35','string','삼성아산병원','당근 폭식증','string','string','당근윤',2),
(8,2,4,300,'2023-03-24 15:54:20','string','삼성아산병원','당근 폭식증','string','string','당근윤',2),
(9,2,4,500,'2023-03-24 15:54:24','string','삼성아산병원','당근 폭식증','string','string','당근윤',2),
(10,1,1,500,'2023-03-28 05:40:26','string','string','string','string','string','string',1),
(11,1,1,500,'2023-03-28 14:48:32','string','string','string','string','string','string',0),
(12,1,1,500,'2023-03-28 05:51:28','string','string','string','string','string','string',2),
(15,1,1,500,'2023-03-28 06:03:47','string','string','string','string','string','string',2),
(16,1,1,500,'2023-03-28 15:04:35','string','string','string','string','string','string',1),
(17,16,1,100,'2023-03-29 09:06:37','13ffe','당근병원','아퍼','진짜아퍼','너무아퍼','김당근',1),
(18,4,2,100000,'2023-03-30 17:38:08','rr2r2e','동디리당깅병원','다발성경화증','너무아픔','ㄹㅈㄹㅈ','동근윤',1),
(19,4,1,0,'2023-03-30 17:38:10','2r12r','당디리동딩병원','아프다','진짜아프다','ㄹㄹㄷㄹㄷ','윤둔근',1),
(24,1,2,50000,'2023-04-04 22:36:39','string','string','string','string','string','string',1),
(25,1,3,200000,'2023-04-05 00:43:08','0xca4e2096988b1a08955c04dc28c8b19c293da4401c729fe876c1fd894e91c4dc','도른자 병원','천식','간염 수술','https://cdn.pixabay.com/photo/2017/01/25/11/31/beggar-2007647__340.jpg','차무식',2);
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `member_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `member_profile_img` varchar(300) DEFAULT NULL,
  `member_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `role` varchar(20) NOT NULL COMMENT 'USER, ADMIN, HOSPITAL',
  `social_id` varchar(100) NOT NULL,
  `member_total_amount_raised` int(11) NOT NULL DEFAULT 0,
  `member_point` int(11) NOT NULL DEFAULT 0,
  `member_inactive` tinyint(1) NOT NULL DEFAULT 0 COMMENT '회원 탈퇴시 true',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES
(6,'케빈','xpoxxxxxxx@naver.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','MEMBER','234524534245',0,0,0),
(7,'존','qqqqqqqqqqc@daum.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','MEMBER','3456453656',0,0,0),
(8,'톰','nenenenss@nexon.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','MEMBER','67876867876',0,0,0),
(9,'에일리','eduplus213@ssafy.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','MEMBER','4567567657',0,0,0),
(10,'아이유','pp03929@naver.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','HOSPITAL','67868768',0,0,0),
(11,'김원장','cxvxvr@daum.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','HOSPITAL','67676577',0,0,0),
(12,'박의사','qpqp9292@naver.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','HOSPITAL','38879879',0,0,0),
(13,'백과장','sdfxcvsdf@hanmail.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','HOSPITAL','12345098',0,0,0),
(14,'김과장','sdfcvxcv@google.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','HOSPITAL','345668890',0,0,0),
(15,'박과장','vxcwe@naver.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','HOSPITAL','2456688078',0,0,0),
(16,'성형왕','vxcqqqqq@daum.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','MEMBER','98783546',0,0,0),
(21,'수혜자없는병원','pspxpxll@kkkk.com','http://k.kakaocdn.net/dn/cRNaZX/btrZ72jjZMr/RaYGo77QRJei5J6rZWWQ9K/img_640x640.jpg','2023-03-17 05:48:34','HOSPITAL','1231232323',0,0,0),
(28,'elePhant PinC','pincelephant@gmail.com','https://lh3.googleusercontent.com/a/AGNmyxZ_RXUh0Ft4O6MmXWfzbKN5O9gxCWsPmNhGzfRm=s96-c','2023-04-06 03:19:07','MEMBER','111812634396917714552',0,0,0);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 15:10:20

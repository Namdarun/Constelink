-- MariaDB dump 10.19  Distrib 10.11.2-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: constelink_beneficiary
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
-- Table structure for table `beneficiary`
--

DROP TABLE IF EXISTS `beneficiary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beneficiary` (
  `beneficiary_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hospital_id` bigint(20) NOT NULL COMMENT '권한이 병원으로 전환된 회원의 번호를 저장',
  `beneficiary_name` varchar(10) NOT NULL,
  `beneficiary_birthday` date NOT NULL DEFAULT curdate(),
  `beneficiary_disease` varchar(50) NOT NULL,
  `beneficiary_photo` varchar(300) DEFAULT NULL COMMENT '이미지 링크',
  `beneficiary_amount_raised` int(11) NOT NULL DEFAULT 0,
  `beneficiary_amount_goal` int(11) NOT NULL DEFAULT 0,
  `beneficiary_status` varchar(20) NOT NULL COMMENT 'RAISING: 모금중, RECOVERING : 회복중,  DONE : 완료',
  PRIMARY KEY (`beneficiary_id`),
  KEY `FK_hospital_TO_beneficiary_1` (`hospital_id`),
  CONSTRAINT `FK_hospital_TO_beneficiary_1` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`hospital_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beneficiary`
--

LOCK TABLES `beneficiary` WRITE;
/*!40000 ALTER TABLE `beneficiary` DISABLE KEYS */;
INSERT INTO `beneficiary` VALUES
(1,3,'차무식','2001-03-13','천식','https://cdn.pixabay.com/photo/2015/08/20/17/23/community-897760__340.jpg',150000,2000000,'DONE'),
(2,3,'오승훈','1983-03-13','가래','https://cdn.pixabay.com/photo/2015/02/19/00/38/peru-641632__340.jpg',2000,2000000,'RECOVERING'),
(3,10,'양정팔','1980-03-13','기침','https://cdn.pixabay.com/photo/2019/09/21/09/07/banana-4493420__340.jpg',350000,2000000,'RAISING'),
(6,3,'이상구','1988-03-13','탈모','https://cdn.pixabay.com/photo/2020/12/27/09/13/bangkok-5863391_960_720.jpg',3500000,2000000,'DONE'),
(11,3,'마크','2000-03-13','무좀','https://cdn.pixabay.com/photo/2017/10/22/21/13/old-man-2879303__340.jpg',150000,2000000,'DONE'),
(45,3,'문동은','2023-03-29','복수','https://cdn.pixabay.com/photo/2020/10/06/11/55/woman-5632026__340.jpg',0,4444,'RAISING'),
(46,10,'문동은','1992-03-04','복수','https://cdn.pixabay.com/photo/2015/06/19/09/39/lonely-814631__340.jpg',0,444444444,'RAISING'),
(47,10,'전재준','1992-03-27','적녹색약','https://cdn.pixabay.com/photo/2021/02/18/21/05/woman-6028423__340.jpg',0,20202000,'RAISING'),
(48,3,'문동은','2023-03-29','복수','https://cdn.pixabay.com/photo/2018/01/31/09/42/people-3120717__340.jpg',0,4444,'RAISING'),
(49,3,'김감겅','2020-03-29','콜록콜록','https://cdn.pixabay.com/photo/2023/03/21/10/30/crab-7866915__340.jpg',0,3000000,'RAISING'),
(50,3,'김독수','2006-02-28','볼걸이','https://cdn.pixabay.com/photo/2023/03/21/10/30/crab-7866915__340.jpg',0,1500000,'RAISING'),
(51,10,'문동은','2023-03-29','복수','http://photo.com',0,4444,'RAISING'),
(52,10,'문동은','2023-03-29','복수','http://photo.com',0,4444,'RAISING'),
(53,3,'함분움','2006-02-28','변비','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,1800000,'RAISING'),
(54,10,'전전전','2006-02-28','재재재','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(55,11,'검굠겜','2006-02-28','굼윔밈','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(56,11,'검굠겜','2006-02-28','굼윔밈','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(57,10,'전전전','2006-02-28','우아아아','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(58,10,'','2023-03-29','','',0,0,'RAISING'),
(59,10,'','2023-03-29','','',0,0,'RAISING'),
(60,10,'','2023-03-29','','',0,0,'RAISING'),
(61,10,'전전전','2006-02-28','재재재','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(62,10,'전전전','2006-02-28','재재재','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(63,10,'전전전','2006-02-28','재재재','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(64,10,'최','2023-03-07','아','blob:http://localhost:3000/8028b036-6503-432d-ad47-2773364cae16',0,2,'RAISING'),
(65,10,'','2023-03-29','','',0,0,'RAISING'),
(66,10,'','2023-03-29','','',0,0,'RAISING'),
(67,11,'검굠겜','2006-02-28','굼윔밈','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(68,11,'검굠겜','2006-02-28','굼윔밈','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(69,11,'검굠겜','2006-02-28','굼윔밈','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(70,11,'검굠겜','2006-02-28','굼윔밈','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe18dcf54-cdbc-4540-89d4-549989845bb4-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680061693308470&alt=media',0,2000000,'RAISING'),
(71,10,'','2023-03-29','','',0,0,'RAISING'),
(72,10,'최혜정','1992-03-10','목구멍','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fb263f712-9691-44db-bcb6-33a38f7c6d4e-%EC%B5%9C%ED%98%9C%EC%A9%A1.png?generation=1680067719859660&alt=media',0,1,'RAISING'),
(73,10,'','2023-03-29','','',0,0,'RAISING'),
(74,10,'손명오','2008-02-26','학교폭력','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fbe6c3f2c-1c96-4819-a27a-d8ae7fcf06df-%EC%86%90%EB%AA%85%EC%98%A4.png?generation=1680068509337151&alt=media',0,10000000,'RAISING'),
(75,10,'','2023-03-29','','',0,0,'RAISING'),
(76,10,'','2023-03-30','','',0,0,'RAISING'),
(77,10,'','2023-03-30','','',0,0,'RAISING'),
(78,10,'','2023-03-30','','',0,0,'RAISING'),
(79,10,'','2023-03-31','','',0,0,'RAISING'),
(80,10,'','2023-04-03','','',0,0,'RAISING'),
(81,10,'','2023-04-03','','',0,0,'RAISING'),
(82,10,'','2023-04-03','','',0,0,'RAISING'),
(83,10,'','2023-04-03','','',0,0,'RAISING'),
(84,10,'','2023-04-03','','',0,0,'RAISING'),
(85,10,'','2023-04-03','','',0,0,'RAISING'),
(86,4,'string','2023-04-04','string','string',0,50000,'RAISING'),
(87,10,'오시멘','2023-04-05','string','string',0,2000000,'RAISING'),
(88,10,'오시멘','1989-01-11','부상 챔스 결장','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F5b479058-a462-4d30-babf-c0cf45b30cb1-%EC%98%A4%EC%8B%9C%EB%A9%98.jpg?generation=1680655292649523&alt=media',0,200000000,'RAISING'),
(89,10,'오시멘','1989-01-11','부상 챔스 결장','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fa91a7bd4-694c-47cb-afac-1bcac20de35e-%EC%98%A4%EC%8B%9C%EB%A9%98.jpg?generation=1680655589125696&alt=media',0,200000000,'RAISING'),
(90,10,'','2023-04-05','','',0,0,'RAISING'),
(91,10,'','2023-04-05','','',0,0,'RAISING'),
(92,10,'','2023-04-05','','',0,0,'RAISING'),
(93,10,'박지성','1986-09-17','무릎 많이 아픔','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fa06b5bac-34c0-4fe6-b207-9ed111b29220-%EC%9C%84%EC%88%AD%EB%B9%A0%EB%A0%88.jpg?generation=1680656361346957&alt=media',0,2000000,'RAISING'),
(94,10,'김민재','1996-09-18','손흥민 언팔로우','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F58a51b81-dbc8-423b-90f2-623f3568cdd2-%EA%B9%80%EB%AF%BC%EC%9E%AC.jpg?generation=1680657494479269&alt=media',0,1000000,'RAISING'),
(95,21,'오시멘','2001-01-01','무릎 부상 챔스 결장','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fe3cb1dbf-10ff-4657-bb46-a90f5d6225fe-%EC%98%A4%EC%8B%9C%EB%A9%98.jpg?generation=1680660076651657&alt=media',0,10000000,'RAISING'),
(96,21,'박지성','2002-02-22','콩콩','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fd9b48ed1-1344-4c4b-8a0e-afb86bf43654-%EC%9C%84%EC%88%AD%EB%B9%A0%EB%A0%88.jpg?generation=1680660192469578&alt=media',0,20000000,'RAISING'),
(97,21,'해리케인','2003-03-03','무관','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F27c6e6aa-0159-4626-b67a-7abec87c22e6-%ED%95%B4%EB%A6%AC%EC%BC%80%EC%9D%B8.jpg?generation=1680660223806905&alt=media',0,300000,'RAISING'),
(98,21,'흐비차','2004-04-04','카테고리 넣어야 함','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F5250ae97-49b2-4190-9dc9-ce36235b4332-%ED%9D%90%EB%B9%84%EC%B0%A8.jpg?generation=1680660266269342&alt=media',0,40000000,'RAISING'),
(99,21,'김민재','2005-05-05','손흥민 언팔로우','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F7b3e7546-793e-441c-a373-04b42134185d-%EA%B9%80%EB%AF%BC%EC%9E%AC.jpg?generation=1680660294159795&alt=media',0,50000,'RAISING'),
(100,21,'식스맨','2006-06-06','여섯번째도 나와야 함','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Faaf26968-a60b-4e59-917f-856b12954ec6-%EC%A0%84%EC%9E%AC%EC%A4%80.png?generation=1680660335626596&alt=media',0,600000000,'RAISING'),
(101,30,'차무식','1984-04-04','구강암','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fbab269a4-6f10-4a60-8d01-7f98ed71cd68-%EC%B0%A8%EB%AC%B4%EC%8B%9D.jpg?generation=1680758929119316&alt=media',0,10000000,'RAISING'),
(102,20,'정원철','2023-04-03','간암','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fb9283c6b-811c-4596-9e33-373144a51ebe-ADE7554F-4ABD-4480-AD97-5894FCD4CC77-2484-00000117E4B877F7.png?generation=1680762980048639&alt=media',0,2000000,'RAISING'),
(103,20,'정진호','1976-04-16','요로결석','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F0d7332e7-ff72-472c-b300-ca928635f0a6-%EC%A0%95%EC%9B%90%EC%B2%A0-1.jpg?generation=1680786694220732&alt=media',0,4000000,'RAISING'),
(104,20,'정다니엘','1992-04-23','골다공증','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F4d5975c4-fc9c-42b2-9c8d-da188486cf8d-090030.jpg?generation=1680790477734988&alt=media',0,2000000,'RAISING'),
(105,20,'감가리엘','2023-04-06','혈액암','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F0244756a-f799-49c4-b450-63a548de5ce6-090004.jpg?generation=1680790543064739&alt=media',0,4500000,'RAISING'),
(106,20,'정발산','1982-04-16','혈액암','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fc03488c1-3f3a-4fb3-9be4-89ae39f60ca1-090005.jpg?generation=1680790573346851&alt=media',0,6500000,'RAISING'),
(107,20,'두가인','1967-04-20','루게릭병','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fab12788f-2b3a-40a5-b40e-6012e8c5cfdd-090013.jpg?generation=1680790637354125&alt=media',0,7700000,'RAISING');
/*!40000 ALTER TABLE `beneficiary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital`
--

DROP TABLE IF EXISTS `hospital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospital` (
  `hospital_id` bigint(20) NOT NULL COMMENT '권한이 병원으로 전환된 회원의 번호를 저장',
  `hospital_name` varchar(20) NOT NULL,
  `hospital_total_amount_raised` int(11) NOT NULL DEFAULT 0 COMMENT '여태까지 병원에 모금된 금액',
  `hospital_total_beneficiary` int(11) NOT NULL DEFAULT 0 COMMENT '여태까지 병원에 등록된 수혜자수',
  `hospital_wallet_address` varchar(100) DEFAULT NULL,
  `hospital_link` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`hospital_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital`
--

LOCK TABLES `hospital` WRITE;
/*!40000 ALTER TABLE `hospital` DISABLE KEYS */;
INSERT INTO `hospital` VALUES
(3,'도른자 병원',5000000,3000000,'0x1234123412341234','https://www.naver.com/'),
(4,'헬스외과',200000,50000,'0x1234123412341234','https://www.naver.com/'),
(10,'킴스 클럽외과',5000000,1253000,'0x1234123412341234','https://www.naver.com/'),
(11,'김 정형외과',6000000,3500000,'0x1234123412341234','https://www.naver.com/'),
(12,'아프지 않는 우리 치과',2000000,900000,'0x1234123412341234','https://www.naver.com/'),
(13,'잘하는 의원',3000000,2100000,'0x1234123412341234','https://www.naver.com/'),
(14,'김 신경외과',1000000,100000,'0x1234123412341234','https://www.naver.com/'),
(15,'신의 정형외과',300000,240000,'0x1234123412341234','https://www.naver.com/'),
(17,'바람의 피부과',100000,50000,'0x1234123412341234','https://www.naver.com/'),
(20,'정엔투엔철 병원',0,0,'0x756768595','https://www.nvar.com'),
(21,'수혜자 없는 병원',0,0,'0x1234123412341234','https://www.naver.com/'),
(30,'중앙 보훈 병원',0,0,'0x11111','https://www.naver.com/');
/*!40000 ALTER TABLE `hospital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recovery_diary`
--

DROP TABLE IF EXISTS `recovery_diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recovery_diary` (
  `recovery_diary_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `beneficiary_id` bigint(20) NOT NULL,
  `recovery_diary_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `recovery_diary_photo` varchar(300) DEFAULT NULL,
  `recovery_diary_content` longtext NOT NULL,
  `recovery_diary_amount_spent` int(11) NOT NULL DEFAULT 0,
  `recovery_diary_title` varchar(50) NOT NULL,
  PRIMARY KEY (`recovery_diary_id`),
  KEY `FK_beneficiary_TO_recovery_diary_1` (`beneficiary_id`),
  CONSTRAINT `FK_beneficiary_TO_recovery_diary_1` FOREIGN KEY (`beneficiary_id`) REFERENCES `beneficiary` (`beneficiary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recovery_diary`
--

LOCK TABLES `recovery_diary` WRITE;
/*!40000 ALTER TABLE `recovery_diary` DISABLE KEYS */;
INSERT INTO `recovery_diary` VALUES
(1,1,'2023-03-19 14:27:20','https://t3.ftcdn.net/jpg/02/33/37/14/240_F_233371456_GX1MSkwyzCrt6rCp6FnCI7oGjVs0uvo1.jpg','내용을 적는다',1000,'일기 제목1'),
(2,1,'2023-03-16 07:28:12','https://t3.ftcdn.net/jpg/02/33/37/14/240_F_233371456_GX1MSkwyzCrt6rCp6FnCI7oGjVs0uvo1.jpg','2번째 내용',2000,'일기 제목2'),
(3,2,'2023-03-28 19:32:18','https://t3.ftcdn.net/jpg/02/33/37/14/240_F_233371456_GX1MSkwyzCrt6rCp6FnCI7oGjVs0uvo1.jpg','때린다',1000,'3'),
(4,3,'2023-03-08 20:28:44','https://t3.ftcdn.net/jpg/02/33/37/14/240_F_233371456_GX1MSkwyzCrt6rCp6FnCI7oGjVs0uvo1.jpg','화가난다',3000,'일기 4'),
(6,1,'2023-01-13 11:28:52','https://t3.ftcdn.net/jpg/02/33/37/14/240_F_233371456_GX1MSkwyzCrt6rCp6FnCI7oGjVs0uvo1.jpg','내용',4000,'일기 6'),
(7,1,'2023-02-24 02:08:49','string','string',1,'string'),
(8,2,'2023-03-24 01:09:08','string','string',1,'string'),
(9,1,'2023-03-29 08:04:42','string','string',0,'string'),
(10,1,'2023-03-29 08:04:55','string','string',0,'string'),
(11,1,'2023-03-29 08:05:02','string','string',0,'string'),
(12,1,'2023-03-29 08:08:21','string','string',0,'string'),
(13,2,'2023-03-29 08:12:04','string','string',0,'string'),
(14,3,'2023-03-29 08:12:08','string','string',0,'string'),
(15,11,'2023-03-29 08:12:11','string','string',0,'string'),
(35,1,'2023-03-30 10:55:04','사진','살았다',1,'제목'),
(39,45,'2023-03-30 10:56:43','http://hello','제목입니다2',1000,'제목입니다'),
(40,3,'2023-03-30 10:56:50','string','string',100,'string'),
(41,1,'2023-03-30 10:57:32','string','string',100,'string'),
(42,47,'2023-03-30 11:10:36','사진','내용',1,'제목'),
(43,46,'2023-03-30 11:33:50','사진','내용',1,'제목'),
(44,1,'2023-03-30 11:33:50','사진','내용',1,'제목'),
(45,48,'2023-03-30 11:33:50','사진','내용',1,'제목'),
(46,1,'2023-03-30 11:33:50','사진','내용',1,'제목'),
(47,1,'2023-03-30 11:33:51','사진','내용',1,'제목'),
(48,1,'2023-03-30 11:36:35','<p>sadf</p>','<p>sadf</p>',0,'asdf'),
(49,1,'2023-03-30 11:50:21','<p>gg</p>','<p>gg</p>',0,'gg'),
(50,49,'2023-03-30 12:20:48','string','string',100,'string'),
(51,1,'2023-03-30 12:23:06','<p>gg</p>','<p>gg</p>',0,'gg'),
(52,1,'2023-03-30 12:27:10','<p>gg</p>','<p>gg</p>',0,'gg'),
(53,52,'2023-03-30 12:27:21','<p>gg</p>','<p>gg</p>',0,'gg'),
(54,2,'2023-03-30 12:27:40','<p>gg</p>','<p>gg</p>',0,'gg'),
(55,2,'2023-03-30 12:28:05','<p>bb</p>','<p>bb</p>',0,'bb'),
(56,6,'2023-03-30 12:29:18','<p>gg</p>','<p>gg</p>',0,'gg'),
(57,6,'2023-03-30 12:30:19','<p>bb</p>','<p>bb</p>',0,'bb'),
(58,6,'2023-03-30 12:32:48','<p>b</p>','<p>b</p>',0,'bb'),
(59,6,'2023-03-30 12:34:25','<p>bb</p>','<p>bb</p>',0,'bb'),
(60,6,'2023-03-30 12:34:50','<p>gg</p>','<p>gg</p>',0,'gg'),
(61,11,'2023-03-30 12:35:12','<p>테스트</p>','<p>테스트</p>',0,'테스트'),
(62,11,'2023-03-30 12:39:10','<p>??</p>','<p>??</p>',0,'??'),
(63,11,'2023-03-30 12:39:17','<p>??????????</p>','<p>??????????</p>',0,'???????'),
(64,11,'2023-03-30 12:39:42','<p>ㅁㄴㅇㄻㄴㅇㄹ</p>','<p>ㅁㄴㅇㄻㄴㅇㄹ</p>',0,'ㅁㄴㅇㄻㄴㄹ'),
(65,11,'2023-03-30 12:39:50','<p>ㅁㄴㅇㄻㄴㅇㄹ</p>','<p>ㅁㄴㅇㄻㄴㅇㄹ</p>',0,'ㅎㅎ'),
(66,11,'2023-03-30 12:39:55','<p>ㅠㅠ</p>','<p>ㅠㅠ</p>',0,'ㅠㅠ'),
(67,11,'2023-03-30 12:40:16','<p>ㅠㅠ</p>','<p>ㅠㅠ</p>',0,'ㅋㅋ'),
(68,11,'2023-03-30 12:40:22','<p>ㅠㅠ</p>','<p>ㅠㅠ</p>',0,'ㅋㅋ'),
(69,1,'2023-03-30 12:41:24','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(70,1,'2023-03-30 12:41:25','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(71,1,'2023-03-30 12:41:30','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(72,1,'2023-03-30 12:41:35','<p>ㅋㅋ</p>','<p>ㅋㅋ</p>',0,'ㅋㅋ'),
(73,1,'2023-03-30 13:09:22','<p>gg</p>','<p>gg</p>',0,'gg'),
(74,1,'2023-03-30 13:09:30','<p>텟으트야</p>','<p>텟으트야</p>',0,'테스트'),
(75,53,'2023-03-30 13:09:39','<p>텟으트야</p>','<p>텟으트야</p>',0,'ㅎㅎ'),
(76,1,'2023-03-30 13:10:09','<p>테스트</p>','<p>테스트</p>',0,'테스트'),
(77,1,'2023-03-30 13:10:16','<p>최종</p>','<p>최종</p>',0,'최종'),
(78,1,'2023-03-30 13:10:27','<p>테스트2</p>','<p>테스트2</p>',0,'테스트1'),
(79,1,'2023-03-30 13:11:04','<p>테스트1</p>','<p>테스트1</p>',0,'테스트1'),
(80,51,'2023-03-30 13:11:14','<p>테스트2</p>','<p>테스트2</p>',0,'테스트2'),
(81,1,'2023-03-30 13:11:28','<p>테스트3</p>','<p>테스트3</p>',0,'테스트3'),
(82,1,'2023-03-30 13:11:47','<p>테스트4</p>','<p>테스트4</p>',0,'테스트4'),
(83,54,'2023-03-30 13:26:25','<p>bb</p>','<p>bb</p>',0,'bb'),
(84,1,'2023-03-30 13:26:57','<p>ㅂㅈㄷㄱ</p>','<p>ㅂㅈㄷㄱ</p>',0,'테스트1'),
(85,1,'2023-03-30 13:35:39','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(86,1,'2023-03-30 13:36:30','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'86번째 테스트'),
(87,1,'2023-03-30 13:37:05','<p>ㅠㅠ</p>','<p>ㅠㅠ</p>',0,'ㅠㅠ'),
(88,54,'2023-03-30 13:37:33','<p>요청</p>','<p>요청</p>',0,'요청'),
(89,1,'2023-03-30 13:42:08','<p>ㅎ믛므</p>','<p>ㅎ믛므</p>',0,'흠ㅎ므'),
(90,1,'2023-03-30 13:44:17','<p>테스트야~</p>','<p>테스트야~</p>',0,'90번째 글'),
(91,1,'2023-03-30 13:44:56','<p>테스트야~</p>','<p>테스트야~</p>',0,'91번째글'),
(92,1,'2023-03-30 13:55:17','<p>gg</p>','<p>gg</p>',0,'gg'),
(93,1,'2023-03-30 13:55:34','<p>???</p>','<p>???</p>',0,'93번째글'),
(94,1,'2023-03-30 14:08:20','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(95,1,'2023-03-30 14:08:44','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(96,1,'2023-03-30 14:08:50','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎㅎ'),
(97,1,'2023-03-30 14:09:14','<p>??</p>','<p>??</p>',0,'97번째'),
(98,1,'2023-03-30 14:09:30','<p>98</p>','<p>98</p>',0,'98'),
(99,2,'2023-03-30 14:15:29','<p>??</p>','<p>??</p>',0,'99번째글'),
(100,2,'2023-03-30 14:15:50','<p>???</p>','<p>???</p>',0,'100번째'),
(101,55,'2023-03-30 14:16:20','<p>101</p>','<p>101</p>',0,'101번째글'),
(102,2,'2023-03-30 14:18:39','<p>102</p>','<p>102</p>',0,'102글'),
(103,45,'2023-03-30 14:21:24','<p>101</p>','<p>101</p>',0,'101'),
(104,1,'2023-03-30 14:21:31','<p>104</p>','<p>104</p>',0,'104'),
(105,1,'2023-03-30 14:21:48','<p>105</p>','<p>105</p>',0,'105'),
(106,1,'2023-03-30 14:22:05','<p>106</p>','<p>106</p>',0,'106'),
(107,1,'2023-03-30 14:22:17','<p>제목</p>','<p>제목</p>',0,'제목이랑'),
(108,1,'2023-03-30 14:22:58','<p>108</p>','<p>108</p>',0,'108'),
(109,1,'2023-03-30 14:23:10','<p>109</p>','<p>109</p>',0,'109'),
(110,2,'2023-03-30 14:23:50','<p>생성</p>','<p>생성</p>',0,'몇번째'),
(111,2,'2023-03-30 14:24:34','<p>테스트</p>','<p>테스트</p>',0,'생성'),
(112,2,'2023-03-30 14:25:09','<p>안녕</p>','<p>안녕</p>',0,'안녕'),
(113,2,'2023-03-30 14:25:30','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(114,1,'2023-03-30 14:25:44','<p>테스트</p>','<p>테스트</p>',0,'테스트'),
(115,1,'2023-03-30 14:27:11','<p>안녕</p>','<p>안녕</p>',0,'안녕'),
(116,1,'2023-03-30 14:28:01','<p>흠?</p>','<p>흠?</p>',0,'흠?'),
(117,1,'2023-03-30 14:37:14','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(118,1,'2023-03-30 14:37:58','<p>테스트</p>','<p>테스트</p>',0,'119'),
(119,1,'2023-03-30 14:40:56','<p>asdfasdf</p><p>asdf</p>','<p>asdfasdf</p><p>asdf</p>',0,'aasdf'),
(120,1,'2023-03-30 14:41:11','<p>생성</p>','<p>생성</p>',0,'생성'),
(121,1,'2023-03-30 14:41:27','<p>ㅎㅎ</p>','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(122,1,'2023-03-30 15:19:57','','<p>asdf</p>',0,'dgsdg'),
(123,1,'2023-03-30 15:20:09','','<p>gg</p>',0,'gg'),
(124,1,'2023-03-30 15:22:50','','<p>이렇게 적어봐~</p>',0,'gg'),
(125,1,'2023-03-30 15:23:43','','<p>ㅁㄴㅇㄹ</p>',0,'ㅁㄴㅇㄹ'),
(126,1,'2023-03-30 15:24:38','','<p>ㅁㄴㅇㄹ</p>',0,'ㅁㄴㅇㄹ'),
(127,1,'2023-03-30 15:25:01','','<p>ㅁㄴㅇㄹ</p>',0,'ㅎㅎ'),
(128,1,'2023-03-30 15:27:08','','<p>asdf</p>',0,'asfd'),
(129,1,'2023-03-30 15:28:04','','<p>asdf</p>',0,'gg'),
(130,1,'2023-03-30 15:30:22','','<p>asgasdg</p>',0,'asdg'),
(131,1,'2023-03-30 15:30:31','','<p>asgasdg</p>',0,'asdf'),
(132,1,'2023-03-30 15:31:01','','<p>&nbsp;테스트<br></p>',0,'테스트'),
(133,1,'2023-03-30 15:38:17','','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(134,3,'2023-03-30 15:46:16','','<p>gg</p>',0,'gg'),
(135,2,'2023-03-30 15:49:41','','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(136,2,'2023-03-30 15:49:44','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F13ab1a33-8038-480d-8f44-4d2f133f432c-123.jpg?generation=1680158983672823&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(137,2,'2023-03-30 15:49:45','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F9de289c7-ad6d-4dfa-91c2-5cbb284be552-123.jpg?generation=1680158985012108&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(138,2,'2023-03-30 15:49:46','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fcafb8630-ae39-4815-8fba-89d33ff8a6f1-123.jpg?generation=1680158986098285&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(139,2,'2023-03-30 15:49:47','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F95f8c30c-5516-4730-b400-dcec33df7b50-123.jpg?generation=1680158987187987&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(140,2,'2023-03-30 15:49:48','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F947ffa82-9a86-4423-a69e-5c952741e4ba-123.jpg?generation=1680158988233828&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(141,2,'2023-03-30 15:49:49','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F06cfdb0f-f75b-4fa4-97ad-359358f6a1ca-123.jpg?generation=1680158989285883&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(142,2,'2023-03-30 15:49:50','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fa007ae44-e229-49a5-a0bf-04a057d2c46a-123.jpg?generation=1680158990403200&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(143,2,'2023-03-30 15:49:51','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fc9de1222-7d45-45e9-82cd-a8fbefe62d88-123.jpg?generation=1680158991599559&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(144,2,'2023-03-30 15:49:53','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F3c6c6b64-7b0a-426f-89d8-542b92b1a125-123.jpg?generation=1680158992705935&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(145,2,'2023-03-30 15:49:54','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F56632f7d-eef0-4406-9b29-98e862d5948c-123.jpg?generation=1680158993875281&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(146,2,'2023-03-30 15:49:55','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F03b8d398-82e7-4600-91a1-a4c78f92831d-123.jpg?generation=1680158995009922&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(147,2,'2023-03-30 15:49:56','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F764edbc8-9208-41e1-856c-b9ef2bde8a8b-123.jpg?generation=1680158996085963&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(148,2,'2023-03-30 15:49:57','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F0f4fa257-2040-4d75-81ed-04ee669c30e2-123.jpg?generation=1680158997111182&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(149,2,'2023-03-30 15:49:58','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F2bbcda83-5df1-4a95-a446-88e3d6486501-123.jpg?generation=1680158998178880&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(150,2,'2023-03-30 15:49:59','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fcd1edb0d-15e2-4559-adca-ded8f35ea1ac-123.jpg?generation=1680158999314874&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(151,2,'2023-03-30 15:50:00','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fb9bcfe51-4d1f-4c1d-b62d-91fd43fa7cca-123.jpg?generation=1680159000348700&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(152,2,'2023-03-30 15:50:02','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Ff8379482-25a2-4fe9-b989-8e27e1966edd-123.jpg?generation=1680159001538610&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(153,1,'2023-03-30 15:52:54','','<p>힘들어</p>',0,'사진좀 보여줘'),
(154,1,'2023-03-30 15:54:05','','<p>ㅁㄴㅇㄹ</p>',0,'초기화 테스트'),
(155,1,'2023-03-30 15:55:16','','<p>ㅁㄴㅇㄹ</p>',0,'초기화 테스트'),
(156,1,'2023-03-30 15:55:35','','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(157,1,'2023-03-30 15:55:36','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fded1196f-bb5e-40e8-88a2-8affa01523a7-123.jpg?generation=1680159336332387&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(158,1,'2023-03-30 15:55:37','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F08d9e47a-075d-408f-b6f2-c28d3d73b53a-123.jpg?generation=1680159337338654&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(159,1,'2023-03-30 15:55:38','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fba78ba27-96ba-40dc-bc79-4f0ef4d16c2e-123.jpg?generation=1680159338395012&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(160,1,'2023-03-30 15:55:39','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F0144aae2-2afb-4a13-a988-4f66e450173a-123.jpg?generation=1680159339406436&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(161,1,'2023-03-30 15:55:41','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fcadba810-5635-406e-bf03-823c746756e6-123.jpg?generation=1680159340654692&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(162,1,'2023-03-30 15:55:41','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F5a43466e-90c1-4a14-a9cc-9a7a780404ea-123.jpg?generation=1680159341765788&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(163,1,'2023-03-30 15:55:43','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Ffbd73fbd-c325-4441-9a6d-5f9f4b7b9dc8-123.jpg?generation=1680159343097410&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(164,1,'2023-03-30 15:55:44','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Faf584c80-5741-424e-b36b-4874a61e754c-123.jpg?generation=1680159344299010&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(165,1,'2023-03-30 15:55:45','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F184d90fe-cbf0-463c-af06-35b32970059a-123.jpg?generation=1680159345337357&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(166,1,'2023-03-30 15:59:04','','<p>ㅎㅎ</p>',0,'테스트!'),
(167,1,'2023-03-30 15:59:35','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fee02ed19-a021-4f68-a2f4-69e933dbda19-8%EA%B8%B0_%EC%84%9C%EC%9A%B8_%EB%82%A8%EA%B8%B0%EC%84%B1.jpg?generation=1680159545599165&alt=media','<p>ㅎㅎ</p>',0,'테스트!'),
(168,1,'2023-03-30 15:59:51','','<p>ㅎㅇ</p>',0,'ㅇㅎㅇ'),
(169,1,'2023-03-30 16:00:08','','<p>ㅎㅇ</p>',0,'ㅎㅇㅎㅇㅎㅇ'),
(170,1,'2023-03-30 16:00:33','','<p>ㅁㄴㅇㄹ</p>',0,'ㅁㄴㅇㄹ'),
(171,1,'2023-03-30 16:00:53','','<p>가고싶어</p>',0,'안녕'),
(172,3,'2023-03-30 17:14:08','','<p>ㅎㅎ</p>',0,'생성해보자'),
(173,1,'2023-03-30 17:17:51','','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(174,1,'2023-03-30 17:21:53','','<p>&nbsp; 테스트<br></p>',0,'테스트'),
(175,1,'2023-03-30 17:46:20','','<p>asdf</p>',0,'gg'),
(176,1,'2023-03-30 17:46:35','','<p>테스트</p>',0,'테스트'),
(177,2,'2023-03-30 21:43:08','','<p>ㅎㅎㅎㅎ</p>',0,'마지막테스트'),
(178,1,'2023-03-31 08:42:29','','<p>ㅎㅎㅎ</p>',0,'안녕?'),
(179,1,'2023-03-31 08:42:51','','<p>ㅁㄴㅇㄹㄹ</p>',0,'안녕!'),
(180,1,'2023-03-31 08:43:55','','<p>ㄱ</p>',0,'ㄱㄱ'),
(181,1,'2023-03-31 08:44:12','','<p>테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트</p>',0,'테스트'),
(182,3,'2023-03-31 10:31:58','','<p>ㅎㅎ</p>',0,'테스트'),
(183,3,'2023-03-31 10:43:43','','<p>안녕</p>',0,'ㅠㅠ'),
(184,3,'2023-03-31 10:46:09','','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(185,3,'2023-03-31 10:53:15','','<p>ㅎㅎ</p>',0,'테스트1'),
(186,3,'2023-03-31 10:55:00','','<p>ㅇㅁㄹㄴ</p>',0,'테스트2'),
(187,1,'2023-03-31 13:13:01','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F637500bb-24cb-49a8-8762-bf42f97da079-123.jpg?generation=1680235882404722&alt=media','<p>ㅎㅇ</p>',0,'테스트'),
(188,1,'2023-03-31 13:14:00','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fa695da87-9fcf-44c8-86c3-1b8f490b4d66-123.jpg?generation=1680235982303270&alt=media','<p>ㅎㅇ</p>',0,'테스트'),
(189,1,'2023-03-31 13:14:03','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F5713ce1b-7bf6-42db-8dd8-3d2609940451-123.jpg?generation=1680236041794691&alt=media','<p>ㅎㅇ</p>',0,'테스트'),
(190,1,'2023-03-31 13:17:10','','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(191,1,'2023-03-31 13:17:25','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F78f0d842-72ab-40a9-b373-367960a8081e-123.jpg?generation=1680236230638722&alt=media','<p>ㅁㄴㅇㄹㅇㄹㄴㅁㅇㄻㄴㄹ</p>',0,'12132123'),
(192,1,'2023-03-31 13:17:57','','<p>테스ㅡ트테스ㅡ트테스ㅡ트테스ㅡ트테스ㅡ트테스ㅡ트테스ㅡ트테스ㅡ트</p>',0,'123123124124'),
(193,1,'2023-03-31 13:19:24','','<p>ㅁㄴㅇㅎㅁㄶㅁㄶㄴㅁㅇㅎ</p>',0,'테스트123'),
(194,1,'2023-03-31 13:19:45','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fa59d056a-07c4-4601-8141-6fd376a2e74a-%EA%B0%80%EC%9E%90.jpg?generation=1680236365128667&alt=media','<p>두번째 테스트</p>',0,'테스트9879879'),
(195,1,'2023-03-31 13:27:13','','<p>ㅁㄴㅇㄻㄴㅇㄹ</p>',0,'ㅁㄴㅇㄻㄴㅇㄹ'),
(196,11,'2023-03-31 13:29:59','','<p>ㅁㄴㅇㄹ</p>',0,'안녕'),
(197,11,'2023-03-31 13:30:39','','<p>ㅁㄴㅇㄹ</p>',0,'테스트123'),
(198,11,'2023-03-31 13:35:14','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F8ad7e055-7fc2-4cbb-9a8f-63446fbb41b0-123.jpg?generation=1680237040215933&alt=media','<p>ㅁㄴㅇㄻㄴㅇㄹ</p>',0,'테스트123123'),
(199,11,'2023-03-31 13:36:31','','<p>ㅁㄴㅇㄹ</p>',0,'안녕'),
(200,11,'2023-03-31 13:36:50','','<p>ㅁㄴㅇㄻㄴㅇㄹ</p>',0,'왜 제대로 안와?'),
(201,11,'2023-03-31 13:55:03','','<p>gg</p>',0,'gg'),
(202,1,'2023-03-31 13:56:36','','<p>ㅁㄴㅇㄻㄴㅇㄹ</p>',0,'테스트'),
(203,1,'2023-03-31 13:56:49','','<p>ㅁㄴㅇㄹ</p>',0,'테스트987987'),
(204,11,'2023-03-31 14:04:43','','<p>ㅁㄴㅇㄹ</p>',0,'가보자~'),
(205,11,'2023-03-31 14:27:28','','<p>테스트야</p>',0,'가나?'),
(206,11,'2023-03-31 14:45:34','','<p>gg</p>',0,'gg'),
(207,11,'2023-03-31 14:46:36','','<p>ggggg</p>',0,'gggg'),
(208,11,'2023-03-31 14:47:00','','<p>ㅠㅠ</p>',0,'왜 안돼?'),
(209,11,'2023-03-31 14:50:11','','<p>ㅎㅎ</p>',0,'다시1'),
(210,11,'2023-03-31 14:51:53','','<p>ㅎㅎ</p>',0,'다시2'),
(211,11,'2023-03-31 14:54:06','','<p>ㅁㄴㅇㄹ</p>',0,'다시3'),
(212,11,'2023-03-31 14:54:40','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F6284f826-2527-4cb2-b2b9-2e79e0da951b-cardImg1.png?generation=1680242047370653&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'다시4'),
(213,11,'2023-03-31 15:00:48','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F0de4d3ae-cb37-4495-84c5-c1032c0d4807-%EC%A0%95%ED%98%B8.png?generation=1680242081400173&alt=media','<p>ㅎㅎㅎ</p>',0,'ㅎㅎ'),
(214,11,'2023-03-31 15:01:26','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F8a9f63b8-4435-40b7-8201-6a8301dccbef-andy-holmes-rCbdp8VCYhQ-unsplash.jpg?generation=1680242450181089&alt=media','<p>ㅎㅎㅎ</p>',0,'ㅎㅎㅎ'),
(215,11,'2023-03-31 15:01:53','','<p>ㅁㅇㄻㄴㅇㄻㄴㅇㄹ</p>',0,'처음부터1'),
(216,11,'2023-03-31 15:02:10','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F97eede2f-ffcc-469c-aa9a-9f66028d966e-andy-holmes-rCbdp8VCYhQ-unsplash.jpg?generation=1680242516159798&alt=media','<p>ㅁㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄹ</p>',0,'처음부터2'),
(217,11,'2023-03-31 15:03:55','','<p>안녕</p>',0,'다시222'),
(218,11,'2023-03-31 15:06:13','','<p>ㅁㄴㅇㄹㅁㄴㅇㄹ</p>',0,'테스트2'),
(219,11,'2023-03-31 15:07:52','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F00099238-e2e5-4aed-aad1-191d51b0014d-andy-holmes-rCbdp8VCYhQ-unsplash.jpg?generation=1680242775231395&alt=media','<p>ㅎㅎ</p>',0,'테스트10'),
(220,11,'2023-03-31 15:12:38','','<p>흠흠</p>',0,'테스트11'),
(221,11,'2023-03-31 15:13:29','','<p>ㄴㅁㅇㄹ</p>',0,'테스트12'),
(222,1,'2023-03-31 15:18:19','','<p>ㅁㄴㅇㄹ</p>',0,'테스트12'),
(223,1,'2023-03-31 15:18:51','','<p>ㅁㄴㅇㄹ</p>',0,'사진이안나와'),
(224,1,'2023-03-31 15:20:52','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fb1ff33e3-ba8d-4945-b200-864075bf7ce0-andy-holmes-rCbdp8VCYhQ-unsplash.jpg?generation=1680243532589783&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'사진이안나와2'),
(225,1,'2023-03-31 15:21:36','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fce94335d-87a4-488a-93ae-20456737ea2b-greg-rakozy-oMpAz-DN-9I-unsplash.jpg?generation=1680243654038331&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'사진이안나와3'),
(226,1,'2023-03-31 15:25:48','','<p>ㅁㄴㅇㄹ</p>',0,'테스트1'),
(227,1,'2023-03-31 15:27:05','','<p>테스트2</p>',0,'테스트2'),
(228,1,'2023-03-31 15:28:20','string','string',0,'string'),
(229,1,'2023-03-31 15:34:05','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fadc988d6-97b6-4288-84fe-12c1ae516851-andy-holmes-rCbdp8VCYhQ-unsplash.jpg?generation=1680244445329545&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트3'),
(230,1,'2023-03-31 15:34:47','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F1870aa52-d166-4b1b-ab0a-61aa5e89f330-vedrana-filipovic-kLqZ92hmqTw-unsplash.jpg?generation=1680244487066904&alt=media','<p>&nbsp;테스트4<br></p>',0,'테스트4'),
(231,1,'2023-03-31 15:42:50','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fbe2dcaa5-e38e-471d-840c-16a9e072c424-andy-holmes-rCbdp8VCYhQ-unsplash.jpg?generation=1680244969526744&alt=media','<p>만들었나?</p>',0,'하핫'),
(232,1,'2023-03-31 15:52:22','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F3b667aee-3bb0-4e2c-9f0e-0603ed6841c2-andy-holmes-rCbdp8VCYhQ-unsplash.jpg?generation=1680245541602572&alt=media','<p>가나다라</p>',0,'하핫'),
(233,1,'2023-03-31 15:52:43','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F984f1921-80be-44cb-a326-ac1a39cb7879-vedrana-filipovic-kLqZ92hmqTw-unsplash.jpg?generation=1680245563438579&alt=media','<p>ㅎㅎㅎ</p>',0,'어떻게아는거지?'),
(234,1,'2023-03-31 15:54:47','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fedebe7ce-6c56-47a3-a38e-d755a9a15ea2-vedrana-filipovic-kLqZ92hmqTw-unsplash.jpg?generation=1680245687569983&alt=media','<p>ㅎㅎㅎ</p>',0,'어떻게어떻게어떻게어떻게'),
(235,1,'2023-03-31 16:18:26',NULL,'<p>사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다</p>',0,'어떻게아는거지요?'),
(236,1,'2023-03-31 16:21:20','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F355813ac-bd6e-444d-b937-d49c2c6275f3-andy-holmes-rCbdp8VCYhQ-unsplash.jpg?generation=1680247280241790&alt=media','<p>사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다사랑합니다</p>',0,'어떻게?'),
(237,1,'2023-03-31 16:32:38','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F49e2e40c-3a7d-4c4d-b2a4-25e057a4ad11-greg-rakozy-oMpAz-DN-9I-unsplash.jpg?generation=1680247958159651&alt=media','<p>ㅎㅎ</p>',0,'ㅎㅎ'),
(238,2,'2023-03-31 20:40:00','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fea315593-ca24-45f6-be8a-2d9d26c82a51-profile1.png?generation=1680262799794111&alt=media','<p>ㅠㅠ</p>',0,'안만들어져 ㅠㅠ'),
(239,1,'2023-04-02 21:33:08','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fcf187b7c-b4e7-4fa4-927f-1c62a725c5bf-profile1.png?generation=1680438787989277&alt=media','<p>ㅎㅎㅎㅎ</p>',0,'흠흠'),
(240,11,'2023-04-03 09:09:01','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Ff4bbceac-ea44-4cf3-a2b9-6e664b2e78b5-%EA%B0%80%EC%9E%90.jpg?generation=1680480540587432&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'마지막테스트 '),
(241,11,'2023-04-03 09:25:15','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fd547b013-45de-4a0e-a857-9b0933d169cf-%EA%B2%BD%ED%97%98%EC%B9%98.png?generation=1680481515143081&alt=media','<p>bhjbhj</p>',0,'hhbjbhj'),
(242,1,'2023-04-03 10:54:11','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F467b3259-a93b-48b4-aaa3-dcff6275a19e-%EA%B0%80%EC%9E%90.jpg?generation=1680486850812425&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'테스트'),
(243,1,'2023-04-03 11:11:09','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Fd3a4dd14-cafe-46e3-b437-db3e5063568e-123.jpg?generation=1680487869467136&alt=media','<p>???</p>',0,'다시한번 테스트'),
(244,1,'2023-04-03 14:00:33','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2F2d4ead4c-61ab-459d-99d0-d26f78728023-123.jpg?generation=1680498032708295&alt=media','<p>모르겠다용</p>',0,'ㅎㅎㅎㅎ'),
(245,1,'2023-04-03 15:29:27','https://storage.googleapis.com/download/storage/v1/b/carrot_box555/o/img%2Ffd2455f2-ee7d-4bc1-a5fe-bd020bc1aaf4-greg-rakozy-oMpAz-DN-9I-unsplash.jpg?generation=1680503367438861&alt=media','<p>ㅁㄴㅇㄹ</p>',0,'4월 3일 첫게시물');
/*!40000 ALTER TABLE `recovery_diary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 15:09:07

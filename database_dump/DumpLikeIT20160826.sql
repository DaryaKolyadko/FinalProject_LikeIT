CREATE DATABASE  IF NOT EXISTS `like_it` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `like_it`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: like_it
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `avatar`
--

DROP TABLE IF EXISTS `avatar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avatar` (
  `avatar_id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(700) NOT NULL,
  PRIMARY KEY (`avatar_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avatar`
--

LOCK TABLES `avatar` WRITE;
/*!40000 ALTER TABLE `avatar` DISABLE KEYS */;
INSERT INTO `avatar` VALUES (1,'http://res.cloudinary.com/daryakolyadkocloud/image/upload/v1440830182/grey_bymqfg.jpg');
/*!40000 ALTER TABLE `avatar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` varchar(25) CHARACTER SET latin7 COLLATE latin7_general_cs NOT NULL,
  `question_id` int(11) NOT NULL,
  `text` varchar(600) NOT NULL,
  `creation_date` datetime NOT NULL,
  `version` tinyint(3) NOT NULL DEFAULT '0',
  `last_modify` timestamp NULL DEFAULT NULL,
  `answer` tinyint(1) NOT NULL DEFAULT '0',
  `mark_num` int(11) NOT NULL DEFAULT '0',
  `rating` float NOT NULL DEFAULT '0',
  `archive` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`comment_id`),
  KEY `comment_author_id_fk_idx` (`author_id`),
  KEY `comment_creation_date_idx` (`creation_date`) USING BTREE,
  KEY `comment_archive_idx` (`archive`) USING BTREE,
  KEY `comment_question_id_fk_idx` (`question_id`),
  CONSTRAINT `comment_author_id_fk` FOREIGN KEY (`author_id`) REFERENCES `user` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `comment_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'izzi',2,'Интерфейс - это контракт, которому должен удовлетворять класс','2016-08-01 22:13:51',0,NULL,0,1,10,0),(2,'dasha',2,'А можно простыми словами?(','2016-08-02 14:59:22',0,NULL,0,0,0,0),(3,'izzi',2,'Вы правы. Просто список методов, которые должны быть реализованы','2016-08-02 17:00:18',0,NULL,0,0,0,0),(4,'dasha',2,'Спасибо','2016-08-02 21:11:47',0,NULL,0,0,0,0),(5,'dasha',4,'скорее всего внешние библиотеки не \"подтянулись\"','2016-08-02 23:35:00',0,NULL,0,0,0,0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_rating`
--

DROP TABLE IF EXISTS `comment_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_rating` (
  `comment_id` int(11) NOT NULL,
  `user_id` varchar(25) CHARACTER SET latin7 COLLATE latin7_general_cs NOT NULL,
  `mark` tinyint(2) NOT NULL,
  PRIMARY KEY (`comment_id`,`user_id`),
  KEY `c_rating_user_id_idx` (`user_id`),
  CONSTRAINT `c_rating_comment_id_fk` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`comment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `c_rating_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_rating`
--

LOCK TABLES `comment_rating` WRITE;
/*!40000 ALTER TABLE `comment_rating` DISABLE KEYS */;
INSERT INTO `comment_rating` VALUES (2,'jace',10),(2,'simon',8),(4,'simon',9),(5,'izzi',9),(5,'simon',4);
/*!40000 ALTER TABLE `comment_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` varchar(25) CHARACTER SET latin7 COLLATE latin7_general_cs NOT NULL,
  `section_id` int(11) NOT NULL,
  `title` varchar(65) NOT NULL,
  `text` varchar(4000) NOT NULL,
  `creation_date` datetime NOT NULL,
  `version` tinyint(3) NOT NULL DEFAULT '0',
  `last_modify` timestamp NULL DEFAULT NULL,
  `comment_num` int(11) NOT NULL DEFAULT '0',
  `answer_num` tinyint(3) DEFAULT '0',
  `mark_num` int(11) NOT NULL DEFAULT '0',
  `rating` float NOT NULL DEFAULT '0',
  `archive` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`question_id`),
  KEY `question_author_id_fk_idx` (`author_id`),
  KEY `question_section_id_fk_idx` (`section_id`),
  KEY `question_creation_date_idx` (`creation_date`) USING BTREE,
  KEY `question_archive_idx` (`archive`) USING BTREE,
  CONSTRAINT `question_author_id_fk` FOREIGN KEY (`author_id`) REFERENCES `user` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `question_section_id_fk` FOREIGN KEY (`section_id`) REFERENCES `section` (`section_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'izzi',6,'Перемещение итератора','Добрый день!\r\nЕсть ли метод , который перемещает итератор на заданное число позиций?','2016-08-01 18:45:35',0,NULL,0,0,2,7,0),(2,'dasha',6,'Интерфейс и переменные','Как работает интерфейс? Он просто играет роль списка методов которые должны быть в классе который реализует интерфейс? Почему тогда в таком случае, в интерфейсе нельзя \"зарезервировать\" места под переменные, которые будут реализованы в классе?','2016-08-01 19:11:58',0,NULL,4,0,0,0,0),(3,'dasha',7,'title','question','2016-08-02 16:47:12',0,NULL,0,0,0,0,1),(4,'jace',9,'IDEA + h2 - Java БД','Всем доброго. В IntelliJ IDEA делаю подключение к БД H2 (h2-1.3.176) при запуске в самой idea все работает, но когда запускаю скомпилированный файл из out\\artifacts приложение запускается, а вот почему то к БД не подключается (((( Как быть куда копать? Сильно не ругайтесь, только учусь.','2016-08-02 23:20:09',0,NULL,1,0,2,5.5,0),(5,'simon',8,'Изменение внешнего вида GUI-элементов Java','Возможно ли изменять графический вид окна и других элементов управления, или создавать их самому? Если есть какие-то материалы на эту тему, сбросьте, пожалуйста.','2016-08-03 00:00:29',0,NULL,0,0,0,0,0),(6,'jace',11,'Дилемма при создании навигации','Господа, всем доброго времени суток, у меня тупой вопрос, основанный на смутных головных сомнениях..\r\nЕсть ли смысл навигацию писать не ручками а выгружать из соответствующих табличек БД?\r\nРазрабатываю приложение в котором пока три области, возможно будет больше. Каждая область занимается своим делом, профиль учетными данными пользователя и т.д.\r\nСуть в том что вся навигация в том же профиле написана руками, это порядка 5 ссылок в меню, но приложение будет развиваться и их кол-во будет расти и верстать руками как то не очень хочется да и думаю это не практично.\r\nЧто я решил, завести специальные табы в бд.\r\nRoots -к примеру будет хранить имена областей, Nodes - будет уже хранить связанные с именами урлы и всю необходимую инфу об урлах.\r\nЧто скажите господа специалисты?\r\nПару плюсов в сторону такого подхода,\r\nСокращение верстки можно написать свой контрол либо под Razor в виде helper method либо на js посредством метода возвращающего Json данные, а фронтендер уже сам там накрутит все.','2016-08-18 13:36:13',0,NULL,0,0,0,0,0);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`question_BEFORE_INSERT` BEFORE INSERT ON `question` FOR EACH ROW
BEGIN
CALL check_question_section(NEW.section_id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`question_AFTER_INSERT` AFTER INSERT ON `question` FOR EACH ROW
BEGIN
UPDATE section SET question_num=question_num + 1 WHERE section_id=NEW.section_id OR section_id=(SELECT major_section_id FROM section WHERE section_id=NEW.section_id); 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`question_BEFORE_UPDATE` BEFORE UPDATE ON `question` FOR EACH ROW
BEGIN
CALL check_question_section(NEW.section_id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`question_AFTER_UPDATE` AFTER UPDATE ON `question` FOR EACH ROW
BEGIN
#these strange inner queries are necessary, because MySQL doesn't support table UPDATE with query from the same table
IF OLD.section_id != NEW.section_id THEN
UPDATE section SET question_num=question_num - 1, answer_num=answer_num - NEW.answer_num WHERE section_id=OLD.section_id OR section_id=(SELECT major_section_id FROM (SELECT major_section_id, section_id FROM section) S1 WHERE section_id=OLD.section_id);
UPDATE section 
SET 
    question_num = question_num + 1,
    answer_num = answer_num + NEW.answer_num
WHERE
    section_id = NEW.section_id
        OR section_id = (SELECT 
            major_section_id
        FROM
            (SELECT 
                major_section_id, section_id
            FROM
                section) S1
        WHERE
            section_id = NEW.section_id); 
END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `question_rating`
--

DROP TABLE IF EXISTS `question_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_rating` (
  `question_id` int(11) NOT NULL,
  `user_id` varchar(25) CHARACTER SET latin7 COLLATE latin7_general_cs NOT NULL,
  `mark` tinyint(2) NOT NULL,
  PRIMARY KEY (`question_id`,`user_id`),
  KEY `q_rating_user_id_idx` (`user_id`),
  CONSTRAINT `q_rating_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `q_rating_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_rating`
--

LOCK TABLES `question_rating` WRITE;
/*!40000 ALTER TABLE `question_rating` DISABLE KEYS */;
INSERT INTO `question_rating` VALUES (1,'izzi',5),(1,'jace',9),(4,'izzi',5);
/*!40000 ALTER TABLE `question_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `section_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `major_section_id` int(11) DEFAULT NULL,
  `question_num` int(11) NOT NULL DEFAULT '0',
  `answer_num` int(11) DEFAULT '0',
  `label_color` char(6) DEFAULT NULL,
  `archive` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`section_id`),
  UNIQUE KEY `section_name_unique_idx` (`name`) USING BTREE,
  KEY `section_major_section_id_fk_idx` (`major_section_id`),
  KEY `section_archive_idx` (`archive`) USING BTREE,
  CONSTRAINT `section_major_section_id_fk` FOREIGN KEY (`major_section_id`) REFERENCES `section` (`section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (2,'Java',NULL,4,0,'d9534f',0),(3,'.NET',NULL,1,0,'f0ad4e',0),(4,'Python',NULL,0,0,'5cb85c',0),(5,'C++',NULL,0,0,'337ab7',1),(6,'Java SE',2,2,0,NULL,0),(7,'Java EE',2,0,0,NULL,0),(8,'Java GUI & Java FX',2,1,0,NULL,0),(9,'Java & JDBC, Hibernate',2,1,0,NULL,0),(10,'C# Windows Forms',3,0,0,NULL,0),(11,'C# & ADO.NET, EntityFramework',3,1,0,NULL,0),(12,'ASP.NET MVC',3,0,0,NULL,0),(13,'C# WPF & Silverlight',3,0,0,NULL,0);
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`section_BEFORE_INSERT` BEFORE INSERT ON `section` FOR EACH ROW
BEGIN
CALL check_sections_nesting_depth(NEW.major_section_id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`section_BEFORE_UPDATE` BEFORE UPDATE ON `section` FOR EACH ROW
BEGIN
CALL check_sections_nesting_depth(NEW.major_section_id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `login` varchar(25) CHARACTER SET latin7 COLLATE latin7_general_cs NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `gender` enum('male','female','other') NOT NULL,
  `password` varchar(128) NOT NULL,
  `birth_date` date NOT NULL,
  `sign_up_date` date NOT NULL,
  `email` varchar(45) NOT NULL,
  `email_confirmed` tinyint(1) NOT NULL DEFAULT '0',
  `avatar_id` int(11) DEFAULT '1',
  `role` enum('user','admin') NOT NULL DEFAULT 'user',
  `state` enum('active','banned') NOT NULL DEFAULT 'active',
  `rating` float NOT NULL DEFAULT '0',
  `answer_num` int(11) NOT NULL DEFAULT '0',
  `question_num` int(11) NOT NULL DEFAULT '0',
  `comment_num` int(11) NOT NULL DEFAULT '0',
  `archive` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`login`),
  KEY `user_avatar_id_idx` (`avatar_id`),
  KEY `user_rating_idx` (`rating`) USING BTREE,
  KEY `user_archive_idx` (`archive`) USING BTREE,
  CONSTRAINT `user_avatar_id_fk` FOREIGN KEY (`avatar_id`) REFERENCES `avatar` (`avatar_id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','Дарья','Колядко','female','c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec','1996-05-25','2016-07-29','darya.kolyadko@gmail.com',0,1,'admin','active',0,0,0,0,0),('Dasha','Даша','Елако','female','3212649967053853e847ea2697e278cd2ecd4c35b73bb5b63c492d7bcd302da5022e1c0828bcb937c42d3cb188e43ea574a733b4d2b468594f7dda19833f7456','1995-12-20','2016-08-03','dasha_elako@mail.ru',0,1,'user','active',0,0,0,0,0),('dasha','Дарья','Колядко','female','b9688c84d5dea764fa3fa4fbcbed220c8743e1831604922829fe6061e3bf6ff3fb8c396f448d6f7e9365ca03b805aa9669bbfe642f9100de26542784a8db62cf','1996-05-25','2016-07-29','skvoznak-1@mail.ru',0,1,'user','active',0,0,1,3,0),('izzi','Isabelle','Lightwood','female','b9d5b3965e3dd0fa268ded94ff7dc615e2e770891d261bc9b875030854f8816a2f7dedafce1425baddddd22ee82666626acad9ca3dee7072e5770539757e0d1b','1995-02-14','2016-07-29','izi.lightwood@gmail.com',0,1,'user','banned',0,0,1,2,0),('jace','Jace','Wayland','male','b8a6f8476cd9f7232e72aed0f99ef3d956659fdc74db736bb8f935b7d31825a02bec0409e777ed6d80d355c7affb0c8e7ffd3edc32c54610187869f486ddc3e2','1994-06-14','2016-08-02','jace.w@mail.ru',0,1,'user','active',0,0,1,0,0),('john.snow','Джон','Сноу','male','1f36d118198f248316cf464d36611b38bf7db49ae6afdc7dced4c72f0c557f6ea7b672f751db392498127a2431a56c770999145a787b8125823769a1a86472a7','1989-07-12','2016-08-24','john.snow@gmail.com',0,1,'user','active',0,0,0,0,0),('simon','Саймон','Льюис','male','24d802b145245d7fc4400c898af6eddc8e89dd05506f2af39b2612ead8970e31b8522aab07d51c1de0068768ad2ab3fcdf33d66da3ea878ceaf656f469e581dc','1994-08-04','2016-08-02','lewis_simon@gmail.com',0,1,'user','active',0,0,0,0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'like_it'
--

--
-- Dumping routines for database 'like_it'
--
/*!50003 DROP PROCEDURE IF EXISTS `check_question_section` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_question_section`(IN curr_section_id INT)
BEGIN
IF (SELECT major_section_id FROM section WHERE section_id=curr_section_id) IS NULL 
THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Question cannot be related to major section. You can relate question only to subsection of major section.';
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `check_sections_nesting_depth` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_sections_nesting_depth`(IN new_major_section_id INT)
BEGIN
IF new_major_section_id IS NOT NULL AND (SELECT major_section_id FROM section WHERE section_id=new_major_section_id) IS NOT NULL 
THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Maximum depth of sections nesting = 1. You can only create major section or subsection inside major section.';
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `move_questions_to_archive_in_section` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `move_questions_to_archive_in_section`(param_section_id INT(11))
BEGIN
DECLARE v_q_id INT(11);
DECLARE q_crs_finished BOOL DEFAULT FALSE;
DECLARE question_archive_crs CURSOR FOR 
    SELECT question_id FROM question WHERE section_id=param_section_id AND archive='false'
    FOR UPDATE;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET q_crs_finished = TRUE;
   
    OPEN question_archive_crs;
    question_loop: LOOP
    FETCH question_archive_crs INTO v_q_id;
    
    IF q_crs_finished THEN
      LEAVE question_loop;
    END IF;
    
    UPDATE question 
    SET archive=TRUE
    WHERE section_id=param_section_id AND question_id=v_q_id;
    END LOOP;
    CLOSE question_archive_crs;   
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `move_section_to_archive` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `move_section_to_archive`(param_section_id INT(11))
BEGIN
DECLARE v_s_id INT(11);
DECLARE s_crs_finished BOOL DEFAULT FALSE;
DECLARE section_archive_csr CURSOR FOR 
    SELECT section_id FROM section WHERE major_section_id=param_section_id AND archive='false'
    FOR UPDATE;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET s_crs_finished = TRUE;	
    UPDATE section
    SET archive = TRUE
    WHERE section_id=param_section_id;
    
    OPEN section_archive_csr;
	section_loop: LOOP
    FETCH section_archive_csr INTO v_s_id;
    
    IF s_crs_finished THEN
      LEAVE section_loop;
    END IF;
    
    CALL move_section_to_archive(v_s_id);
	
    END LOOP;  
	CLOSE section_archive_csr;
    CALL move_questions_to_archive_in_section(param_section_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-26 11:58:23

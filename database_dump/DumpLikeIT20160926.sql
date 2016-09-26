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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author_id` varchar(25) CHARACTER SET latin7 COLLATE latin7_general_cs NOT NULL,
  `question_id` bigint(20) NOT NULL,
  `text` varchar(2000) NOT NULL,
  `creation_date` datetime NOT NULL,
  `answer` tinyint(1) NOT NULL DEFAULT '0',
  `rating` float NOT NULL DEFAULT '0',
  `archive` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`comment_id`),
  KEY `comment_author_id_fk_idx` (`author_id`),
  KEY `comment_creation_date_idx` (`creation_date`) USING BTREE,
  KEY `comment_question_id_fk_idx` (`question_id`),
  CONSTRAINT `comment_author_id_fk` FOREIGN KEY (`author_id`) REFERENCES `user` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `comment_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'izzi',2,'Интерфейс - это контракт, которому должен удовлетворять класс','2016-08-01 22:13:51',1,9,0),(2,'dasha',2,'А можно простыми словами?(','2016-08-02 14:59:22',0,5.75,0),(3,'izzi',2,'Вы правы. Просто список методов, которые должны быть реализованы','2016-08-02 17:00:18',1,0,0),(4,'dasha',2,'Спасибо','2016-08-02 21:11:47',0,9,0),(5,'dasha',4,'скорее всего внешние библиотеки не \"подтянулись\"','2016-08-02 23:35:00',1,6.5,0),(6,'dasha',2,'еще раз спасибо!','2016-09-10 05:13:05',0,0,1),(7,'simon',23,'Писать самому форум (если со всеми фичами) задача на полгода(если вы тру), год (если не особо) и год+ если начинающий. Судя по тому что у вас возник такой вопрос - в вашем случае это год+ PS: конечно если речь не о форуме \"с ограниченым функционалом\"','2016-09-11 02:22:12',0,0,0),(8,'mary',23,'Могу порекомендовать движок opensource MVC: http://mesoboard.com/\r\nПример реализации: http://www.dunaydv.ru/','2016-09-11 02:22:12',1,8,0),(9,'dasha',23,'Я разворачивала MvcForum: http://www.mvcforum.com/.\r\nСодержит весь минимальный функционал. Код понятен и вполне расширяем.','2016-09-11 02:33:52',1,7.5,0),(10,'izzi',23,'Если использовать web forms authentication + разделы + темы + сообщения - разделение прав доступа - личка - голосования - репутация и всё это без ajax плюшек, то можно сделать достаточно быстро ','2016-09-11 02:41:30',0,0,0),(11,'clary',24,'Понятие контекста, в широком смысле, обычно означает окружение объекта. Например, если есть некий объект, то в процессе работы приложения он будет взаимодействовать с другими объектами, иметь доступ к параметрам конфигурации, переменным, зависеть от состояния других объектов или существовать в рамках жизненного цикла. Глобальные и локальные переменные, переменные окружения, взаимодействующие с данным объекты, их состояние и т.д и т.п - все вместе образует контекст объекта - то окружение с которым он работает. Т.е. контекст - это execution / runtime окружение объекта в целом.\r\nServletContext - это объект, который содержит конфигурацию вашего приложения. Это также интерфейс между сервлетом и контейнером сервлетов. ServletContext создает на старте web приложения, позволяет сервлетам и JSP получать доступ к параметрам конфигурации, описанным в web.xml, а также обмениваться данными внутри приложения. \r\nВ Spring, аналогичный по смыслу, но более объемный по возможностям, объект называется ApplicationContext, что более конкретно в определении выполняемых им функций.\r\nАналогично, контекста запроса - это совокупность параметров и среды исполнения, которые доступны для данного запроса. Например, относящиеся только к запросу аттрибуты и HTTP заголовки.\r\nКонтекст web приложения - это по сути само web приложение в runtime.','2016-09-14 00:05:47',1,9.5,0),(12,'ivan',25,'+1 и меня посвятите в тему если кто реально рубит','2016-09-15 22:17:05',1,0,0),(13,'clary',25,' Правильно мужики глину месят. Хрень не сусветная. Ждем хорошего гуру!!!!','2016-09-15 22:17:05',0,0,0),(14,'mary',26,'SNI is a TLS extension, not SSL, so the first step is selecting the right protocol. But yes, .NET as of 4.5 doesn\'t support SNI, which is a damn shame.','2016-09-26 18:22:58',1,0,0),(15,'dasha',26,'Our SecureBlackbox fully supports SNI in both client and server TLS components','2016-09-26 19:23:40',0,0,0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`comment_AFTER_INSERT` AFTER INSERT ON `comment` FOR EACH ROW
BEGIN
DECLARE s_id INT(11);
SELECT section_id INTO @s_id FROM comment C JOIN question Q ON C.question_id=Q.question_id WHERE C.comment_id=NEW.comment_id;
call update_section_answer_num(@s_id);
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`comment_AFTER_UPDATE` AFTER UPDATE ON `comment` FOR EACH ROW
BEGIN
DECLARE s_id INT(11);
IF NEW.answer != OLD.answer OR OLD.archive != NEW.archive THEN
SELECT section_id INTO @s_id FROM comment C JOIN question Q ON C.question_id=Q.question_id WHERE C.comment_id=NEW.comment_id;
call update_section_answer_num(@s_id);
END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `comment_rating`
--

DROP TABLE IF EXISTS `comment_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_rating` (
  `comment_id` bigint(20) NOT NULL,
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
INSERT INTO `comment_rating` VALUES (1,'dasha',9),(1,'izzi',9),(2,'dasha',0),(2,'izzi',5),(2,'jace',10),(2,'simon',8),(4,'simon',9),(5,'dasha',4),(5,'izzi',9),(5,'mary',9),(5,'simon',4),(8,'clary',8),(8,'dasha',8),(9,'clary',8),(9,'izzi',7),(11,'izzi',10),(11,'jace',9);
/*!40000 ALTER TABLE `comment_rating` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`comment_rating_AFTER_INSERT` AFTER INSERT ON `comment_rating` FOR EACH ROW
BEGIN
CALL update_comment_rating(NEW.comment_id);
CALL update_user_rating(NEW.user_id);
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`comment_rating_AFTER_UPDATE` AFTER UPDATE ON `comment_rating` FOR EACH ROW
BEGIN
CALL update_comment_rating(NEW.comment_id);
CALL update_user_rating(NEW.user_id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `question_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author_id` varchar(25) CHARACTER SET latin7 COLLATE latin7_general_cs NOT NULL,
  `section_id` bigint(20) NOT NULL,
  `title` varchar(65) NOT NULL,
  `text` varchar(4000) NOT NULL,
  `creation_date` datetime NOT NULL,
  `rating` float NOT NULL DEFAULT '0',
  `archive` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`question_id`),
  KEY `question_author_id_fk_idx` (`author_id`),
  KEY `question_section_id_fk_idx` (`section_id`),
  KEY `question_creation_date_idx` (`creation_date`) USING BTREE,
  CONSTRAINT `question_author_id_fk` FOREIGN KEY (`author_id`) REFERENCES `user` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `question_section_id_fk` FOREIGN KEY (`section_id`) REFERENCES `section` (`section_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'izzi',6,'Перемещение итератора',' Доброго времени суток!\r\nЕсть ли метод , который перемещает итератор на заданное число позиций?','2016-08-01 18:45:35',6.75,0),(2,'dasha',6,'Интерфейс и переменные','   Как работает интерфейс? Он просто играет роль списка методов которые должны быть в классе который реализует интерфейс? Почему тогда в таком случае, в интерфейсе нельзя \"зарезервировать\" места под переменные, которые будут реализованы в классе?','2016-08-01 19:11:58',0,0),(3,'dasha',7,'title','question','2016-08-02 16:47:12',0,1),(4,'jace',9,'IDEA + h2 - Java БД','Всем доброго. В IntelliJ IDEA делаю подключение к БД H2 (h2-1.3.176) при запуске в самой idea все работает, но когда запускаю скомпилированный файл из out\\artifacts приложение запускается, а вот почему то к БД не подключается (((( Как быть куда копать? Сильно не ругайтесь, только учусь.','2016-08-02 23:20:09',5.5,0),(5,'simon',8,'Изменение внешнего вида GUI-элементов Java','Возможно ли изменять графический вид окна и других элементов управления, или создавать их самому? Если есть какие-то материалы на эту тему, сбросьте, пожалуйста.','2016-08-03 00:00:29',0,0),(6,'jace',11,'Дилемма при создании навигации','Господа, всем доброго времени суток, у меня тупой вопрос, основанный на смутных головных сомнениях..\r\nЕсть ли смысл навигацию писать не ручками а выгружать из соответствующих табличек БД?\r\nРазрабатываю приложение в котором пока три области, возможно будет больше. Каждая область занимается своим делом, профиль учетными данными пользователя и т.д.\r\nСуть в том что вся навигация в том же профиле написана руками, это порядка 5 ссылок в меню, но приложение будет развиваться и их кол-во будет расти и верстать руками как то не очень хочется да и думаю это не практично.\r\nЧто я решил, завести специальные табы в бд.\r\nRoots -к примеру будет хранить имена областей, Nodes - будет уже хранить связанные с именами урлы и всю необходимую инфу об урлах.\r\nЧто скажите господа специалисты?\r\nПару плюсов в сторону такого подхода,\r\nСокращение верстки можно написать свой контрол либо под Razor в виде helper method либо на js посредством метода возвращающего Json данные, а фронтендер уже сам там накрутит все.','2016-08-18 13:36:13',0,0),(17,'dasha',8,'Вопрос 1','texxt','2016-09-06 05:39:17',0,1),(18,'mary',7,'Q1','  cjdjhb\r\nhgvf\r\nhnyhbgb h6g\r\nggtg','2016-09-06 14:14:45',0,0),(19,'mary',7,'Download files from web page','I want to download pdf files from http://bookzz.org/ using java. \r\nHow I can pass a file name to the site and get list to choose from? How can I make all these steps run in the background?','2016-09-06 16:42:22',0,0),(20,'mary',14,'Django question','Django vvv\r\nsegh trytfuyg\r\nvgjbh rftygy 5rd6ftgy rdtfcvg   rtfytg r6t e5rt drftg  rtyh drfyt  ryt 56 t d6ftgy 5676t 9o8yuygtf 567r6t 676 78','2016-09-07 05:11:26',0,0),(21,'cdcd',13,'Question WPF','fhfhijv\r\nvfvvvf\r\ncjnckj j ujk uui uvi\r\nhghio hiuo viogu','2016-09-08 04:37:58',0,0),(22,'dasha',6,'question from dasha','question text\r\nmore\r\ntext\r\nmore\r\nand\r\nmore','2016-09-08 14:37:20',4,0),(23,'clary',12,'Форум на ASP.NET MVC 3',' Здравствуйте, стоит задача сделать форум на сайте, есть 2 пути:\r\n1. самому написать\r\n2. взять готовый движок\r\nСуществует ли, какое-нибудь легковесное решение этой задачи или надо писать самому? \r\nP.S если существует, подскажите пожалуйста.\r\n','2016-09-11 02:19:21',7,0),(24,'izzi',7,'Понятие контекста в веб приложении','  Хотела узнать что такое контекст?\r\n1. Контекст сервлета\r\n2. Контекст запроса\r\nИз прочитанного мною в сети не могу понять, что подразумевается под понятием контекста? Если кто-то сможет дать линк на инфу с объяснением что такое контекст для веб приложения или дать хороший пример, я буду рада.','2016-09-13 23:38:30',0,0),(25,'ivan',26,'Что за хрень?',' Полная ботва, кто бы мог нормально прожевать вопрос? Благодарность будет безграничной в пределах разумного!!!','2016-09-15 22:14:32',10,0),(26,'simon',13,'Server Name Indication from C#','  As far as I can tell, there seems to be a big limitation in .NET in that there is no way using C# and .NET to make an TLS connection that uses Server Name Indication (SNI). Have I missed something or is my understanding correct?\r\n\r\nDoes anybody know if and how I could make an SNI connection using OpenSSL.NET, libcurl.NET or some other 3rd party .NET library? Some sample code would be very much appreciated\r\n','2016-09-26 18:17:30',7,0);
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
call update_section_question_num(NEW.section_id);
call update_section_answer_num(NEW.section_id);
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
IF OLD.section_id != NEW.section_id OR OLD.archive != NEW.archive THEN
call update_section_question_num(OLD.section_id);
call update_section_question_num(NEW.section_id);
call update_section_answer_num(OLD.section_id);
call update_section_answer_num(NEW.section_id);
END IF;

IF OLD.archive != NEW.archive THEN 
call update_section_question_num(NEW.section_id);
END IF;
 
 #TODO answer num
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`question_AFTER_DELETE` AFTER DELETE ON `question` FOR EACH ROW
BEGIN
call update_section_question_num(OLD.section_id);
call update_section_answer_num(OLD.section_id);
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
  `question_id` bigint(20) NOT NULL,
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
INSERT INTO `question_rating` VALUES (1,'dasha',8),(1,'izzi',5),(1,'jace',8),(1,'simon',6),(4,'dasha',6),(4,'izzi',5),(22,'dasha',4),(23,'dasha',8),(23,'izzi',6),(25,'clary',10),(25,'ivan',10),(26,'mary',7);
/*!40000 ALTER TABLE `question_rating` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`question_rating_AFTER_INSERT` AFTER INSERT ON `question_rating` FOR EACH ROW
BEGIN
CALL update_question_rating(NEW.question_id);
CALL update_user_rating(NEW.user_id);
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `like_it`.`question_rating_AFTER_UPDATE` AFTER UPDATE ON `question_rating` FOR EACH ROW
BEGIN
CALL update_question_rating(NEW.question_id);
CALL update_user_rating(NEW.user_id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `section_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `major_section_id` bigint(20) DEFAULT NULL,
  `question_num` int(11) NOT NULL DEFAULT '0',
  `answer_num` int(11) DEFAULT '0',
  `label_color` char(6) DEFAULT NULL,
  `archive` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`section_id`),
  KEY `section_major_section_id_fk_idx` (`major_section_id`),
  CONSTRAINT `section_major_section_id_fk` FOREIGN KEY (`major_section_id`) REFERENCES `section` (`section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (2,'Java',NULL,8,3,'d9534f',0),(3,'.NET',NULL,4,2,'f0ad4e',0),(4,'Python',NULL,1,0,'5cb85c',0),(5,'C++',NULL,0,0,'337ab7',1),(6,'Java SE',2,3,1,NULL,0),(7,'Java EE',2,3,1,NULL,0),(8,'Java GUI & Java FX',2,1,0,NULL,0),(9,'Java & JDBC, Hibernate',2,1,1,NULL,0),(10,'C# Windows Forms',3,0,0,NULL,0),(11,'C# & ADO.NET, EntityFramework',3,1,0,NULL,0),(12,'ASP.NET MVC',3,1,1,NULL,0),(13,'C# WPF & Silverlight',3,2,1,NULL,0),(14,'Django',4,1,0,NULL,0),(17,'Delphi',NULL,1,1,'2f81d4',0),(18,'Django',4,0,0,NULL,1),(23,'очень важная секция',2,0,0,NULL,1),(24,'yyy',3,0,0,NULL,1),(25,'root1',NULL,1,0,'146e78',1),(26,'new in delphi',17,1,1,NULL,0),(27,'s1',NULL,0,0,'8a2121',1),(28,'section tru la la',NULL,0,0,'c7188a',1),(29,'Разная всячина',NULL,0,0,'2bb01c',1);
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
  `role` enum('user','admin') NOT NULL DEFAULT 'user',
  `state` enum('active','banned') NOT NULL DEFAULT 'active',
  `rating` float NOT NULL DEFAULT '0',
  `archive` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`login`),
  KEY `user_rating_idx` (`rating`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','Admin','Admin','female','c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec','1996-05-02','2016-07-29','darya.kolyadko@gmail.com','admin','active',0,0),('cdcd','Carly','Haylin','female','b9688c84d5dea764fa3fa4fbcbed220c8743e1831604922829fe6061e3bf6ff3fb8c396f448d6f7e9365ca03b805aa9669bbfe642f9100de26542784a8db62cf','2016-08-11','2016-08-31','carly@gmail.com','user','active',0,1),('clary','Кларисса','Феричайлд','female','cd4bca01c28909b5ba67c45c5630d336857a87a54fd964a15e69c6a095256f95f45c919d5f4c46eff3ab2c64c7bd0bc02ec0da91e2970e6956988122db9ee55c','1990-08-15','2016-09-11','clary@gmail.com','user','active',8.25,0),('Dasha','Даша','Елако','female','3212649967053853e847ea2697e278cd2ecd4c35b73bb5b63c492d7bcd302da5022e1c0828bcb937c42d3cb188e43ea574a733b4d2b468594f7dda19833f7456','1995-12-20','2016-08-03','dasha_elako@mail.ru','user','active',5.54167,0),('dasha','Дарья','Колядко','female','b9688c84d5dea764fa3fa4fbcbed220c8743e1831604922829fe6061e3bf6ff3fb8c396f448d6f7e9365ca03b805aa9669bbfe642f9100de26542784a8db62cf','1996-05-25','2016-07-29','darya@gmail.com','user','active',5.54167,0),('ivan','Иван','Иванов','male','23f080a7a1d8a53205df180fa23109e6213d9398bfc4455de3e97717807abaf31c3b00c6293618f97fe8ec83bbdfcfe7fedc5f15f2bffe3276daff5f3a6c6907','2016-09-15','2016-09-15','ivan@mail.ru','user','active',10,0),('izzi','Isabelle','Lightwood','female','b9d5b3965e3dd0fa268ded94ff7dc615e2e770891d261bc9b875030854f8816a2f7dedafce1425baddddd22ee82666626acad9ca3dee7072e5770539757e0d1b','1995-02-14','2016-07-29','izi.lightwood@gmail.com','user','active',5.25,0),('jace','Jace','Wayland','male','b8a6f8476cd9f7232e72aed0f99ef3d956659fdc74db736bb8f935b7d31825a02bec0409e777ed6d80d355c7affb0c8e7ffd3edc32c54610187869f486ddc3e2','1994-06-14','2016-08-02','jace.w@mail.ru','user','banned',2.75,0),('john.snow','Джон','Сноу','male','1f36d118198f248316cf464d36611b38bf7db49ae6afdc7dced4c72f0c557f6ea7b672f751db392498127a2431a56c770999145a787b8125823769a1a86472a7','1989-07-12','2016-08-24','john.snow@gmail.com','user','banned',0,0),('lola','Lola','Lala','female','c5b283f34d8cc083279d8694846d4089151d6b21c7d77eaad5f90eb10a156231f85f544d1d617004c18e42158677ffe4845cb43db6a40290202dae1a079a4616','2010-06-02','2016-09-12','lola@gmail.com','user','active',0,0),('mary','Мария','Колядко','female','925ece1aeeeec8a68e8921e1a00b889a8895205f2ad3569b38dd1fdf68a909156e893ac49a8c279acd28889e90ed22f53467da7564631189e2f299fe2ad669d7','2004-11-01','2016-08-27','mary.kolyadko@gmail.com','user','active',4,0),('simon','Саймон','Льюис','male','24d802b145245d7fc4400c898af6eddc8e89dd05506f2af39b2612ead8970e31b8522aab07d51c1de0068768ad2ab3fcdf33d66da3ea878ceaf656f469e581dc','1994-08-03','2016-08-02','lewis_simon@gmail.com','user','active',0,0),('ttt','Temperance','Brennan','female','0602611a56f977501462561aacb2f3d57af108c43f5983bc73f26de2129c30fa3aa12da49cd144e800b9a0c378e60c2ec858475aed4579067985ea1bed2f3fb1','2010-02-17','2016-09-04','brennan@gmail.com','user','active',0,0);
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_question_section`(IN curr_section_id BIGINT)
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_sections_nesting_depth`(IN new_major_section_id BIGINT)
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
/*!50003 DROP PROCEDURE IF EXISTS `update_comment_rating` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_comment_rating`(IN curr_comment_id BIGINT)
BEGIN
DECLARE com_r FLOAT;
SELECT AVG(mark) INTO @com_r FROM comment_rating WHERE comment_id=curr_comment_id;

IF @com_r IS NULL THEN
SET @com_r = 0;
END IF;

UPDATE comment SET rating=@com_r WHERE comment_id=curr_comment_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_question_rating` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_question_rating`(IN curr_question_id BIGINT)
BEGIN
DECLARE quest_r FLOAT;
SELECT AVG(mark) INTO @quest_r FROM question_rating WHERE question_id=curr_question_id;

IF @quest_r IS NULL THEN
SET @quest_r = 0;
END IF;
UPDATE question SET rating=@quest_r WHERE question_id=curr_question_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_section_answer_num` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_section_answer_num`(IN curr_section_id BIGINT)
BEGIN
DECLARE s_id BIGINT(20);
DECLARE a_num INT(11);
DECLARE sum_a_num INT(11);
#not major section
IF (SELECT major_section_id FROM section WHERE section_id=curr_section_id) IS NOT NULL
THEN 
SELECT COUNT(*) INTO @a_num FROM (SELECT Q.question_id FROM question Q JOIN comment C ON Q.question_id=C.question_id
WHERE section_id=curr_section_id AND Q.archive=0 AND C.archive=0 AND answer=1 GROUP BY Q.question_id) T;
UPDATE section SET answer_num=@a_num WHERE section_id=curr_section_id;
SET @s_id = (SELECT major_section_id FROM section WHERE section_id=curr_section_id);
END IF;
#major section
IF (SELECT major_section_id FROM section WHERE section_id=curr_section_id) IS NULL 
THEN SET @s_id = curr_section_id;
END IF;

SELECT SUM(answer_num) INTO @sum_a_num FROM section WHERE major_section_id = @s_id AND archive=0 
GROUP BY major_section_id;
UPDATE section SET answer_num = @sum_a_num WHERE section_id = @s_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_section_question_num` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_section_question_num`(IN curr_section_id BIGINT)
BEGIN
DECLARE s_id BIGINT(11);
DECLARE q_num INT(11);
DECLARE sum_q_num INT(11);
#not major section
IF (SELECT major_section_id FROM section WHERE section_id=curr_section_id) IS NOT NULL
THEN 
SELECT COUNT(*) INTO @q_num FROM question WHERE section_id=curr_section_id AND archive=0;
UPDATE section SET question_num=@q_num WHERE section_id=curr_section_id;
SET @s_id = (SELECT major_section_id FROM section WHERE section_id=curr_section_id);
END IF;
#major section
IF (SELECT major_section_id FROM section WHERE section_id=curr_section_id) IS NULL 
THEN SET @s_id = curr_section_id;
END IF;

SELECT SUM(question_num) INTO @sum_q_num FROM section WHERE major_section_id = @s_id AND archive=0 
GROUP BY major_section_id;
UPDATE section SET question_num = @sum_q_num WHERE section_id = @s_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_user_rating` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_user_rating`(IN curr_user VARCHAR(25))
BEGIN
DECLARE u_r FLOAT;
SELECT AVG(rating) INTO @u_r FROM (SELECT rating FROM comment C WHERE author_id=curr_user UNION SELECT rating FROM question Q WHERE author_id=curr_user) R;

IF @u_r IS NULL THEN
SET @u_r = 0;
END IF;

UPDATE user SET rating=@u_r WHERE login=curr_user;
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

-- Dump completed on 2016-09-26 19:51:48

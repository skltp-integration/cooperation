-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: localhost    Database: cooperation
-- ------------------------------------------------------
-- Server version	5.6.27

USE cooperation;


--
-- Drop tables in correct order due to Foreign Key Constraints
--


DROP TABLE IF EXISTS `serviceproduction`;
DROP TABLE IF EXISTS `cooperation`;
DROP TABLE IF EXISTS `connectionpoint`;
DROP TABLE IF EXISTS `serviceconsumer`;
DROP TABLE IF EXISTS `servicecontract`;
DROP TABLE IF EXISTS `serviceproducer`;
DROP TABLE IF EXISTS `logicaladdress`;
DROP TABLE IF EXISTS `servicedomain`;


--
-- Table structure for table `connectionpoint`
--

CREATE TABLE `connectionpoint` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `environment` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  `snapshot_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Table structure for table `logicaladdress`
--

CREATE TABLE `logicaladdress` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `logical_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_logicaladdress_1` (`logical_address`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8;

--
-- Table structure for table `serviceconsumer`
--

CREATE TABLE `serviceconsumer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `hsa_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_serviceconsumer_1` (`hsa_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8;

--
-- Table structure for table `servicedomain`
--


CREATE TABLE `servicedomain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_servicedomain_1` (`namespace`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET= utf8;

--
-- Table structure for table `servicecontract`
--

CREATE TABLE `servicecontract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `major` int(11) DEFAULT NULL,
  `minor` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `service_domain_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_servicecontract_1` FOREIGN KEY (`service_domain_id`) REFERENCES `servicedomain` (`id`),
  UNIQUE KEY `UK_servicecontract_1` (`namespace`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET= utf8;

--
-- Table structure for table `serviceproducer`
--

CREATE TABLE `serviceproducer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `hsa_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_serviceproducer_1` (`hsa_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8;

--
-- Table structure for table `serviceproduction`
--

CREATE TABLE `serviceproduction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `physical_address` varchar(255) DEFAULT NULL,
  `rivta_profile` varchar(255) DEFAULT NULL,
  `connection_point_id` bigint(20) DEFAULT NULL,
  `logical_address_id` bigint(20) DEFAULT NULL,
  `service_contract_id` bigint(20) DEFAULT NULL,
  `service_producer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_serviceproduction_1` (`connection_point_id`),
  KEY `IX_serviceproduction_2` (`logical_address_id`),
  KEY `IX_serviceproduction_3` (`service_contract_id`),
  KEY `IX_serviceproduction_4` (`service_producer_id`),
  CONSTRAINT `FK_serviceproduction_1` FOREIGN KEY (`service_contract_id`) REFERENCES `servicecontract` (`id`),
  CONSTRAINT `FK_serviceproduction_2` FOREIGN KEY (`service_producer_id`) REFERENCES `serviceproducer` (`id`),
  CONSTRAINT `FK_serviceproduction_3` FOREIGN KEY (`connection_point_id`) REFERENCES `connectionpoint` (`id`),
  CONSTRAINT `FK_serviceproduction_4` FOREIGN KEY (`logical_address_id`) REFERENCES `logicaladdress` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET= utf8;


--
-- Table structure for table `cooperation`
--

CREATE TABLE `cooperation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `connection_point_id` bigint(20) DEFAULT NULL,
  `logical_address_id` bigint(20) DEFAULT NULL,
  `service_consumer_id` bigint(20) DEFAULT NULL,
  `service_contract_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_cooperation_1` (`connection_point_id`),
  KEY `IX_cooperation_2` (`logical_address_id`),
  KEY `IX_cooperation_3` (`service_consumer_id`),
  KEY `IX_cooperation_4` (`service_contract_id`),
  CONSTRAINT `FK_cooperation_1` FOREIGN KEY (`service_consumer_id`) REFERENCES `serviceconsumer` (`id`),
  CONSTRAINT `FK_cooperation_2` FOREIGN KEY (`connection_point_id`) REFERENCES `connectionpoint` (`id`),
  CONSTRAINT `FK_cooperation_3` FOREIGN KEY (`logical_address_id`) REFERENCES `logicaladdress` (`id`),
  CONSTRAINT `FK_cooperation_4` FOREIGN KEY (`service_contract_id`) REFERENCES `servicecontract` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET= utf8;



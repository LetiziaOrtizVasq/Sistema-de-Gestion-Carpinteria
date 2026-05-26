mysqldump: [Warning] Using a password on the command line interface can be insecure.
-- MySQL dump 10.13  Distrib 8.4.8, for Win64 (x86_64)
--
-- Host: localhost    Database: carpinteriadb
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `asignacion_produccion`
--

DROP TABLE IF EXISTS `asignacion_produccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignacion_produccion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_asignacion` date NOT NULL,
  `fecha_inicio_estimada` date DEFAULT NULL,
  `observaciones` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `operario` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pedido_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ouadex3j03i44wbf0u79w7g2s` (`pedido_id`),
  CONSTRAINT `FKqcombvrs1vy785fv4t4njffeq` FOREIGN KEY (`pedido_id`) REFERENCES `pedido_confirmado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignacion_produccion`
--

LOCK TABLES `asignacion_produccion` WRITE;
/*!40000 ALTER TABLE `asignacion_produccion` DISABLE KEYS */;
INSERT INTO `asignacion_produccion` VALUES (1,'ASIGNADO','2026-05-10','2026-05-21','','Juan Mamani',3),(4,'ASIGNADO','2026-05-11',NULL,'','Juan Mamani',2),(8,'ASIGNADO','2026-05-10','2026-05-12','Produccion escritorio','Juan Perez',4),(9,'ASIGNADO','2026-05-10','2026-05-12','Ropero pino','Luis Torrez',5),(10,'ASIGNADO','2026-05-23','2026-05-24','','Juan Mamani',6),(11,'EN_PROCESO','2026-05-25','2026-05-26','Prioridad alta, cliente pagó completo','Juan Mamani',7),(12,'ASIGNADO','2026-05-25','2026-05-27','Verificar medidas exactas antes de cortar','Carlos Quispe',8),(13,'EN_PROCESO','2026-05-25','2026-05-26','Pasacables a medida','Mario Huanca',9);
/*!40000 ALTER TABLE `asignacion_produccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avance_produccion`
--

DROP TABLE IF EXISTS `avance_produccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avance_produccion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `completado` bit(1) NOT NULL,
  `descripcion` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `etapa` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `porcentaje` int NOT NULL,
  `asignacion_id` bigint NOT NULL,
  `hora_fin` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hora_inicio` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `insumos` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `observaciones` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `operario` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9ajw5v5sdpbqs3kkalbaehwoc` (`asignacion_id`),
  CONSTRAINT `FK9ajw5v5sdpbqs3kkalbaehwoc` FOREIGN KEY (`asignacion_id`) REFERENCES `asignacion_produccion` (`id`),
  CONSTRAINT `avance_produccion_chk_1` CHECK (((`porcentaje` <= 100) and (`porcentaje` >= 0)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avance_produccion`
--

LOCK TABLES `avance_produccion` WRITE;
/*!40000 ALTER TABLE `avance_produccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `avance_produccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ci` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fecha_registro` date NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Toledo','9052527','Calle Batalla de Toledo ','m.toltorrico@gmail.com','2026-05-10','Mariana','71039543'),(2,'Ortiz','7821779','No hay','letiziao319@gmail.com','2026-05-23','Letizia','75686436'),(3,'Garcia','','','','2026-05-25','Ana','71234567'),(4,'Lopez','','','','2026-05-25','Maria','76543210'),(5,'Mendoza','','','','2026-05-25','Carlos','78901234'),(6,'Gutierrez','','','','2026-05-25','Ana','76543210'),(7,'.','','','','2026-05-25','Camila','75686436'),(8,'Chávez','','','','2026-05-25','Roberto','71234001'),(9,'Mamani','','','','2026-05-25','Lucía','72345002'),(10,'Vargas','','','','2026-05-25','Diego','73456003');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cotizacion`
--

DROP TABLE IF EXISTS `cotizacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cotizacion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_cotizacion` date NOT NULL,
  `observaciones` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `precio_mano_obra` decimal(10,2) NOT NULL,
  `precio_materiales` decimal(10,2) NOT NULL,
  `precio_total` decimal(10,2) NOT NULL,
  `valida_hasta` date NOT NULL,
  `solicitud_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3bqgc418oip1fl3q7of6sjbs4` (`solicitud_id`),
  CONSTRAINT `FK3bqgc418oip1fl3q7of6sjbs4` FOREIGN KEY (`solicitud_id`) REFERENCES `solicitud_cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cotizacion`
--

LOCK TABLES `cotizacion` WRITE;
/*!40000 ALTER TABLE `cotizacion` DISABLE KEYS */;
INSERT INTO `cotizacion` VALUES (1,'ACEPTADA','2026-05-10','Mesa Redonda',100.00,100.00,200.00,'2026-05-20',1),(2,'ACEPTADA','2026-05-11','Armario de pino con acabado lustrado',800.00,1200.00,2000.00,'2026-06-11',3),(3,'ACEPTADA','2026-05-10','Cotizacion escritorio ejecutivo',800.00,1200.00,2000.00,'2026-05-25',4),(4,'ACEPTADA','2026-05-10','Ropero pino 3 puertas',600.00,900.00,1500.00,'2026-05-25',5),(5,'ACEPTADA','2026-05-10','Mesa comedor cedro',700.00,1100.00,1800.00,'2026-05-25',6),(6,'PENDIENTE','2026-05-23','',150.00,2000.00,2150.00,'2026-06-07',1),(7,'ACEPTADA','2026-05-23','',100.00,400.00,500.00,'2026-06-07',8),(8,'ACEPTADA','2026-05-25','Cedro premium, barniz mate',800.00,1200.00,2000.00,'2026-06-30',9),(9,'ACEPTADA','2026-05-25','Nogal con acabado satinado',1300.00,2200.00,3500.00,'2026-06-30',10),(10,'ACEPTADA','2026-05-25','Roble barniz brillante',600.00,950.00,1550.00,'2026-06-30',11),(11,'ENVIADA','2026-05-25','',1000.00,800.00,1800.00,'2026-06-09',1),(12,'PENDIENTE','2026-05-25','',700.00,400.00,1100.00,'2026-06-09',12);
/*!40000 ALTER TABLE `cotizacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `embalaje_pedido`
--

DROP TABLE IF EXISTS `embalaje_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `embalaje_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_embalaje` date NOT NULL,
  `observaciones` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `responsable` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipo_embalaje` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pedido_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_99btptvjiin8qjka9s6kaq3a7` (`pedido_id`),
  CONSTRAINT `FKowal7i9xgdcrewmnugrxoibup` FOREIGN KEY (`pedido_id`) REFERENCES `pedido_confirmado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `embalaje_pedido`
--

LOCK TABLES `embalaje_pedido` WRITE;
/*!40000 ALTER TABLE `embalaje_pedido` DISABLE KEYS */;
INSERT INTO `embalaje_pedido` VALUES (1,'LISTO','2026-05-10','Embalado correctamente','Pedro Lopez','Caja madera',4),(2,'LISTO','2026-05-10','Listo para entrega','Roberto Mamani','Film stretch',5),(3,'EMBALADO','2026-05-23','','JUAN','CARTON',2),(4,'EMBALADO','2026-05-25','Esquinas protegidas con espuma','Ana Villca','Stretch film',9),(5,'EN_PROCESO','2026-05-25','','JUAN','Cartón corrugado',6);
/*!40000 ALTER TABLE `embalaje_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrega_pedido`
--

DROP TABLE IF EXISTS `entrega_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrega_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ci` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_entrega` date NOT NULL,
  `observaciones` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recibido_por` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pedido_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i1xvrt60qtbnuulls7ivru25x` (`pedido_id`),
  CONSTRAINT `FKlxlfctgvw10t93oy4gvn96qd0` FOREIGN KEY (`pedido_id`) REFERENCES `pedido_confirmado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega_pedido`
--

LOCK TABLES `entrega_pedido` WRITE;
/*!40000 ALTER TABLE `entrega_pedido` DISABLE KEYS */;
INSERT INTO `entrega_pedido` VALUES (1,'12345678 CB','ENTREGADO','2026-05-10','Cliente conforme','Carlos Mendoza',4),(2,'9876543 SC','ENTREGADO','2026-05-10','Cliente satisfecha','Maria Lopez',5),(3,'9052527','ENTREGADO','2026-05-23','','JUAN',2),(4,'7456321','ENTREGADO','2026-05-25','Entrega en domicilio, cliente conforme','Diego Vargas',9),(5,'7821779','ENTREGADO','2026-05-25','','JUAN',6);
/*!40000 ALTER TABLE `entrega_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagen_pedido`
--

DROP TABLE IF EXISTS `imagen_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imagen_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fecha_subida` datetime(6) NOT NULL,
  `nombre_guardado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre_original` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tamanio` bigint DEFAULT NULL,
  `tipo_contenido` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pedido_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiny84x421dgaeo2wcpr98fytg` (`pedido_id`),
  CONSTRAINT `FKiny84x421dgaeo2wcpr98fytg` FOREIGN KEY (`pedido_id`) REFERENCES `pedido_confirmado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagen_pedido`
--

LOCK TABLES `imagen_pedido` WRITE;
/*!40000 ALTER TABLE `imagen_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagen_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informacion_tecnica`
--

DROP TABLE IF EXISTS `informacion_tecnica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `informacion_tecnica` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `acabado` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `color` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dimensiones_exactas` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `especificaciones` varchar(600) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_registro` date NOT NULL,
  `notas_adicionales` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipos_madera` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `solicitud_id` bigint NOT NULL,
  `disenio` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estilo_mueble` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `plegable` bit(1) DEFAULT NULL,
  `requerimientos_especiales` varchar(600) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_amme1fwdhan3t6ib39l2c2ybh` (`solicitud_id`),
  CONSTRAINT `FKd4w8fsn4sr39jmf5ugxfdqbt6` FOREIGN KEY (`solicitud_id`) REFERENCES `solicitud_cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informacion_tecnica`
--

LOCK TABLES `informacion_tecnica` WRITE;
/*!40000 ALTER TABLE `informacion_tecnica` DISABLE KEYS */;
INSERT INTO `informacion_tecnica` VALUES (1,'','','','a','2026-05-23','','CEDRO',2,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `informacion_tecnica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspeccion_calidad`
--

DROP TABLE IF EXISTS `inspeccion_calidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inspeccion_calidad` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accion_retrabajo` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fecha` date NOT NULL,
  `inspector` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `observaciones` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `resultado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pedido_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9wga2qpsj82fth8tcd4nra14h` (`pedido_id`),
  CONSTRAINT `FK9vcmgvrwmf46t69fwqy24emfr` FOREIGN KEY (`pedido_id`) REFERENCES `pedido_confirmado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspeccion_calidad`
--

LOCK TABLES `inspeccion_calidad` WRITE;
/*!40000 ALTER TABLE `inspeccion_calidad` DISABLE KEYS */;
INSERT INTO `inspeccion_calidad` VALUES (1,NULL,'2026-05-10','Ana Garcia','Sin observaciones','APROBADO',4),(2,NULL,'2026-05-10','Carmen Vidal','Sin observaciones','APROBADO',5),(3,'','2026-05-23','JUAN','','APROBADO',2),(4,NULL,'2026-05-25','Ana Villca','Superficies perfectas, barniz uniforme, sin defectos','APROBADO',9),(5,'','2026-05-25','Mario Huanca','Leve diferencia de tono en un extremo de la mesa','OBSERVADO',7),(6,'','2026-05-25','Ana Villca','','OBSERVADO',3),(7,'','2026-05-25','Ana Villca','','APROBADO',6),(8,'','2026-05-25','Ana Villca','','APROBADO',8);
/*!40000 ALTER TABLE `inspeccion_calidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_solicitud`
--

DROP TABLE IF EXISTS `item_solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_solicitud` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `descripcion` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dimensiones_aproximadas` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `material_preferido` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipo_producto` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `solicitud_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3civtndm6i9g0qahqnik84p7r` (`solicitud_id`),
  CONSTRAINT `FK3civtndm6i9g0qahqnik84p7r` FOREIGN KEY (`solicitud_id`) REFERENCES `solicitud_cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_solicitud`
--

LOCK TABLES `item_solicitud` WRITE;
/*!40000 ALTER TABLE `item_solicitud` DISABLE KEYS */;
INSERT INTO `item_solicitud` VALUES (1,1,'Mesa','1.20 m x 0.80 m','Cedro','Mesa',1),(2,1,'Mesa de comedor para 6 personas','1.80m x 0.90m','Cedro','Mesa',2),(3,2,'Armario empotrado dormitorio','2.00m x 1.20m','Pino','Armario',3),(4,1,'Escritorio ejecutivo con cajones','1.50m x 0.70m','Cedro','Escritorio',4),(5,1,'Ropero de 3 puertas con espejo','2.00m x 0.60m','Pino','Ropero',5),(6,1,'Mesa rectangular 6 personas','1.80m x 0.90m','Cedro','Mesa comedor',6),(7,1,'Mesa','1.20 m x 0.80 m','Cedro','Mesa',7),(8,1,'','1.20 m x 0.80 m','Cedro','Mesa',8),(9,1,'','1.20 m x 0.80 m','Cedro','Silla',8),(10,1,'Comedor para 6 personas','1.80m x 0.90m','Cedro','Mesa',9),(11,1,'Cama matrimonial con cabecera','2.00m x 1.60m','Nogal','Cama',10),(12,1,'Armario 3 puertas con espejo','2.00m x 1.20m','Nogal','Armario',10),(13,1,'Escritorio en L con cajones','1.60m x 1.20m','Roble','Escritorio',11),(14,1,'Estante flotante 5 repisas','2.00m x 0.30m','Roble','Estante',11),(15,1,'','1.20 m x 0.80 m','Caoba','Mueble de cocina',12),(16,1,'','1.20 m x 0.80 m','Roble','Puerta',12);
/*!40000 ALTER TABLE `item_solicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento_madera`
--

DROP TABLE IF EXISTS `movimiento_madera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento_madera` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(10,2) NOT NULL,
  `fecha` date NOT NULL,
  `observaciones` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipo_movimiento` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pedido_id` bigint DEFAULT NULL,
  `proveedor_id` bigint DEFAULT NULL,
  `stock_madera_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKja1068g1jlj7svdcqmsq1fy5h` (`pedido_id`),
  KEY `FKa38vd0g8ae00ylstud0mmflj3` (`proveedor_id`),
  KEY `FKk27iw94j0xg5597nys5r3blxj` (`stock_madera_id`),
  CONSTRAINT `FKa38vd0g8ae00ylstud0mmflj3` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id`),
  CONSTRAINT `FKja1068g1jlj7svdcqmsq1fy5h` FOREIGN KEY (`pedido_id`) REFERENCES `pedido_confirmado` (`id`),
  CONSTRAINT `FKk27iw94j0xg5597nys5r3blxj` FOREIGN KEY (`stock_madera_id`) REFERENCES `stock_madera` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento_madera`
--

LOCK TABLES `movimiento_madera` WRITE;
/*!40000 ALTER TABLE `movimiento_madera` DISABLE KEYS */;
INSERT INTO `movimiento_madera` VALUES (1,12.00,'2026-05-25','Consumo para mesa comedor Roberto Chávez','CONSUMO',7,NULL,1),(2,20.00,'2026-05-25','Consumo para dormitorio Lucía Mamani','CONSUMO',8,NULL,2),(3,15.00,'2026-05-25','Consumo para escritorio Diego Vargas','CONSUMO',9,NULL,3),(4,50.00,'2026-05-25','Entrega factura #F-2026-0041','INGRESO',NULL,1,1),(5,40.00,'2026-05-25','Reposicion urgente Roble','INGRESO',NULL,1,3),(6,30.00,'2026-05-25','Lote mensual Nogal','INGRESO',NULL,2,2),(7,20.00,'2026-05-25','Planchas MDF 18mm','INGRESO',NULL,2,4),(8,12.00,'2026-05-25','Corte principal mesa comedor','CONSUMO',2,NULL,1),(9,8.00,'2026-05-25','Armario nogal puertas','CONSUMO',3,NULL,2),(10,6.00,'2026-05-25','Fondo cajones MDF','CONSUMO',4,NULL,4),(11,15.00,'2026-05-25','Cama matrimonial tablones','CONSUMO',5,NULL,1),(12,25.00,'2026-05-25','Ajuste inventario fisico','REPOSICION',NULL,NULL,3),(13,10.00,'2026-05-25','Compra directa ferreteria','REPOSICION',NULL,NULL,4);
/*!40000 ALTER TABLE `movimiento_madera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operario`
--

DROP TABLE IF EXISTS `operario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `cargo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operario`
--

LOCK TABLES `operario` WRITE;
/*!40000 ALTER TABLE `operario` DISABLE KEYS */;
INSERT INTO `operario` VALUES (1,_binary '','CARPINTERO','Juan Mamani','70012345'),(2,_binary '','CARPINTERO','Carlos Quispe','71234567'),(3,_binary '','LIJADOR','Pedro Flores','72345678'),(4,_binary '','BARNIZADOR','Luis Condori','73456789'),(5,_binary '','MAESTRO','Mario Huanca','74567890'),(6,_binary '','ACABADOS','Ana Villca','75678901');
/*!40000 ALTER TABLE `operario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_final`
--

DROP TABLE IF EXISTS `pago_final`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago_final` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comprobante` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_pago` date NOT NULL,
  `metodo_pago` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `monto_pagado` decimal(10,2) NOT NULL,
  `monto_requerido` decimal(10,2) DEFAULT NULL,
  `pedido_id` bigint NOT NULL,
  `numero_recibo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfj8mq39y06w9m8sueot90gvo` (`pedido_id`),
  CONSTRAINT `FKdjiij9sgbwes3ht85h7oxqg3v` FOREIGN KEY (`pedido_id`) REFERENCES `pedido_confirmado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_final`
--

LOCK TABLES `pago_final` WRITE;
/*!40000 ALTER TABLE `pago_final` DISABLE KEYS */;
INSERT INTO `pago_final` VALUES (1,'REC-002','CONFIRMADO','2026-05-10','Efectivo',1000.00,1000.00,4,NULL),(2,'TRX-20260510-002','CONFIRMADO','2026-05-10','Transferencia',750.00,750.00,5,NULL),(3,'2024','CONFIRMADO','2026-05-23','Transferencia',200.00,100.00,2,NULL),(4,'','CONFIRMADO','2026-05-25','Efectivo',775.00,775.00,9,'NV-2026-0004'),(5,'','CONFIRMADO','2026-05-25','QR',1000.00,1000.00,3,'NV-2026-0005'),(6,'2024','CONFIRMADO','2026-05-25','Efectivo',200.00,250.00,6,'NV-2026-0006');
/*!40000 ALTER TABLE `pago_final` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_inicial`
--

DROP TABLE IF EXISTS `pago_inicial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago_inicial` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comprobante` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_pago` date NOT NULL,
  `metodo_pago` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `monto_pagado` decimal(10,2) NOT NULL,
  `monto_requerido` decimal(10,2) DEFAULT NULL,
  `pedido_id` bigint NOT NULL,
  `numero_recibo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ehjxk3f2ijq69e2rmqu9woq0j` (`pedido_id`),
  CONSTRAINT `FKrww83uif6t20m81t1ppmxc5cj` FOREIGN KEY (`pedido_id`) REFERENCES `pedido_confirmado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_inicial`
--

LOCK TABLES `pago_inicial` WRITE;
/*!40000 ALTER TABLE `pago_inicial` DISABLE KEYS */;
INSERT INTO `pago_inicial` VALUES (1,'','PENDIENTE','2026-05-10','Efectivo',500.00,100.00,2,NULL),(2,'2024','CONFIRMADO','2026-05-10','Efectivo',200.00,1000.00,3,NULL),(3,'REC-001','CONFIRMADO','2026-05-10','Efectivo',1000.00,1000.00,4,NULL),(4,'QR-20260510-001','CONFIRMADO','2026-05-10','QR',750.00,750.00,5,NULL),(5,'123','PENDIENTE','2026-05-23','Efectivo',250.00,250.00,6,NULL),(6,'','CONFIRMADO','2026-05-25','Efectivo',1000.00,1000.00,7,'REC-2026-0006'),(7,'QR-20260525-001','CONFIRMADO','2026-05-25','QR',1750.00,1750.00,8,'REC-2026-0007'),(8,'TRX-20260525-001','CONFIRMADO','2026-05-25','Transferencia',775.00,775.00,9,'REC-2026-0008');
/*!40000 ALTER TABLE `pago_inicial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido_confirmado`
--

DROP TABLE IF EXISTS `pedido_confirmado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido_confirmado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_confirmacion` date NOT NULL,
  `fecha_entrega_estimada` date NOT NULL,
  `observaciones` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cotizacion_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c7c24vj3j36anfndrdnydlsrb` (`cotizacion_id`),
  CONSTRAINT `FKnmjg39deb91bsakqecw7ta5x9` FOREIGN KEY (`cotizacion_id`) REFERENCES `cotizacion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido_confirmado`
--

LOCK TABLES `pedido_confirmado` WRITE;
/*!40000 ALTER TABLE `pedido_confirmado` DISABLE KEYS */;
INSERT INTO `pedido_confirmado` VALUES (2,'CERRADO','2026-05-10','2026-06-15','',1),(3,'CERRADO','2026-05-10','2026-05-21','',2),(4,'CERRADO','2026-05-10','2026-06-01','Pedido escritorio ejecutivo',3),(5,'CERRADO','2026-05-10','2026-06-15','Ropero urgente',4),(6,'CERRADO','2026-05-23','2026-06-04','',7),(7,'EN_PRODUCCION','2026-05-25','2026-06-15','Cliente prefiere entrega por la mañana',8),(8,'LISTO_ENTREGA','2026-05-25','2026-06-20','Confirmar color final antes de barnizar',9),(9,'CERRADO','2026-05-25','2026-06-10','Incluir pasacables en escritorio',10);
/*!40000 ALTER TABLE `pedido_confirmado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `contacto` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ruc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `telefono` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,_binary '','Jorge Salinas',NULL,'norte@maderas.bo','Maderas del Norte','1234567','72345678'),(2,_binary '','Carmen Flores',NULL,'aserradero@bo.com','Aserradero Boliviano','7654321','71234567');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitud_cliente`
--

DROP TABLE IF EXISTS `solicitud_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitud_cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `nombre_cliente` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cliente_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp7fu34tk6uv4ufdpjfqmofr0l` (`cliente_id`),
  CONSTRAINT `FKp7fu34tk6uv4ufdpjfqmofr0l` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitud_cliente`
--

LOCK TABLES `solicitud_cliente` WRITE;
/*!40000 ALTER TABLE `solicitud_cliente` DISABLE KEYS */;
INSERT INTO `solicitud_cliente` VALUES (1,'PENDIENTE','2026-05-10','Mariana Toledo','71039543',NULL),(2,'PENDIENTE','2026-05-10','Ana Garcia','71234567',NULL),(3,'PENDIENTE','2026-05-11','Maria Lopez','76543210',NULL),(4,'PENDIENTE','2026-05-11','Carlos Mendoza','78901234',NULL),(5,'PENDIENTE','2026-05-10','Maria Lopez','77112233',NULL),(6,'PENDIENTE','2026-05-10','Ana Gutierrez','76543210',NULL),(7,'EN_PROCESO','2026-05-23','Camila','75686436',NULL),(8,'PENDIENTE','2026-05-23','Mariana Toledo','75686436',NULL),(9,'PENDIENTE','2026-05-25','Roberto Chávez','71234001',NULL),(10,'PENDIENTE','2026-05-25','Lucía Mamani','72345002',NULL),(11,'PENDIENTE','2026-05-25','Diego Vargas','73456003',NULL),(12,'PENDIENTE','2026-05-25','Mariana Toledo','71039543',1);
/*!40000 ALTER TABLE `solicitud_cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_madera`
--

DROP TABLE IF EXISTS `stock_madera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_madera` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_disponible` decimal(10,2) NOT NULL,
  `descripcion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dimension` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `precio_por_unidad` decimal(10,2) DEFAULT NULL,
  `stock_minimo` decimal(10,2) NOT NULL,
  `tipo_madera` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `unidad` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_madera`
--

LOCK TABLES `stock_madera` WRITE;
/*!40000 ALTER TABLE `stock_madera` DISABLE KEYS */;
INSERT INTO `stock_madera` VALUES (1,131.00,'Cedro nacional seco','2x4 pulgadas',8.50,30.00,'Cedro','pies'),(2,87.00,'Nogal importado primera calidad','1x6 pulgadas',14.00,25.00,'Nogal','pies'),(3,68.00,'Roble europeo - stock critico','2x4 pulgadas',12.00,20.00,'Roble','pies'),(4,69.00,'MDF grueso para fondos y cajones','18mm',22.00,10.00,'MDF','m2');
/*!40000 ALTER TABLE `stock_madera` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-25 23:04:47

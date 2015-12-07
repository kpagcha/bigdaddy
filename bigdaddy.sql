SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

CREATE DATABASE IF NOT EXISTS `bigdaddy` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `bigdaddy`;

CREATE TABLE IF NOT EXISTS `conde` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) NOT NULL,
  `dinastia` varchar(64) NOT NULL,
  `ordenDinastico` int(11) NOT NULL,
  `id_EsatusSocial` int(64) DEFAULT NULL COMMENT 'Clave foranea de tabla estatussocial',
  PRIMARY KEY (`id`),
  KEY `estatus` (`id_EstatusSocial`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

INSERT INTO `conde` (`id`, `nombre`, `dinastia`, `ordenDinastico`, `id_EsatusSocial`) VALUES
(1, 'Vladimir', 'Dracula', 4, 1),
(2, 'Dimitri', 'Dracula', 5, 1);

CREATE TABLE IF NOT EXISTS `estatussocial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

INSERT INTO `estatussocial` (`id`, `nombre`) VALUES
(1, 'Noble'),
(3, 'Monarca'),
(4, 'Burgués');


ALTER TABLE `conde`
  ADD CONSTRAINT `conde_ibfk_1` FOREIGN KEY (`id_EsatusSocial`) REFERENCES `estatussocial` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

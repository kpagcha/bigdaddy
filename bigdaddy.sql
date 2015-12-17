-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-12-2015 a las 10:38:16
-- Versión del servidor: 5.6.17
-- Versión de PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `bigdaddy`
--
CREATE DATABASE IF NOT EXISTS `bigdaddy` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `bigdaddy`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conde`
--

DROP TABLE IF EXISTS `conde`;
CREATE TABLE IF NOT EXISTS `conde` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) NOT NULL,
  `dinastia` varchar(64) NOT NULL,
  `ordenDinastico` int(11) NOT NULL,
  `id_EstatusSocial` int(64) DEFAULT NULL COMMENT 'Clave foranea de tabla estatussocial',
  PRIMARY KEY (`id`),
  KEY `estatus` (`id_EstatusSocial`),
  KEY `nombre` (`nombre`),
  KEY `dinastia` (`dinastia`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Volcado de datos para la tabla `conde`
--

INSERT INTO `conde` (`id`, `nombre`, `dinastia`, `ordenDinastico`, `id_EstatusSocial`) VALUES
(1, 'Vladimir', 'Dracula', 4, 1),
(2, 'Dimitri', 'Dracula', 5, 1);

--
-- Disparadores `conde`
--
DROP TRIGGER IF EXISTS `Conde_bi`;
DELIMITER //
CREATE TRIGGER `Conde_bi` BEFORE INSERT ON `conde`
 FOR EACH ROW begin
	if NEW.nombre='' then
    	signal sqlstate '45000'
        	set message_text = 'El nombre del conde no puede estar vacío.';
    end if;
    if NEW.dinastia='' then
    	signal sqlstate '45000'
        	set message_text = 'La dinastía del conde no puede estar vacía.';
    end if;
    if NEW.ordenDinastico<1 then
    	signal sqlstate '45000'
        	set message_text = 'El orden dinástico del conde no puede ser menor que 1.';
    end if;
end
//
DELIMITER ;
DROP TRIGGER IF EXISTS `Conde_bu`;
DELIMITER //
CREATE TRIGGER `Conde_bu` BEFORE UPDATE ON `conde`
 FOR EACH ROW begin
	if NEW.nombre='' then
    	signal sqlstate '45000'
        	set message_text = 'El nombre del conde no puede estar vacío.';
    end if;
    if NEW.dinastia='' then
    	signal sqlstate '45000'
        	set message_text = 'La dinastía del conde no puede estar vacía.';
    end if;
    if NEW.ordenDinastico<1 then
    	signal sqlstate '45000'
        	set message_text = 'El orden dinástico del conde no puede ser menor que 1.';
    end if;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estatussocial`
--

DROP TABLE IF EXISTS `estatussocial`;
CREATE TABLE IF NOT EXISTS `estatussocial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_2` (`nombre`),
  KEY `nombre` (`nombre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `estatussocial`
--

INSERT INTO `estatussocial` (`id`, `nombre`) VALUES
(4, 'Burgués'),
(3, 'Monarca'),
(1, 'Noble');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `text` varchar(64) NOT NULL,
  `integer` int(11) NOT NULL,
  `real` float NOT NULL,
  `boolean` tinyint(1) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `test`
--

INSERT INTO `test` (`text`, `integer`, `real`, `boolean`, `date`) VALUES
('texto1', 10, 10.1, 1, '2015-12-09'),
('teeeexto', 20, 0.9, 0, '2015-08-05');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `conde`
--
ALTER TABLE `conde`
  ADD CONSTRAINT `conde_ibfk_1` FOREIGN KEY (`id_EstatusSocial`) REFERENCES `estatussocial` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

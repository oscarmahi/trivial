-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-02-2022 a las 20:25:29
-- Versión del servidor: 10.4.20-MariaDB
-- Versión de PHP: 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `preguntas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `preguntas`
--

CREATE TABLE `preguntas` (
  `id_pregunta` int(11) NOT NULL,
  `cuestion` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `respuesta1` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `respuesta2` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `respuesta3` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `correcta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `preguntas`
--

INSERT INTO `preguntas` (`id_pregunta`, `cuestion`, `respuesta1`, `respuesta2`, `respuesta3`, `correcta`) VALUES
(1, 'de que color es', 'rojo', 'verde', 'azul', 2),
(2, 'Cuantas asignaturas hay en 2DAM', '10', '5', '6', 3),
(3, 'Colores bandera de Escocia', '3', '2', '1', 2),
(4, 'Nombre capital de Alemania', 'Berlin', 'Hamburgo', 'Colonia', 1),
(5, 'Nombre profesor de ADD', 'Carlos', 'David', 'Juan', 2),
(6, 'Cuantas estaciones de Metro hay en Madrid', '28', '12', '38', 3),
(7, 'pregunta nueva', 'uno', 'dos', 'tres', 1),
(8, 'Cuantas asignaturas hay en 2DAM', '10', '5', '6', 3),
(9, 'Colores bandera de Escocia', '3', '2', '1', 2),
(10, 'Nombre capital de Alemania', 'Berlin', 'Hamburgo', 'Colonia', 1),
(11, 'Nombre profesor de ADD', 'Carlos', 'David', 'Juan', 2),
(12, 'Cuantas estaciones de Metro hay en Madrid', '28', '12', '38', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `records`
--

CREATE TABLE `records` (
  `nick` varchar(30) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `puntos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `records`
--

INSERT INTO `records` (`nick`, `fecha`, `puntos`) VALUES
('oscarmahi', 'Tue Feb 08 17:16:03 CET 2022', 5),
('pepe', 'Wed Feb 09 20:18:01 CET 2022', 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `preguntas`
--
ALTER TABLE `preguntas`
  ADD PRIMARY KEY (`id_pregunta`);

--
-- Indices de la tabla `records`
--
ALTER TABLE `records`
  ADD PRIMARY KEY (`nick`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `preguntas`
--
ALTER TABLE `preguntas`
  MODIFY `id_pregunta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

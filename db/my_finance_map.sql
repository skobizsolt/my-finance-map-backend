-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2022. Okt 28. 19:17
-- Kiszolgáló verziója: 10.4.24-MariaDB
-- PHP verzió: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `my_finance_map`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `address`
--

CREATE TABLE `address` (
  `address_id` bigint(20) NOT NULL,
  `after_house_number` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `house_number` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `street_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `address`
--

INSERT INTO `address` (`address_id`, `after_house_number`, `city`, `country`, `house_number`, `postal_code`, `state`, `street_name`, `street_type`) VALUES
(1, '1. em.', 'Gyor', 'Magyarorszag', '1', '9027', 'Gyor-Moson Sopron', 'Budai', 'ut'),
(2, 'fsz. ', 'Gyor', 'Magyarorszag', '1', '9024', 'Gyor-Moson Sopron', 'Vasvari Pal', 'utca'),
(3, '3.em 2-es ajto', 'Gyor', 'Magyarorszag', '42', '9024', 'Gyor-Moson-Sopron', 'Tancsics Mihaly', 'utca'),
(4, '2.em. 6-os ajto', 'Gyor', 'Magyarorszag', '16', '9024', 'Gyor-Moson-Sopron', 'Petofi', 'utca');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `budget`
--

CREATE TABLE `budget` (
  `budget_id` bigint(20) NOT NULL,
  `budget_cap` decimal(19,2) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `budget`
--

INSERT INTO `budget` (`budget_id`, `budget_cap`, `currency`, `user_id`) VALUES
(1, '350000.00', 'HUF', 1),
(2, '470000.00', 'HUF', 2);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `business_category`
--

CREATE TABLE `business_category` (
  `category_id` bigint(20) NOT NULL,
  `category_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `business_category`
--

INSERT INTO `business_category` (`category_id`, `category_name`) VALUES
(1, 'elektronikai bolt'),
(2, 'szepsegapolas'),
(3, 'ekszerbolt'),
(4, 'parfumeria'),
(5, 'ruhazati bolt'),
(6, 'cipobolt'),
(7, 'sportruhazat'),
(8, 'konyvesbolt'),
(9, 'orabolt'),
(10, 'etterem'),
(11, 'optikus');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `profile`
--

CREATE TABLE `profile` (
  `profile_id` bigint(20) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `profile`
--

INSERT INTO `profile` (`profile_id`, `birth_date`, `first_name`, `last_name`, `address_id`) VALUES
(1, '1998-10-18', 'Emil', 'Jonas', 3),
(2, '2002-07-14', 'Gabor', 'Kiss', 4);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `shop`
--

CREATE TABLE `shop` (
  `shop_id` bigint(20) NOT NULL,
  `coordinatex` varchar(255) DEFAULT NULL,
  `coordinatey` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `shop`
--

INSERT INTO `shop` (`shop_id`, `coordinatex`, `coordinatey`, `name`, `address_id`, `category_id`) VALUES
(1, '47.689730689122115', '17.644799760869585', 'Humanic - Arkad', 1, 5),
(2, '47.68971702485496', '17.644499784711424', 'New Yorker', 1, 5),
(3, '47.68980963815137', '17.644551660287643', 'Hervis Gyor', 1, 7),
(4, '47.689871178587694', '17.644500832094245', 'iSTYLE Gyor - Apple Authorised Reseller', 1, 1),
(5, '47.689869373076775', '17.644444505707675', 'Douglas Parf?m?ria', 1, 4),
(6, '47.68990458052816', '17.644215176848057', 'ZARA', 1, 5),
(7, '47.68971139063052', '17.64424736335467', 'X-CLASS', 1, 5),
(8, '47.68967257201335', '17.644653718000658', 'Sizeer', 1, 6),
(9, '47.689551602183826', '17.64460007382297', 'GAS', 1, 5),
(10, '47.690478011054964', '17.644388649729134', 'Burger King', 1, 10),
(11, '47.690354786227125', '17.643701333693624', 'KFC Gyor Arkad', 1, 10),
(12, '47.69044054712789', '17.64389646438996', 'Bijou Brigitte', 1, 3),
(13, '47.69032860655565', '17.644019845998642', 'PANDORA', 1, 3),
(14, '47.690253227081875', '17.6438086220423', 'Ujvilag Eszer Ora - Gyor Arkad', 1, 9),
(15, '47.69082961857324', '17.643655974987254', 'INTERSPAR Hipermarket', 1, 3),
(16, '47.66903042848801', '17.65200807745882', 'budmil Store', 2, 5),
(17, '47.66933131790197', '17.651292897514132', 'Dinamic Sport', 2, 7),
(18, '47.66913907406618', '17.65189575118021', 'Best Byte', 2, 1),
(19, '47.668830132895245', '17.651946147319663', 'Rossmann', 2, 2),
(20, '47.669345648548806', '17.651419846492946', 'CCC Shoes & Bags', 2, 6),
(21, '47.66967469554514', '17.65029795183866', 'Don Pepe Gyor', 2, 10),
(22, '47.66957521172715', '17.65059905572674', 'Hope Grill', 2, 10),
(23, '47.669543316111756', '17.650511092793145', 'Thai Bufe', 2, 10),
(24, '47.66986303080403', '17.65038591477434', 'PEPCO', 2, 5),
(25, '47.670093892455064', '17.650841517668653', 'Retro Jeans Outlet', 2, 5),
(26, '47.670191856463966', '17.65067686910064', 'DEICHMANN', 2, 6),
(27, '47.66885431251732', '17.651440921561445', 'eMag Gyor', 2, 1),
(28, '47.66965550287374', '17.650810869388497', 'Pizza Hut Gyor Plaza', 2, 10),
(29, '47.6696095792331', '17.651337496628337', 'Vision Express', 2, 11),
(30, '47.66938249352783', '17.65104747427766', 'Libri Gyor Plaza Konyvesbolt', 2, 8);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` bigint(20) NOT NULL,
  `cost` decimal(19,2) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_income` bit(1) NOT NULL,
  `issued_at` date NOT NULL,
  `payment_method` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `cost`, `currency`, `description`, `is_income`, `issued_at`, `payment_method`, `user_id`) VALUES
(1, '15000.00', 'HUF', 'ruhat vettem', b'1', '2022-10-29', 0, 1),
(2, '14325.00', 'HUF', 'kaja', b'1', '2022-10-30', 1, 2),
(3, '650.00', 'HUF', 'hugom ruhaja', b'1', '2022-10-31', 0, 2),
(4, '11450.00', 'HUF', 'tesomnak fejhallgato', b'1', '2022-11-01', 0, 1),
(5, '12000.00', 'HUF', 'bubble tea', b'1', '2022-11-02', 1, 1),
(6, '12350.00', 'HUF', 'ruhat vettem', b'1', '2022-11-03', 0, 1),
(7, '6980.00', 'HUF', 'kaja', b'1', '2022-11-04', 1, 1),
(8, '1140.00', 'HUF', 'virag', b'1', '2022-11-05', 0, 2),
(9, '870.00', 'HUF', 'iskolataska', b'1', '2022-11-06', 1, 2),
(10, '5560.00', 'HUF', 'fuzetek', b'1', '2022-11-07', 0, 1),
(11, '40946.00', 'HUF', 'napszemuveg', b'1', '2022-11-08', 1, 2),
(12, '27374.00', 'HUF', 'kaja', b'1', '2022-11-09', 0, 1),
(13, '4204.00', 'HUF', 'nyaklanc', b'1', '2022-11-10', 1, 2),
(14, '27585.00', 'HUF', 'fulbevalo', b'1', '2022-11-11', 0, 2),
(15, '12732.00', 'HUF', 'ekszerek', b'1', '2022-11-12', 0, 1),
(16, '35463.00', 'HUF', 'karkoto', b'1', '2022-11-13', 0, 2),
(17, '9093.00', 'HUF', 'ruhat vettem', b'1', '2022-11-14', 0, 1),
(18, '8188.00', 'HUF', 'kaja', b'1', '2022-11-15', 1, 2),
(19, '20218.00', 'HUF', 'hugom ruhaja', b'1', '2022-11-16', 1, 1),
(20, '40601.00', 'HUF', 'tesomnak fejhallgato', b'1', '2022-11-17', 0, 2),
(21, '137410.00', 'HUF', 'tv karacsonyra', b'1', '2022-11-18', 1, 1),
(22, '19636.00', 'HUF', 'ruhat vettem', b'1', '2022-11-19', 0, 2),
(23, '11377.00', 'HUF', 'kaja', b'1', '2022-11-20', 1, 1),
(24, '19175.00', 'HUF', 'virag', b'1', '2022-11-21', 1, 1),
(25, '17405.00', 'HUF', 'iskolataska', b'1', '2022-11-22', 0, 1),
(26, '6728.00', 'HUF', 'fuzetek', b'1', '2022-11-23', 1, 2),
(27, '40483.00', 'HUF', 'napszemuveg', b'1', '2022-11-24', 0, 2),
(28, '39693.00', 'HUF', 'kaja', b'1', '2022-11-25', 1, 1),
(29, '16542.00', 'HUF', 'nyaklanc', b'1', '2022-11-26', 0, 2),
(30, '20360.00', 'HUF', 'fulbevalo', b'1', '2022-11-27', 1, 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `public_id` varchar(255) NOT NULL,
  `registration_date` datetime NOT NULL,
  `username` varchar(255) NOT NULL,
  `profile_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`user_id`, `email`, `is_admin`, `password`, `public_id`, `registration_date`, `username`, `profile_id`) VALUES
(1, 'jonasemil@gmail.com', b'1', 'JonasEmil11', '8952a10e-e0e0-4e66-a25e-56799af6d1d0', '2022-10-28 18:28:47', 'emilke11', 1),
(2, 'kissgabor14@gmail.com', b'1', 'Gaborka001', '87239190-98b1-438e-984a-2c2534e8c987', '2022-10-27 11:42:57', 'gabesz77', 2);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`address_id`);

--
-- A tábla indexei `budget`
--
ALTER TABLE `budget`
  ADD PRIMARY KEY (`budget_id`),
  ADD KEY `FKkuh8cj1roovp9nh6ut2igrxm2` (`user_id`);

--
-- A tábla indexei `business_category`
--
ALTER TABLE `business_category`
  ADD PRIMARY KEY (`category_id`);

--
-- A tábla indexei `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`),
  ADD KEY `FK2hsdsntwy25qr73fsvd7l3wu7` (`address_id`);

--
-- A tábla indexei `shop`
--
ALTER TABLE `shop`
  ADD PRIMARY KEY (`shop_id`),
  ADD KEY `FKqwhej4gio2sg92f7hmwprpbyi` (`address_id`),
  ADD KEY `FKjh2e42gk184ut1sk1xrwctle9` (`category_id`);

--
-- A tábla indexei `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `FKsg7jp0aj6qipr50856wf6vbw1` (`user_id`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UK_fuqka493llh9k0imxq2nqkby` (`public_id`),
  ADD UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  ADD KEY `FKof44u64o1d7scaukghm9veo23` (`profile_id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `address`
--
ALTER TABLE `address`
  MODIFY `address_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT a táblához `budget`
--
ALTER TABLE `budget`
  MODIFY `budget_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `business_category`
--
ALTER TABLE `business_category`
  MODIFY `category_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT a táblához `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT a táblához `shop`
--
ALTER TABLE `shop`
  MODIFY `shop_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT a táblához `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transaction_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `budget`
--
ALTER TABLE `budget`
  ADD CONSTRAINT `FKkuh8cj1roovp9nh6ut2igrxm2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Megkötések a táblához `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `FK2hsdsntwy25qr73fsvd7l3wu7` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`);

--
-- Megkötések a táblához `shop`
--
ALTER TABLE `shop`
  ADD CONSTRAINT `FKjh2e42gk184ut1sk1xrwctle9` FOREIGN KEY (`category_id`) REFERENCES `business_category` (`category_id`),
  ADD CONSTRAINT `FKqwhej4gio2sg92f7hmwprpbyi` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`);

--
-- Megkötések a táblához `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `FKsg7jp0aj6qipr50856wf6vbw1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Megkötések a táblához `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKof44u64o1d7scaukghm9veo23` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

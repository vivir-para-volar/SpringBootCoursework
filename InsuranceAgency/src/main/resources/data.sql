insert into employee(id, full_name, birthday, telephone, email, passport) values
(1, 'AgencyAdmin', '2021-09-01', '89209380662', 'agencyaadm@gmail.com', '1819567633'),
(2, 'Мишина Екатерина Федоровна', '1992-05-19', '89209430243', 'mishina@mail.ru', '1298454567'),
(3, 'Новиков Владимир Святославович', '1987-10-21', '89346566645', 'novikov@mail.ru', '2387006767'),
(4, 'Александров Филипп Сергеевич', '1999-09-05', '89665556734', 'aleksandrov@mail.ru', '1799545433'),
(5, 'Морозова Александра Максимовна', '1998-03-15', '89340043434', 'morozova@mail.ru', '1765767676');

insert into car(id, model, vin, registration_plate, vehicle_passport) values
(1, 'Hyundai Santa Fe (3G)', '3GNDA23D08S544387', 'ОР897Ш09', '67ТЬ907867'),
(2, 'KIA Ceed (2G)', 'JN1CZ24A8LX094080', 'АЛ090РВ12', '25ТЧ082337'),
(3, 'Renault Fluence', '3N1CC1AP8AL392058', 'Е222ХС22', '89ТЦ348280'),
(4, 'Rover 45', '1N6BF0KL5CN138975', 'А343РР90', '34ТВ734342'),
(5, 'Лада 2101', 'JN8AF5MV4DT293416', 'ВО378Р79', '38ТЕ489348'),
(6, 'Opel Astra H', '1GCESBFE2C8192977', 'ЛО839Р23', '45ТФ893489'),
(7, 'Mazda 626 IV', '12343HJKKL4564320', '239GHj67', '1237767833');

insert into photo(id, path, upload_date, car_id) values
(1, '88f7e05s-960.jpg', '2021-10-07', 1),
(2, 'fabeb6s-960.jpg', '2021-10-07', 1),
(3, '30c0cas-960.jpg', '2021-10-01', 2),
(4, 'WDZ4OyI0uP8MIzVeV0vUx7FHc08-480.jpg', '2021-10-01', 2),
(5, '2duHfUNCY.jpg', '2021-06-15', 3),
(6, 'gen270_1121631.jpg', '2021-06-15', 3),
(7, 'Megane-4-Sedan-550x300.jpg', '2021-06-15', 3),
(8, 'rover_45_2004-600.jpg', '2020-11-11', 4),
(9, 'big_91446_1.jpg', '2020-11-11', 4),
(10, 'c437bd3eb_800x400.jpg', '2021-08-05', 5),
(11, 'big_1131762.jpg', '2022-10-11', 6),
(12, 'QdMQLSYxdx.jpg', '2022-10-11', 6),
(13, '34b7e4as-960.jpg', '2022-12-09', 7);

insert into policyholder(id, full_name, birthday, telephone, email, passport) values
(1, 'Шестаков Даниил Данилович', '1990-01-01', '89969798752', 'aspshestakovdaniil@gmail.com', '4395200670'),
(2, 'Бессонов Иван Савельевич', '1971-01-16', '89180741287', null, '7330729340'),
(3, 'Морозова Софья Яновна', '1979-08-15', '89347650895', 'aspmorozovasofi@gmail.com', '2218864204'),
(4, 'Гаврилов Борис Дмитриевич', '1999-03-15', '89017654595', 'laba4laba44@gmail.com', '7818864232'),
(5, 'Колесова Эмилия Тимофеевна', '1991-11-08', '89217652333', null, '1134711573'),
(6, 'Корнилов Ярослав Максимович', '1976-01-12', '89200755299', null, '1220729563');

insert into person_allowed_to_drive(id, full_name, driving_licence) values
(1, 'Бессонов Иван Савельевич', '7345901132'),
(2, 'Васильева Мария Ильинична', '2929222999'),
(3, 'Попов Иван Александрович', '1731634821'),
(4, 'Маркова Таисия Серафимовна', '3489344688'),
(5, 'Сидоров Максим Максимович', '7292742382'),
(6, 'Корнилов Ярослав Максимович', '1907540089'),
(7, 'Морозова Софья Яновна', '5278982365'),
(8, 'Гаврилов Борис Дмитриевич', '7342367288'),
(9, 'Шестаков Даниил Данилович', '4397562113'),
(10, 'Колесова Эмилия Тимофеевна', '8734284883');

insert into policy(id, insurance_type, insurance_premium, insurance_amount, date_of_conclusion, expiration_date, policyholder_id, car_id, employee_id) values
(1, 'ОСАГО', 8000, 500000, '2021-10-07', '2022-10-07', 1, 1, 1),
(2, 'КАСКО', 10000, 600000, '2021-10-07', '2022-10-07', 1, 1, 1),
(3, 'ОСАГО', 4500, 500000, '2021-10-01', '2022-04-01', 2, 2, 2),
(4, 'ОСАГО', 9800, 500000, '2021-06-15', '2022-06-15', 3, 3, 3),
(5, 'КАСКО', 12000, 800000, '2020-11-11', '2021-11-11', 4, 4, 4),
(6, 'ОСАГО', 7000, 500000, '2021-08-05', '2022-08-05', 5, 5, 5);

insert into policy_persons_allowed_to_drive(policies_id, persons_allowed_to_drive_id) values
(1, 9),
(1, 8),
(2, 9),
(3, 1),
(3, 10),
(4, 7),
(4, 1),
(4, 4),
(5, 8),
(6, 10),
(6, 2);

insert into insurance_event(id, incident_date, insurance_payment, description, policy_id) values
(1, '2022-04-20', 12000, 'ДТП произошло в результате столкновения двух транспортных средств; Гражданская ответственность обоих владельцев застрахована по ОСАГО; Вред причинен только этим двум транспортным средствам; Страхователь - виновник ДТП', 1),
(2, '2022-06-20', 40000, 'ДТП произошло в результате столкновения двух транспортных средств; Гражданская ответственность обоих владельцев застрахована по ОСАГО и КАСКО; Вред причинен только этим двум транспортным средствам; Страхователь не является виновником ДТП', 2),
(3, '2021-11-22', 1000, 'ДТП произошло в результате столкновения двух транспортных средств; Гражданская ответственность обоих владельцев застрахована по ОСАГО; Вред причинен только этим двум транспортным средствам; Страхователь не является виновником ДТП', 4),
(4, '2022-02-14', 21000, 'ДТП произошло в результате столкновения двух транспортных средств; Гражданская ответственность обоих владельцев застрахована по ОСАГО; Вред причинен только этим двум транспортным средствам; Страхователь - виновник ДТП', 4),
(5, '2022-04-19', 2500, 'ДТП произошло в результате столкновения двух транспортных средств; Гражданская ответственность обоих владельцев застрахована по ОСАГО; Вред причинен только этим двум транспортным средствам; Страхователь - виновник ДТП', 6);
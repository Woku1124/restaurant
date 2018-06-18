insert into personal_data (id, name, surname) values (1, 'Kinga','Mirkiewicz');
insert into personal_data (id, name, surname) values (2, 'Piotr','Morawski');
insert into personal_data (id, name, surname) values (3, 'Adam','Tomaszczyk');
insert into personal_data (id, name, surname) values (4, 'Piotr','Rokicki');
insert into personal_data (id, name, surname) values (5, 'Krystian','Potentas');

insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (1, 'Warszawa', 'aa@wp.pl', null, 1, 123456789, 'Piotrkowska');
insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (2, 'Rawicz', 'bb@wp.pl', 12, 3, 987654321, 'Narutowicza');
insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (3, 'Warszawa', 'cc@wp.pl', 41, 71, 787878787, 'Zielona');
insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (4, 'Sopot', 'dd@wp.pl', 5, 15, 123412345, 'Jasna');
insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (5, 'Muchimor', 'ee@wp.pl', 4, null, 987698765, 'Siwa');

insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (1, '2018-05-16', '2018-05-16', 6, 1, 1);
insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (2, '2018-06-15', '2018-06-15', 7, 2, 2);
insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (3, '2018-06-15', '2018-06-15', 10, 3, 3);
insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (4, '2018-05-17', '2018-05-17', 17, 4, 4);
insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (5, '2018-06-01', null, 9, 5, 5);

insert into dish_food_order(id, dish, food_order) values (1, 20, 1);
insert into dish_food_order(id, dish, food_order) values (2, 21, 1);
insert into dish_food_order(id, dish, food_order) values (3, 10, 2);
insert into dish_food_order(id, dish, food_order) values (4, 11, 2);
insert into dish_food_order(id, dish, food_order) values (3, 10, 3);
insert into dish_food_order(id, dish, food_order) values (4, 20, 3);
insert into dish_food_order(id, dish, food_order) values (5, 20, 3);
insert into dish_food_order(id, dish, food_order) values (6, 12, 4);
insert into dish_food_order(id, dish, food_order) values (7, 13, 4);
insert into dish_food_order(id, dish, food_order) values (8, 14, 4);
insert into dish_food_order(id, dish, food_order) values (9, 15, 5);

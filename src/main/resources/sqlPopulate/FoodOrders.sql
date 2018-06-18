insert into personal_data (id, name, surname) values (101, 'Kinga','Mirkiewicz');
insert into personal_data (id, name, surname) values (102, 'Piotr','Morawski');
insert into personal_data (id, name, surname) values (103, 'Adam','Tomaszczyk');
insert into personal_data (id, name, surname) values (104, 'Piotr','Rokicki');
insert into personal_data (id, name, surname) values (105, 'Krystian','Potentas');

insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (101, 'Warszawa', 'aa@wp.pl', null, 1, 123456789, 'Piotrkowska');
insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (102, 'Rawicz', 'bb@wp.pl', 12, 3, 987654321, 'Narutowicza');
insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (103, 'Warszawa', 'cc@wp.pl', 41, 71, 787878787, 'Zielona');
insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (104, 'Sopot', 'dd@wp.pl', 5, 15, 123412345, 'Jasna');
insert into address (id, city, email, flat_nr, home_nr, phone_nr, street) values (105, 'Muchimor', 'ee@wp.pl', 4, null, 987698765, 'Siwa');

insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (101, '2018-05-16', '2018-05-16', 6, 101, 101);
insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (102, '2018-06-15', '2018-06-15', 7, 102, 102);
insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (103, '2018-06-15', '2018-06-15', 10, 103, 103);
insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (104, '2018-05-17', '2018-05-17', 17, 104, 104);
insert into food_order(id, date_of_order, date_of_realization, full_price, address, personal_data) values (105, '2018-06-01', null, 9, 105, 105);

insert into dish_food_order(id, dish, food_order) values (101, 20, 101);
insert into dish_food_order(id, dish, food_order) values (102, 21, 101);
insert into dish_food_order(id, dish, food_order) values (103, 10, 102);
insert into dish_food_order(id, dish, food_order) values (104, 11, 102);
insert into dish_food_order(id, dish, food_order) values (105, 10, 103);
insert into dish_food_order(id, dish, food_order) values (106, 20, 103);
insert into dish_food_order(id, dish, food_order) values (107, 20, 103);
insert into dish_food_order(id, dish, food_order) values (108, 12, 104);
insert into dish_food_order(id, dish, food_order) values (109, 13, 104);
insert into dish_food_order(id, dish, food_order) values (110, 14, 104);
insert into dish_food_order(id, dish, food_order) values (111, 15, 105);

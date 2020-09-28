insert into user (username, password, city, zip, state, street, house_number, phone_number) values ('user', '$2y$12$WBXIL31LyKA23QZAyVl60uNVpaH8HRcHPg4iUx4Ef7J88xzWql5ka', 'x', '11-111',
'xxx', 'xxx', '1', '592123124');
insert into user (username, password, city, zip, state, street, house_number, phone_number) values ('arken', '$2y$12$WBXIL31LyKA23QZAyVl60uNVpaH8HRcHPg4iUx4Ef7J88xzWql5ka', 'Lubin', '59-300',
'dolnośląskie', 'Paderewskiego', '3/2', '123123123');
insert into user (username, password, city, zip, state, street, house_number, phone_number) values ('deodeo', '$2y$12$WBXIL31LyKA23QZAyVl60uNVpaH8HRcHPg4iUx4Ef7J88xzWql5ka', 'Wrocław', '52-340',
'dolnośląskie', 'Jana Pawła II', '92/3', '424264231');
insert into user (username, password, city, zip, state, street, house_number, phone_number) values ('marek_77', '$2y$12$WBXIL31LyKA23QZAyVl60uNVpaH8HRcHPg4iUx4Ef7J88xzWql5ka', 'Warszawa', '50-222',
'mazowieckie', 'Kolejowa', '7', '120425423');
insert into user (username, password, city, zip, state, street, house_number, phone_number) values ('handlarz123', '$2y$12$WBXIL31LyKA23QZAyVl60uNVpaH8HRcHPg4iUx4Ef7J88xzWql5ka', 'Gdańsk', '22-222',
'pomorskie', 'Kwiatowa', '17/5', '123009421');
insert into user (username, password, city, zip, state, street, house_number, phone_number) values ('car_dealer', '$2y$12$WBXIL31LyKA23QZAyVl60uNVpaH8HRcHPg4iUx4Ef7J88xzWql5ka', 'Poznań', '33-333',
'wielkopolskie', 'Pastelowa', '3', '924857312');
insert into user (username, password, city, zip, state, street, house_number, phone_number) values ('KOMIS_LUBIN', '$2y$12$WBXIL31LyKA23QZAyVl60uNVpaH8HRcHPg4iUx4Ef7J88xzWql5ka', 'Lubin', '59-300',
'dolnośląskie', 'Lipowa', '9', '917864364');
insert into user (username, password, city, zip, state, street, house_number, phone_number) values ('Danuta95', '$2y$12$WBXIL31LyKA23QZAyVl60uNVpaH8HRcHPg4iUx4Ef7J88xzWql5ka', 'Zielona Góra', '88-888',
'lubuskie', 'Fabryczna', '4', '948234123');

insert into brand (id, name) values (1, 'Audi');
insert into brand (id, name) values (2, 'BMW');

insert into offer (id, name, brand_id, description, where_it_is, why_for_sale, is_for_negotiation, price, placed_at, user_id) values (1,
'Audi A4 B5', 1, 'Witam sprzedam Audi z automatem, bez elektroniki chodzi bez problemowo . Wyposażony w 2 poduszki powietrza, elektryczne szyby-przód, klimatyzacja ,radio ,wspomaganie kierownicy ,odpinany hak, central zamek z alarmem -multilok ,aluminiowe felgi, opony wielosezonowe kupione rok temu. Wszystkie wymiany olejów i klocki itd. robione na bieżąco . Auto po przeglądzie w lipcu. W aucie nie palono. Auto kupione z Berlina przyjechało na niemieckich numerach i w Polsce rejestrowane.',
'Lubin', 'Znudziło mi się', true, 2000, NOW(), 2);
insert into offer (id, name, brand_id, description, where_it_is, why_for_sale, is_for_negotiation, price, placed_at, user_id) values (2,
'Opel Astra GTC 1.9CDTI', 1,  'Mam do zaoferowania sprowadzoną z NIEMIEC, jedyną w swoim rodzaju ASTRE H GTC w pakiecie OPC.
Ten egzemplarz jest wyposażony w bezawaryjny, bardzo dynamiczny a zaciszem ekonomicznym silnik 1.9 cdti 150 KM.',
'Warszawa', 'Znudziło mi się', true, 13000, NOW(), 4);


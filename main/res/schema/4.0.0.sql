/* 
 * Filename:    4.0.0 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */

create table core_sectors
(
    id int not null auto_increment,
    name varchar(255) not null,

    primary key(id)
);

INSERT INTO core_sectors VALUES (1, 'Geral' );
INSERT INTO core_sectors VALUES (2, 'NTI');
INSERT INTO core_sectors VALUES (3, 'RH' );


alter table core_users add ref_sector int not null default 1, ADD FOREIGN KEY fk_users_ref_sectors(ref_sector) REFERENCES core_sectors(id) ;

create table req_categories 
(
    id int not null,
    name varchar(255) not null,
    info text,

    primary key(id)
);
-- 
-- INSERT INTO req_categories VALUES ( 1, 'Financeiro' , '' );
-- INSERT INTO req_categories VALUES ( 2, 'Academico'  , 'lorem ipsum in dolor' );
-- 
-- 
-- CREATE TABLE req_types 
-- (
--     id int not null,
--     name varchar(255) not null,
--     info text,
--     ref_category int not null,
-- 
--     primary key( id ),
--     foreign key fk_req_category (ref_category) references req_categories(id)
-- );
-- 
-- INSERT INTO req_types VALUES (1, 'Solicitação do FIES', 'Requisição para receber o FIES', 2);
-- INSERT INTO req_types VALUES (2, 'Aumento Salarial', '', 1);
-- INSERT INTO req_types VALUES (3, 'Reembolso Despesas', '', 1);
-- 
-- 
-- CREATE TABLE req_fields 
-- (
--     id       int not null,
--     label    varchar(255) null,
--     runtime  int null,
--     required tinyint not null,
--     ref_type int not null,
--     seq      int default 0,
--     
--     primary key(id),
--     foreign key fk_fild_req_type (ref_type) references req_types(id)
-- );
-- 
-- INSERT INTO req_fields VALUES (1, 'Data Solicitação', 3, 1, 1, 0);
-- INSERT INTO req_fields VALUES (2, 'Data Solicitação', 3, 1, 2, 0);
-- INSERT INTO req_fields VALUES (3, 'Data Solicitação', 3, 1, 3, 0);
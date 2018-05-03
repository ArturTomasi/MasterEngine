/**
 * Author:  artur
 * Created: Feb 5, 2018
 */
alter table eng_licenses rename to core_licenses;
alter table eng_cities   rename to core_cities;
alter table eng_states   rename to core_states;
alter table eng_clients  rename to core_clients;
alter table eng_contacts rename to core_contacts;


create table fin_posting_categories
(
    id                    int          not null auto_increment,
    name                  varchar ( 200 ) not null,
    info                  text,
    posting_type      	  integer         not null,

    constraint pk_posting_categories primary key ( id ),
    constraint uq_cl_name            unique ( name, posting_type )
);

create table fin_postings
(
    id                       int          not null auto_increment,
    name                     varchar ( 200 ) not null,
    info                     text,
    real_date                date,
    estimate_date            date            not null,  
    fl_completion_auto       boolean not null default false,
    real_value               double,
    estimate_value           double         not null, 
    portion                  int not null,
    portion_total            int not null,
    fl_repeat                boolean not null  default false,
    state                    int not null  default 0,
    ref_category             int not null, 
    ref_user                 int not null,
    completion_type          int ,
    ref_client               int not null,
    ref_posting              int ,

    primary key ( id ),
    foreign key fk_posting_users (ref_user) references core_users (id),
    foreign key fk_postings_ref_posting (ref_posting) references fin_postings (id),
    foreign key fk_postings_ref_category (ref_category) references fin_posting_categories (id),
    foreign key fk_postings_ref_client (ref_client) references core_clients (id)
);


create table core_attachments
(
    id   int          not null auto_increment,
    name varchar ( 200 ) not null,
    url  varchar ( 200 ) not null,
    info varchar ( 200 ) ,

    constraint pk_attachments_id primary key ( id )
);


/* 
 * CREATE INDEX FOR TABLES 
 */

create index index_ref_user_posting on postings ( ref_user );
create index index_ref_posntig_posting on postings ( ref_posting ); 


insert into fin_posting_categories ( name, info, posting_type ) values ( 'EDUCAÇÃO',         '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'ALIMENTAÇÃO',      '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'COMBUSTÍVEL',      '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'OUTROS',           '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'SALÁRIO',          '', 0 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'OUTROS',           '', 0 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'EMPRÉSTIMO',       '', 0 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'EMPRÉSTIMO',       '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'LAZER',            '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'SAÚDE',            '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'TRABALHOS EXTRAS', '', 0 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'ALUGUEL',          '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'CONDOMINIO', 	 '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'AGUA',             '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'LUZ',              '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'TELEFONE',         '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'ACADEMIA', 	 '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'INTERNET', 	 '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'MANUTENÇAO CASA',  '', 1 );
insert into fin_posting_categories ( name, info, posting_type ) values ( 'MANUTENÇAO CARRO', '', 1 );


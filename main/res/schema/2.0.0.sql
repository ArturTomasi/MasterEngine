/**
 * Author:  artur
 * Created: Feb 5, 2018
 */
create table eng_licenses
(
    id int not null auto_increment,
    ref_user int not null,
    module_name varchar(160) not null,
    session_id varchar(160) not null,
    dt_created date not null,
    
    primary key(id),
    foreign key fk_user_licenses (ref_user) references core_users (id)
);


insert into core_roles values ( 3, 'recipes', 'Gerenciador de Receitas' );

alter table eng_samples change dt_executed dt_executed timestamp;
alter table eng_samples change dt_rupture  dt_executed timestamp;
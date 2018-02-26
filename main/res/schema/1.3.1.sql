/**
 * Author:  artur
 * Created: Jan 22, 2018
 */
alter table eng_jobs add column address varchar(300);
alter table eng_jobs add column cnpj varchar(14) not null;
alter table eng_jobs add column ref_city int; 

alter table eng_jobs add constraint fk_jobs_cities foreign key (ref_city ) references eng_cities(id); 

alter table eng_contacts add column ref_job int;

alter table eng_contacts add constraint fk_contacts_jobs foreign key(ref_job ) references eng_jobs(id); 

/**
 * UPDATE
 */
alter table eng_jobs change column cnpj cei varchar(14) not null;
update eng_jobs set cei = ' ';


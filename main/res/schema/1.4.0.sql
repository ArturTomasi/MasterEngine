/**
 * Author:  artur
 * Created: Feb 5, 2018
 */
alter table eng_jobs MODIFY cei varchar(14) null;


/**
 * Adicionar aos contatos o job como unique
 */
alter table eng_contacts add constraint unique_key_contanct unique ( ref_client, ref_job , position  );

alter table eng_contacts drop index ref_client;
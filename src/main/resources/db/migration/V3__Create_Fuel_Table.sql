create table if not exists project_green.fuel_data
(
	id serial not null
		constraint fuel_data_pk
			primary key,
	region_id integer not null
		constraint fuel_data_regional_data_id_fk
			references project_green.regional_data
				on update cascade on delete cascade,
	fuel text not null,
	percentage integer
);

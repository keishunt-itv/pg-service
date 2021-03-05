create table project_green.regional_data
(
    id serial not null
        constraint regional_data_pk
            primary key,
    region text,
    forecast     integer,
    index_rating text
);

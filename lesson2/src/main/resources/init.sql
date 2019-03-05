create table if not exists users
(
  id_user serial not null
    constraint users__pkey
    primary key,
  name varchar(255)
);

create table if not exists autos
(
  id_auto serial not null
    constraint autos__pkey
    primary key,
  model varchar(255),
  id_user integer REFERENCES users( id_user )
);
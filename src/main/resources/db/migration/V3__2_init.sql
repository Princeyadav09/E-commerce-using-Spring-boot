create sequence users_id_seq start 4 increment 1;

create table if not exists user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

create table if not exists users
(
    id                  int8    not null,
    activation_code     varchar(255),
    active              boolean not null,
    address             varchar(255),
    city                varchar(255),
    email               varchar(255),
    first_name          varchar(255),
    last_name           varchar(255),
    password            varchar(255),
    password_reset_code varchar(255),
    phone_number        varchar(255),
    post_index          varchar(255),
    provider            varchar(255),
    primary key (id)
);
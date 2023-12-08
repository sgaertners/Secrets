create database if not exists secrets;

create table if not exists users (
     id                      bigint auto_increment primary key,
     account_non_expired     tinyint(1)   not null,
     account_non_locked      tinyint(1)   not null,
     credentials_non_expired tinyint(1)   not null,
     email                   varchar(255) not null,
     enabled                 tinyint(1)   not null,
     name                    varchar(255) null,
     password                varchar(255) not null,
     username                varchar(255) not null,
     constraint UK_6dotkott2kjsp8vw4d0m25fb7
         unique (email),
     constraint UK_r43af9ap4edm43mmtq01oddj6
         unique (username)
);

create table if not exists roles (
     id   bigint auto_increment primary key,
     name varchar(255) null
);

create table if not exists users_roles (
     user_id bigint not null,
     role_id bigint not null,
     primary key (user_id, role_id),
     constraint FK2o0jvgh89lemvvo17cbqvdxaa
         foreign key (user_id) references users (id),
     constraint FKj6m8fwv7oqv74fcehir1a9ffy
         foreign key (role_id) references roles (id)
);

create table if not exists languages (
    id             int not null primary key,
    locale         varchar(255) null,
    messagekey     varchar(255) null,
    messagecontent varchar(4096) null
);
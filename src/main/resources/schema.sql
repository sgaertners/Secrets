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

create table if not exists apps (
    id          int not null primary key,
    role        varchar(255) not null unique,
    endpoint    varchar(255) not null unique,
    image       Blob null,
    headline    varchar(255),
    text        varchar(1024),
    button      varchar(255)
);

create table if not exists whiskeys (
    id          bigint auto_increment primary key,
    name        varchar(255),
    age         int,
    vol         float,
    size        float,
    taste       varchar(255),
    barrel      varchar(255),
    typ        varchar(255),
    destillery  varchar(255),
    town        varchar(255),
    country     varchar(255),
    image       LONGBLOB,
    imagename   varchar(255),
    tastingnote varchar(1024),
    tastingdate DATETIME,
    userId      bigint,
    userName    varchar(255)
);

# create table if not exists category (
#     id   bigint auto_increment primary key,
#     category varchar(255)
# );

create table if not exists knowhow (
    id          bigint auto_increment primary key,
    date        DATETIME,
    URL         varchar(255),
    category    varchar(255),
    description text,
    FULLTEXT ( description ),
    image       LONGBLOB
);

# create fulltext index knowhow_desc_idx
#     on knowhow (description);

# create table if not exists knowhow_categories (
#        knowhow_id bigint not null,
#        category_id bigint not null,
#        primary key (knowhow_id, category_id),
#        constraint cat1
#            foreign key (knowhow_id) references knowhow (id),
#        constraint cat2
#            foreign key (category_id) references category (id)
# );

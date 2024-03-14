create table if not exists address_book
(
    id         bigint auto_increment
        primary key,
    user_id    bigint                       not null,
    consignee  varchar(12)                  not null,
    sex        tinyint unsigned             not null,
    phone      varchar(10)                  not null,
    detail     varchar(200) charset utf8mb4 not null,
    label      varchar(20) charset utf8mb4  not null,
    is_default tinyint unsigned default '0' not null
)
    collate = utf8mb3_bin;

create table if not exists category
(
    id          bigint auto_increment
        primary key,
    type        tinyint unsigned             not null,
    name        varchar(20)                  not null,
    sort        tinyint unsigned default '0' not null,
    status      tinyint unsigned default '0' not null,
    create_time datetime                     not null,
    update_time datetime                     not null,
    create_user bigint                       not null,
    update_user bigint                       not null,
    constraint idx_category_name
        unique (name)
)
    collate = utf8mb3_bin;

create table if not exists dish
(
    id          bigint auto_increment
        primary key,
    name        varchar(20)                  not null,
    category_id bigint                       not null,
    price       decimal(7, 2)                not null,
    image       varchar(255)                 not null,
    description varchar(255)                 null,
    status      tinyint unsigned default '1' not null,
    create_time datetime                     not null,
    update_time datetime                     not null,
    create_user bigint                       not null,
    update_user bigint                       not null,
    constraint idx_dish_name
        unique (name)
)
    collate = utf8mb3_bin;

create table if not exists dish_flavor
(
    id      bigint auto_increment
        primary key,
    dish_id bigint       not null,
    name    varchar(20)  null,
    value   varchar(255) null
)
    collate = utf8mb3_bin;

create table if not exists employee
(
    id          bigint auto_increment
        primary key,
    name        varchar(12)                  not null,
    username    varchar(20)                  not null,
    password    varchar(64)                  not null,
    phone       varchar(10)                  not null,
    sex         tinyint unsigned             not null,
    id_number   varchar(9)                   not null,
    status      tinyint unsigned default '1' not null,
    create_time datetime                     not null,
    update_time datetime                     not null,
    create_user bigint                       not null,
    update_user bigint                       not null,
    constraint idx_username
        unique (username)
)
    collate = utf8mb3_bin;

create table if not exists order_detail
(
    id          bigint auto_increment
        primary key,
    name        varchar(32)              not null,
    image       varchar(255)             not null,
    order_id    bigint                   not null,
    dish_id     bigint                   null,
    setmeal_id  bigint                   null,
    dish_flavor varchar(50)              null,
    number      int unsigned default '1' not null,
    amount      decimal(7, 2)            not null
)
    collate = utf8mb3_bin;

create table if not exists orders
(
    id                      bigint auto_increment
        primary key,
    number                  varchar(50)                  not null,
    status                  tinyint unsigned default '1' not null,
    user_id                 bigint                       not null,
    address_book_id         bigint                       not null,
    order_time              datetime                     not null,
    checkout_time           datetime                     null,
    pay_method              tinyint unsigned default '1' not null,
    pay_status              tinyint unsigned default '0' not null,
    amount                  decimal(7, 2)                not null,
    remark                  varchar(50)                  null,
    phone                   varchar(10)                  not null,
    address                 varchar(50)                  not null,
    user_name               varchar(12)                  null,
    consignee               varchar(12)                  not null,
    cancel_reason           varchar(50)                  null,
    rejection_reason        varchar(50)                  null,
    cancel_time             datetime                     null,
    estimated_delivery_time datetime                     null,
    delivery_status         tinyint unsigned default '1' not null,
    delivery_time           datetime                     null,
    pack_amount             tinyint unsigned             not null,
    tableware_number        tinyint unsigned             not null,
    tableware_status        tinyint unsigned default '1' not null
)
    collate = utf8mb3_bin;

create table if not exists setmeal
(
    id          bigint auto_increment
        primary key,
    category_id bigint                       not null,
    name        varchar(20)                  not null,
    price       decimal(7, 2)                not null,
    status      tinyint unsigned default '1' not null,
    description varchar(255)                 null,
    image       varchar(255)                 not null,
    create_time datetime                     not null,
    update_time datetime                     not null,
    create_user bigint                       not null,
    update_user bigint                       not null,
    constraint idx_setmeal_name
        unique (name)
)
    collate = utf8mb3_bin;

create table if not exists setmeal_dish
(
    id         bigint auto_increment
        primary key,
    setmeal_id bigint         not null,
    dish_id    bigint         not null,
    name       varchar(32)    not null,
    price      decimal(10, 2) not null,
    copies     int unsigned   not null
)
    collate = utf8mb3_bin;

create table if not exists shopping_cart
(
    id          bigint auto_increment
        primary key,
    name        varchar(20)                  not null,
    image       varchar(255)                 not null,
    user_id     bigint                       not null,
    dish_id     bigint                       null,
    setmeal_id  bigint                       null,
    dish_flavor varchar(50)                  null,
    number      tinyint unsigned default '1' not null,
    amount      decimal(7, 2)                not null,
    create_time datetime                     not null
)
    collate = utf8mb3_bin;

create table if not exists user
(
    id          bigint auto_increment
        primary key,
    openid      varchar(45)      not null,
    name        varchar(12)      null,
    phone       varchar(10)      null,
    sex         tinyint unsigned null,
    id_number   varchar(9)       null,
    avatar      varchar(500)     null,
    create_time datetime         not null
)
    collate = utf8mb3_bin;


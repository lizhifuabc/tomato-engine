drop table if exists example;
create table example(
  id bigint not null auto_increment,
  name varchar(255) not null,
  age int(11) not null,
  code varchar(255) not null,
  tenant_id int(11) not null,
  create_time datetime not null,
  primary key(id)
)

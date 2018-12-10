
-- 为 JDBC implementation of the UserDetailsService (JdbcDaoImpl) 或 <jdbc-user-service data-source-ref= >
-- MySQL

drop table users;
create table users(
      username varchar2(30) not null primary key,
      password varchar2(60) not null,
      enabled number(1) not null);

drop table  authorities;

create table authorities (
    username varchar(30) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);
    
   insert into users(username,password,enabled)values('user','$2a$10$7L49D48WyQgMFn0yAbniX.WYRs5RkTPErPhpEVV91R1x0mx0ak76y',1);
   -- user 密码bcrypt
   insert into users(username,password,enabled)values('admin','$2a$10$wdPgE/GODVeARl1WGumnIegNW0O0OQ//GRlgQfd4QIctjRS9e./ee',1);
   -- admin
   
   insert into authorities(username,authority)values('user','ROLE_USER');
   insert into authorities(username,authority)values('admin','ROLE_ADMIN');
   insert into authorities(username,authority)values('admin','ROLE_USER');
   
   
-- for <remember-me data-source-ref="dataSource">
create table persistent_logins (username varchar(64) not null,
                                series varchar(64) primary key,
                                token varchar(64) not null,
                                last_used timestamp not null);
                                 
   
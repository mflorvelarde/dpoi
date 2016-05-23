# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table article (
  id                            bigint not null,
  articleinpath                 bigint,
  name                          varchar(255),
  slughsha                      varchar(255),
  content                       varchar(255),
  author                        varchar(255),
  published                     varchar(255),
  tuft                          varchar(255),
  title                         varchar(255),
  constraint pk_article primary key (id)
);
create sequence article_seq;

create table domain (
  domainid                      bigint not null,
  domain                        varchar(255),
  name                          varchar(255),
  constraint pk_domain primary key (domainid)
);
create sequence domain_seq;

create table path (
  pathid                        bigint not null,
  name                          varchar(255),
  domainpaths                   bigint,
  constraint pk_path primary key (pathid)
);
create sequence path_seq;

create table prvademecum (
  id                            bigint not null,
  domain                        varchar(255),
  path                          varchar(255),
  slugsha                       varchar(255),
  describes                     varchar(255),
  title                         varchar(255),
  laboratory                    varchar(255),
  constraint pk_prvademecum primary key (id)
);
create sequence prvademecum_seq;

alter table article add constraint fk_article_articleinpath foreign key (articleinpath) references path (pathid) on delete restrict on update restrict;
create index ix_article_articleinpath on article (articleinpath);

alter table path add constraint fk_path_domainpaths foreign key (domainpaths) references domain (domainid) on delete restrict on update restrict;
create index ix_path_domainpaths on path (domainpaths);


# --- !Downs

alter table article drop constraint if exists fk_article_articleinpath;
drop index if exists ix_article_articleinpath;

alter table path drop constraint if exists fk_path_domainpaths;
drop index if exists ix_path_domainpaths;

drop table if exists article;
drop sequence if exists article_seq;

drop table if exists domain;
drop sequence if exists domain_seq;

drop table if exists path;
drop sequence if exists path_seq;

drop table if exists prvademecum;
drop sequence if exists prvademecum_seq;


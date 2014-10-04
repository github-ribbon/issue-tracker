
-- IssueTracker 

-- Test data



-- removes all data

delete from affected_version;
delete from fixed_version;
delete from issue_tag;
delete from response;
delete from issue;
delete from version;
delete from project;
delete from usr;
delete from issue_type;
delete from status;
delete from priority;

-- status
insert into status(status_id) values('o');
insert into status(status_id) values('r');

--issue_type
insert into issue_type(issue_type_id) values('b');
insert into issue_type(issue_type_id) values('d');
insert into issue_type(issue_type_id) values('i');
insert into issue_type(issue_type_id) values('n');
insert into issue_type(issue_type_id) values('r');
insert into issue_type(issue_type_id) values('t');

-- priority
insert into priority(priority_id) values('i');
insert into priority(priority_id) values('a');

-- usr
insert into usr(user_id,name, surname,password) values('user1','Jan','Kowalski','$2a$10$RPQL9PgYQY/FYZCabJj0MOWpHQfEayw6M/wKTGkZVWZPrQeQKWkhy');
insert into usr(user_id,name, surname,password) values('user2','Tomasz','Nowakowski','$2a$10$RPQL9PgYQY/FYZCabJj0MOWpHQfEayw6M/wKTGkZVWZPrQeQKWkhy');
insert into usr(user_id,name, surname,password) values('user3','Katarzyna','Malinowska','$2a$10$UbojSnGHib7J4AuWsohowOzI7g3DRCTaiojX2FyCRsrj62WKMD.iW');
insert into usr(user_id,name, surname,password) values('user4','Piotr','Zieliński','$2a$10$KIBk9dqudRE7Ggv50rcvyu1dzzpHsAkUIxMpgYeRc00T6OG/6kG32');
insert into usr(user_id,name, surname,password) values('user5','Anna','Wiśniewska','$2a$10$KIBk9dqudRE7Ggv50rcvyu1dzzpHsAkUIxMpgYeRc00T6OG/6kG32');
insert into usr(user_id,name, surname,password) values('user6','Stefan','Wojciechowski','$2a$10$KIBk9dqudRE7Ggv50rcvyu1dzzpHsAkUIxMpgYeRc00T6OG/6kG32');
insert into usr(user_id,name, surname,password) values('user7','Dorota','Wolińska','$2a$10$KIBk9dqudRE7Ggv50rcvyu1dzzpHsAkUIxMpgYeRc00T6OG/6kG32');
insert into usr(user_id,name, surname,password) values('user8','Mariusz','Orzechowski','$2a$10$KIBk9dqudRE7Ggv50rcvyu1dzzpHsAkUIxMpgYeRc00T6OG/6kG32');
insert into usr(user_id,name, surname,password) values('user9','Sylwia','Kowalska','$2a$10$KIBk9dqudRE7Ggv50rcvyu1dzzpHsAkUIxMpgYeRc00T6OG/6kG32');
insert into usr(user_id,name, surname,password) values('user10','Adam','Sowicki','$2a$10$KIBk9dqudRE7Ggv50rcvyu1dzzpHsAkUIxMpgYeRc00T6OG/6kG32s');


-- project





insert into project(owner_id, project_id, description) values('user1','project1','project description');
insert into project(owner_id, project_id, description) values('user1','project2','project description');
insert into project(owner_id, project_id, description) values('user1','project3','project description');
insert into project(owner_id, project_id, description) values('user2','project1','project description');
insert into project(owner_id, project_id, description) values('user3','project1','project description');
insert into project(owner_id, project_id, description) values('user4','project1','project description');
insert into project(owner_id, project_id, description) values('user4','project2','project description');
insert into project(owner_id, project_id, description) values('user5','project1','project description');
insert into project(owner_id, project_id, description) values('user6','project1','project description');
insert into project(owner_id, project_id, description) values('user6','project2','project description');


-- version

insert into version(owner_id, project_id, version_id) values('user1','project1','1.0.0.RC1');
insert into version(owner_id, project_id, version_id) values('user1','project1','2.0.0.FINAL');
insert into version(owner_id, project_id, version_id) values('user1','project1','2.3.2.M1');

insert into version(owner_id, project_id, version_id) values('user1','project2','1.1.0.RC1');
insert into version(owner_id, project_id, version_id) values('user1','project2','2.1.0.FINAL');
insert into version(owner_id, project_id, version_id) values('user1','project2','3.2.2.M1');

insert into version(owner_id, project_id, version_id) values('user3','project1','1.0.2.M1');

insert into version(owner_id, project_id, version_id) values
('user4','project1','1.0.1.FINAL');

-- issue

insert into issue(issue_id, assignee, title, content, created, issue_type_id, modified, owner_id, priority_id, project_id, reporter, status_id) values(101, 'user4','title1','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin facilisis nibh eget vehicula facilisis. Cras ullamcorper fringilla ante id vestibulum.','2014-08-11 08:55:53','b', '2014-08-12 08:55:53', 'user1', 'i', 'project1', 'user5','o');

insert into issue(issue_id, assignee, title, content, created, issue_type_id, modified, owner_id, priority_id, project_id, reporter, status_id) values(102, 'user5','title2','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin facilisis nibh eget vehicula facilisis. Cras ullamcorper fringilla ante id vestibulum.','2014-08-12 08:55:53','b', '2014-08-17 08:55:53', 'user1', 'a', 'project1', 'user3','r');

insert into issue(issue_id, assignee, title, content, created, issue_type_id, modified, owner_id, priority_id, project_id, reporter, status_id) values(103, null,'title3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin facilisis nibh eget vehicula facilisis. Cras ullamcorper fringilla ante id vestibulum.','2014-10-12 08:55:53','b', '2014-11-22 08:55:53', 'user1', 'i', 'project1', 'user2','o');


-- response

insert into response(response_id, content, created, modified, user_id, issue_id) values(101, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin facilisis nibh eget vehicula facilisis. Cras ullamcorper fringilla ante id vestibulum.','2014-12-17 08:55:53', '2014-08-17 08:55:53', 'user8', 101);


insert into response(response_id, content, created, modified, user_id, issue_id) values(102, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin facilisis nibh eget vehicula facilisis. Cras ullamcorper fringilla ante id vestibulum.','2014-11-17 08:55:53', '2014-11-17 09:55:53', 'user9', 101);


insert into response(response_id, content, created, modified, user_id, issue_id) values(103, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin facilisis nibh eget vehicula facilisis. Cras ullamcorper fringilla ante id vestibulum.','2014-12-19 08:55:53', '2014-12-20 14:55:53', 'user7', 102);



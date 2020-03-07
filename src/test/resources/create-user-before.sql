delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$a4A2lyovehwQ.Rjpr5YxJ.yMeycBAa9p3pQLvTnuPdh6ToYfzlqKe', 'q'),
(2, true, '$2a$08$a4A2lyovehwQ.Rjpr5YxJ.yMeycBAa9p3pQLvTnuPdh6ToYfzlqKe', 'q2');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');
delete from post_tag;
delete from post;
delete from tag;

insert into tag(id, name) values
(1, 'tag'),
(2, 'tag2'),
(3, 'tag3');

insert into post(id, image, text, user_id) values
(1, 'first', 'my-tag', 1),
(2, 'second', 'new', 1),
(3, 'third', 'my-tag', 1),
(4, 'fourth', 'another', 1);

insert into post_tag(post_id, tag_id) values
(1, 1),
(2, 3),
(3, 2),
(4, 1);

alter sequence hibernate_sequence restart 20;

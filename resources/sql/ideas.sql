-- Ideas SQL definitions

-- :name q-create-ideas-table
-- :command :execute
-- :result :raw
create table ideas (
    id integer primary key autoincrement,
    name varchar(255) unique
)

-- :name q-create-users-ideas-table
-- :command :execute
-- :result :raw
create table users_ideas (
    "user-id" integer,
    "idea-id" integer,
    foreign key ("user-id") references users(id),
    foreign key ("idea-id") references ideas(id),
    primary key ("user-id", "idea-id")
)

-- :name q-insert-idea :i!
insert into ideas (name)
values (:name)

-- :name q-get-by-id :? :1
select * from ideas
where id=:id

-- :name q-get-by-name :? :1
select * from ideas
where name=:name

-- :name q-get-vote :1
select * from users_ideas
where "user-id"=:user-id and "idea-id"=:idea-id

-- :name q-upvote :i!
insert into users_ideas ("user-id", "idea-id")
values (:user-id, :idea-id)

-- :name q-downvote :!
delete from users_ideas
where "user-id"=:user-id and "idea-id"=:idea-id

-- :name q-get-idea-votes :? :1
select count(ui."user-id") from ideas as i
inner join users_ideas as ui
on (i.id=ui."idea-id")
where i.id=:id

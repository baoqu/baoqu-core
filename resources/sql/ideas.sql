-- Ideas SQL definitions

-- :name q-create-ideas-table
-- :command :execute
-- :result :raw
create table ideas (
    id integer primary key autoincrement,
    name varchar(255),
    "event-id" integer,
    foreign key ("event-id") references event(id),
    unique ("event-id", name)
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
insert into ideas (name, "event-id")
values (:name, :event-id)

-- :name q-get-all :?
select * from ideas

-- :name q-get-all-votes-for-event
select ui.* from users_ideas as ui
 inner join ideas as i
         on ui."idea-id" = i.id
      where i."event-id" = :event-id

-- :name q-get-by-id :? :1
select * from ideas
where id=:id

-- :name q-get-by-name-and-event :? :1
select * from ideas
   where name=:name
     and "event-id"=:event-id

-- :name q-get-all-for-user :?
select i.* from ideas as i
 inner join users_ideas as ui
         on i.id = ui."idea-id"
      where ui."user-id" = :user-id

-- :name q-get-all-for-event :?
select * from ideas
   where "event-id"=:event-id

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

-- :name q-get-user-votes
select * from users_ideas
 where "user-id" = :user-id

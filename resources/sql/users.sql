-- Users SQL definitions

-- :name q-create-users-table
-- :command execute
-- :results :raw
create table users (
    id integer primary key autoincrement,
    name varchar(255)
)

-- :name q-insert-user :i!
insert into users (name)
values (:name)

-- :name q-get-by-id :? :1
select * from users
where id=:id

-- :name q-get-all :?
select * from users

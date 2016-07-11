-- UsersCircles SQL definitions

-- :name create-users-circles-table
-- :command execute
-- :result :raw
create table users_circles (
    foreign key (id_user) references users(id),
    foreign key (id_circle) references circles(id),
    primary key (id_user, id_circle)
)

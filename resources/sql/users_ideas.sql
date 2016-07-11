-- UsersIdeas SQL definitions

-- :name create-users-ideas-table
-- :command :execute
-- :result :raw
create table users_ideas (
    foreign key (id_user) references users(id),
    foreign key (id_idea) references ideas(id),
    primary key (id_user, id_idea)
)

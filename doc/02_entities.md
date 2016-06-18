# Entities

`baoqu-core` is intended to work with a set of entities that represent the different data usefull for the platform. This entities are the following:

- Event
- Circle
- Idea
- User

## Event

Stores the event related information, such as its properties, the circle size, constraints and so on...

It contains **circles**, **users** and **ideas**, and it also stores the users that are currently not associated with any circle. This users are also known to be in the `revolver` circle, waiting to be reassigned to an uncomplete circle.

```
{
    "id": 1,
    "name": "What should we do with our last budget?"
    "circle-size": 3,
    "approval-factor": 3,
    "revolver-user-ids": [12, 7]
}
```

## Circle

In a circle we can find the information relative to the users inside it, the discussion that is happening between them (in the form of **comments**) and the most upvoted idea.

```
[{
    "id": 3,
    "level": 2,
    "parent-circle": null,
    "user-ids": [7, 24, 8, 7, 12, 31],
    "main-idea": 27
},
{
    "id": 2,
    "level": 1,
    "parent-circle": 3,
    "user-ids": [7, 24, 8],
    "main-idea": 27
},
{
    "id": 1,
    "level": 1,
    "parent-circle": 3,
    "user-ids": [7, 12, 31],
    "main-idea": 13
}]
```

## Comment

Every **comment** belongs to a **circle**, so efectively we will have a discussion happening in each circle. When the circle gains a new level and is merged with others, a new discussion feed is created.

Users always can view and keep adding comments in all the circles they are in.

```
{
    "id": 1,
    "author": 27,
    "circle": 2,
    "date": SOMEHOW_FORMATTED_TIMESTAMP,
    "body": "agree with you"
}
```

## User

A user will always be able to see all the circles and discussions happening inside them. They can upvote any idea, either it be inside their circles or outside. All user votes have the same value.

```
{
    "id": 1,
    "name": "Jane Doe"
}
```

## Idea

An idea is just a line of text, nothing more. An idea will never have an author, when is created is just an idea inside a circle with one vote (its author's).

```
{
    "id": 1,
    "body": "Fix the street lights",
    "backer-ids": [7, 23]
}
```

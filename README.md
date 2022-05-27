# Solution

- As the relationship between users and teams is described as a many-to-many relationship, my solution was to add the id of the role on the join table used on this relationship. 
- So, both the membership and the role of that user on that group is stored on the same place, making it easy to query both the roles of the users on their teams, the roles of the users on a specific team, and which members have that role across all teams.

# How to Run

- Required tools: Docker and Java JDK 11
- After cloning the repository, run the following commands on the root folder

```shell
    $ ./gradlew build
    $ docker-compose up
```

# Improvements
- Pagination on all endpoints that return a list of objects (`/users` and `/teams` )
- An endpoint to query all the teams that a user belong
- An endpoint to query all the teams that a user is a team lead
- An endpoint to query the teams by name
- An endpoint to query the users by name, display name or location

# What happens if the data is deleted?

- If a role is deleted?
  - The default role cannot be deleted 
  - All memberships associated with role beig deleted are associated to the default role and then the role is deleted
- If a user is deleted?
  - If the user is the lead of a team it cannot be deleted
  - All memberships associated with that user are deleted
- If a team is deleted?
  - All memberships associated with that team are deleted

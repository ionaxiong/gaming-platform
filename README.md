# gaming-platform

##### The project is deployed on [Heroku](https://gamesite-mingxiong.herokuapp.com/gamelist)

Deploying to Heroku:

##### 1. Creation 

- Create new app on Heroku web page
- Choose region Europe
 
##### 2. Configuration

- Set heroku configuration from local terminal `heroku git:remote -a name`
- Push code to heroku `git push heroku master`

##### 3. Postgres

- Add postgres to heroku `heroku addons:create heroku-postgresql` 
- Set Config Vars in Heroku:

```
DATABASE_URL (was set by adding addon)
SPRING_DATASOURCE_PASSWORD random string
SPRING_DATASOURCE_USER random string
```

- Add Config Vars to application.properties:

```
spring.datasource.url=${JDBC_DATABASE_URL}
spring.datasource.username=${JDBC_DATABASE_USERNAME}
spring.datasource.password=${JDBC_DATABASE_PASSWORD}
```

- Deploy if app is not working
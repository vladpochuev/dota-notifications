## Project overview

A simple Telegram bot for receiving recent Dota 2 match statistics.
The bot retrieves data from [OpenDota](https://www.opendota.com).
**Remember to set your Dota 2 profile to public so OpenDota can access and share your statistics.**

On the free plan, you are limited to 2,000 requests per day.
The bot is designed for a single user and makes requests every 45 seconds, which is close to the limit.
If you want to support multiple users, increase this delay in the ```application.yml``` file.
For example, for two users, set it to 90 seconds, and so on.

## Requirements

1. Java 17+
2. Spring Boot 3.3.0
3. PostgreSQL
4. Telegram Bot API key

## Installation guide

Clone the repository  to your device:

```
git clone https://github.com/vladpochuev/dota-notifications.git
```

Create an ```application.yml``` file inside the ```src/main/resources``` directory.

Here is a template for ```application.yml```:

```
spring:
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    show-sql: false
  application:
    name: tg_bot_dota
  datasource:
    url: "" # Full database URL
    username: "" # Database username
    password: "" # Database password
    driver-class-name: org.postgresql.Driver
bot:
  name: "" # Bot's Telegram username
  token: "" # Bot's Telegram token
  request-seconds-delay: 45
```

Run the application using any IDE or the following Maven command in the main directory:

```
mvn spring-boot:run
```

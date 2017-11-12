# user schema

# ---!Ups

CREATE TABLE users(
  id VARCHAR(36) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  username VARCHAR(255) NOT NULL,
  hashed_password VARCHAR(72) NOT NULL,
  email VARCHAR(255) NOT NULL,
  mobile_number VARCHAR(20),

  PRIMARY KEY (id, created_at)
);

# ---!Downs

DROP TABLE users
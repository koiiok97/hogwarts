CREATE TABLE Car
(
    id    BIGSERIAL PRIMARY KEY,
    brand VARCHAR(255)   NOT NULL,
    model VARCHAR(255)   NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Person
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    age         INTEGER      NOT NULL,
    has_license BOOLEAN      NOT NULL
);

CREATE TABLE PersonCar
(
    person_id BIGINT NOT NULL REFERENCES Person (id) ON DELETE CASCADE,
    car_id    BIGINT NOT NULL REFERENCES Car (id) ON DELETE RESTRICT,
    PRIMARY KEY (person_id, car_id)
);
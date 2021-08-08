FROM postgres:13
COPY ./commons/src/test/resources/schema.sql /docker-entrypoint-initdb.d/
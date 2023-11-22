psql -U postgres -c 'CREATE DATABASE postgres;'
psql -U postgres -d postgres -f schema.sql

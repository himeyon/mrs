It is a sample program for a flame-up project.
-------------------------------

* Creating it with SpringBoot.
* The HTML template is Thymeleaf 3.
* The database connection is JPA.

##### http://www.shoeisha.co.jp/book/detail/9784798142470

### create user
 
CREATE ROLE mrs LOGIN  
PASSWORD 'mrs'  
NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;  
 
### create database
 
CREATE DATABASE mrs  
WITH OWNER = mrs  
ENCODING = 'UTF8'  
TABLESPACE = pg_default  
LC_COLLATE = 'C'  
LC_CTYPE = 'C'  
TEMPLATE = 'template0'  
CONNECTION LIMIT = -1;  


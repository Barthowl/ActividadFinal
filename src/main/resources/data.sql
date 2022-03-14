insert into asignatura (id, nombre, descripcion, curso)
select 1, 'Lengua', 'Literatura y escritores', 2 from dual where not exists (select 1 from asignatura where id = 1);

insert into asignatura (id, nombre, descripcion, curso)
select 2, 'Matemáticas', 'Ecuaciones y algebra', 1 from dual where not exists (select 1 from asignatura where id = 2);

insert into asignatura (id, nombre, descripcion, curso)
select 3, 'Tecnología', 'Conocimientos sobre powerpoint', 1 from dual where not exists (select 1 from asignatura where id = 3);

insert into rol (id, rol)
select 1, 'ADMIN' from dual where not exists (select 1 from rol where id = 1);

insert into rol (id, rol)
select 2, 'CONSULTA' from dual where not exists (select 1 from rol where id = 2);

/* pass = 1111 */
insert into usuario (username, nombre, password, rol_id)
select 'admin', 'Administrador',
'$2a$10$5xOe75pbLcAjp0TbVWaluunrSshgYdH82YNwGd.b0Os4hAWbIEkry', 1 from dual where not
exists (select 1 from usuario where username = 'admin');

insert into usuario (username, nombre, password, rol_id)
select 'consulta', 'Consulta',
'$2a$10$5xOe75pbLcAjp0TbVWaluunrSshgYdH82YNwGd.b0Os4hAWbIEkry', 2 from dual where not
exists (select 1 from usuario where username = 'consulta');

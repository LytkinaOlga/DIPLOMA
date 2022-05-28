insert into tasks (id, name, class_name) values (1, 'Testing Task', 'by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.TestTask');
insert into tasks (id, name, class_name) values (2, 'Manual Task', 'by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.ManualTask');
insert into tasks (id, name, class_name) values (3, 'Master List Creator', 'by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.MasterListCreationTask');
insert into tasks (id, name, class_name) values (4, 'Adapter', 'by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter.AdapterTask');
insert into tasks (id, name, class_name) values (5, 'Random Failing Adapter', 'by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter.RandomFailingAdapter');

insert into task_params (id, task_id, name) values (1, 1, 'Delay');
insert into task_params (id, task_id, name) values (2, 1, 'Message');
insert into task_params (id, task_id, name) values (3, 4, 'URL');
insert into task_params (id, task_id, name) values (4, 5, 'Success Rate');
insert into task_params (id, task_id, name) values (5, 3, 'Entity Table');
insert into task_params (id, task_id, name) values (6, 3, 'Entity Column');
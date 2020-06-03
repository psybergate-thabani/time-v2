insert into Time_Entry (id, version, employee_id, task_id, status, status_reason, description, period, date, deleted, created_by, created) values
('56269d672e0b44e58c9541d2b20d7a75',0,'65ed6644718b11eabc550242ac130003', '2e60b4f5f4f74dd1a62d92c2ea864be7', 'NEW','Dummy data','Batch and Support',500, CURRENT_DATE, FALSE,'system',CURRENT_TIMESTAMP),
('94b97da909e44aab9816913e109463a7',0,'65ed6644718b11eabc550242ac130003', 'e3140fd9abbc4b348de5414b4a530f2e', 'NEW','Dummy data','Batch and Support',1800, CURRENT_DATE, FALSE,'system',CURRENT_TIMESTAMP),
('68cb92b9e00b413a963298f575543428',0,'1061cfca718c11eabc550242ac130003', '636bb7841b98443aa4f6de90158a6952', 'NEW', 'Dummy data',NULL,36000, CURRENT_DATE, FALSE,'system', CURRENT_TIMESTAMP);
insert into Status_History (id, version, status, status_update_reason, time_entry_id, time_stamp, deleted, created_by, created) values
('082042d0f5d34b7f949c33d112d95847',0,'NEW','Dummy data','56269d672e0b44e58c9541d2b20d7a75', CURRENT_TIMESTAMP, FALSE, 'system', CURRENT_TIMESTAMP),
('79f111acdb0d4959b1fd2544fca91b71',0,'NEW','Dummy data','94b97da909e44aab9816913e109463a7', CURRENT_TIMESTAMP, FALSE, 'system', CURRENT_TIMESTAMP),
('78f58a50ecb3462f9cd22d91d42604f9',0,'NEW','Dummy data','68cb92b9e00b413a963298f575543428', CURRENT_TIMESTAMP, FALSE, 'system', CURRENT_TIMESTAMP);
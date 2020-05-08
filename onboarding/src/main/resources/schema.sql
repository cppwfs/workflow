/*
 * Copyright 2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
DROP TABLE IF EXISTS employee_application ;
DROP TABLE IF EXISTS accepted_application ;
DROP TABLE IF EXISTS employee;

CREATE TABLE employee_application
(
   last_name varchar(55),
   first_name varchar(55),
   middle_initial varchar(1),
   govt_id varchar(11),
   candidate_id varchar(11),
   address varchar(255),
   state_abbrev varchar(2),
   postal_code varchar(15)
);

CREATE TABLE accepted_application
(
   candidate_id varchar(11),
   employee_type_id varchar(11)
);

CREATE TABLE employee
(
   last_name varchar(55),
   first_name varchar(55),
   middle_initial varchar(1),
   govt_id varchar(11),
   employee_id varchar(13),
   address varchar(255),
   state_abbrev varchar(2),
   postal_code varchar(15),
   start_date date,
   employee_type_id varchar(11)
);

INSERT INTO employee_application values ('abrams', 'adam', 'a', '1', '1', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('barnes', 'brenda', 'b', '2', '2', '2 bedford street', 'ga', '30001');
INSERT INTO employee_application values ('carnes', 'chris', 'c', '3', '3', '3 cardina street', 'ca', '30001');
INSERT INTO employee_application values ('dodd', 'danielle', 'd', '4', '4', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('emerald', 'edmond', 'a', '5', '5', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('ferris', 'felecia', 'a', '6', '6', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('green', 'george', 'a', '7', '7', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('height', 'hilda', 'a', '8', '8', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('jenkins', 'james', 'a', '9', '9', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('idea', 'isis', 'a', '10', '10', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('kramp', 'kelly', 'a', '11', '11', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('lamore', 'linda', 'a', '12', '12', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('moore', 'marvin', 'a', '13', '13', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('noodle', 'nora', 'a', '14', '14', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('oscar', 'cat', 'a', '15', '15', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('pickle', 'perry', 'a', '16', '16', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('quest', 'quincy', 'a', '17', '17', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('restful', 'rena', 'a', '18', '18', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('squarepants', 'spongebob', 'a', '19', '19', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('tester', 'trish', 'a', '20', '20', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('useless', 'ulrick', 'a', '21', '21', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('victory', 'victoria', 'a', '22', '22', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('west', 'wes', 'a', '23', '23', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('xenaphobe', 'xena', 'a', '24', '24', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('young', 'yolanda', 'a', '25', '25', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('zebra', 'zany', 'a', '26', '26', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('amans', 'andy', 'a', '27', '27', '1 adam street', 'al', '30001');
INSERT INTO employee_application values ('steel', 'stanly', 'a', '28', '28', '1 adam street', 'al', '30001');




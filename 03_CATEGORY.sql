CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    category_type VARCHAR(50),
    name VARCHAR(50)
);

insert into CATEGORY (category_type, name) values ('INCOME', 'Salary');
insert into CATEGORY (category_type, name) values ('INCOME', 'Business');
insert into CATEGORY (category_type, name) values ('INCOME', 'Investment');
insert into CATEGORY (category_type, name) values ('INCOME', 'Passive');
insert into CATEGORY (category_type, name) values ('INCOME', 'Retirement Funds');
insert into CATEGORY (category_type, name) values ('INCOME', 'Other income');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Other expenses');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Food & Groceries');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Rent');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Utilities');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Transport');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Insurance');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Debt Repayment');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Health & Medical');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Subscriptions');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Education');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Charity & Donations');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Personal Care');
insert into CATEGORY (category_type, name) values ('EXPENSES', 'Services');


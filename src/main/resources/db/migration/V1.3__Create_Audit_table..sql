create table audit (
    auditid UUID not null primary key,
    auditor_timestamp timestamp,
    audit_action varchar(50),
    account_number varchar(50)
);
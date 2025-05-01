CREATE TABLE departments
(
    department_id UUID         NOT NULL,
    name          VARCHAR(100) NOT NULL,
    head_id       UUID,
    CONSTRAINT pk_departments PRIMARY KEY (department_id)
);

CREATE TABLE designers
(
    employee_id   UUID    NOT NULL,
    patents_count INTEGER NOT NULL,
    CONSTRAINT pk_designers PRIMARY KEY (employee_id)
);

CREATE TABLE employees
(
    employee_id          UUID         NOT NULL,
    first_name           VARCHAR(100) NOT NULL,
    last_name            VARCHAR(100) NOT NULL,
    employee_type        VARCHAR(255) NOT NULL,
    employee_position_id UUID         NOT NULL,
    age                  INTEGER,
    department_id        UUID,
    CONSTRAINT pk_employees PRIMARY KEY (employee_id)
);

CREATE TABLE engineer_specializations
(
    specialization_id UUID         NOT NULL,
    name              VARCHAR(100) NOT NULL,
    CONSTRAINT pk_engineer_specializations PRIMARY KEY (specialization_id)
);

CREATE TABLE engineers
(
    employee_id UUID NOT NULL,
    CONSTRAINT pk_engineers PRIMARY KEY (employee_id)
);

CREATE TABLE lab_assistants
(
    employee_id   UUID NOT NULL,
    laboratory_id UUID,
    CONSTRAINT pk_lab_assistants PRIMARY KEY (employee_id)
);

CREATE TABLE laboratories
(
    laboratory_id UUID         NOT NULL,
    name          VARCHAR(100) NOT NULL,
    CONSTRAINT pk_laboratories PRIMARY KEY (laboratory_id)
);

CREATE TABLE positions
(
    position_id   UUID         NOT NULL,
    employee_type VARCHAR(255) NOT NULL,
    name          VARCHAR(100) NOT NULL,
    CONSTRAINT pk_positions PRIMARY KEY (position_id)
);

CREATE TABLE technician_equipment
(
    employee_id  UUID NOT NULL,
    equipment_id UUID
);

CREATE TABLE technicians
(
    employee_id UUID NOT NULL,
    CONSTRAINT pk_technicians PRIMARY KEY (employee_id)
);

ALTER TABLE positions
    ADD CONSTRAINT uc_208488e538c2818911da562bc UNIQUE (employee_type, name);

ALTER TABLE departments
    ADD CONSTRAINT uc_departments_head UNIQUE (head_id);

ALTER TABLE engineer_specializations
    ADD CONSTRAINT uc_engineer_specializations_name UNIQUE (name);

ALTER TABLE departments
    ADD CONSTRAINT FK_DEPARTMENTS_ON_HEAD FOREIGN KEY (head_id) REFERENCES employees (employee_id);

ALTER TABLE designers
    ADD CONSTRAINT FK_DESIGNERS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (employee_id);

ALTER TABLE employees
    ADD CONSTRAINT FK_EMPLOYEES_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (department_id);

ALTER TABLE employees
    ADD CONSTRAINT FK_EMPLOYEES_ON_EMPLOYEE_POSITION FOREIGN KEY (employee_position_id) REFERENCES positions (position_id);

ALTER TABLE engineers
    ADD CONSTRAINT FK_ENGINEERS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (employee_id);

ALTER TABLE lab_assistants
    ADD CONSTRAINT FK_LAB_ASSISTANTS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (employee_id);

ALTER TABLE lab_assistants
    ADD CONSTRAINT FK_LAB_ASSISTANTS_ON_LABORATORY FOREIGN KEY (laboratory_id) REFERENCES laboratories (laboratory_id);

ALTER TABLE technicians
    ADD CONSTRAINT FK_TECHNICIANS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (employee_id);

ALTER TABLE technician_equipment
    ADD CONSTRAINT fk_technician_equipment_on_technician FOREIGN KEY (employee_id) REFERENCES technicians (employee_id);
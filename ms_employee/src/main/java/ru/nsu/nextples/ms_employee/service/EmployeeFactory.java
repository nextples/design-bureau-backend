package ru.nsu.nextples.ms_employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeCreateDTO;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeUpdateDTO;
import ru.nsu.nextples.ms_employee.exception.DepartmentNotFoundException;
import ru.nsu.nextples.ms_employee.exception.PositionNotFoundException;
import ru.nsu.nextples.ms_employee.exception.InvalidPositionException;
import ru.nsu.nextples.ms_employee.exception.LaboratoryNotFoundException;
import ru.nsu.nextples.ms_employee.model.*;
import ru.nsu.nextples.ms_employee.repository.DepartmentRepository;
import ru.nsu.nextples.ms_employee.repository.PositionRepository;
import ru.nsu.nextples.ms_employee.repository.EngineerSpecializationRepository;
import ru.nsu.nextples.ms_employee.repository.LaboratoryRepository;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeFactory {

    private final PositionRepository positionRepository;
    private final EngineerSpecializationRepository specializationRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final DepartmentRepository departmentRepository;

    public Employee createEmployee(EmployeeCreateDTO request) {
        validatePosition(request.getPositionId(), request.getEmployeeType());

        return switch (request.getEmployeeType()) {
            case DESIGNER -> createDesigner(request);
            case ENGINEER -> createEngineer(request);
            case LAB_ASSISTANT -> createLabAssistant(request);
            case TECHNICIAN -> createTechnician(request);
        };
    }

    public void updateEmployee(Employee employee, EmployeeUpdateDTO request) {
        updateBaseFields(employee, request);
        updateSpecificFields(employee, request);
    }

    private void setEmployeePosition(Employee employee, UUID positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new PositionNotFoundException(positionId));
        employee.setPosition(position);
    }

    private void setEmployeeDepartment(Employee employee, UUID departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        employee.setDepartment(department);
    }

    private Designer createDesigner(EmployeeCreateDTO request) {
        Designer designer = new Designer();
        setBaseFields(designer, request);

        designer.setEmployeeType(EmployeeType.DESIGNER);
        designer.setPatentsCount(request.getPatentsCount());
        return designer;
    }

    private Engineer createEngineer(EmployeeCreateDTO request) {
        Engineer engineer = new Engineer();
        setBaseFields(engineer, request);
        engineer.setSpecializations(new HashSet<>(
                specializationRepository.findAllById(request.getSpecializationIds())
        ));
        return engineer;
    }

    private LabAssistant createLabAssistant(EmployeeCreateDTO request) {
        LabAssistant labAssistant = new LabAssistant();
        setBaseFields(labAssistant, request);
        labAssistant.setLaboratory(laboratoryRepository.findById(request.getLaboratoryId())
                .orElseThrow(() -> new LaboratoryNotFoundException(request.getLaboratoryId())));
        return labAssistant;
    }

    private Technician createTechnician(EmployeeCreateDTO request) {
        Technician technician = new Technician();
        setBaseFields(technician, request);
        technician.setAssignedEquipmentIds(new HashSet<>(request.getEquipmentIds()));
        return technician;
    }

    private void setBaseFields(Employee employee, EmployeeCreateDTO request) {
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setAge(request.getAge());
        setEmployeeDepartment(employee, request.getDepartmentId());
        setEmployeePosition(employee, request.getPositionId());
    }
    
    private void updateBaseFields(Employee employee, EmployeeUpdateDTO request) {
        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        if (request.getAge() != null) {
            employee.setAge(request.getAge());
        }
        if (request.getPositionId() != null) {
            validatePosition(request.getPositionId(), employee.getEmployeeType());
            setEmployeePosition(employee, request.getPositionId());
        }
    }

    private void updateSpecificFields(Employee employee, EmployeeUpdateDTO request) {
        if (employee instanceof Designer designer && request.getPatentsCount() != null) {
            designer.setPatentsCount(request.getPatentsCount());
        }

        if (employee instanceof Engineer engineer && request.getSpecializationIds() != null) {
            engineer.setSpecializations(new HashSet<>(
                    specializationRepository.findAllById(request.getSpecializationIds())
            ));
        }

        if (employee instanceof LabAssistant labAssistant && request.getLaboratoryId() != null) {
            labAssistant.setLaboratory(laboratoryRepository.findById(request.getLaboratoryId())
                    .orElseThrow(() -> new LaboratoryNotFoundException(request.getLaboratoryId())));
        }

        if (employee instanceof Technician technician && request.getEquipmentIds() != null) {
            technician.setAssignedEquipmentIds(new HashSet<>(request.getEquipmentIds()));
        }
    }

    private void validatePosition(UUID positionId, EmployeeType type) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new PositionNotFoundException(positionId));

        if (position.getEmployeeType() != type) {
            throw new InvalidPositionException(positionId, type);
        }
    }
}


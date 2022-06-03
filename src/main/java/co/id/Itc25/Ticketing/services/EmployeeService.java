package co.id.Itc25.Ticketing.services;

import co.id.Itc25.Ticketing.dtos.employee.*;
import co.id.Itc25.Ticketing.dtos.employeeTechSupport.EmployeeTechSupportHeaderDto;
import co.id.Itc25.Ticketing.dtos.user.UserHeaderDto;
import co.id.Itc25.Ticketing.dtos.user.UserUsernamePasswordDto;
import co.id.Itc25.Ticketing.exceptions.EntityBadRequestException;
import co.id.Itc25.Ticketing.models.User;
import co.id.Itc25.Ticketing.repositories.EmployeeTechSupportAvailableRepository;
import co.id.Itc25.Ticketing.exceptions.EntitiyFailedInsertException;
import co.id.Itc25.Ticketing.exceptions.EntityNotFoundException;
import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.EmployeeTechSupportAvailable;
import co.id.Itc25.Ticketing.models.Job;
import co.id.Itc25.Ticketing.repositories.EmployeeRepository;
import co.id.Itc25.Ticketing.repositories.JobRepository;
import co.id.Itc25.Ticketing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeTechSupportAvailableRepository employeeTechSupportAvailableRepository;
    private JobRepository jobRepository;
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, JobRepository jobRepository,
                           EmployeeTechSupportAvailableRepository employeeTechSupportAvailableRepository,
                           UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
        this.employeeTechSupportAvailableRepository = employeeTechSupportAvailableRepository;
        this.userRepository = userRepository;
    }

    //Find All
    public List<EmployeeHeaderDto> findAllEmployee(){
        return EmployeeHeaderDto.toList(employeeRepository.findAll());
    }

    //Find By Is Available
    public List<EmployeeTechSupportHeaderDto> findAllEmployeeIsAvailable(int available){
        return EmployeeTechSupportHeaderDto.toList(employeeTechSupportAvailableRepository.getEmployeeIsAvailable(available));
    }

    //Insert
    public EmployeeInsertResponseDto insertEmployee(EmployeeInsertDto newEmployee){
        List<Employee> employees = employeeRepository.findAll();
        Employee employee = new Employee();
        EmployeeTechSupportAvailable employeeTechSupport = new EmployeeTechSupportAvailable();

        employee = newEmployee.convert();
        String employeeId = employee.getEmployeeId();
        for (Employee emp : employees){
            if (emp.getEmployeeId().equals(employeeId)){
                throw new EntitiyFailedInsertException("Employee ID sudah ada");
            }
        }

        Job job = jobRepository.findById(getJobId(employeeId))
                .orElseThrow(() -> new EntityNotFoundException("Job ID tidak ditemukan"));

        employee.setJob(job);
        employeeTechSupport.setEmployeeID(employee.getEmployeeId());

        employeeRepository.save(employee);
        employeeTechSupportAvailableRepository.save(employeeTechSupport);

        return EmployeeInsertResponseDto.set(employee);
    }

    public int getJobId(String employeeId){
        String id = employeeId.substring(0, 1);
        int jobId = 0;
        if (id.equals("A")){
            jobId = 1;
        } else if (id.equals("T")){
            jobId = 2;
        } else {
            jobId = 3;
        }

        return jobId;
    }

    //Insert User
    public UserHeaderDto registration(UserUsernamePasswordDto registrant){
        Employee employee = employeeRepository.findById(registrant.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee tidak ditemukan"));

        List<User> users = userRepository.findAll();
        User newUser = new User(registrant.getUsername(), registrant.getPassword());

        for (User user : users){
            if (user.getUserId().equals(employee.getEmployeeId()) && user.isEnabled()){
                throw new EntityBadRequestException("UserID sudah ada dan aktif");
            }
            if (user.getUsername().equals(newUser.getUsername())){
                throw new EntityBadRequestException("Username sudah ada");
            }
        }

        newUser.setUserId(employee.getEmployeeId());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole(getRole(employee.getEmployeeId()));

        userRepository.save(newUser);

        newUser.getPassword();
        return UserHeaderDto.set(newUser);
    }

    public String getRole(String employeeId){
        String id = employeeId.substring(0,1);
        String role = "";
        if (id.equals("A")){
            role = "ADMIN";
        } else if (id.equals("T")){
            role = "TECH_SUPPORT";
        } else {
            role = "MANAGER";
        }

        return role;
    }

    //Update
    public EmployeeUpdateDto updateEmployee(EmployeeUpdateDto employee){
        Employee oldEmployee = employeeRepository.findById(employee.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee tidak ditemukan"));

        oldEmployee.setFirstName(employee.getFirstName());
        oldEmployee.setLastName(employee.getLastName());

        employeeRepository.save(oldEmployee);
        return EmployeeUpdateDto.set(oldEmployee);
    }

    //Delete
    public List<EmployeeDeleteResponseDto> deleteEmployee(List<String> employeeIds){
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        Employee employee = new Employee();
        for (String employeeId : employeeIds){
            employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee tidak ditemukan"));
        }

//        String id = employee.getEmployeeId().substring(0, 1);
//        if (id.equals("T")){
//            employeeTechSupportAvailableRepository.deleteAllById(employeeIds);
//        }

        employee.setActive(false);
//        employeeRepository.deleteAllById(employeeIds);
        employeeRepository.save(employee);

        return EmployeeDeleteResponseDto.toList(employees);
    }

}

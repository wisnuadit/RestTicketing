package co.id.Itc25.Ticketing.contollers;

import co.id.Itc25.Ticketing.dtos.RestResponse;
import co.id.Itc25.Ticketing.dtos.employee.*;
import co.id.Itc25.Ticketing.dtos.employeeTechSupport.EmployeeTechSupportHeaderDto;
import co.id.Itc25.Ticketing.dtos.employeeTechSupport.EmployeeTechSupportRequestDto;
import co.id.Itc25.Ticketing.dtos.user.UserHeaderDto;
import co.id.Itc25.Ticketing.dtos.user.UserUsernamePasswordDto;
import co.id.Itc25.Ticketing.models.EmployeeTechSupportAvailable;
import co.id.Itc25.Ticketing.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<EmployeeHeaderDto>>> findAllEmployee(){
        return new ResponseEntity<>(
                new RestResponse<>(service.findAllEmployee(),
                        "Berhasil ditemukan",
                        "200"),
                HttpStatus.OK);
    }

    @GetMapping("employee-available")
    public ResponseEntity<RestResponse<List<EmployeeTechSupportHeaderDto>>> findAllEmployeeIsAvailable
            (@RequestBody EmployeeTechSupportRequestDto available){
        return new ResponseEntity<>(
                new RestResponse<>(service.findAllEmployeeIsAvailable(available.getAvailable()),
                        "Berhasil ditemukan",
                        "200"),
                HttpStatus.OK);
    }

    @PostMapping("insert-employee")
    public ResponseEntity<RestResponse<EmployeeInsertResponseDto>> insertEmployee(@RequestBody EmployeeInsertDto employee){
        return new ResponseEntity<>(
                new RestResponse<>(service.insertEmployee(employee),
                        "Berhasil menambahkan Employee baru",
                        "201"),
                HttpStatus.CREATED);
    }

    @PostMapping("register")
    public ResponseEntity<RestResponse<UserHeaderDto>> registration(@RequestBody UserUsernamePasswordDto registrant){
        return new ResponseEntity<>(
                new RestResponse<>(service.registration(registrant),
                        "Berhasil ditambahkan",
                        "201"),
                HttpStatus.CREATED);
    }

    @PutMapping("update-employee")
    public ResponseEntity<RestResponse<EmployeeUpdateDto>> updateEmployee(@RequestBody EmployeeUpdateDto employee){
        return new ResponseEntity<>(
                new RestResponse<>(service.updateEmployee(employee),
                        "Berhasil melakukan update data Employee " + employee.getEmployeeId(),
                        "201"),
                HttpStatus.CREATED);
    }

    @DeleteMapping("delete-employee")
    public ResponseEntity<RestResponse<List<EmployeeDeleteResponseDto>>> deleteEmployee(@RequestBody EmployeeDeleteRequestDto employeeIds){
        return new ResponseEntity<>(
                new RestResponse<>(service.deleteEmployee(employeeIds.getEmployeeIds()),
                        "Berhasil menghapus Employee",
                        "200"),
                HttpStatus.OK);
    }
}

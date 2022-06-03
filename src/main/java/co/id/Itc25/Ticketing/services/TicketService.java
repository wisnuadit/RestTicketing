package co.id.Itc25.Ticketing.services;

import co.id.Itc25.Ticketing.dtos.ticket.*;
import co.id.Itc25.Ticketing.enums.StatusTicket;
import co.id.Itc25.Ticketing.models.User;
import co.id.Itc25.Ticketing.repositories.EmployeeTechSupportAvailableRepository;
import co.id.Itc25.Ticketing.exceptions.EntityBadRequestException;
import co.id.Itc25.Ticketing.exceptions.EntityNotFoundException;
import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.EmployeeTechSupportAvailable;
import co.id.Itc25.Ticketing.models.Ticket;
import co.id.Itc25.Ticketing.repositories.EmployeeRepository;
import co.id.Itc25.Ticketing.repositories.TicketRepository;
import co.id.Itc25.Ticketing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private EmployeeRepository employeeRepository;
    private EmployeeTechSupportAvailableRepository employeeTechSupportAvailableRepository;
    private UserRepository userRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EmployeeRepository employeeRepository,
                         EmployeeTechSupportAvailableRepository employeeTechSupportAvailableRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
        this.employeeTechSupportAvailableRepository = employeeTechSupportAvailableRepository;
        this.userRepository = userRepository;
    }

    //Find All
    public List<TicketHeaderDto> findAllTicket(){
        return TicketHeaderDto.toList(ticketRepository.findAll());
    }

    //Find By Year
    public List<TicketHeaderDto> findAllTicketByYear(int year){
        return TicketHeaderDto.toList(ticketRepository.getTicketByYear(year));
    }

    //Find By Status
    public List<TicketHeaderDto> findAllTicketByStatus(String status){
        return TicketHeaderDto.toList(ticketRepository.getTicketByStatus(status));
    }

    //Find By Order Urgency
    public List<TicketHeaderDto> findAllTicketOrderByUrgency(){
        return TicketHeaderDto.toList(ticketRepository.getTicketOrderByUrgency());
    }

    //Find By Request
    public List<TicketHeaderDto> findAllTickerByRequest(int year, String status){
        return TicketHeaderDto.toList(ticketRepository.getTicketByRequest(year, status));
    }

    //Insert
    public TicketInsertResponseDto insertTicket(TicketInsertDto newTicket, String username, String techId){
        Ticket ticket = new Ticket();

        ticket = newTicket.convert();

        Employee createdByEmployee = getEmployee(userRepository, employeeRepository, username);

        Employee appointedToEmloyee = employeeRepository.findById(techId)
                .orElseThrow(() -> new EntityNotFoundException("Id Tech Support tidak ditemukan"));
        EmployeeTechSupportAvailable employeeTechSupport = employeeTechSupportAvailableRepository.findById(techId).get();

        if (!employeeTechSupport.isAvailable()){
            throw new EntityBadRequestException(String.format("Entity ID %s sedang tidak tersedia", techId));
        }

        List<Ticket> tickets = ticketRepository.findAll();
        int id = tickets.size() + 1;

        ticket.setTicketId(getTicketId(id));
        ticket.setCreatedBy(createdByEmployee);
        ticket.setAppointedTo(appointedToEmloyee);
        employeeTechSupport.setAvailable(false);

        ticketRepository.save(ticket);
        employeeTechSupportAvailableRepository.save(employeeTechSupport);

        return TicketInsertResponseDto.set(ticket);
    }

    public Employee getEmployee(UserRepository userRepository, EmployeeRepository employeeRepository, String username){
        User user = userRepository.findByUsername(username).get();
        Employee employee = employeeRepository.findById(user.getUserId()).get();

        return employee;
    }

    public String getTicketId(int id){
        int year = LocalDate.now().getYear();
        String ticketId = String.format("SRQ/%d/%s", year, id);

        return ticketId;
    }

    //Update
    public TicketUpdateResponseDto updateToCancelledTicket(TicketUpdateDto ticket){
        String ticketId = String.format("SRQ/%d/%d", ticket.getYear(), ticket.getId());

        Ticket oldTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket tidak ditemukan"));

        String employeeId = oldTicket.getAppointedTo().getEmployeeId();
        EmployeeTechSupportAvailable emPloyeeTechSupport = employeeTechSupportAvailableRepository.findById(employeeId).get();

        String status = oldTicket.getStatus().toString();
        if (status.equals("COMPLETED")){
            throw new EntityBadRequestException("Ticket is COMPLETED");
        }
        if (status.equals("CANCELLED")){
            throw new EntityBadRequestException("Ticket is CANCELLED");
        }

        oldTicket.setStatus(StatusTicket.CANCELLED);
        ticketRepository.save(oldTicket);

        emPloyeeTechSupport.setAvailable(true);
        employeeTechSupportAvailableRepository.save(emPloyeeTechSupport);

        return TicketUpdateResponseDto.set(oldTicket);
    }
}

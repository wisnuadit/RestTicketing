package co.id.Itc25.Ticketing.services;

import co.id.Itc25.Ticketing.enums.StatusTicket;
import co.id.Itc25.Ticketing.models.*;
import co.id.Itc25.Ticketing.repositories.*;
import co.id.Itc25.Ticketing.exceptions.EntityBadRequestException;
import co.id.Itc25.Ticketing.dtos.ticketHistory.*;
import co.id.Itc25.Ticketing.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TicketHistoryService {

    private TicketHistoryRepository ticketHistoryRepository;
    private TicketRepository ticketRepository;
    private EmployeeTechSupportAvailableRepository employeeTechSupportAvailableRepository;
    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;

    @Autowired
    public TicketHistoryService(TicketHistoryRepository ticketHistoryRepository, TicketRepository ticketRepository,
                                EmployeeTechSupportAvailableRepository employeeTechSupportAvailableRepository,
                                EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.ticketRepository = ticketRepository;
        this.employeeTechSupportAvailableRepository = employeeTechSupportAvailableRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    //Find All
    public List<TicketHistoryHeaderDto> findAllTicketHistory(){
        return TicketHistoryHeaderDto.toList(ticketHistoryRepository.findAll());
    }

    //Insert
    public TicketHistoryInsertResponseDto insertTicketHistory(TicketHistoryInsertDto newTicketHistory, String username){
        String ticketId = String.format("SRQ/%d/%d", newTicketHistory.getYear(), newTicketHistory.getId());

        List<TicketHistory> ticketHistories = ticketHistoryRepository.findAll();
        TicketHistory ticketHistory = new TicketHistory();

        ticketHistory = newTicketHistory.convert();

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket tidak ditemukan"));
        Employee employeeTech = getEmployee(userRepository, employeeRepository, username);

        String status = ticket.getStatus().toString();
        if (status.equals("COMPLETED")){
            throw new EntityBadRequestException("Ticket is COMPLETED");
        }
        if (status.equals("CANCELLED")){
            throw new EntityBadRequestException("Ticket is CANCELLED");
        }

        String appointedTo = ticket.getAppointedTo().getEmployeeId();
        if (!appointedTo.equals(employeeTech.getEmployeeId())){
            String fullName = ticket.getAppointedTo().fetchFullName();
            throw new EntityBadRequestException(String.format("Tugas tersebut milik %s dengan Employee ID %s", appointedTo, fullName));
        }

        String employeeId = ticket.getAppointedTo().getEmployeeId();
        EmployeeTechSupportAvailable employeeTechSupport = employeeTechSupportAvailableRepository.findById(employeeId).get();

        if (getPeriod(ticket, newTicketHistory) >= 0){
            ticketHistory.setStatus("ON_TIME");
        } else {
            ticketHistory.setStatus("OVERDUE");
        }

        ticketHistory.setTicketId(ticketId);
        ticketHistoryRepository.save(ticketHistory);

        employeeTechSupport.setAvailable(true);
        employeeTechSupportAvailableRepository.save(employeeTechSupport);

        return TicketHistoryInsertResponseDto.set(ticketHistory);
    }

    public Employee getEmployee(UserRepository userRepository, EmployeeRepository employeeRepository, String username){
        User user = userRepository.findByUsername(username).get();
        Employee employee = employeeRepository.findById(user.getUserId()).get();

        return employee;
    }

    public int getPeriod(Ticket ticket, TicketHistoryInsertDto newTicketHistory){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate completeDate = LocalDate.parse(newTicketHistory.getCompleteDate(), formatter);
        LocalDate dueDate = ticket.getDueDate();
        Period period = Period.between(completeDate, dueDate);

        int result = period.getDays();

        return result;
    }

    //Update to Completed
    public TicketHistoryUpdateResponseDto updateToCompletedTicketHistory(TicketHistoryUpdateDto ticketHistory, String username){
        String ticketId = String.format("SRQ/%d/%d", ticketHistory.getYear(), ticketHistory.getId());

        TicketHistory oldTicketHistory = ticketHistoryRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket tidak ditemukan"));

        Employee employeeManager = getEmployee(userRepository, employeeRepository, username);

        Ticket oldTicket = ticketRepository.findById(ticketId).get();

        String status = oldTicket.getStatus().toString();
        if (status.equals("COMPLETED")){
            throw new EntityBadRequestException("Bad Request, Status is COMPLETED");
        }

        oldTicket.setStatus(StatusTicket.COMPLETED);
        oldTicketHistory.setApprovedBy(employeeManager);

        ticketRepository.save(oldTicket);
        ticketHistoryRepository.save(oldTicketHistory);

        return TicketHistoryUpdateResponseDto.set(oldTicketHistory);
    }
}

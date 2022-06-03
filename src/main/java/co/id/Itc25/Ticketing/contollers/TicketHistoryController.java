package co.id.Itc25.Ticketing.contollers;

import co.id.Itc25.Ticketing.dtos.RestResponse;
import co.id.Itc25.Ticketing.dtos.ticketHistory.*;
import co.id.Itc25.Ticketing.models.TicketHistory;
import co.id.Itc25.Ticketing.services.TicketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ticket-history")
public class TicketHistoryController {

    private TicketHistoryService service;

    @Autowired
    public TicketHistoryController(TicketHistoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<TicketHistoryHeaderDto>>> findAllTicketHistory(){
        return new ResponseEntity<>(
                new RestResponse<>(service.findAllTicketHistory(),
                        "Berhasil ditemukan",
                        "200"),
                HttpStatus.OK);
    }

    @PostMapping("insert-ticket-history")
    public ResponseEntity<RestResponse<TicketHistoryInsertResponseDto>> insertTicketHistory
            (Authentication authentication, @RequestBody TicketHistoryInsertDto ticketHistory){
        String employeeTech = authentication.getName();
        return new ResponseEntity<>(
                new RestResponse<>(service.insertTicketHistory(ticketHistory, employeeTech),
                        "Berhasil menambahkan Ticket History",
                        "201"),
                HttpStatus.CREATED);
    }

    @PutMapping("update-tocompleted-ticket")
    public ResponseEntity<RestResponse<TicketHistoryUpdateResponseDto>> completedTicket
            (Authentication authentication, @RequestBody TicketHistoryUpdateDto ticketHistory){
        String employeeManager = authentication.getName();
        return new ResponseEntity<>(
                new RestResponse<>(service.updateToCompletedTicketHistory(ticketHistory, employeeManager),
                        "Berhasil melakukan COMPLETED Ticket",
                        "201"),
                HttpStatus.CREATED);
    }
}

package co.id.Itc25.Ticketing.contollers;

import co.id.Itc25.Ticketing.dtos.RestResponse;
import co.id.Itc25.Ticketing.dtos.ticket.*;
import co.id.Itc25.Ticketing.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {

    private TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<TicketHeaderDto>>> findAllTicket(){
        return new ResponseEntity<>(
                new RestResponse<>(service.findAllTicket(),
                        "Ticket berhasil ditemukan",
                        "200"),
                HttpStatus.OK);
    }

    @GetMapping("ticket-by-year")
    public ResponseEntity<RestResponse<List<TicketHeaderDto>>> findAllTicketByYear(@RequestBody TicketYearRequestDto year){
        return new ResponseEntity<>(
                new RestResponse<>(service.findAllTicketByYear(year.getYear()),
                        "Berhasil ditemukan",
                        "200"),
                HttpStatus.OK);
    }

    @GetMapping("ticket-by-status")
    public ResponseEntity<RestResponse<List<TicketHeaderDto>>> findAllTicketByStatus(@RequestBody TicketStatusRequestDto status){
        return new ResponseEntity<>(
                new RestResponse<>(service.findAllTicketByStatus(status.getStatus()),
                        "Berhasil ditemukan",
                        "200"),
                HttpStatus.OK);
    }

    @GetMapping("ticket-orderby-urgency")
    public ResponseEntity<RestResponse<List<TicketHeaderDto>>> findAllTicketOrderByUrgency(){
        return new ResponseEntity<>(
                new RestResponse<>(service.findAllTicketOrderByUrgency(),
                        "Berhasil ditemukan",
                        "200"),
                HttpStatus.OK);
    }

    @GetMapping("ticket-request")
    public ResponseEntity<RestResponse<List<TicketHeaderDto>>> findALlTicketByRequest(@RequestBody TicketRequestDto request){
        return new ResponseEntity<>(
                new RestResponse<>(service.findAllTickerByRequest(request.getYear(), request.getStatus()),
                        "Berhasil ditemukan",
                        "200"),
                HttpStatus.OK);
    }

    @PostMapping("insert-ticket")
    public ResponseEntity<RestResponse<TicketInsertResponseDto>> insertTicket(Authentication authentication, @RequestBody TicketInsertDto ticket){
        String employeeAdmin = authentication.getName();
        return new ResponseEntity<>(
                new RestResponse<>(service.insertTicket(ticket, employeeAdmin, ticket.getAppointedTo()),
                        "Berhasil menambahkan ticket baru",
                        "201"),
                HttpStatus.CREATED);
    }

    @PutMapping("cancel-ticket")
    public ResponseEntity<RestResponse<TicketUpdateResponseDto>> cancelledTicket(@RequestBody TicketUpdateDto ticket){
        return new ResponseEntity<>(
                new RestResponse<>(service.updateToCancelledTicket(ticket),
                        "Berhasil melakukan cancel ticket",
                        "201"),
                HttpStatus.CREATED);
    }

}

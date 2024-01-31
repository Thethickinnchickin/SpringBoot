package org.springboardLogin.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // Endpoint to retrieve admin information
    @GetMapping
    public ResponseEntity<?> getAdmin() {
        // Log information about accessing admin endpoint
        System.out.println("Accessing admin endpoint");

        // Return a response indicating successful retrieval of admin information
        return ResponseEntity.ok("Got Admin");
    }
}

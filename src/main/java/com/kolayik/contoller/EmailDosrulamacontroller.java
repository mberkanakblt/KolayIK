package com.kolayik.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email-verification")
@CrossOrigin("*")
public class EmailDosrulamacontroller {
    /**
     * https://www.muhammethoca.com/api/email-verification/EA43-ASE43-
     * @param code
     * @return
     */
    @GetMapping("/{code}")
    public ResponseEntity<Void> emailVerify(@PathVariable String code){
        return ResponseEntity.ok().build();
    }
}

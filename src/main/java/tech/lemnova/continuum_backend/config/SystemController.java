/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tech.lemnova.continuum_backend.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lemxvos
 */
@RestController
public class SystemController {

    @GetMapping("/")
    public String home() {
        return """
        <html>
            <head>
                <title>Continuum API</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body>
                 <p1>The API is alive!. Everything is okay :)</p1><br>
                 <a href="https://github.com/lemnova/continuum-backend">For more details click here!</a>
            </body>
        </html>
        """;
    }

    @RequestMapping(value = "/health",
            method = {RequestMethod.GET,
            RequestMethod.HEAD})
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}

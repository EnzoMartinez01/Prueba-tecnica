package com.technicaltest.mantenimientos.Controllers;

import com.technicaltest.mantenimientos.Dto.EntidadDto;
import com.technicaltest.mantenimientos.Models.Entidad;
import com.technicaltest.mantenimientos.Services.EntidadServices;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/entidades")
public class EntidadController {
    private final EntidadServices entidadServices;

    public EntidadController(EntidadServices entidadServices) {
        this.entidadServices = entidadServices;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<EntidadDto>> getAll(
            @RequestParam(required = false) Integer documentType,
            @RequestParam(required = false) Integer contributorType,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<EntidadDto> entidades = entidadServices.findAllFiltered(
                    documentType, contributorType, active, searchTerm, page, size
            );
            return ResponseEntity.ok(entidades);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<EntidadDto> getEntidadById(@RequestParam int id) {
        try {
            EntidadDto entidad = entidadServices.findById(id);
            return ResponseEntity.ok(entidad);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/saveEntidad")
    public ResponseEntity<Map<String, String>> saveEntidad(@RequestBody Entidad entidad) {
        try {
            Entidad savedEntidad = entidadServices.saveEntidad(entidad);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Entidad registrado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body( response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateEntidad/{id}")
    public ResponseEntity<Map<String, String>> updateEntidad(@PathVariable Integer id, @RequestBody Entidad entidad) {
        try {
            Entidad updatedEntidad = entidadServices.updateEntidad(id, entidad);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Entidad: " + entidad.getId() + " actualizado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteEntidad/{id}")
    public ResponseEntity<Map<String, String>> deleteEntidad(@PathVariable int id) {
        try {
            entidadServices.deleteEntidad(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Entidad " + id + " eliminado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

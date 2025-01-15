package com.technicaltest.mantenimientos.Controllers;

import com.technicaltest.mantenimientos.Models.ContributorType;
import com.technicaltest.mantenimientos.Services.ContributorTypeServices;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/contributor-type")
public class ContributorTypeController {
    private final ContributorTypeServices contributorTypeServices;

    public ContributorTypeController(ContributorTypeServices contributorTypeServices) {
        this.contributorTypeServices = contributorTypeServices;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<ContributorType>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ContributorType> contributorTypes = contributorTypeServices.findAll(page, size);
            return ResponseEntity.ok(contributorTypes);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ContributorType> getContributorId(@PathVariable Integer id) {
        try {
            ContributorType contributorType = contributorTypeServices.findById(id);
            return ResponseEntity.ok(contributorType);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/saveContributor")
    public ResponseEntity<Map<String, String>> createContributor(@RequestBody ContributorType contributorType) {
        try {
            ContributorType savedContributorType = contributorTypeServices.saveContributorType(contributorType);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tipo de Contribuidor creado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateContributorType/{id}")
    public ResponseEntity<Map<String, String>> updateContributorType(@RequestBody ContributorType contributorType, @PathVariable Integer id) {
        try {
            ContributorType updatedContributorType = contributorTypeServices.updateContributorType(id,contributorType);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contribuidor " + contributorType.getName() + " actualizado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteContributor/{id}")
    public ResponseEntity<Map<String, String>> deleteContributor(@PathVariable Integer id) {
        try {
            contributorTypeServices.deleteContributorType(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contribuidor " + id + " eliminado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

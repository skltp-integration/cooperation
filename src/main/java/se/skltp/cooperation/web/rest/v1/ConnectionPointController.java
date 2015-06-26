package se.skltp.cooperation.web.rest.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ConnectionPoint.
 */
@RestController
@RequestMapping("/v1/connectionPoints")
public class ConnectionPointController {

    private final Logger log = LoggerFactory.getLogger(ConnectionPointController.class);

    @Autowired
    private ConnectionPointRepository connectionPointRepository;

    /**
     * GET  /connectionPoints -> get all the connectionPoints.
     */
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConnectionPoint> getAll() {
        log.debug("REST request to get all ConnectionPoints");
        return connectionPointRepository.findAll();
    }

    /**
     * GET  /connectionPoints/:id -> get the "id" connectionPoint.
     */
    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConnectionPoint> get(@PathVariable Long id) {
        log.debug("REST request to get ConnectionPoint : {}", id);
        return Optional.ofNullable(connectionPointRepository.findOne(id))
            .map(connectionPoint -> new ResponseEntity<>(
                connectionPoint,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

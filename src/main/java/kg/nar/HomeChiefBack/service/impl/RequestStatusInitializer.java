package kg.nar.HomeChiefBack.service.impl;

import jakarta.annotation.PostConstruct;
import kg.nar.HomeChiefBack.entity.RequestStatus;
import kg.nar.HomeChiefBack.repository.RequestStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RequestStatusInitializer {

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @PostConstruct
    public void init() {
        if (requestStatusRepository.count() == 0) {
            requestStatusRepository.save(new RequestStatus(UUID.randomUUID(), "not verified"));
            requestStatusRepository.save(new RequestStatus(UUID.randomUUID(), "requested"));
            requestStatusRepository.save(new RequestStatus(UUID.randomUUID(), "not accepted"));
            requestStatusRepository.save(new RequestStatus(UUID.randomUUID(), "accepted"));
            requestStatusRepository.save(new RequestStatus(UUID.randomUUID(), "blocked"));
            System.out.println("Initial data for RequestStatus inserted.");
        }
    }
}

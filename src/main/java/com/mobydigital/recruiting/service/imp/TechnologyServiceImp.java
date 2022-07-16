package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.repository.TechnologyRepository;
import com.mobydigital.recruiting.service.TechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TechnologyServiceImp implements TechnologyService {
    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String createTechnology(TechnologyDto request) {
        try {
            log.info("Technology will be created");
            List<Technology> technologyList = technologyRepository.findAll();
            if (technologyList.stream().anyMatch(technology -> technology.getName().equals(request.getName()) && technology.getVersion().equals(request.getVersion()))) {
                throw new DataAlreadyExistException("This Technology already exist");
            }
            Technology technology = modelMapper.map(request, Technology.class);
            log.info("Technology will be saved in the Data Base");
            technologyRepository.save(technology);
            log.info("Successfully Saved Technology");
            return "Successfully Saved Technology";
        } catch (DataAlreadyExistException e) {
            log.error("This Technology already exist", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String deleteTechnologyById(Long id) {
        try {
            log.info("Technology Id: " + id + " will be deleted");
            Optional<Technology> technology = technologyRepository.findById(id);
            if (!technology.isPresent()) {
                throw new NotFoundException("Technology " + id + " not found");
            }
            log.info("Technology will be deleted in the Data Base");
            technologyRepository.delete(technology.get());
            return "Successfully deleted Technology";
        } catch (NotFoundException e) {
            log.error("Technology " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String updateTechnology(Long id, TechnologyDto request) {
        try {
            log.info("Technology id: " + id + " will be updated");
            Optional<Technology> technology = technologyRepository.findById(id);
            if (!technology.isPresent()) {
                throw new NotFoundException("Technology id: " + id + " not found");
            }
            technology.get().setName(request.getName());
            technology.get().setVersion(request.getVersion());
            log.info("Technology will be saved in the Data Base");
            technologyRepository.save(technology.get());
            log.info("Successfully updated Technology");
            return "Successfully updated Technology";
        } catch (NotFoundException e) {
            log.error("Technology id: " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<TechnologyDto> getAllTechnologies() {
        log.info("All Technologies will be searched");
        List<Technology> technologyList = technologyRepository.findAll();
        List<TechnologyDto> technologyDtoList = new ArrayList<>();
        for (Technology technology : technologyList) {
            log.info("Technology id: " + technology.getId() + " is being added to the list");
            technologyDtoList.add(modelMapper.map(technology, TechnologyDto.class));
        }
        log.info("Technologies searched successfully");
        return technologyDtoList;
    }

    @Override
    public TechnologyDto getTechnologyById(Long id) {
        try {
            log.info("Technology id: " + id + " will be searched");
            Optional<Technology> technology = technologyRepository.findById(id);
            if (!technology.isPresent()) {
                throw new NotFoundException("Technology " + id + " not found");
            }
            TechnologyDto technologyDto = modelMapper.map(technology.get(), TechnologyDto.class);
            log.info("Technology searched successfully");
            return technologyDto;
        } catch (NotFoundException e) {
            log.error("Technology " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Technology returnTechnologyById(Long id) {
        try {
            log.info("Technology id: " + id + " will be searched");
            Optional<Technology> technology = technologyRepository.findById(id);
            if (!technology.isPresent()) {
                throw new NotFoundException("Technology " + id + " not found");
            }
            log.info("Technology searched successfully");
            return technology.get();
        } catch (NotFoundException e) {
            log.error("Technology " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}

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

import static com.mobydigital.recruiting.utils.Constant.ADDED_TO_THE_LIST;
import static com.mobydigital.recruiting.utils.Constant.ALREADY_EXIST;
import static com.mobydigital.recruiting.utils.Constant.ID_EQUAL_TO;
import static com.mobydigital.recruiting.utils.Constant.NOT_FOUND;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_SAVED;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_SEARCHED;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_UPDATED;
import static com.mobydigital.recruiting.utils.Constant.TECHNOLOGIES;
import static com.mobydigital.recruiting.utils.Constant.TECHNOLOGY;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_CREATED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_DELETED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_DELETED_IN_THE_DATA_BASE;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_SAVED_IN_THE_DATA_BASE;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_SEARCHED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_UPDATED;

@Slf4j
@Service
public class TechnologyServiceImp implements TechnologyService {

    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createTechnology(TechnologyDto request) {
        try {
            log.info(TECHNOLOGY + WILL_BE_CREATED);
            List<Technology> technologyList = technologyRepository.findAll();
            if (technologyList.stream().anyMatch(technology -> technology.getName().equals(request.getName()) && technology.getVersion().equals(request.getVersion()))) {
                throw new DataAlreadyExistException(ALREADY_EXIST);
            }
            Technology technology = modelMapper.map(request, Technology.class);
            log.info(TECHNOLOGY + WILL_BE_SAVED_IN_THE_DATA_BASE);
            technologyRepository.save(technology);
            log.info(SUCCESSFULLY_SAVED + TECHNOLOGY);
        } catch (DataAlreadyExistException e) {
            log.error(TECHNOLOGY + ALREADY_EXIST, e);
            throw new DataAlreadyExistException(e.getMessage());
        }
    }

    @Override
    public void deleteTechnologyById(Long id) {
        try {
            log.info(TECHNOLOGY + ID_EQUAL_TO + id + WILL_BE_DELETED);
            Optional<Technology> technology = technologyRepository.findById(id);
            if (technology.isEmpty()) {
                throw new NotFoundException(TECHNOLOGY + id + NOT_FOUND);
            }
            log.info(TECHNOLOGY + WILL_BE_DELETED_IN_THE_DATA_BASE);
            technologyRepository.delete(technology.get());
        } catch (NotFoundException e) {
            log.error(TECHNOLOGY + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void updateTechnology(Long id, TechnologyDto request) {
        try {
            log.info(ID_EQUAL_TO + id + WILL_BE_UPDATED);
            Optional<Technology> technology = technologyRepository.findById(id);
            if (technology.isEmpty()) {
                throw new NotFoundException(ID_EQUAL_TO + id + NOT_FOUND);
            }
            technology.get().setName(request.getName());
            technology.get().setVersion(request.getVersion());
            log.info(TECHNOLOGY + WILL_BE_SAVED_IN_THE_DATA_BASE);
            technologyRepository.save(technology.get());
            log.info(SUCCESSFULLY_UPDATED + TECHNOLOGY);
        } catch (NotFoundException e) {
            log.error(TECHNOLOGY + ID_EQUAL_TO + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public List<TechnologyDto> getAllTechnologies() {
        log.info(TECHNOLOGIES + WILL_BE_SEARCHED);
        List<Technology> technologyList = technologyRepository.findAll();
        List<TechnologyDto> technologyDtoList = new ArrayList<>();
        technologyList.forEach(technology -> {
            log.info(TECHNOLOGY + ID_EQUAL_TO + technology.getId() + ADDED_TO_THE_LIST);
            technologyDtoList.add(modelMapper.map(technology, TechnologyDto.class));
        });
        log.info(SUCCESSFULLY_SEARCHED + TECHNOLOGIES);
        return technologyDtoList;
    }

    @Override
    public TechnologyDto getTechnologyById(Long id) {
        try {
            log.info(TECHNOLOGY + ID_EQUAL_TO + id + WILL_BE_SEARCHED);
            Optional<Technology> technology = technologyRepository.findById(id);
            if (technology.isEmpty()) {
                throw new NotFoundException(TECHNOLOGY + id + NOT_FOUND);
            }
            log.info(SUCCESSFULLY_SEARCHED + TECHNOLOGY);
            return modelMapper.map(technology.get(), TechnologyDto.class);
        } catch (NotFoundException e) {
            log.error(TECHNOLOGY + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Technology returnTechnologyById(Long id) {
        try {
            log.info(TECHNOLOGY + ID_EQUAL_TO + id + WILL_BE_SEARCHED);
            Optional<Technology> technology = technologyRepository.findById(id);
            if (technology.isEmpty()) {
                throw new NotFoundException(TECHNOLOGY + id + NOT_FOUND);
            }
            log.info(SUCCESSFULLY_SEARCHED + TECHNOLOGY);
            return technology.get();
        } catch (NotFoundException e) {
            log.error(TECHNOLOGY + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }
}

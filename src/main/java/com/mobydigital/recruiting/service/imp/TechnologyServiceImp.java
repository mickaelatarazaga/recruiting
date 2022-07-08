package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.repository.TechnologyRepository;
import com.mobydigital.recruiting.service.TechnologyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TechnologyServiceImp implements TechnologyService {
    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String createTechnology(TechnologyDto request) throws DataAlreadyExistException {
        List<Technology> technologyList = technologyRepository.findAll();
        if (technologyList.stream().anyMatch(technology -> technology.getName().equals(request.getName()) && technology.getVersion().equals(request.getVersion()))) {
            throw new DataAlreadyExistException("This Technology already exist");
        }
        Technology technology = modelMapper.map(request, Technology.class);
        technologyRepository.save(technology);
        return "Successfully Saved Technology";
    }

    @Override
    public String deleteTechnologyById(Long id) throws NotFoundException {
        Optional<Technology> technology = technologyRepository.findById(id);
        if (!technology.isPresent()) {
            throw new NotFoundException("Technology " + id + " not found");
        }
        technologyRepository.delete(technology.get());
        return "Successfully deleted Technology";
    }

    @Override
    public String updateTechnology(Long id, TechnologyDto request) throws NotFoundException {
        Optional<Technology> technology = technologyRepository.findById(id);
        if (!technology.isPresent()) {
            throw new NotFoundException("Technology not found");
        }
        technology.get().setName(request.getName());
        technology.get().setVersion(request.getVersion());
        technologyRepository.save(technology.get());
        return "Successfully updated Technology";
    }

    @Override
    public List<TechnologyDto> getAllTechnologies() {
        List<Technology> technologyList = technologyRepository.findAll();
        List<TechnologyDto> technologyDtoList = new ArrayList<>();
        for (Technology technology : technologyList) {
            technologyDtoList.add(modelMapper.map(technology, TechnologyDto.class));
        }
        return technologyDtoList;
    }

    @Override
    public TechnologyDto getTechnologyById(Long id) throws NotFoundException {
        Optional<Technology> technology = technologyRepository.findById(id);
        if (!technology.isPresent()) {
            throw new NotFoundException("Technology " + id + " not found");
        }
        TechnologyDto technologyDto = modelMapper.map(technology.get(), TechnologyDto.class);
        return technologyDto;
    }

    @Override
    public Technology returnTechnologyById(Long id) throws NotFoundException {
        Optional<Technology> technology = technologyRepository.findById(id);
        if (!technology.isPresent()) {
            throw new NotFoundException("Technology " + id + " not found");
        }
        return technology.get();
    }
}

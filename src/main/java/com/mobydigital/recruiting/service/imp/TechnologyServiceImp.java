package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.model.dto.TechnologyRequest;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.repository.TechnologyRepository;
import com.mobydigital.recruiting.service.TechnologyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyServiceImp implements TechnologyService {
    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String createTechnology(TechnologyRequest request) throws DataAlreadyExistException {
        List<Technology> technologyList = technologyRepository.findAll();
        if (technologyList.stream().anyMatch(candidate -> candidate.getName().equals(request.getName()) && candidate.getVersion().equals(request.getVersion()))) {
            throw new DataAlreadyExistException("This Technology already exist");
        }
        Technology technology = modelMapper.map(request, Technology.class);
        technologyRepository.save(technology);
        return "Successfully Saved Technology";
    }
}

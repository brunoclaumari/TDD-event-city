package com.devsuperior.bds02.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exception.DatabaseException;
import com.devsuperior.bds02.services.exception.ResourceNotFoundException;

@Service
public class CityService {
	
	@Autowired
	private CityRepository repository;
	
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll(){
		
//		List<DepartmentDTO> list = repository.findAll()
//				.stream()
//				.map(x -> new DepartmentDTO(x))
//				.sorted((d1,d2) -> d1.getName().compareTo(d2.getName()))
//				.toList();
		List<CityDTO> list = repository.findAll(Sort.by("name"))
				.stream()
				.map(x -> new CityDTO(x))				
				.toList();
		
		return list;
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		
		return new CityDTO(entity);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {//lançada na camada repository	
			//exception abaixo da camada de service
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e) {	//lançada na camada repository	
			//exception abaixo da camada de service
			throw new DatabaseException("Integrity Violation!");
		}
		
	}

}

package com.devsuperior.bds02.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exception.DatabaseException;
import com.devsuperior.bds02.services.exception.ResourceNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	
	
//	@Transactional(readOnly = true)
//	public List<EventDTO> findAll(){		
//
//		List<EventDTO> list = repository.findAll(Sort.by("name"))
//				.stream()
//				.map(x -> new EventDTO(x))				
//				.toList();
//		
//		return list;
//	}
	
//	@Transactional
//	public EventDTO insert(EventDTO dto) {
//		Event entity = new Event();
//		entity.setName(dto.getName());
//		entity.setDate(dto.getDate());
//		entity.setUrl(dto.getUrl());
//		entity.setCity(new City(dto.getCityId(), null));
//		
//		entity = repository.save(entity);
//		
//		return new EventDTO(entity);
//	}
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto){
		EventDTO eventDTO = null;
		try {
			Event entity = repository.getOne(id);
			//System.out.println("Teste entity: "+ entity);
			entity = dtoToEvent(id, dto);
			entity = repository.save(entity);		
			eventDTO =  new EventDTO(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: "+ id);
			
		} catch (JpaObjectRetrievalFailureException e) {
			throw new ResourceNotFoundException("Id not found: "+ id);	
			
		}
		
		return eventDTO;		
	}
	
	public Event dtoToEvent(Long id, EventDTO dto) {
		
		Event event = new Event(
				id, 
				dto.getName(), 
				dto.getDate(), 
				dto.getUrl(), 
				new City(dto.getCityId(), null));
		
		return event;
	}



}

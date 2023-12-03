package com.btc.news.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btc.news.model.News;
import com.btc.news.respository.NewsRepository;

@RestController
@RequestMapping(value = "/api")
public class NewsRestApi {

	// log 4j
	private static final Logger logger = LogManager.getLogger(NewsRestApi.class);
	
	@Autowired
	NewsRepository newsRepository;

	@GetMapping(value = "/")
	public String get() {
		return "";
	}

	/**
	 * getUserById
	 * 
	 * @return
	 */
	@GetMapping(value = "/news/{id}")
	public ResponseEntity<News> getNewsById(@PathVariable Integer id) {
		logger.info("Start method getUserById with id: "+id);
		Optional<News> newsData = newsRepository.findById(id);
		logger.info("End method getUserById");
		if (newsData.isPresent()) {
			return new ResponseEntity<>(newsData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@PostMapping(value = "/news")
	public ResponseEntity<News> createNews(@RequestBody News newsDto) {
		logger.info("Start method createNews");
		logger.info("Info request body: "+ newsDto.toString());
		try {
			News newsData = newsRepository
					.save(new News( newsDto.getTitle(), newsDto.getContent(), newsDto.getUserCreateId()));
			logger.info("End method createNews");
			return new ResponseEntity<>(newsData, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Message: "+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/news/usercreate/{id}")
	public ResponseEntity<List<News>> findByUserCreateId(@PathVariable Integer id){
		logger.info("Start method findByUserCreateId with id ="+id);
		List<News> newsData = newsRepository.findByUserCreateId(id);
		logger.info("End method findByUserCreateId");
		return new ResponseEntity<>(newsData, HttpStatus.OK);
	}
}

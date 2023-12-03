package com.btc.user.controller;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.btc.user.dto.NewsDto;
import com.btc.user.model.AppUser;
import com.btc.user.respository.UserRespository;

@RestController
@RequestMapping(value = "/api")
public class UserRestApi {
	
	// log 4j
	private static final Logger logger = LogManager.getLogger(UserRestApi.class);

	@Autowired
	private UserRespository userRespository;

	@Autowired
	private WebClient webClient;

	/**
	 * getUserById
	 * 
	 * @return
	 */
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<AppUser> getUserById(@PathVariable Integer id) {
		Optional<AppUser> userData = userRespository.findById(id);

		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/users")
	public ResponseEntity<AppUser> createUsers(@RequestBody AppUser userDto) {
		try {
			AppUser user = userRespository.save(
					new AppUser(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getEmail()));
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("ERROR create user " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/users/news/usercreate/{id}")
	public ResponseEntity<List<NewsDto>> findNewsByIdUserCreate(@PathVariable String id) {
		try {
			logger.info("Start method: findNewsByIdUserCreate");
			logger.info("Parameters: "+id);
			List<NewsDto> newsData = webClient
					.get()
					.uri("/api/news/usercreate/" + id)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToFlux(NewsDto.class)
					.collectList()
					.block();
			logger.info("End method: findNewsByIdUserCreate");
			return new ResponseEntity<>(newsData, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Message: "+ e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

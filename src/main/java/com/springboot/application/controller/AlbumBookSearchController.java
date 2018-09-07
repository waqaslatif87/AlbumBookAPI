
package com.springboot.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.application.model.APIResponseItem;
import com.springboot.application.service.IAlbumBookService;

/**
 * This Rest Controller exposed the endpoint for Searching Albums and Books.
 * 
 * @author Waqas
 *
 */

@RestController
@RequestMapping("/api")
public class AlbumBookSearchController {

	@Autowired
	IAlbumBookService albumBookService;

	/**
	 * This Endpoint accept a request with text parameter on input. It will return
	 * maximum of 5 books and maximum of 5 albums that are related to the input
	 * term. The response elements will only contain title, authors(/artists) and
	 * information whether it's a book or an album.
	 * 
	 * @return {@link List} of all available {@link APIResponseItem}
	 * @throws Exception
	 */

	@GetMapping("/list")
	private List<APIResponseItem> searchByParam(@RequestParam String term) throws Exception {
		return albumBookService.findByTerm(term);

	}
}

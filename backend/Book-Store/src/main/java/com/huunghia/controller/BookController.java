package com.huunghia.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huunghia.entity.Book;
import com.huunghia.service.BookService;

@RestController

public class BookController {
	
	@Autowired
	private BookService bookService;

	@PostMapping("admin/addBook")
	public Book addBook(@RequestBody Book book) {
		return bookService.save(book);
	}
	
	@PostMapping("admin/addImageBook")
	public ResponseEntity<String> upload(@RequestParam("id") Long id, HttpServletRequest req, HttpServletResponse resp){
		try {

			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) req;
			Iterator<String> it = multipartHttpServletRequest.getFileNames();
			MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());
			String fileName = id + ".png";
			
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
					new File("src/main/resources/static/image/book/"+fileName))); //luu hinh anh vao thu muc image/book
			stream.write(bytes);
			stream.close();
			
			return new ResponseEntity<String>("upload hinh anh thanh cong", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("upload hinh anh that bai", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("admin/updateImageBook")
	public ResponseEntity<String> updateImage(@RequestParam("id") Long id, HttpServletRequest req, HttpServletResponse resp){
		try {

			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) req;
			Iterator<String> it = multipartHttpServletRequest.getFileNames();
			MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());
			String fileName = id + ".png";
			
			Files.delete(Paths.get("src/main/resources/static/image/book/"+fileName)); //xoa anh cu
			
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
					new File("src/main/resources/static/image/book/"+fileName)));
			stream.write(bytes);
			stream.close();
			
			return new ResponseEntity<String>("upload hinh anh thanh cong", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("upload hinh anh that bai", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("book/bookList")
	public List<Book> getBookList(){
		return bookService.findAll();
	}
	
	@GetMapping("book/{id}")
	public Book getBook(@PathVariable("id") Long id) {
		Book book = bookService.findOne(id);
		return book;
	}
	
	@PutMapping("admin/updateBook")
	public Book updateBook(@RequestBody Book book) {
		return bookService.save(book);
	}
	
	@DeleteMapping("admin/removeBook/{id}")
	public ResponseEntity<String> remove(@PathVariable String id) throws IOException {
		bookService.removeOne(Long.parseLong(id));
		String fileName = id + ".png";
		
		Files.delete(Paths.get("src/main/resources/static/image/book/"+fileName)); //xoa anh
		return new ResponseEntity<String>("xoa thanh cong", HttpStatus.OK);
	}
	
	@PostMapping("book/searchBook")
	public List<Book> searchBook (@RequestBody String keyword) {
		List<Book> bookList = bookService.blurrySearch(keyword);
		
		return bookList;
	}
}

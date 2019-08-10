package com.huunghia.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huunghia.entity.Book;
import com.huunghia.repository.BookRepository;
import com.huunghia.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> findAll() {
		List<Book> books = bookRepository.findAll();

		//tra ve list sach duoc active
		List<Book> activeBooks = new ArrayList<Book>();

		for (Book book : books) {
			if (book.isActive()) {
				activeBooks.add(book);
			}
		}

		return activeBooks;
	}

	@Override
	public Book findOne(Long id) {
		return bookRepository.findById(id).get();
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public List<Book> blurrySearch(String keyword) {
		List<Book> books = bookRepository.findByTitleContaining(keyword);
		
		//tra ve list sach duoc active
		List<Book> activeBooks = new ArrayList<Book>();

		for (Book book : books) {
			if (book.isActive()) {
				activeBooks.add(book);
			}
		}

		return activeBooks;
	}

	@Override
	public void removeOne(Long id) {
		bookRepository.deleteById(id);
	}

}
